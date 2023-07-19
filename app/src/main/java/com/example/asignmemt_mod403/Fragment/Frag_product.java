package com.example.asignmemt_mod403.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.example.asignmemt_mod403.Adapter.Product_Adapter;
import com.example.asignmemt_mod403.AddProduct;
import com.example.asignmemt_mod403.DeleteProduct;
import com.example.asignmemt_mod403.EditProduct;
import com.example.asignmemt_mod403.GetProduct;
import com.example.asignmemt_mod403.Model.Product;
import com.example.asignmemt_mod403.R;
import com.example.asignmemt_mod403.UrlString;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class Frag_product extends Fragment {
    GridView gridView;
    FloatingActionButton floatingActionButton;
    ProgressDialog pDialog;
    Dialog dialog;
    Product_Adapter product_adapter;
    public Frag_product() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, container, false);
        gridView = v.findViewById(R.id.gridView);
        pDialog = new ProgressDialog(getActivity());
        floatingActionButton = v.findViewById(R.id.btnAdd);
        reload(getContext());
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        return v;
    }

    public void reload(Context context){
        GetProduct getProduct = new GetProduct(gridView,context);
        getProduct.execute();
    }

    public void add(){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_product);
        EditText edName = dialog.findViewById(R.id.edName);
        EditText edPrice = dialog.findViewById(R.id.edPrice);
        EditText edNQuantity = dialog.findViewById(R.id.edQuantity);
        EditText edType = dialog.findViewById(R.id.edType);
        Button btnSave = dialog.findViewById(R.id.btnSave);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product objProduct = new Product();
                objProduct.setName(edName.getText().toString());
                objProduct.setPrice(edPrice.getText().toString());
                objProduct.setQuantity(edNQuantity.getText().toString());
                objProduct.setType(edType.getText().toString());

                AddProduct addProduct = new AddProduct(objProduct,getContext());
                addProduct.execute();
                reload(getContext());
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
    public void edit (final Product objProduct,Context context){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_product);
        EditText edName = dialog.findViewById(R.id.edName);
        EditText edPrice = dialog.findViewById(R.id.edPrice);
        EditText edQuantity = dialog.findViewById(R.id.edQuantity);
        EditText edType = dialog.findViewById(R.id.edType);

        edName.setText(objProduct.getName());
        edPrice.setText(objProduct.getPrice());
        edQuantity.setText(objProduct.getQuantity());
        edType.setText(objProduct.getType());

        Button btnSave = dialog.findViewById(R.id.btnSave);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product objProduct = new Product();
                objProduct.setName(edName.getText().toString());
                objProduct.setPrice(edPrice.getText().toString());
                objProduct.setQuantity(edQuantity.getText().toString());
                objProduct.setType(edType.getText().toString());

                EditProduct editProduct = new EditProduct(objProduct,context);
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public void delete(final String id,Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DeleteProduct deleteProduct = new DeleteProduct(id);
                deleteProduct.execute();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }




}