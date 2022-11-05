package glaxymedia.realmnote;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //https://github.com/nostra13/Android-Universal-Image-Loader/wiki/Useful-Info

    /* Image Load có thể lấy các hình ảnh từ những nguồn lưu trữ
    "http://site.com/image.png" // from Web
    "file:///mnt/sdcard/image.png" // from SD card
    "file:///mnt/sdcard/video.mp4" // from SD card (video thumbnail)
    "content://media/external/images/media/13" // from content provider
    "content://media/external/video/media/13" // from content provider (video thumbnail)
    "assets://image.png" // from assets
    "drawable://" + R.drawable.img // from drawables (non-9patch images)
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final ImageView img= (ImageView) findViewById(R.id.imageView2);
        final String url="https://pbs.twimg.com/profile_images/709838275209351168/28bckvxD.jpg";

        /*
        File cacheDir = StorageUtils.getCacheDirectory(context);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
        .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
        .diskCacheExtraOptions(480, 800, null)
        .taskExecutor(...)
        .taskExecutorForCachedImages(...)
        .threadPoolSize(3) // default
        .threadPriority(Thread.NORM_PRIORITY - 1) // default
        .tasksProcessingOrder(QueueProcessingType.FIFO) // default
        .denyCacheImageMultipleSizesInMemory()
        .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
        .memoryCacheSize(2 * 1024 * 1024)
        .memoryCacheSizePercentage(13) // default
        .diskCache(new UnlimitedDiscCache(cacheDir)) // default
        .diskCacheSize(50 * 1024 * 1024)
        .diskCacheFileCount(100)
        .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
        .imageDownloader(new BaseImageDownloader(context)) // default
        .imageDecoder(new BaseImageDecoder()) // default
        .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
        .writeDebugLogs()
        .build();

        */

        /*
         if(isConnectingToInternet()){
            imageLoader.displayImage(image_url, imageView, options);
        }
        else{
            File file = imageLoader.getDiscCache().get(image_url);
            Log.v(TAG,"file_ path :"+file.getPath());
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
        }
        */

        /*
        DisplayImageOptions imageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.drawable.noimage)
                .showImageOnFail(R.drawable.noimage)
                .showImageOnLoading(R.drawable.noimage)
                .resetViewBeforeLoading(true)
                .considerExifParams(true)
                .build();
        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(imageOptions)
                .build();
        ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().displayImage(url,img,imageOptions);
        */
        final Activity ac=this;
        //Mặt đinh cache lưu trong data/data/packageapp/cache. Hàm bên dưới lưu cache trên bộ nhớ sdcard
        if (!Glide.isSetup()) {
            GlideBuilder gb = new GlideBuilder(this);
            DiskCache dlw = DiskLruCacheWrapper.get(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myCatch/"), 250 * 1024 * 1024);
            gb.setDiskCache(dlw);
            Glide.setup(gb);
        }
        Glide
            .with(this)
            .load(url)//.load(Uri.parse("file://" + imagePath))->load ảnh từ source
            .diskCacheStrategy(DiskCacheStrategy.ALL)//
            .skipMemoryCache(true)//Cho phép lưi offline
            .centerCrop()
            .placeholder(R.drawable.noimage)
            .crossFade()
            .into(img);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Handler mainHandler = new Handler(Looper.getMainLooper());
                Runnable myRunnable = new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(ac).clearDiskCache();
                        Glide.get(ac).clearMemory();
                    }
                };
                mainHandler.post(myRunnable);
                */
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(MainActivity.this).clearDiskCache();
                        //Glide.get(MainActivity.this).clearMemory();
                    }
                }).start();

                //ImageLoader.getInstance().clearDiskCache();
                //ImageLoader.getInstance().clearMemoryCache();
                /*File file = ImageLoader.getInstance().getDiscCache().get(url);
                if (!file.exists()) {
                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .cacheOnDisc()
                            .build();
                    ImageLoader.getInstance().displayImage(url, img, options);
                }
                else {
                    img.setImageURI(Uri.parse(file.getAbsolutePath()));
                }
                */
            }
        });

        Button btn= (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t=new Intent(ac,LoadActivity.class);
                startActivity(t);
            }
        });
    }
    public static boolean isImageAvailableInCache(String imageUrl){
        MemoryCache memoryCache = ImageLoader.getInstance().getMemoryCache();
        if(memoryCache!=null) {
            for (String key : memoryCache.keys()) {
                if (key.startsWith(imageUrl)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isDiskCache(String url) {
        //MemoryCacheUtils.findCachedBitmapsForImageUri(imageUri, ImageLoader.getInstance().getMemoryCache());

        File file = DiskCacheUtils.findInCache(url, ImageLoader.getInstance().getDiskCache());
        return file != null;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
