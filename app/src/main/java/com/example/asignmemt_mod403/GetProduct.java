package com.example.asignmemt_mod403;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import com.example.asignmemt_mod403.Adapter.Product_Adapter;
import com.example.asignmemt_mod403.Model.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetProduct extends AsyncTask<Void,Void,Void> {
    private String TAG = MainActivity.class.getSimpleName();

    public static String urlString = UrlString.url;

    ArrayList<Product> list;

    private ProgressDialog pDialog;
    GridView gridView;
    Context context;
    Product_Adapter adapter;

    public GetProduct(GridView gridView, Context context) {
        this.gridView = gridView;
        this.context = context;
        list = new ArrayList<Product>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpHandler handler = new HttpHandler();
        // making request to url and getting response
        String jsonStr = handler.makeServiceCall(urlString);
        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject("{\"products\":"+jsonStr+"}");
                // Getting JSON Array node
                JSONArray products = jsonObject.getJSONArray("products");
                // looping through all Contacts

                for (int i = 0; i < products.length(); i++) {
                    JSONObject c = products.getJSONObject(i);

                    String id = c.getString("_id");
                    String name = c.getString("name");
                    String price = c.getString("price");
                    String image = c.getString("image");
                    String quantity = c.getString("quantity");
                    String type = c.getString("type");


                    Product objProduct = new Product();
                    objProduct.setId(id);
                    objProduct.setName(name);
                    objProduct.setPrice(price);
                    objProduct.setImage(image);
                    objProduct.setQuantity(quantity);
                    objProduct.setType(type);
                    list.add(objProduct);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        adapter = new Product_Adapter(context, list);
        gridView.setAdapter(adapter);

    }
}
