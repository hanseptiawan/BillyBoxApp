package com.box.billy.billybox.Main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.box.billy.billybox.Model.DataBodyUser;
import com.box.billy.billybox.Model.GetUser2;
import com.box.billy.billybox.Model.GetUserResponse2;
import com.box.billy.billybox.Model.UpdateDataUser;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiServicesLokal;
import com.box.billy.billybox.Rest.ApiUtils;
import com.box.billy.billybox.Utils.Datepicker_Fragment;
import com.box.billy.billybox.Utils.Permission;
import com.box.billy.billybox.Utils.SessionManager;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by han on 7/19/2018.
 */

public class EditProfil extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    EditText et_fname, et_lname, et_address, et_ttl, et_nohp, et_nohp_holder,
            et_username, et_password1, et_password2;
    ImageView iv_upload, iv_date;
    Button btn_submit;
    Bitmap bitmap;
    CircleImageView circleImageView;
    String userchosenTask;
    SessionManager sessionManager;
    ApiServices apiServices;
//    ApiServicesLokal apiServices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        sessionManager = new SessionManager(getApplicationContext());
        apiServices = ApiUtils.getApiServices();

        et_fname = findViewById(R.id.et_fname);
        et_lname = findViewById(R.id.et_lname);
        et_address = findViewById(R.id.et_address);
        et_ttl = findViewById(R.id.et_ttl);
        et_nohp = findViewById(R.id.et_nohp);
        et_username = findViewById(R.id.et_username);
        et_password1 = findViewById(R.id.et_password_signup1);
        et_password2 = findViewById(R.id.et_password_signup2);
        iv_date = findViewById(R.id.iv_date);
        iv_upload= findViewById(R.id.iv_upload);
        btn_submit = findViewById(R.id.btn_submit);
        circleImageView = findViewById(R.id.circular_profile2);
        et_nohp_holder = findViewById(R.id.et_nohp_awal);

        HashMap<String, String> user = sessionManager.getUserDetails();
        String imguser = user.get(sessionManager.KEY_IMG);

        if (imguser == null){
            HashMap<String, String> imgsession = sessionManager.getImg();
            String a = imgsession.get(sessionManager.KEY_IMGBASE64);
            Log.d("img dr session img :", a);
            decode(a);
        } else {
            Log.d("img session", "img blm ada");
        }

        String username = getIntent().getStringExtra("username");

        getUser(username);

        iv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        iv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datepicker = new Datepicker_Fragment();
                datepicker.show(getSupportFragmentManager(), "date picker");

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = et_fname.getText().toString().trim();
                String lname = et_lname.getText().toString().trim();
                String username = et_username.getText().toString().trim();
                String password1 = et_password1.getText().toString().trim();
                String password2 = et_password2.getText().toString().trim();
                String alamat = et_address.getText().toString();
                String notelp = et_nohp.getText().toString();
                String ttl = et_ttl.getText().toString();

                HashMap<String, String> user = sessionManager.getUserDetails();
                String userID = user.get(sessionManager.KEY_ID);

                Log.d("userID :", userID);

                HashMap<String, String> img = sessionManager.getImg();
                String imgencoded = img.get(sessionManager.KEY_IMGBASE64);

                if (validation(fname, lname,password1, password2, alamat, notelp)){
                    updateprofile(userID,fname,lname, imgencoded,username,
                            password2,alamat,notelp,ttl);
                }
            }
        });

    }

    private void getUser(String userid) {
        apiServices.getUserbyID(userid)
                .enqueue(new Callback<GetUserResponse2>() {
                    @Override
                    public void onResponse(Call<GetUserResponse2> call, Response<GetUserResponse2> response) {
                        if (response.body() != null){
                            DataBodyUser dataBodyUser = response.body().getDataBody();
                            GetUser2 getUser2 = dataBodyUser.get0();

                            et_fname.setText(getUser2.getNamaDepan());
                            et_lname.setText(getUser2.getNamaBelakang());
                            et_ttl.setText(getUser2.getTglLahir());
                            et_nohp_holder.setEnabled(false);
                            et_ttl.setEnabled(false);
                            et_nohp.setText(getUser2.getNoTelp());
                            et_address.setText(getUser2.getAlamat());
                            et_username.setText(getUser2.getUsername());
                            et_username.setEnabled(false);
                            et_password1.setText(getUser2.getPassword());

                            Glide.with(getApplicationContext())
                                    .load(getUser2.getMediaUrl())
                                    .fitCenter()
                                    .placeholder(R.drawable.ic_noimg)
                                    .error(R.drawable.ic_broken_image)
                                    .into(circleImageView);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetUserResponse2> call, Throwable t) {

                    }
                });
    }

    private void selectImage() {
        final CharSequence[] items = {"Camera", "Gallery","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfil.this);
        builder.setTitle("Upload photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Permission.checkPermission(EditProfil.this);
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
            circleImageView.setImageBitmap(bitmap);
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
            circleImageView.setImageBitmap(bm);
            encodeToBase64(bm);
        }
    }

    private void decode(String imgencoded) {
        if (imgencoded != null){
            Log.d("decode edit profile : ", imgencoded);
            byte[] data = Base64.decode(imgencoded, Base64.DEFAULT);
            Bitmap decodedbyte = BitmapFactory.decodeByteArray(data,0,data.length);

            circleImageView.setImageBitmap(decodedbyte);
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
        sessionManager.createImg(imageEncoded);
    }

    private boolean validation(String fname, String lname, String password1, String password2,
                               String alamat, String notelp) {
        if (fname == null || fname.trim().length() == 0){
            Toast.makeText(EditProfil.this, "Silahkan isi nama depan anda",
                    Toast.LENGTH_LONG).show();
            et_fname.setFocusable(true);
            return false;
        }
        else if(lname == null || lname.trim().length() == 0){
            Toast.makeText(EditProfil.this, "Silahkan isi nama belakang anda",
                    Toast.LENGTH_LONG).show();
            et_lname.setFocusable(true);
            return false;

        }
        else if(password1 == null || password1.trim().length() == 0){
            Toast.makeText(EditProfil.this, "Silahkan isi password anda",
                    Toast.LENGTH_LONG).show();
            et_password1.setFocusable(true);
            return false;

        }
        else if(password2 == null || password2.trim().length() == 0){
            Toast.makeText(EditProfil.this, "Silahkan konfirmasi password anda",
                    Toast.LENGTH_LONG).show();
            et_password2.setFocusable(true);
            return false;

        }
        else if(!password1.equals(password2)){
            Toast.makeText(EditProfil.this, "Konfirmasi password tidak sama, silahkan diulangi",
                    Toast.LENGTH_LONG).show();
            et_password1.setText("");
            et_password2.setText("");
            et_password1.setFocusable(true);
            return false;

        }
        else if(alamat == null || alamat.trim().length() == 0){
            Toast.makeText(EditProfil.this, "Silahkan isi alamat anda",
                    Toast.LENGTH_LONG).show();
            et_address.setFocusable(true);
            return false;
        }
        else if(notelp == null || notelp.trim().length() == 0){
            Toast.makeText(EditProfil.this, "Silahkan isi nomor telepon anda",
                    Toast.LENGTH_LONG).show();
            et_nohp.setFocusable(true);
            return false;
        }
        return true;
    }

    private void updateprofile(String userID, final String fname, final String lname, String imgencoded,
                               final String username, final String password, final String alamat, final String notelp,
                               final String ttl) {
        Log.d("data parameter : ", userID+fname+lname+imgencoded+username+password+alamat
        +notelp+ttl);

            apiServices.updateDataUser(fname, lname, imgencoded,
                    username, password,alamat, notelp, ttl,userID)
                    .enqueue(new Callback<UpdateDataUser>() {
                        @Override
                        public void onResponse(Call<UpdateDataUser> call, Response<UpdateDataUser> response) {
                            if (response.isSuccessful()){
                                Log.d("response : ", String.valueOf(response));
                                sessionManager.updateUserSession(fname,lname,username,password,alamat,notelp,ttl);
                                Log.d("update session : ", fname+lname+username+password+alamat+notelp+ttl);

                                Toast.makeText(EditProfil.this, "Update profil sukses",
                                        Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateDataUser> call, Throwable t) {
                            Log.d("call : ", String.valueOf(call));
                            Log.d("throwable : ", String.valueOf(t));
                            Toast.makeText(EditProfil.this, "Update profil gagal",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        et_ttl = findViewById(R.id.et_ttl);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        et_ttl.setText(year+"-"+month+"-"+day);
    }
}
