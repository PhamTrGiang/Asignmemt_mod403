package com.example.asignmemt_mod403.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.asignmemt_mod403.Adapter.Product_Adapter;
import com.example.asignmemt_mod403.AppController;
import com.example.asignmemt_mod403.Model.Product;
import com.example.asignmemt_mod403.R;
import com.example.asignmemt_mod403.UrlString;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Frag_product extends Fragment {
    GridView gridView;
    private String urlString = "http://"+ UrlString.ipv4 +":3000/api";

    private static String TAG = Frag_product.class.getSimpleName();
    private ProgressDialog pDialog;

    ArrayList<Product> list = new ArrayList<>();

    public Frag_product() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, container, false);
        gridView = v.findViewById(R.id.gridView);
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        makeJsonArrayRequest();
        return v;
    }

    private void makeJsonArrayRequest() {
        showDialog();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(urlString,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d(TAG, jsonArray.toString());
                        try {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject product = (JSONObject) jsonArray.get(i);
                                String id = product.getString("_id");
                                String name = product.getString("name");
                                String price = product.getString("price");
                                String quatity = product.getString("quantity");
                                String type = product.getString("type");
                                String image = product.getString("image");

                                Product objProduct = new Product();
                                objProduct.setId(id);
                                objProduct.setImage(image);
                                objProduct.setName(name);
                                objProduct.setType(type);
                                objProduct.setPrice(price);
                                objProduct.setQuantity(quatity);
                                list.add(objProduct);
                            }
                            Product_Adapter adapter = new Product_Adapter(getContext(),list);
                            gridView.setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext().getApplicationContext(), "Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        hideDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(TAG,"Error: "+ volleyError.getMessage());
                Toast.makeText(getContext().getApplicationContext(),volleyError.getMessage(),Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(arrayRequest);
    }

    private void showDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }
    private void hideDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}