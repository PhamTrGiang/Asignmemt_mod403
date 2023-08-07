package com.example.asignmemt_mod403.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.asignmemt_mod403.Fragment.Frag_product;
import com.example.asignmemt_mod403.Model.Product;
import com.example.asignmemt_mod403.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Product_Adapter extends BaseAdapter {
    List<Product> list;
    private Callback callback;

    public void setTableItems(List<Product> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Product_Adapter(ArrayList<Product> list,Callback callback) {
        this.list = list;
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View classView;
        if (view == null){
            classView =View.inflate(viewGroup.getContext(), R.layout.item,null);
        }else{
            classView = view;
        }

        final Product objProduct = list.get(i);

        TextView name = classView.findViewById(R.id.tvName);
        TextView price = classView.findViewById(R.id.tvPrice);
        TextView quantity = classView.findViewById(R.id.tvQuatity);
        ImageView img = classView.findViewById(R.id.img);
        Button btnDel = classView.findViewById(R.id.btnDel);
        Button btnEdit = classView.findViewById(R.id.btnEdit);
        name.setText(objProduct.getName());
        price.setText(objProduct.getPrice());
        quantity.setText(objProduct.getQuantity());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        imageBytes = Base64.decode(objProduct.getImage(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        img.setImageBitmap(decodedImage);


        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.deletePr(objProduct);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.editPr(objProduct);
            }
        });
        return classView;
    }
    public  interface Callback{
        void editPr(Product model);
        void deletePr(Product model);
    }
}
