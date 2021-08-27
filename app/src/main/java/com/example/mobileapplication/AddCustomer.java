package com.example.mobileapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class AddCustomer extends AppCompatActivity {

    TextInputEditText customerName, storeName, mobile, streetAddress, city;
    MaterialButton modifyBtn, addPP, addSP;
    ShapeableImageView storePPImg;
    boolean allFieldsValid = false;
    String AddStatusMsg, customerID, profilePicUri = null, storePicUri = null;
    Bundle bundle;
    DBHelper db;
    Cursor cursor;
    ImageView storeSPImg;
    static final int REQUEST_IMAGE_CAPTURE_PP = 1;
    static final int REQUEST_IMAGE_CAPTURE_SP = 2;
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        customerName = findViewById(R.id.customer_name);
        storeName = findViewById(R.id.store_name);
        mobile = findViewById(R.id.phone_number);
        streetAddress = findViewById(R.id.street_address);
        city = findViewById(R.id.city);
        modifyBtn = findViewById(R.id.modify_btn);
        addPP = findViewById(R.id.btn_pp_img);
        addSP = findViewById(R.id.btn_sp_img);
        storeSPImg = findViewById(R.id.sp_img);
        storePPImg = findViewById(R.id.pp_img);

        addPP.setOnClickListener(v -> dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE_PP));
        addSP.setOnClickListener(v -> dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE_SP));

        try {
            bundle = getIntent().getExtras();
            customerID = bundle.getString("CustomerID");
            modifyBtn.setText(getString(R.string.btn_update));
            setData(customerID);
        } catch (Exception ignore) { }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.customers);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.items:
                    startActivity(new Intent(getApplicationContext()
                            , Items.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.customers:
                    return true;

                case R.id.home:
                    startActivity(new Intent(getApplicationContext()
                            , MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.routes:
                    startActivity(new Intent(getApplicationContext()
                            , Routes.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.sales:
                    startActivity(new Intent(getApplicationContext()
                            , Sales.class));
                    overridePendingTransition(0,0);
                    return true;
            }

            return false;
        });
    }

    private void dispatchTakePictureIntent(int REQUEST_IMAGE_CAPTURE) {
        Log.d("workflow", "dispatchTakePictureIntent");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.d("workflow", "Intent - " + takePictureIntent);
        // Ensure that there's a camera activity to handle the intent
        Log.d("workflow", "Intent not null pass ");
        // Create the File where the photo should go
        photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ignored) { }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.mobileapplication.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            Log.d("workflow", "takePictureIntent - " + String.valueOf(takePictureIntent));
            Log.d("workflow", "takePictureIntent - " + String.valueOf(takePictureIntent.getExtras()));
            Log.d("workflow", "takePictureIntent string - " + String.valueOf(takePictureIntent.getExtras()));
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent i = new Intent(Intent.ACTION_VIEW);
        Log.d("workflow", "New Intent - " + String.valueOf(i));
        Uri outputUri = FileProvider.getUriForFile(this, "com.example.mobileapplication.fileprovider", photoFile);
        Log.d("workflow", "outputUri - " + String.valueOf(outputUri));

        if (resultCode == RESULT_OK) {
            Bitmap imageBitmap = null;
            switch (requestCode) {
                case 1:
                    try {
                        profilePicUri = String.valueOf(outputUri);
                        imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), outputUri);
                        Log.d("workflow", "ImageBitmap - " + String.valueOf(imageBitmap));
                    } catch (IOException ignored) {

                    }
                    storePPImg.setImageBitmap(imageBitmap);
                    break;
                case 2:
                    try {
                        storePicUri = String.valueOf(outputUri);
                        imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), outputUri);
                        Log.d("workflow", "ImageBitmap - " + String.valueOf(imageBitmap));
                    } catch (IOException ignored) {

                    }
                    storeSPImg.setImageBitmap(imageBitmap);
                    break;
            }
        }
    }

    String currentPhotoPath;
    @SuppressLint("SimpleDateFormat")
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_DS_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentPhotoPath = image.getAbsolutePath();
        Log.d("workflow", "Photo path " + currentPhotoPath);
        return image;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void modifyCustomer(View view) {
        if (TextUtils.isEmpty(customerID)) {
            insertCustomer();
        }
        else {
            updateCustomer();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertCustomer() {
        Log.d("workflow","Add insertCustomer  method  Called");
        allFieldsValid = CheckAllFields();

        if (allFieldsValid) {
            DBHelper dbHelper = new DBHelper(this);

            long val = dbHelper.insertCustomer(
                    Objects.requireNonNull(customerName.getText()).toString(),
                    Objects.requireNonNull(storeName.getText()).toString(),
                    Integer.parseInt(Objects.requireNonNull(mobile.getText()).toString()),
                    Objects.requireNonNull(streetAddress.getText()).toString(),
                    Objects.requireNonNull(city.getText()).toString(),
                    profilePicUri,
                    storePicUri
            );

            Log.d("workflow", String.valueOf(val));

            if (val == -1) {
                AddStatusMsg = "Customer added unsuccessful.";
            }
            else {
                AddStatusMsg = "Customer added successful.";
            }

            Intent intent = new Intent(this, Customers.class)
                    .putExtra("passMessage", AddStatusMsg);
            startActivity(intent);
            Log.d("BTN Click", "Save customer button clicked");
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateCustomer() {
        Log.d("workflow","Add updateCustomer  method  Called");
        allFieldsValid = CheckAllFields();

        if (allFieldsValid) {
            DBHelper dbHelper = new DBHelper(this);

            long val = dbHelper.updateCustomer(customerID,
                    Objects.requireNonNull(customerName.getText()).toString(),
                    Objects.requireNonNull(storeName.getText()).toString(),
                    Integer.parseInt(Objects.requireNonNull(mobile.getText()).toString()),
                    Objects.requireNonNull(streetAddress.getText()).toString(),
                    Objects.requireNonNull(city.getText()).toString(),
                    profilePicUri,
                    storePicUri
            );

            Log.d("workflow", String.valueOf(val));

            if (val == -1) {
                AddStatusMsg = "Customer updated unsuccessful.";
            }
            else {
                AddStatusMsg = "Customer updated successful.";
            }

            Intent intent = new Intent(this, Customers.class)
                    .putExtra("passMessage", AddStatusMsg);
            startActivity(intent);
            Log.d("BTN Click", "Save customer button clicked");
        }
    }

    private void setData(String customerID) {
        Log.i("TAG", "Thread ID " + Thread.currentThread().getId());
        Log.d("workflow", "loadCustomer initiated");
        db = new DBHelper(getApplicationContext());
        cursor = db.getExistingDataToUpdate(customerID);
        Log.d("workflow", "get row to cursor");
        if (cursor.getCount() == 0) {
            Log.d("workflow", "No customer");
        } else {
            if (cursor.moveToFirst()) {
                customerName.setText(cursor.getString(0));
                storeName.setText(cursor.getString(1));
                mobile.setText(cursor.getString(2));
                streetAddress.setText(cursor.getString(3));
                city.setText(cursor.getString(4));
                profilePicUri = cursor.getString(5);
                if (!profilePicUri.isEmpty()) {
                    storePPImg.setImageURI(Uri.parse(profilePicUri));
                }
                storePicUri = cursor.getString(6);
                if (!storePicUri.isEmpty()) {
                    storeSPImg.setImageURI(Uri.parse(storePicUri));
                }
            }
        }
    }

    private boolean CheckAllFields() {
        Log.d("workflow","Add customer CheckAllFields  method  Called");
        if (customerName.length() == 0) {
            customerName.setError("Customer name is required");
            return false;
        }

        if (storeName.length() == 0) {
            storeName.setError("Store name is required");
            return false;
        }

        if (mobile.length() == 0) {
            mobile.setError("Mobile is required");
            if (mobile.length() > 10) {
                mobile.setError("Mobile cannot exceed 10 numbers");
                return false;
            }
            return false;
        }

        if (streetAddress.length() == 0) {
            streetAddress.setError("Street Address is required");
            return false;
        }

        if (city.length() == 0) {
            city.setError("City is required");
            return false;
        }

        return true;
    }
}