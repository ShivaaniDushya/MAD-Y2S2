package com.example.mobileapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Create_item_Activity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE_PP = 1 ;
    EditText ItemName_input, ItemBrand_input, ItemCount_input, BuyPriceItem_input, SellPriceItem_input, ItemDescrip_input;
    public boolean isfieldsvalidated=false;
    String issetasdefault="0";
    Button back;
    MaterialButton addItemBtn;
    Bundle bundle;
    String ItemName;
    File itemPhotoFile;


    @RequiresApi(api = VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        Log.d("workflow", "Add Items onCreate  method  Called");
        ItemName_input = findViewById(R.id.inp_itemname);
        ItemBrand_input = findViewById(R.id.inp_itembrand);
        ItemCount_input = findViewById(R.id.inp_itemcount);
        BuyPriceItem_input = findViewById(R.id.inp_itemBuyPrice);
        SellPriceItem_input = findViewById(R.id.inp_itemSellPrice);
        ItemDescrip_input = findViewById(R.id.inp_itemDescription);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.items);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.items:
                        return true;

                    case R.id.customers:
                        startActivity(new Intent(getApplicationContext()
                                , Customers.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.routes:
                        startActivity(new Intent(getApplicationContext()
                                , Routes.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.sales:
                        startActivity(new Intent(getApplicationContext()
                                , Sales.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                Log.d("workflow", "Add Item Bottom Navigation  method  Called");
                return false;
            }
        });

    }


//    private void dispatchTakePictureIntent(int REQUEST_IMAGE_CAPTURE) throws IOException {
//        Log.d("workflow", "dispatchTakePictureIntent");
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        Log.d("workflow", "Intent - " + takePictureIntent);
//        // Ensure that there's a camera activity to handle the intent
//        Log.d("workflow", "Intent not null pass ");
//        // Create the File where the photo should go
//        itemPhotoFile = null;
//        itemPhotoFile = createImageFile();
//        // Continue only if the File was successfully created
//        if (itemPhotoFile != null) {
//            Uri photoURI = FileProvider.getUriForFile(this,
//                    "com.example.mobileapplication.fileprovider",
//                    itemPhotoFile);
//            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//            Log.d("workflow", "takePictureIntent - " + String.valueOf(takePictureIntent));
//            Log.d("workflow", "takePictureIntent - " + String.valueOf(takePictureIntent.getExtras()));
//            Log.d("workflow", "takePictureIntent string - " + String.valueOf(takePictureIntent.getExtras()));
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        Log.d("workflow", "New Intent - " + String.valueOf(i));
//        Uri outputUri = FileProvider.getUriForFile(this, "com.example.mobileapplication.fileprovider", itemPhotoFile);
//        Log.d("workflow", "outputUri - " + String.valueOf(outputUri));
//
//        if (resultCode == RESULT_OK) {
//            Bitmap imageBitmap = null;
//            ItemImageURL = String.valueOf(outputUri);
//            try {
//                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), outputUri);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Log.d("workflow", "ImageBitmap - " + String.valueOf(imageBitmap));
//        }
//    }

//    String currentPhotoPath;
//    @SuppressLint("SimpleDateFormat")
//    private File createImageFile() throws IOException {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_DS_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,
//                ".jpg",
//                storageDir
//        );
//
//        currentPhotoPath = image.getAbsolutePath();
//        Log.d("workflow", "Photo path " + currentPhotoPath);
//        return image;
//    }


    @RequiresApi(api = VERSION_CODES.O)
        public void addItem(View view) {
            Log.d("workflow","Add Item addItem  method  Called");
            isfieldsvalidated = CheckAllFields();

          if (isfieldsvalidated) {
                DBHelper dbHelper = new DBHelper(this);
                //removedefault(issetasdefault);

                long val;
                val = dbHelper.addItem(ItemName_input.getText().toString(),
                        ItemBrand_input.getText().toString(),
                        Integer.parseInt(ItemCount_input.getText().toString()),
                        Double.parseDouble(BuyPriceItem_input.getText().toString()),
                        Double.parseDouble(SellPriceItem_input.getText().toString()),
                        ItemDescrip_input.getText().toString()

                        );

                Snackbar.make(view,"Record Added Succesfully", BaseTransientBottomBar.LENGTH_LONG).setAction("OK",null).show();

                Toast.makeText(this, "Record Added Succesfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, Items.class);
                startActivity(intent);
                Log.i("BTN Click", "Add Item Confirmation button clicked");
            }
}



        private boolean CheckAllFields() {
            Log.d("workflow","Add Item CheckAllFields  method  Called");
            if (ItemName_input.length() == 0) {
                ItemName_input.setError("This field is required");
                return false;
            }

            if (ItemBrand_input.length() == 0) {
                ItemBrand_input.setError("This field is required");
                return false;
            }

            if (ItemCount_input.length() == 0) {
                ItemCount_input.setError("This field is required");
                return false;
            }
            if (BuyPriceItem_input.length() == 0) {
                BuyPriceItem_input.setError("This field is required");
                return false;
            }

            if (SellPriceItem_input.length() == 0) {
                SellPriceItem_input.setError("This field is required");
                return false;
            }

            if (ItemDescrip_input.length()  == 0 ) {
                ItemDescrip_input.setError("This field is required");
                return false;
            }

            return true;

        }


//    public void openItemsActivity() {
//        Intent intent = new Intent(this, Items.class);
//        startActivity(intent);
//    }

}