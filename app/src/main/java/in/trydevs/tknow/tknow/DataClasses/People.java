package in.trydevs.tknow.tknow.DataClasses;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sundareswaran on 10-08-2015.
 */
public class People implements Parcelable {
    public static final Parcelable.Creator<People> CREATOR = new Parcelable.Creator<People>() {
        public People createFromParcel(Parcel source) {
            return new People(source);
        }

        public People[] newArray(int size) {
            return new People[size];
        }
    };
    String name, about, facebook, twitter, image;

    public People() {
        name = "";
        about = "";
        facebook = "";
        twitter = "";
        image = "";
    }

    protected People(Parcel in) {
        this.name = in.readString();
        this.about = in.readString();
        this.facebook = in.readString();
        this.twitter = in.readString();
        this.image = in.readString();
    }

    public static People getPeopleFromJson(JSONObject object) {
        People people = new People();
        try {
            if (object.has(UrlLinkNames.getJsonName()))
                people.setName(object.getString(UrlLinkNames.getJsonName()));
            if (object.has(UrlLinkNames.getJsonAbout()))
                people.setAbout(object.getString(UrlLinkNames.getJsonAbout()));
            if (object.has(UrlLinkNames.getJsonImage()))
                people.setImage(object.getString(UrlLinkNames.getJsonImage()));
            if (object.has(UrlLinkNames.getJsonFacebookUrl()))
                people.setFacebook(object.getString(UrlLinkNames.getJsonFacebookUrl()));
            if (object.has(UrlLinkNames.getJsonTwitterUrl()))
                people.setTwitter(object.getString(UrlLinkNames.getJsonTwitterUrl()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return people;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.about);
        dest.writeString(this.facebook);
        dest.writeString(this.twitter);
        dest.writeString(this.image);
    }
}
