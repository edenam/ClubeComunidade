package eden.com.br.clubecomunidade.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable {

    // Obligated attributes
    private int id;
    private String headLine;

    // Non-obligated attributes
    private String imageUrl = null;
    private String text = null;
    private String source = null;
    private Boolean isVisible = false;

    public News(){
        super();
    }

    private News(Parcel in){
        super();

        this.id = in.readInt();
        this.headLine = in.readString();
        this.imageUrl = in.readString();
        this.text = in.readString();
        this.source = in.readString();
        this.isVisible = in.readByte() != 0;

    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        public News[] newArray(int size) {
            return new News[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(getId());
        dest.writeString(getHeadLine());
        dest.writeString(getImageUrl());
        dest.writeString(getText());
        dest.writeString(getSource());
        dest.writeByte((byte) (getIsVisible() ? 1 : 0));
    }

    public int getId() {
        return id;
    }
    public void setId(int id) { this.id = id; }
    public String getHeadLine() {
        return headLine;
    }
    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getSource() { return source; }
    public void setSource(String source){ this.source = source; }
    public Boolean getIsVisible() { return isVisible; }
    public void setIsVisible(Boolean isVisible) { this.isVisible = isVisible; }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        News other = (News) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + headLine + ", imageUrl="
                + imageUrl + "]";
    }
}
