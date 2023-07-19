package com.example.asignmemt_mod403;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.asignmemt_mod403.Model.Product;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AddProduct extends AsyncTask<Void,Void,Void> {
    String urlString = UrlString.url+"add/";
    Product objProduct;
    Context context;
    ProgressDialog pDialog;

    public AddProduct(Product objProduct, Context context) {
        this.objProduct = objProduct;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            URL url = new URL(urlString);
            String param = "name="+ URLEncoder.encode(objProduct.getName(),"utf-8")
                    +"&price="+URLEncoder.encode(objProduct.getPrice(),"utf-8")
                    +"&quantity="+URLEncoder.encode(objProduct.getQuantity(),"utf-8")
                    +"&type="+URLEncoder.encode(objProduct.getType(),"utf-8")
                    +"&image="+URLEncoder.encode("images//1689756145964--8-min.jpg","utf-8");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setFixedLengthStreamingMode(param.getBytes().length);
            urlConnection.setRequestProperty("Context-Type","application/x-www-form-urlencoded");

            PrintWriter print = new PrintWriter(urlConnection.getOutputStream());
            print.print(param);
            print.close();

            urlConnection.getInputStream();
            urlConnection.disconnect();

        }catch (Exception e){

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
    }
}
