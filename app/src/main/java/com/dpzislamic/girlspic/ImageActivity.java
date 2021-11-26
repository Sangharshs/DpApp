package com.dpzislamic.girlspic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.artjimlop.altex.AltexImageDownloader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.dpzislamic.girlspic.Model.Image_Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.bluetooth.BluetoothGattDescriptor.PERMISSION_READ;
import static android.bluetooth.BluetoothGattDescriptor.PERMISSION_WRITE;

import static com.dpzislamic.girlspic.api.GET_IMAGE_FOLDER;
import static com.dpzislamic.girlspic.api.MainUrl;

public class ImageActivity extends AppCompatActivity {
    File file;
    String dirPath, fileName;
    ProgressDialog mProgressDialog;
    ViewPager viewPager;
    String currentImage;
    int p;
    int positionn;
    List<Image_Model> image_models = new ArrayList<>();
    ImagePagerAdapter pagerAdapter;
    Image_Model wallpaper;
    private static final String FACEBOOK_ID = "com.facebook.katana";
    //Share Content
    CardView downoad_img, share_fb, share_instagram, share_wh, share_multi;
    //Image
    ImageView imageView;
    Context context;
    String TAG = "ADS";
    int id;
    String pos;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId(getString(R.string.banner_ads));
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        context = getApplicationContext();
        //Share Content
        share_wh = findViewById(R.id.share_wh);
        share_fb = findViewById(R.id.share_fb);
        share_multi = findViewById(R.id.share_share);
        share_instagram = findViewById(R.id.share_insta);
        downoad_img = findViewById(R.id.download_btn);
        //Share Content
        wallpaper = new Image_Model();
        viewPager = findViewById(R.id.viewPager);
        viewPager.setPageTransformer(true, new RotateUpPageTransformer());

        Intent i = getIntent();
        positionn = i.getIntExtra("POSITION_ID", 0);
        currentImage = i.getStringExtra("current_i");
        id = i.getIntExtra("id", 0);

        wallpaper = (Image_Model) getIntent().getSerializableExtra(Constant.EXTRA_OBJC);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constant.BUNDLE);

        image_models = (List<Image_Model>) bundle.getSerializable(Constant.ARRAY_LIST);

        pagerAdapter = new ImagePagerAdapter();

        viewPager.setAdapter(pagerAdapter);

        viewPager.setCurrentItem(positionn);
        insertData();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                insertData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        checkPermission();
        share_content();
    }


    private class ImagePagerAdapter extends PagerAdapter {

        private final LayoutInflater inflater;

        ImagePagerAdapter() {
            inflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return image_models.size() - 1;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view.equals(object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {

            View imageLayout = inflater.inflate(R.layout.viewpager_item, container, false);
            assert imageLayout != null;
            imageView = imageLayout.findViewById(R.id.image);
            SpinKitView pr = imageLayout.findViewById(R.id.loading);
            pos = image_models.get(position).getImage();
            Circle doubleBounce = new Circle();
            pr.setIndeterminateDrawable(doubleBounce);
            pr.setVisibility(View.VISIBLE);
            Glide.with(ImageActivity.this)
                    .load(GET_IMAGE_FOLDER + image_models.get(position).getImage())

                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            pr.setVisibility(View.GONE);
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            pr.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
             pr.setVisibility(View.GONE);
            container.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    public class RotateUpPageTransformer implements ViewPager.PageTransformer {
        private static final float ROTATION = -15f;

        @Override
        public void transformPage(View page, float pos) {
            final float width = page.getWidth();
            final float height = page.getHeight();
            final float rotation = ROTATION * pos * -1.25f;

            page.setPivotX(width * 0.5f);
            page.setPivotY(height);
            page.setRotation(rotation);

        }

    }

    public void downloadWallpaper(String imageURL) {
        positionn = viewPager.getCurrentItem();
        if (!verifyPermissions()) {
            return;
        }
        Glide.with(this)
                .download(GET_IMAGE_FOLDER + image_models.get(positionn).getImage().replace(" ", "%20"))
                .listener(new RequestListener<File>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
                        try {
                            if (image_models.get(positionn).getImage().endsWith(".gif") || image_models.get(positionn).getImage().endsWith(".gif")) {
                                Tools.saveImage(ImageActivity.this, Tools.getBytesFromFile(resource), Tools.createName(image_models.get(positionn).getImage()), "image/gif");
                            } else if (image_models.get(positionn).getImage().endsWith(".png") || image_models.get(positionn).getImage().endsWith(".png")) {
                                Tools.saveImage(ImageActivity.this, Tools.getBytesFromFile(resource), Tools.createName(image_models.get(positionn).getImage()), "image/png");
                            } else {
                                Tools.saveImage(ImageActivity.this, Tools.getBytesFromFile(resource), Tools.createName(image_models.get(positionn).getImage()), "image/jpg");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                }).submit();
    }

    private void share_content() {
        downoad_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                positionn = viewPager.getCurrentItem();
                Toast.makeText(context, "Download Start...", Toast.LENGTH_SHORT).show();
                //downloadWallpaper(image_models.get(positionn).getImage());
                AltexImageDownloader.writeToDisk(getApplicationContext(), GET_IMAGE_FOLDER+image_models.get(positionn).getImage(), String.valueOf(R.string.app_name)); //NAME_FOLDER is the name of the folder where you want to save the image.
                final AltexImageDownloader downloader = new AltexImageDownloader(new AltexImageDownloader.OnImageLoaderListener() {
                    @Override
                    public void onError(AltexImageDownloader.ImageError error) {
                        Toast.makeText(ImageActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProgressChange(int percent) {
                    }

                    @Override
                    public void onComplete(Bitmap result) {
                        Toast.makeText(context, "Download Complete", Toast.LENGTH_SHORT).show();
                    }
                });
                downloader.download(GET_IMAGE_FOLDER+image_models.get(positionn).getImage(),true);
            }
        });


        share_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //shareWallpaper(imgURL);
                shareImage();
            }
        });
        share_wh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImagewh();
            }
        });
        share_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImagewithInstagram();
            }
        });
        share_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImageonFb();
            }
        });
    }

    private void shareImageonFb() {
        if (!verifyPermissions()) {
            return;
        }
        positionn = viewPager.getCurrentItem();
        Glide.with(this)
                .download(GET_IMAGE_FOLDER + image_models.get(positionn).getImage().replace(" ", "%20"))
                .listener(new RequestListener<File>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
                        try {
                            if (image_models.get(positionn).getImage().endsWith(".gif") || image_models.get(positionn).getImage().endsWith(".gif")) {
                                Tools.shareImageFB(ImageActivity.this, Tools.getBytesFromFile(resource), Tools.createName(image_models.get(positionn).getImage()), "image/gif");
                            } else if (image_models.get(positionn).getImage().endsWith(".png") || image_models.get(positionn).getImage().endsWith(".png")) {
                                Tools.shareImageFB(ImageActivity.this, Tools.getBytesFromFile(resource), Tools.createName(image_models.get(positionn).getImage()), "image/png");
                            } else {
                                Tools.shareImageFB(ImageActivity.this, Tools.getBytesFromFile(resource), Tools.createName(image_models.get(positionn).getImage()), "image/jpg");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                }).submit();

    }

    private void shareImagewithInstagram() {
        if (!verifyPermissions()) {
            return;
        }
        positionn = viewPager.getCurrentItem();
        Glide.with(this)
                .download(GET_IMAGE_FOLDER + image_models.get(positionn).getImage().replace(" ", "%20"))
                .listener(new RequestListener<File>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
                        try {
                            if (image_models.get(positionn).getImage().endsWith(".gif") || image_models.get(positionn).getImage().endsWith(".gif")) {
                                Tools.shareImageInsta(ImageActivity.this, Tools.getBytesFromFile(resource), Tools.createName(image_models.get(positionn).getImage()), "image/gif");
                            } else if (image_models.get(positionn).getImage().endsWith(".png") || image_models.get(positionn).getImage().endsWith(".png")) {
                                Tools.shareImageInsta(ImageActivity.this, Tools.getBytesFromFile(resource), Tools.createName(image_models.get(positionn).getImage()), "image/png");
                            } else {
                                Tools.shareImageInsta(ImageActivity.this, Tools.getBytesFromFile(resource), Tools.createName(image_models.get(positionn).getImage()), "image/jpg");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                }).submit();
    }

    private void shareImagewh() {
        if (!verifyPermissions()) {
            return;
        }
        positionn = viewPager.getCurrentItem();
        Glide.with(this)
                .download(GET_IMAGE_FOLDER + image_models.get(positionn).getImage().replace(" ", "%20"))
                .listener(new RequestListener<File>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
                        //  Toast.makeText(getApplicationContext(), String.valueOf(pos), Toast.LENGTH_SHORT).show();
                        try {
                            if (image_models.get(positionn).getImage().endsWith(".gif") || image_models.get(positionn).getImage().endsWith(".gif")) {
                                Tools.shareImageWh(ImageActivity.this, Tools.getBytesFromFile(resource), Tools.createName(image_models.get(positionn).getImage()), "image/gif");
                            } else if (image_models.get(positionn).getImage().endsWith(".png") || image_models.get(positionn).getImage().endsWith(".png")) {
                                Tools.shareImageWh(ImageActivity.this, Tools.getBytesFromFile(resource), Tools.createName(image_models.get(positionn).getImage()), "image/png");
                            } else {
                                Tools.shareImageWh(ImageActivity.this, Tools.getBytesFromFile(resource), Tools.createName(image_models.get(positionn).getImage()), "image/jpg");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                }).submit();
    }

    private void shareImage() {
        if (!verifyPermissions()) {
            return;
        }
        positionn = viewPager.getCurrentItem();
        Glide.with(this)
                .download(GET_IMAGE_FOLDER + image_models.get(positionn).getImage().replace(" ", "%20"))
                .listener(new RequestListener<File>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
                        //  Toast.makeText(getApplicationContext(), String.valueOf(pos), Toast.LENGTH_SHORT).show();
                        try {
                            if (image_models.get(positionn).getImage().endsWith(".gif") || image_models.get(positionn).getImage().endsWith(".gif")) {
                                Tools.shareImage(ImageActivity.this, Tools.getBytesFromFile(resource), Tools.createName(image_models.get(positionn).getImage()), "image/gif");
                            } else if (image_models.get(positionn).getImage().endsWith(".png") || image_models.get(positionn).getImage().endsWith(".png")) {
                                Tools.shareImage(ImageActivity.this, Tools.getBytesFromFile(resource), Tools.createName(image_models.get(positionn).getImage()), "image/png");
                            } else {
                                Tools.shareImage(ImageActivity.this, Tools.getBytesFromFile(resource), Tools.createName(image_models.get(positionn).getImage()), "image/jpg");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                }).submit();
//        Bitmap bitmap = getBitmapFromView(imageView);
//        try {
//            File file = new File(this.getExternalCacheDir(),"download.jpg");
//            FileOutputStream fOut = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fOut);
//            fOut.flush();
//            fOut.close();
//            file.setReadable(true,false);
//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra(Intent.EXTRA_STREAM,FileProvider.getUriForFile(ImageActivity.this, BuildConfig.APPLICATION_ID +
//                    ".provider",file));
//            intent.setType("image/*");
//            startActivity(Intent.createChooser(intent,"Share Image"));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap returnBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnBitmap;
    }

    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int WRITE_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_WRITE);
            return false;
        }
        if ((WRITE_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_READ);
            return false;
        }
        return true;
    }

    public Boolean verifyPermissions() {
        int permissionExternalMemory = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionExternalMemory != PackageManager.PERMISSION_GRANTED) {
            String[] STORAGE_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, STORAGE_PERMISSIONS, 1);
            return false;
        }
        return true;
    }

    public void insertData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MainUrl + "viewscount.php?id=" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("Success")) {
                            //  Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ImageActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
