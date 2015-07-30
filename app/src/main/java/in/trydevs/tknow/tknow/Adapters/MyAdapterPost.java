package in.trydevs.tknow.tknow.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import in.trydevs.tknow.tknow.DataClasses.Post;
import in.trydevs.tknow.tknow.R;

/**
 * Created by Sundareswaran on 27-07-2015.
 */
public class MyAdapterPost extends RecyclerView.Adapter<MyAdapterPost.MyViewHolder>{

    Context context;
    List<Post> data;
    LayoutInflater inflater;

    public MyAdapterPost(Context context,List<Post> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row_message_1,parent,false);
        return (new MyViewHolder(view));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Post current = data.get(position);
        if (!current.getImage().equals(""))
            Glide.with(context)
                .load(current.getImage())
                .centerCrop()
                .placeholder(R.drawable.loadingeffect)
                .crossFade()
                .into(holder.image);
        holder.name.setText(current.getName());
        holder.message.setText(current.getMessage());
        holder.time.setText(current.getDate());
        holder.title.setText(current.getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,title,message,time;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            name = (TextView) itemView.findViewById(R.id.name);
            message = (TextView) itemView.findViewById(R.id.message);
            time = (TextView) itemView.findViewById(R.id.time);
            title = (TextView) itemView.findViewById(R.id.post_title);
        }
    }
}
