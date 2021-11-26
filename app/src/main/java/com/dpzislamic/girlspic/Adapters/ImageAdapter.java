package com.dpzislamic.girlspic.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.Circle;
import com.dpzislamic.girlspic.Constant;
import com.dpzislamic.girlspic.ImageActivity;
import com.dpzislamic.girlspic.Model.Image_Model;
import com.dpzislamic.girlspic.R;

import java.io.Serializable;
import java.util.List;

import static com.dpzislamic.girlspic.api.GET_IMAGE_FOLDER;

public class ImageAdapter extends BaseAdapter {
    List<Image_Model> image_models_list;
    Context mContext;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public ImageAdapter(List<Image_Model> image_models_list, Context mContext) {
        this.image_models_list = image_models_list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return image_models_list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder {
        ImageView imageView;
        SpinKitView progressBar;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder = null;
//        ImageView imageView = (ImageView) convertView;

            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
            }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.ImageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        ProgressBar progressBar = (SpinKitView) convertView.findViewById(R.id.pBAAR);
        progressBar.setVisibility(View.VISIBLE);
        Circle doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);

        String img = image_models_list.get(position).getImage();
        if(img.startsWith("http")){
            Toast.makeText(mContext, img, Toast.LENGTH_SHORT).show();
            Glide.with(mContext).load(img).into(imageView);
        }
        Glide.with(mContext).load(GET_IMAGE_FOLDER+img).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            // progressBar.setVisibility(View.GONE);
                return false;
            }
            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            //  progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(imageView);

        View finalConvertView = convertView;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.ARRAY_LIST, (Serializable) image_models_list);
                intent.putExtra(Constant.BUNDLE, bundle);
                intent.putExtra("id",image_models_list.get(position).getPosition());
                intent.putExtra("current_i",image_models_list.get(position).getImage());
                intent.putExtra("POSITION_ID", position);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

}
