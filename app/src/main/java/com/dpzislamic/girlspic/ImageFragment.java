package com.dpzislamic.girlspic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment extends Fragment {
    View view;
    ImageView imageView;
    public ImageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_image, container, false);
        imageView = view.findViewById(R.id.bigImage);

        int imgsrc = getArguments().getInt("image");

        imageView.setImageResource(imgsrc);


        return view;
    }
}