package com.example.asignmemt_mod403.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.asignmemt_mod403.Model.Product;
import com.example.asignmemt_mod403.R;
import com.example.asignmemt_mod403.UrlString;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Product_Adapter extends BaseAdapter {
    Context context;
    ArrayList<Product> list;

    public Product_Adapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
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

        name.setText(objProduct.getName());
        price.setText(objProduct.getPrice());
        quantity.setText(objProduct.getQuantity());
        Picasso.get().load("http://"+ UrlString.ipv4 +":3000/"+objProduct.getImage()).into(img);

        return classView;
    }
}
