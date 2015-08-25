package in.trydevs.tknow.tknow.DataClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sundareswaran on 26-08-2015.
 */
public class Image implements Parcelable {
    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
    int id;
    String link;

    public Image() {
        id = 0;
        link = "";
    }

    protected Image(Parcel in) {
        this.id = in.readInt();
        this.link = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.link);
    }
}
