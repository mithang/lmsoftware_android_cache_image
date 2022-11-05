package glaxymedia.realmnote;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        ImageView img= (ImageView) findViewById(R.id.imgload);
        final String url="https://pbs.twimg.com/profile_images/709838275209351168/28bckvxD.jpg";

        //ImageLoader.getInstance().displayImage(url,img);
        Glide
                .with(this)
                .load(url)//.load(Uri.parse("file://" + imagePath))->load ảnh từ source
                //.diskCacheStrategy(DiskCacheStrategy.ALL)//DiskCacheStrategy.NONE
                .centerCrop()
                .placeholder(R.drawable.noimage)
                .crossFade()
                .into(img);

    }


}
