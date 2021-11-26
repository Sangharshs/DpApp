package com.dpzislamic.girlspic.Adapters;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dpzislamic.girlspic.FullenglishquoteActivity;
import com.dpzislamic.girlspic.Model.EnglishCategoryModel;
import com.dpzislamic.girlspic.R;

import java.util.List;

public class EnglishCategoryAdapter extends RecyclerView.Adapter<EnglishCategoryAdapter.Viewholder>{

List<EnglishCategoryModel> englishmodellist;

    public EnglishCategoryAdapter(List<EnglishCategoryModel> englishmodellist) {
        this.englishmodellist = englishmodellist;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        holder.categoryName.setText(englishmodellist.get(position).getTextsms());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FullenglishquoteActivity.class);
                intent.putExtra("cname",englishmodellist.get(position).getTextsms());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return englishmodellist.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
    TextView categoryName;


    public Viewholder(@NonNull View itemView) {
        super(itemView);
        categoryName = itemView.findViewById(R.id.categoryName);
        Typeface custom_font = Typeface.createFromAsset(itemView.getContext().getAssets(), "Roboto-Regular.ttf");
        categoryName.setTypeface(custom_font);

    }
}
}
