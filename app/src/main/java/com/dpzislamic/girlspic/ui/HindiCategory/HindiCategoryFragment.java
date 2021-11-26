package com.dpzislamic.girlspic.ui.HindiCategory;

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
import com.dpzislamic.girlspic.Adapters.HindiCategoryAdapter;
import com.dpzislamic.girlspic.Model.HindiCategoryModel;
import com.dpzislamic.girlspic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static com.dpzislamic.girlspic.api.MainUrl;

public class HindiCategoryFragment extends Fragment {
    RecyclerView recyclerView;
    HindiCategoryAdapter adapter;
    HindiCategoryModel hindiCategoryModel;
    SpinKitView progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    List<HindiCategoryModel> hindi_category_list = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recyclerView_Hindi);
        progressBar  = root.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        Circle doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(gridLayoutManager);
        loadHindiCategory();
        swipeRefreshLayout = root.findViewById(R.id.swipe_for_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                hindi_category_list.clear();
                loadHindiCategory();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return root;
    }





    private void loadHindiCategory() {
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,  MainUrl+"gethindicategory.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hindi_category_list.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        hindiCategoryModel = new HindiCategoryModel();

                        hindiCategoryModel.setTextsms(object.getString("category_name"));

                        hindi_category_list.add(hindiCategoryModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Collections.reverse(hindi_category_list);

                adapter = new HindiCategoryAdapter(hindi_category_list);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.INVISIBLE);
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
