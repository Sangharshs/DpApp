package com.dpzislamic.girlspic.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dpzislamic.girlspic.CategorywiseimagesActivity;
import com.dpzislamic.girlspic.Model.ImageCategoryModel;
import com.dpzislamic.girlspic.R;

import java.util.List;

import static com.dpzislamic.girlspic.api.GET_IMAGE_FOLDER;

public class ImagecategoryAdapter extends RecyclerView.Adapter<ImagecategoryAdapter.Viewholder> {

    List<ImageCategoryModel> imageCategoryModelList;
    Context context;

    public ImagecategoryAdapter(List<ImageCategoryModel> imageCategoryModelList, Context context) {
        this.imageCategoryModelList = imageCategoryModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_category_item,parent,false);

        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
     holder.textView.setText(imageCategoryModelList.get(position).getCategory_name());
     String img = imageCategoryModelList.get(position).getCategory_image();
     Glide.with(holder.imageview.getContext()).load(GET_IMAGE_FOLDER +img).into(holder.imageview);

     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent = new Intent(view.getContext(), CategorywiseimagesActivity.class);
             intent.putExtra("cname",imageCategoryModelList.get(position).getCategory_name());
             view.getContext().startActivity(intent);
         }
     });
    }

    @Override
    public int getItemCount() {
        return imageCategoryModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageview;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.category_text_name);
            imageview = itemView.findViewById(R.id.category_item_image);
        }
    }
}
