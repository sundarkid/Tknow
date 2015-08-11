package in.trydevs.tknow.tknow.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import in.trydevs.tknow.tknow.DataClasses.People;
import in.trydevs.tknow.tknow.R;

/**
 * Created by Sundareswaran on 10-08-2015.
 */
public class MyAdapterPeople extends RecyclerView.Adapter<MyAdapterPeople.MyHolder> {


    AppCompatActivity context;
    List<People> data;
    LayoutInflater inflater;

    public MyAdapterPeople(AppCompatActivity context, List<People> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row_people, parent, false);
        return (new MyHolder(view));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final People current = data.get(position);
        holder.name.setText(current.getName());
        holder.fb.setVisibility(View.VISIBLE);
        holder.twitter.setVisibility(View.VISIBLE);
        if (!current.getImage().equalsIgnoreCase(""))
            Glide.with(context)
                    .load(current.getImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .into(holder.photo);
        if (!current.getFacebook().equalsIgnoreCase(""))
            holder.fb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(current.getFacebook()));
                    context.startActivity(i);
                }
            });
        else
            holder.fb.setVisibility(View.GONE);
        if (!current.getTwitter().equalsIgnoreCase(""))
            holder.twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(current.getTwitter()));
                    context.startActivity(i);
                }
            });
        else
            holder.twitter.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView photo, fb, twitter;
        TextView name;

        public MyHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.image);
            fb = (ImageView) itemView.findViewById(R.id.facebook);
            twitter = (ImageView) itemView.findViewById(R.id.twitter);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
