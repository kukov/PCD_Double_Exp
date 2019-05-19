package com.example.pcd_double_exp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.provider.MediaStore;
import android.content.ClipData;


import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    Button button_gallery;
    ImageView imageview;
    Uri imageUri;
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        imageview = (ImageView) findViewById(R.id.imageView33);
        button_gallery = (Button) findViewById(R.id.hatz);


        button_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<Uri> arrayUri = new ArrayList<Uri>();
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            ClipData mClipData = data.getClipData();
            for (int i = 0; i < mClipData.getItemCount(); i++) {
                ClipData.Item item = mClipData.getItemAt(i);
                imageUri = item.getUri();
                arrayUri.add(imageUri);
            }
        }
    }
}
