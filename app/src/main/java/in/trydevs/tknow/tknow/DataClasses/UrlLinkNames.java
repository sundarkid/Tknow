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
}
