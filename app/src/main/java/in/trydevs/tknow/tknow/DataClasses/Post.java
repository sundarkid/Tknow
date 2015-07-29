package in.trydevs.tknow.tknow.DataClasses;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sundareswaran on 27-07-2015.
 */
public class Post implements Parcelable {
    private String name, message, date, image, title;
    private long user_id, post_id,sno;

    public long getSno() {
        return sno;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public long getPost_id() {
        return post_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public String getImage() {
        return image;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSno(long sno) {
        this.sno = sno;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPost_id(long post_id) {
        this.post_id = post_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public static Post fromJSON(JSONObject object) {
        Post post = new Post();
        try {
            if (object.has(UrlLinkNames.getJsonPostId()))
                post.setPost_id(object.getLong(UrlLinkNames.getJsonPostId()));
            if (object.has(UrlLinkNames.getJsonUserId()))
                post.setUser_id(object.getLong(UrlLinkNames.getJsonUserId()));
            if (object.has(UrlLinkNames.getJsonName()))
                post.setName(object.getString(UrlLinkNames.getJsonName()));
            if (object.has(UrlLinkNames.getJsonMessage()))
                post.setMessage(object.getString(UrlLinkNames.getJsonMessage()));
            if(object.has(UrlLinkNames.getJsonImage()))
                post.setImage(object.getString(UrlLinkNames.getJsonImage()));
            if (object.has(UrlLinkNames.getJsonDate()))
                post.setDate(object.getString(UrlLinkNames.getJsonDate()));
            if (object.has(UrlLinkNames.getJsonTitle()))
                post.setTitle(object.getString(UrlLinkNames.getJsonTitle()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.message);
        dest.writeString(this.date);
        dest.writeString(this.image);
        dest.writeString(this.title);
        dest.writeLong(this.user_id);
        dest.writeLong(this.post_id);
    }

    public Post() {
        name = "";
        message = "";
        date  = "";
        image = "";
        title = "";
        user_id = 0;
        post_id = 0;
    }

    protected Post(Parcel in) {
        this.name = in.readString();
        this.message = in.readString();
        this.date = in.readString();
        this.image = in.readString();
        this.title = in.readString();
        this.user_id = in.readLong();
        this.post_id = in.readLong();
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
