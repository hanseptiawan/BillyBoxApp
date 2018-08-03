package com.box.billy.billybox.Main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.box.billy.billybox.Model.AddBuktiTF;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiUtils;
import com.box.billy.billybox.Utils.Permission;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by han on 7/12/2018.
 */

public class BuktiTransfer extends AppCompatActivity {

    ImageView ivback, ivimgholder;
    TextView morderid, midpayment;
    EditText mnobank, mnama, mnominal;
    Button btnupload;
    Bitmap bitmap;
    String userchosenTask;
    ApiServices apiServices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historypesanan);

        apiServices = ApiUtils.getApiServices();

        bind();

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mnobank.length() == 0 ){
                    Toast.makeText(BuktiTransfer.this, "Isi bank terlebih dahulu", Toast.LENGTH_SHORT).show();
                    mnobank.requestFocus();
                }
                else if(mnama.length() == 0 ){
                    Toast.makeText(BuktiTransfer.this, "Isi nama pembayar terlebih dahulu", Toast.LENGTH_SHORT).show();
                    mnama.requestFocus();
                }
                else if(mnominal.length() == 0){
                    Toast.makeText(BuktiTransfer.this, "Isi bank terlebih dahulu", Toast.LENGTH_SHORT).show();
                    mnominal.requestFocus();
                }
                selectImage();
            }
        });

        String orderID = getIntent().getStringExtra("orderID");
        String idpayment = getIntent().getStringExtra("idpayment");
        String status = getIntent().getStringExtra("status");

            morderid.setText(orderID);
            midpayment.setText(idpayment);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void bind() {
        btnupload = findViewById(R.id.btn_uploadbukti);

        morderid = findViewById(R.id.tv_orderID);
        midpayment = findViewById(R.id.tv_tglpesan2);
        mnobank = findViewById(R.id.tv_totalbiaya2);
        mnama = findViewById(R.id.tv_ongkir2);
        mnominal = findViewById(R.id.tv_bayar2);
        ivimgholder = findViewById(R.id.iv_imgholder);
    }

    private void selectImage() {
        final CharSequence[] items = {"Camera", "Gallery","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(BuktiTransfer.this);
        builder.setTitle("Upload photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Permission.checkPermission(BuktiTransfer.this);
                if (items[item].equals("Camera")){
                    userchosenTask = "Camera";
                    if (result)
                        cameraIntent();
                }
                else if (items[item].equals("Gallery")){
                    userchosenTask = "Gallery";
                    if (result)
                        galleryIntent();
                }
                else if (items[item].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void cameraIntent() {
        Intent take = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (take.resolveActivity(getPackageManager())!=null)
            startActivityForResult(take, 0);

    }
    private void galleryIntent() {
        Intent take = new Intent();
        take.setType("image/* video/*");
        take.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(take, "Select File"), 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case Permission.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(userchosenTask.equals("Camera"))
                        cameraIntent();
                    else if (userchosenTask.equals("Gallery"))
                        galleryIntent();
                }
                else {
                    //deny action
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");
            ivimgholder.setImageBitmap(bitmap);
            encodeToBase64(bitmap);
        }
        else if (requestCode == 1 && resultCode == RESULT_OK){
            Bitmap bm = null;
            if (data != null){
                try {
                    bm = MediaStore.Images.Media.getBitmap(
                            getApplicationContext()
                                    .getContentResolver(),
                            data.getData());
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            ivimgholder.setImageBitmap(bm);
            encodeToBase64(bm);
        }
    }

    private void decode(String imgencoded) {
        if (imgencoded != null){
            Log.d("decode edit profile : ", imgencoded);
            byte[] data = Base64.decode(imgencoded, Base64.DEFAULT);
            Bitmap decodedbyte = BitmapFactory.decodeByteArray(data,0,data.length);

            ivimgholder.setImageBitmap(decodedbyte);
        }else {
            Log.d("decode edit profile : ", "tidak ada string base 64");
        }

    }

    private void encodeToBase64(Bitmap fotoBukti){
        Bitmap bitmap = fotoBukti;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] bytes = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(bytes,Base64.DEFAULT);
        Log.e("bitmap : ",imageEncoded);

        String idpayment = getIntent().getStringExtra("idpayment");
        String nobank = mnobank.getText().toString();
        String nama = mnama.getText().toString();
        String nominal = mnominal.getText().toString();

        uploadBukti(idpayment, nobank, nama, nominal, imageEncoded);
    }

    private void uploadBukti(String idpayment, String nobank, String nama, String nominal, String imageEncoded) {
        apiServices.addBukti(idpayment, nobank, nama, nominal, imageEncoded)
                .enqueue(new Callback<AddBuktiTF>() {
                    @Override
                    public void onResponse(Call<AddBuktiTF> call, Response<AddBuktiTF> response) {
                        if (response.isSuccessful() ){
                            Toast.makeText(BuktiTransfer.this, "Upload bukti sukses",
                                    Toast.LENGTH_LONG).show();
                            Intent a = new Intent(BuktiTransfer.this, MainMember.class);
                            startActivity(a);
                            finish();
                        }else{
                            Toast.makeText(BuktiTransfer.this, "Gagal Upload bukti", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<AddBuktiTF> call, Throwable t) {
                        Toast.makeText(BuktiTransfer.this, "Koneksi gagal",
                                Toast.LENGTH_LONG).show();

                    }
                });
    }
}
