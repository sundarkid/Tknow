package in.trydevs.tknow.tknow.extras;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by Sundareswaran on 09-08-2015.
 */
public class GlideConfiguration implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(MyApplication.getAppContext(), DiskLruCacheFactory.DEFAULT_DISK_CACHE_DIR, DiskLruCacheFactory.DEFAULT_DISK_CACHE_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
