package com.dpzislamic.girlspic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.dpzislamic.girlspic.Adapters.ImageAdapter;
import com.dpzislamic.girlspic.Model.Image_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.dpzislamic.girlspic.api.MainUrl;


public class CategorywiseimagesActivity extends AppCompatActivity {

    public  RecyclerView recyclerView;
    SpinKitView progressBar;
    Image_Model image_model;
    List<Image_Model> image_modelList = new ArrayList<>();
    GridView gridView;
    ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorywiseimages);

        gridView = findViewById(R.id.gridView);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Circle doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);
        image_modelList=new ArrayList<Image_Model>();

        getCategorywiseImages();

        AdView mAdView= new AdView(this);

        mAdView.setAdSize(AdSize.BANNER);

        mAdView.setAdUnitId(getString(R.string.banner_ads));
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

        private void getCategorywiseImages() {
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,  MainUrl+"getimages.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                image_modelList.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        image_model = new Image_Model();

                        image_model.setImage(object.getString("image"));

                        String cat = image_model.setCategory(object.getString("category"));
                      //  Toast.makeText(CategorywiseimagesActivity.this, cat+"", Toast.LENGTH_SHORT).show();

                        Intent matchCategory = getIntent();
                           if (matchCategory.getStringExtra("cname").equals(cat)){
                                    image_modelList.add(image_model);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Collections.reverse(image_modelList);
                if (image_modelList.isEmpty()){
                    Toast.makeText(CategorywiseimagesActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
                imageAdapter = new ImageAdapter(image_modelList,getApplicationContext());
                gridView.setAdapter(imageAdapter);
              //  recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//       if(interstitialAd.isAdLoaded()){
//           interstitialAd.show();
//       }else{
//           super.onBackPressed();
//       }

    }
}
