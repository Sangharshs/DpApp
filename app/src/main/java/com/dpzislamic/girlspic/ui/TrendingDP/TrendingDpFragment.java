package com.dpzislamic.girlspic.ui.TrendingDP;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.Circle;
import com.dpzislamic.girlspic.Adapters.ImageAdapter;
import com.dpzislamic.girlspic.Constant;
import com.dpzislamic.girlspic.ImageActivity;
import com.dpzislamic.girlspic.Model.Image_Model;
import com.dpzislamic.girlspic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static com.dpzislamic.girlspic.api.MainUrl;

public class TrendingDpFragment extends Fragment {
    boolean isLoading = true;
    int limit = 10;
    int offset = 10;
    ProgressBar bottom_progress;
    View view;
    List<Image_Model> image_modelList = new ArrayList<>();
    GridView gridView;
    ImageAdapter imageAdapter;
    SpinKitView progressBar;
    Image_Model image_model;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trending_dp, container, false);

        gridView = view.findViewById(R.id.gridView);
        swipeRefreshLayout = view.findViewById(R.id.swipe_for_refresh);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Circle doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);
        bottom_progress = view.findViewById(R.id.bottomProgress);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                image_modelList.clear();
                loadTrendingContent();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        image_modelList=new ArrayList<Image_Model>();
        loadTrendingContent();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.ARRAY_LIST, (Serializable) image_modelList);
                intent.putExtra(Constant.BUNDLE, bundle);
                intent.putExtra("id",image_modelList.get(position).getPosition());
                intent.putExtra("current_i",image_modelList.get(position).getImage());
                intent.putExtra("POSITION_ID", position);
                startActivity(intent);

            }
        });
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                //leave this empty
            }

            @Override
            public void onScrollStateChanged(AbsListView listView, int scrollState) {
                if (!gridView.canScrollVertically(1) && scrollState == SCROLL_STATE_IDLE) {
                    if (!isLoading) {

                 //       Toast.makeText(view.getContext(), "Load More", Toast.LENGTH_SHORT).show();
//                      loadMoreComments();
                        offset = offset + 10;
                    } else {
                    }
                }
            }
        });
        return view;


    }

    private void loadTrendingContent(){
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, MainUrl+"trending.php?"+"limit="+ String.valueOf(limit)+"&offset="+String.valueOf(offset), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                image_modelList.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        image_model = new Image_Model();
                        image_model.setPosition(object.getInt("id"));
                        image_model.setImage(object.getString("image"));
                        image_model.setCategory(object.getString("category"));
                        image_modelList.add(image_model);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Collections.reverse(image_modelList);
                if (image_modelList.isEmpty()) {
                    Toast.makeText(view.getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                }

                Log.e("list", String.valueOf(image_modelList));
                imageAdapter = new ImageAdapter(image_modelList, view.getContext());
                gridView.setAdapter(imageAdapter);
                //recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(view.getContext());
        queue.add(request);
        queue.start();

        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                //refreshLayout.setRefreshing(false);
                isLoading = false;
                //offers_finish = true;
                bottom_progress.setVisibility(View.GONE);

            }
        });
    }
}