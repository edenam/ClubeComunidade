package eden.com.br.clubecomunidade.parse;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import eden.com.br.clubecomunidade.bean.News;
import eden.com.br.clubecomunidade.utils.TagName;

public class ParseReader {

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
}