package in.trydevs.tknow.tknow.DataClasses;

/**
 * Created by Sundareswaran on 28-07-2015.
 */
public class UrlLinkNames {
    public static String JSON_USER_ID = "user_id";
    public static String JSON_POST_ID = "post_id";
    public static String JSON_MESSAGE = "message";
    public static String JSON_DATE = "date";
    public static String JSON_IMAGE = "image";
    public static String JSON_NAME = "name";
    public static String JSON_TITLE = "title";
    public static String JSON_URL = "url";
    public static String JSON_RESULT = "result";
    public static String JSON_SUCCESS = "success";
    public static String JSON_FAILURE = "failure";

    public static String JSON_POST = "post";
    public static String JSON_PEOPLE = "people";

    public static String JSON_FACEBOOK_URL = "url_fb";
    public static String JSON_TWITTER_URL = "url_tweet";
    public static String JSON_ABOUT = "url_about";

    public static String URL_BASE = "http://techknowlogy.trydevs.in/";
    public static String URL_GCM_REGISTER = "gcm_register.php";

    public static String getUrlBase() {
        return URL_BASE;
    }

    public static String getUrlGcmRegister() {
        return getUrlBase() + URL_GCM_REGISTER;
    }

    public static String getJsonDate() {
        return JSON_DATE;
    }

    public static String getJsonImage() {
        return JSON_IMAGE;
    }

    public static String getJsonMessage() {
        return JSON_MESSAGE;
    }

    public static String getJsonName() {
        return JSON_NAME;
    }

    public static String getJsonPostId() {
        return JSON_POST_ID;
    }

    public static String getJsonUserId() {
        return JSON_USER_ID;
    }

    public static String getJsonTitle() {
        return JSON_TITLE;
    }

    public static String getJsonResult() {
        return JSON_RESULT;
    }

    public static String getJsonSuccess() {
        return JSON_SUCCESS;
    }

    public static String getJsonFailure() {
        return JSON_FAILURE;
    }

    public static String getJsonUrl() {
        return JSON_URL;
    }

    public static String getJsonAbout() {
        return JSON_ABOUT;
    }

    public static String getJsonFacebookUrl() {
        return JSON_FACEBOOK_URL;
    }

    public static String getJsonTwitterUrl() {
        return JSON_TWITTER_URL;
    }

    public static String getJsonPeople() {
        return JSON_PEOPLE;
    }

    public static String getJsonPost() {
        return JSON_POST;
    }
}
