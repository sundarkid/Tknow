package in.trydevs.tknow.tknow.Interfaces;

import in.trydevs.tknow.tknow.DataClasses.Post;

/**
 * Created by Sundareswaran on 08-08-2015.
 */
public interface NewMessageReceivedListener {
    void onNewMessageReceived(Post post);
}
