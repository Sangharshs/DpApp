package com.dpzislamic.girlspic.ui.EnglishCategory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.dpzislamic.girlspic.Adapters.EnglishCategoryAdapter;
import com.dpzislamic.girlspic.Model.EnglishCategoryModel;
import com.dpzislamic.girlspic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.dpzislamic.girlspic.api.MainUrl;

public class EnglishCategoryFragment extends Fragment {
    EnglishCategoryAdapter adapter;
    EnglishCategoryModel english_category_model;
    List<EnglishCategoryModel> english_category_list;
    SpinKitView progressBar;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_english_category, container, false);
        super.onAttach(root.getContext());

        recyclerView = root.findViewById(R.id.recyclerView_English);
        swipeRefreshLayout = root.findViewById(R.id.swipe_for_refresh);
        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Circle doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);
        // loadEnglishCategory();

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(gridLayoutManager);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                english_category_list.clear();
                load_english_Category();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        load_english_Category();
        english_category_list = new ArrayList<>();


        return root;
    }

    private void load_english_Category(){
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, MainUrl+"getenglishcategory.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                english_category_list.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        english_category_model = new EnglishCategoryModel();
                        english_category_model.setTextsms(object.getString("category_name"));
                        english_category_list.add(english_category_model);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Collections.reverse(english_category_list);
                    adapter = new EnglishCategoryAdapter(english_category_list);
                    recyclerView.setAdapter(adapter);
                    //swipeRefreshLayout.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);
        swipeRefreshLayout.setRefreshing(false);
    }


}