package eden.com.br.clubecomunidade.parse;

import android.util.Log;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import eden.com.br.clubecomunidade.bean.Events;
import eden.com.br.clubecomunidade.bean.News;
import eden.com.br.clubecomunidade.utils.TagName;

public class ParseReader {

    final static String ERROR_TAG = "ParseReader";

    public static List<News> getNewsForHome(List<ParseObject> parseNews) {

        List<News> returningNews = new ArrayList<News>();
        News news;

        for (int i = 0; i < parseNews.size(); i++) {

            ParseObject newsObj = parseNews.get(i);
            news = new News();

            news.setId(newsObj.getInt(TagName.KEY_ID));
            news.setHeadLine(newsObj.getString(TagName.KEY_NAME));
            news.setImageUrl(newsObj.getParseFile(TagName.KEY_IMAGE_URL).getUrl());

            returningNews.add(news);
        }
        return returningNews;
    }

    public static ArrayList<Events> getEventsForList(List<ParseObject> parseEvents) {

        ArrayList<Events> returningEvents = new ArrayList<Events>();
        Events event;

        for (int i = 0; i < parseEvents.size(); i++) {

            ParseObject eventsObj = parseEvents.get(i);
            event = new Events();

            try {

                if (eventsObj.has(TagName.EVENTS_KEY_NAME))
                    event.setName(eventsObj.getString(TagName.EVENTS_KEY_NAME));

                if (eventsObj.has(TagName.EVENTS_KEY_DESCRIPTION))
                    event.setDescription(eventsObj.getString(TagName.EVENTS_KEY_DESCRIPTION));

                if (eventsObj.has(TagName.EVENTS_KEY_DATE))
                    event.setDate(eventsObj.getDate(TagName.EVENTS_KEY_DATE));

                if (eventsObj.has(TagName.EVENTS_KEY_PICTURE))
                    event.setImageUrl(eventsObj.getParseFile(TagName.EVENTS_KEY_PICTURE).getUrl());

                if (eventsObj.has(TagName.EVENTS_KEY_PLACE))
                    event.setPlace(eventsObj.getString(TagName.EVENTS_KEY_PLACE));

                if (eventsObj.has(TagName.EVENTS_KEY_PRICE))
                    event.setPrice(eventsObj.getString(TagName.EVENTS_KEY_PRICE));

                if (eventsObj.has(TagName.EVENTS_KEY_OBS))
                    event.setObs(eventsObj.getString(TagName.EVENTS_KEY_OBS));

            }catch (IllegalStateException e){

                Log.i(ParseReader.ERROR_TAG, "Field not parsed!");

            }


            returningEvents.add(event);
        }
        return returningEvents;
    }
}