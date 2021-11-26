package com.dpzislamic.girlspic.ui.ImageCategories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.Circle;
import com.dpzislamic.girlspic.Adapters.ImagecategoryAdapter;
import com.dpzislamic.girlspic.Model.ImageCategoryModel;
import com.dpzislamic.girlspic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.dpzislamic.girlspic.api.MainUrl;

public class ImageFragment extends Fragment {

    List<ImageCategoryModel> image_category_list ;
    RecyclerView recyclerView;
    ImagecategoryAdapter adapter;
    ImageCategoryModel imageCategoryModel;
    SpinKitView progressBar;
    SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        progressBar = root.findViewById(R.id.progressBar);
        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Circle doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);
        recyclerView = root.findViewById(R.id.recyclerView_image_category);
        swipeRefreshLayout = root.findViewById(R.id.swipe_for_refresh);
         loadImageCategory();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                image_category_list.clear();
                 loadImageCategory();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return root;
    }


    private void loadImageCategory() {
        progressBar.setVisibility(View.VISIBLE);
        image_category_list = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, MainUrl+"getimagecategories.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                image_category_list.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        imageCategoryModel= new ImageCategoryModel();
                        imageCategoryModel.setCategory_name(object.getString("category_name"));
                        imageCategoryModel.setCategory_image(object.getString("image"));
                        image_category_list.add(imageCategoryModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Collections.reverse(image_category_list);
                adapter = new ImagecategoryAdapter(image_category_list,getContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);
    }
}
