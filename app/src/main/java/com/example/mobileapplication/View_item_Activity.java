package com.example.mobileapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class View_item_Activity extends AppCompatActivity{

    Bundle bundle;
    DBHelper db;
    TextView icode, iname, ibrand, ibuy, isell, icount, idesc;
    String ItemCode;
    Cursor cursor;
    Button btGenerate;
    ImageView ivOutput;
    ImageView shareQR;
    BitmapDrawable drawable;
    Bitmap bit;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        icode = findViewById(R.id.icode_view);
        iname = findViewById(R.id.iname_view);
        ibrand = findViewById(R.id.ibrand_view);
        ibuy = findViewById(R.id.ibuy_view);
        isell = findViewById(R.id.isell_view);
        icount = findViewById(R.id.icount_view);
        idesc = findViewById(R.id.idesc_view);
        btGenerate = findViewById(R.id.qrgenerate_btn);
        ivOutput = findViewById(R.id.iv_output);
        shareQR = findViewById(R.id.sharebtn);


        try {
            bundle = getIntent().getExtras();
            ItemCode = bundle.getString("ItemCode");
            Log.d("workflow", "get ItemCode in earlier activity " + ItemCode);
            Log.i("TAG", "Thread ID " + Thread.currentThread().getId());
            loadItem(ItemCode);
        } catch (Exception e) {
            finish();
        }


        btGenerate.setOnClickListener(v -> {

            String sText = icode.getText().toString().trim() + ", " +
                    iname.getText().toString().trim() + ", " +
                    ibrand.getText().toString().trim() + ", " +
                    icount.getText().toString().trim() + ", " +
                    ibuy.getText().toString().trim() + ", " +
                    isell.getText().toString().trim() + ", " +
                    idesc.getText().toString().trim();
            MultiFormatWriter writer = new MultiFormatWriter();
            Bitmap bitmap;
            try {
                BitMatrix matrix = writer.encode(sText, BarcodeFormat.QR_CODE, 350, 350);
                BarcodeEncoder encoder = new BarcodeEncoder();
                bitmap = encoder.createBitmap(matrix);
                ivOutput.setImageBitmap(bitmap);

            } catch (WriterException e) {
                e.printStackTrace();
            }



        });

        shareQR.setOnClickListener(v -> {
            try {
                shareImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.items);

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

    private void shareImage() throws IOException {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        drawable = (BitmapDrawable) ivOutput.getDrawable();
        bit = drawable.getBitmap();
        File image = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ "/"+"qr_code" + ".png");
        FileOutputStream outputStream = new FileOutputStream(image);
        bit.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        outputStream.flush();
        outputStream.close();
        Uri photoUri = FileProvider.getUriForFile(this, "com.example.mobileapplication.fileprovider", image);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM, photoUri);
        sendIntent.setType("image/*");
        Intent chooser = Intent.createChooser(sendIntent, "Share QR Via: ");
        @SuppressLint("QueryPermissionsNeeded") List<ResolveInfo> resInfoList = this.getPackageManager().queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            this.grantUriPermission(packageName, photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivity(chooser);
    }

    private void loadItem(String ItemCode) {
        new Thread(() -> {
            Log.i("TAG", "Thread ID " + Thread.currentThread().getId());
            Log.d("workflow", "loadItem initiated");
            db = new DBHelper(getApplicationContext());
            cursor = db.readOneItem(ItemCode);
            Log.d("workflow", "get row to cursor");
            if (cursor.getCount() == 0) {
                Log.d("workflow", "No Item");
            }
            else {
                if (cursor.moveToFirst()) {
                    icode.setText(cursor.getString(0));
                    iname.setText(cursor.getString(1));
                    ibrand.setText(cursor.getString(2));
                    icount.setText(cursor.getString(3));
                    ibuy.setText(cursor.getString(4));
                    isell.setText(cursor.getString(5));
                    idesc.setText(cursor.getString(6));
                }
            }

        }).start();
    }

}
