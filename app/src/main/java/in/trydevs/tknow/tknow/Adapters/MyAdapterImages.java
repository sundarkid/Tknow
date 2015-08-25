package in.trydevs.tknow.tknow.Adapters;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import in.trydevs.tknow.tknow.DataClasses.Image;
import in.trydevs.tknow.tknow.R;
import in.trydevs.tknow.tknow.extras.BlurImageView;

/**
 * Created by Sundareswaran on 26-08-2015.
 */
public class MyAdapterImages extends RecyclerView.Adapter<MyAdapterImages.MyHolder> {

    List<Image> images;
    FragmentActivity context;
    LayoutInflater inflater;

    public MyAdapterImages(FragmentActivity context, List<Image> images) {
        if (images.size() > 0)
            this.images.addAll(images);
        else
            this.images = Collections.emptyList();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    protected class MyHolder extends RecyclerView.ViewHolder {

        BlurImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);
            imageView = (BlurImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
