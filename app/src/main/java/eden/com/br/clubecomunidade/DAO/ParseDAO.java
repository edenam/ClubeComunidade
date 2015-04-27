package eden.com.br.clubecomunidade.DAO;

import android.content.Context;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eden.com.br.clubecomunidade.bean.Events;
import eden.com.br.clubecomunidade.bean.News;
import eden.com.br.clubecomunidade.utils.TagName;

/**
 * Created by root on 26/04/15.
 */
class ParseDAO {

    final String ERROR_TAG = this.getClass().getSimpleName();

    private String applicationId = "HsR7qv4fXc8HxTw918ZuCkbIvVdxozNCpvIHj4KE";
    private String clientKey = "CUv9wUNhT6WinQeLSOORTA59D59oyo3faGzcU0kN";

    private static ParseDAO ourInstance = new ParseDAO();

    public static ParseDAO getInstance() {
        return ourInstance;
    }

    private ParseDAO() {

    }

    protected void initialize(Context context){

        // Initializa Parse resources
        // Enable Local Datastore.
        Parse.enableLocalDatastore(context);
        // Enable Crash Reporting
        ParseCrashReporting.enable(context);
        // Initialize context
        Parse.initialize(context, applicationId, clientKey);

        return;
    }


    public void getNewsForHomeBanner(int count, final DAOAccessCallback callback){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("News");

        query.whereEqualTo("visible", true)
                .setLimit(count)
                .selectKeys(
                        Arrays.asList("objectId", "headline", "picture")
                )
                .orderByDescending("updatedAt");


        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseNews, ParseException e) {

                List<News> returningNews = new ArrayList<>();
                News news;

                for (int i = 0; i < parseNews.size(); i++) {

                    ParseObject newsObj = parseNews.get(i);
                    news = new News();

                    news.setId(newsObj.getInt(TagName.KEY_ID));
                    news.setHeadLine(newsObj.getString(TagName.KEY_NAME));
                    news.setImageUrl(newsObj.getParseFile(TagName.KEY_IMAGE_URL).getUrl());

                    returningNews.add(news);
                }

                callback.done(returningNews, e);

            }
        });

    }


    public void getEventsWithPics(int qtd, final DAOAccessCallback callback) {

        ArrayList<Events> returningEvents = new ArrayList<Events>();
        Events event;


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");

        //query.whereEqualTo("visible", true);

        query
                .setLimit(qtd)
                .selectKeys(
                        Arrays.asList("objectId", "name", "picture", "date", "place")
                )
                .orderByAscending("date");


        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> parseEvents, ParseException e) {

                ArrayList<Events> returningEvents = new ArrayList<Events>();
                Events event;

                for (int i = 0; i < parseEvents.size(); i++) {

                    ParseObject eventsObj = parseEvents.get(i);
                    event = new Events();

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

                    returningEvents.add(event);
                }

                callback.done(returningEvents, e);

            }
        });
    }



}