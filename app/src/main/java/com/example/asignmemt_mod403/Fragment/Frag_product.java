package com.example.asignmemt_mod403.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.asignmemt_mod403.API.ApiService;
import com.example.asignmemt_mod403.Adapter.Product_Adapter;
import com.example.asignmemt_mod403.Model.Product;
import com.example.asignmemt_mod403.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Frag_product extends Fragment implements View.OnClickListener, Product_Adapter.Callback {
    private GridView gridView;
    private FloatingActionButton floatingActionButton;
    private Product_Adapter adapter;
    private List<Product> mList;
    public Frag_product() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, container, false);
        mList = new ArrayList<>();
        gridView = v.findViewById(R.id.gridView);
        floatingActionButton = v.findViewById(R.id.btnAdd);
        floatingActionButton.setOnClickListener(this);

        adapter = new Product_Adapter(new ArrayList<>(), this);
        gridView.setAdapter(adapter);
        loadData();
        return v;
    }



    public void loadData(){
        ApiService.apiService.getProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> tableItems = (List<Product>) response.body();
                    if (tableItems != null) {
                        adapter.setTableItems(tableItems);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to retrieve table list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addNewData(String name,String price,String image,String type,String quantity) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setImage(image);
        product.setType(type);
        product.setQuantity(quantity);
        ApiService.apiService.add(product).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    // Xử lý thành công
                    Toast.makeText(getContext(), "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                    List<Product> tableItems = response.body();
                    if (tableItems != null) {
                        adapter.setTableItems(tableItems);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    // Xử lý lỗi khi thêm dữ liệu
                    Toast.makeText(getContext(), "Lỗi khi thêm dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void edit (String id,String name,String price,String image,String type,String quantity){
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setImage(image);
        product.setType(type);
        product.setQuantity(quantity);


        Call<List<Product>> call = ApiService.apiService.update(id, product);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                    List<Product> tableItems = (List<Product>) response.body();
                    if (tableItems != null) {
                        adapter.setTableItems(tableItems);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("MAIN", "Respone Fail" + t.getMessage());
            }
        });
    }
    public void delete(Product model){
        String id = model.getId();
        Call<List<Product>> call = ApiService.apiService.delete(id);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                    List<Product> tableItems = response.body();
                    if (tableItems != null) {
                        adapter.setTableItems(tableItems);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("MAIN111", "Respone Fail" + t.getMessage());
            }
        });
    }
    ImageButton imgBtn;
    @Override
    public void onClick(View view) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_product);
        EditText edName = dialog.findViewById(R.id.edName);
        EditText edPrice = dialog.findViewById(R.id.edPrice);
        EditText edNQuantity = dialog.findViewById(R.id.edQuantity);
        EditText edType = dialog.findViewById(R.id.edType);
        imgBtn = dialog.findViewById(R.id.btnImg);
        Button btnSave = dialog.findViewById(R.id.btnSave);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,123);
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edName.getText().toString();
                String price = edPrice.getText().toString();
                String quantity = edNQuantity.getText().toString();
                String type = edType.getText().toString();

                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgBtn.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                byte[] imageBytes = byteArray.toByteArray();
                String image = Base64.encodeToString(imageBytes, Base64.DEFAULT);;

                addNewData(name,price,image,type,quantity);

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

    @Override
    public void editPr(Product objProduct) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_product);
        EditText edName = dialog.findViewById(R.id.edName);
        EditText edPrice = dialog.findViewById(R.id.edPrice);
        EditText edQuantity = dialog.findViewById(R.id.edQuantity);
        EditText edType = dialog.findViewById(R.id.edType);
        imgBtn = dialog.findViewById(R.id.btnImg);
        edName.setText(objProduct.getName());
        edPrice.setText(objProduct.getPrice());
        edQuantity.setText(objProduct.getQuantity());
        edType.setText(objProduct.getType());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        imageBytes = Base64.decode(objProduct.getImage(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imgBtn.setImageBitmap(decodedImage);

        Button btnSave = dialog.findViewById(R.id.btnSave);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,123);
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edName.getText().toString();
                String price = edPrice.getText().toString();
                String quantity = edQuantity.getText().toString();
                String type = edType.getText().toString();


                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgBtn.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                byte[] imageBytes = byteArray.toByteArray();
                String image = Base64.encodeToString(imageBytes, Base64.DEFAULT);;

                edit(objProduct.getId(),name,price,image,type,quantity);

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

    @Override
    public void deletePr(Product model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete(model);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == Activity.RESULT_OK && data!=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgBtn.setImageBitmap(bitmap);
        }
    }

}