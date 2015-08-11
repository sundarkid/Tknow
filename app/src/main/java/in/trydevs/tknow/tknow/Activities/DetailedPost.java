package in.trydevs.tknow.tknow.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import in.trydevs.tknow.tknow.DataClasses.Post;
import in.trydevs.tknow.tknow.R;

public class DetailedPost extends AppCompatActivity {

    TextView name, title, message, time, url;
    ImageView image;
    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_post);
        Bundle extras = getIntent().getExtras();
        post = extras.getParcelable(getString(R.string.post_extras));
        initialize();
        if (post != null) {
            name.setText(post.getName());
            title.setText(post.getTitle());
            message.setText(post.getMessage());
            time.setText(post.getDate());
            if (!post.getImage().equalsIgnoreCase(""))
                Glide.with(this)
                        .load(post.getImage())
                        .crossFade()
                        .into(image);
            else
                image.setImageDrawable(null);
            if (!post.getUrl().equalsIgnoreCase("")) {
                url.setVisibility(View.VISIBLE);
                url.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(post.getUrl()));
                        startActivity(i);
                    }
                });
            }
        }
    }

    private void initialize() {
        image = (ImageView) findViewById(R.id.imageView);
        name = (TextView) findViewById(R.id.name);
        message = (TextView) findViewById(R.id.message);
        time = (TextView) findViewById(R.id.time);
        title = (TextView) findViewById(R.id.post_title);
        url = (TextView) findViewById(R.id.url);
    }

}
