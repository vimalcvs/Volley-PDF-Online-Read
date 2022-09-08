package com.vimalcvs.pdflist.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vimalcvs.pdflist.Constants;
import com.vimalcvs.pdflist.R;
import com.vimalcvs.pdflist.helper.PDFAdapter;
import com.vimalcvs.pdflist.helper.PDFModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NotifyDataSetChanged")
public class MainActivity extends AppCompatActivity   {

    private RequestQueue requestQueue;
    private final List<PDFModel> list = new ArrayList<>();
    private final String TAG = "MainActivity";
    private PDFAdapter adapter;
    private ProgressBar listProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        listProgressBar = findViewById(R.id.listProgressBar);
        requestQueue = Volley.newRequestQueue(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PDFAdapter(this, list);
        recyclerView.setAdapter(adapter);

        callApi();
    }

    private void callApi(){
        StringRequest request = new StringRequest(Request.Method.GET, Constants.PDF_URL, response -> {
            Log.d(TAG, "callApi: "+ response);
            listProgressBar.setVisibility(View.GONE);
            extract(response);
        }, error -> {
            Log.d("error",error.toString());
            listProgressBar.setVisibility(View.VISIBLE);
        });
        requestQueue.add(request);
    }


    private void extract(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray movies = jsonObject.getJSONArray("PDFList");
            for (int i=0; i<movies.length(); i++){
                JSONObject childObj = movies.getJSONObject(i);
                String pdf_name = childObj.getString("pdf_name");
                String pdf_url = childObj.getString("pdf_url");
                String img_url = childObj.getString("img_url");
                String read_time = childObj.getString("read_time");
                PDFModel PDFModel = new PDFModel(pdf_name, pdf_url, img_url, read_time);
                list.add(PDFModel);
            }
            adapter.notifyDataSetChanged();
        }
        catch (Exception e){
            Log.d(TAG, "extract: "+ e.getMessage());
        }
    }
}