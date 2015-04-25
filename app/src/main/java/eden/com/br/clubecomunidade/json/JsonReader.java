package eden.com.br.clubecomunidade.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eden.com.br.clubecomunidade.bean.News;
import eden.com.br.clubecomunidade.utils.TagName;

public class JsonReader {

    public static List<News> getHome(JSONObject jsonObject)
            throws JSONException {
        List<News> returningNews = new ArrayList<News>();

        JSONArray jsonArray = jsonObject.getJSONArray(TagName.TAG_PRODUCTS);
        News news;
        for (int i = 0; i < jsonArray.length(); i++) {
            news = new News();
            JSONObject productObj = jsonArray.getJSONObject(i);
            news.setId(productObj.getInt(TagName.KEY_ID));
            news.setHeadLine(productObj.getString(TagName.KEY_NAME));
            news.setImageUrl(productObj.getString(TagName.KEY_IMAGE_URL));

            returningNews.add(news);
        }
        return returningNews;
    }
}