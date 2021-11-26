package com.dpzislamic.girlspic.Adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dpzislamic.girlspic.Model.VideoModel;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.viewholdeR> {
    List<VideoModel> videoModelList;

    public VideoAdapter(List<VideoModel> videoModelList) {
        this.videoModelList = videoModelList;
    }

    @NonNull
    @Override
    public viewholdeR onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v ;
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholdeR holder, int position) {

    }

    @Override
    public int getItemCount() {
        return videoModelList.size();
    }

    public class viewholdeR extends RecyclerView.ViewHolder {
        public viewholdeR(@NonNull View itemView) {
            super(itemView);
        }
    }
}
