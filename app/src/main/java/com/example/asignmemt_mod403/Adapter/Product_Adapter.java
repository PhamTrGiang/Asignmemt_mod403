package com.example.asignmemt_mod403.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.example.asignmemt_mod403.DeleteProduct;
import com.example.asignmemt_mod403.Fragment.Frag_product;
import com.example.asignmemt_mod403.GetProduct;
import com.example.asignmemt_mod403.Model.Product;
import com.example.asignmemt_mod403.R;
import com.example.asignmemt_mod403.UrlString;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Product_Adapter extends BaseAdapter {
    Context context;
    ArrayList<Product> list;
    Frag_product frag_product;

    public Product_Adapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
        frag_product = new Frag_product();
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
        Picasso.get().load(UrlString.urlImg+objProduct.getImage()).into(img);

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag_product.delete(objProduct.getId(),context);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag_product.edit(objProduct,context);
            }
        });
        return classView;
    }
}
