package in.trydevs.tknow.tknow.Network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import in.trydevs.tknow.tknow.extras.MyApplication;

public class VolleySingleton {
    private static VolleySingleton mInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader imageLoader;

    private VolleySingleton() {
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());

        /* Image Loader for Caching Images
        This can be used while down streaming images temporarily
        This caches images and returns the cache when the same url is passed
        Thus helpful in avoiding recurring downloads of same images
         */
        imageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> cache = new LruCache<>((int) (Runtime.getRuntime().maxMemory() / 1024) / 8);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static VolleySingleton getInstance() {
        if (mInstance == null)
            mInstance = new VolleySingleton();
        return mInstance;
    }

    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
