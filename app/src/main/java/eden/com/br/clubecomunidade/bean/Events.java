package eden.com.br.clubecomunidade.bean;

import java.util.Date;

/**
 * Created by root on 25/04/15.
 */
public class Events {

    private String name;
    private String description;
    private Date date;
    private String imageUrl;
    private String place;
    private String price;
    private String obs;

    public Events(String name, String description, Date date, String imageUrl,
                  String place, String price, String obs){


        this.name = name;
        this.description = description;
        this.date = date;
        this.imageUrl = imageUrl;
        this.place = place;
        this.price = price;
        this.obs = obs;
    }

    public Events(String name, Date date, String imageUrl){
        this(name, "", date, imageUrl, "", "", "");
    }

    public Events(){ }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
}
