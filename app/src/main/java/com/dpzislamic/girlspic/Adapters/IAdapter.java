package com.dpzislamic.girlspic.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dpzislamic.girlspic.ImageActivity;
import com.dpzislamic.girlspic.Model.Image_Model;
import com.dpzislamic.girlspic.R;

import java.util.List;

import static com.dpzislamic.girlspic.api.GET_IMAGE_FOLDER;

public class IAdapter extends RecyclerView.Adapter<IAdapter.Viewholder> {
    List<Image_Model> image_modelList;

    public IAdapter(List<Image_Model> image_modelList) {
        this.image_modelList = image_modelList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {
    holder.textView.setText(image_modelList.get(position).getCategory());
    String img = image_modelList.get(position).getImage();
        Glide.with(holder.imageView.getContext()).load(GET_IMAGE_FOLDER + img).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ImageActivity.class);
              //  intent.putExtra("ilist", (Parcelable) image_modelList);
                intent.putExtra("position",position-1);
                intent.putExtra("image",image_modelList.get(position).getImage());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return image_modelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ImageView);
            textView = itemView.findViewById(R.id.category);
        }
    }
}
