package com.example.mobileapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class View_item_Activity extends AppCompatActivity{

    Bundle bundle;
    DBHelper db;
    TextView icode, iname, ibrand, ibuy, isell, icount, idesc;
    String ItemCode;
    Cursor cursor;
    Button btGenerate;
    ImageView ivOutput;


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

        ActivityCompat.requestPermissions(View_item_Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(View_item_Activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);


        try {
            bundle = getIntent().getExtras();
            ItemCode = bundle.getString("ItemCode");
            Log.d("workflow", "get ItemCode in earlier activity " + ItemCode);
            Log.i("TAG", "Thread ID " + Thread.currentThread().getId());
            loadItem(ItemCode);
        } catch (Exception e) {
            finish();
        }


        btGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sText = icode.getText().toString().trim() + ", " +
                        iname.getText().toString().trim() + ", " +
                        ibrand.getText().toString().trim() + ", " +
                        icount.getText().toString().trim() + ", " +
                        ibuy.getText().toString().trim() + ", " +
                        isell.getText().toString().trim() + ", " +
                        idesc.getText().toString().trim()  ;
                MultiFormatWriter writer = new MultiFormatWriter();
                try {
                    BitMatrix matrix = writer.encode(sText, BarcodeFormat.QR_CODE, 350, 350);
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    Bitmap bitmap = encoder.createBitmap(matrix);
                    ivOutput.setImageBitmap(bitmap);
//                    InputMethodManager manager = (InputMethodManager) getSystemService(
//                            Context.INPUT_METHOD_SERVICE
//                    );
//                    manager.hideSoftInputFromWindow(etInput.getApplicationWindowToken(), 0);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });

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
