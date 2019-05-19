package com.example.pcd_double_exp;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class SelectPhotos extends AppCompatActivity {

        ImageView imageview;
        Button button_gallery, button_run;
        Uri imageUri;


        private static final int PICK_IMAGE = 100;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_select_photos);
            configureBackButton();


            imageview = (ImageView)findViewById(R.id.imageView10);
            button_gallery = (Button)findViewById(R.id.selectImages);
            button_run = (Button)findViewById(R.id.button_run1);

            button_gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openGallery();
                }
            });

            button_run.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doubleExposure();
                }
            });
        }

        private void openGallery(){
            //Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            //gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
            Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE);
        }

        private void doubleExposure(){ }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode,resultCode,data);
            //ArrayList<Uri> arrayUri = new ArrayList<Uri>();
            if(resultCode ==RESULT_OK && requestCode == PICK_IMAGE){
                //ClipData mClipData = data.getClipData();
                //for(int i=0; i<mClipData.getItemCount();i++){
                    //ClipData.Item item = mClipData.getItemAt(i);
                    //imageUri = item.getUri();
                    //arrayUri.add(imageUri);
                imageUri = data.getData();
                imageview.setImageURI(imageUri);
                }
        }

        private void configureBackButton(){
            Button backButton = (Button) findViewById(R.id.goBack);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
}
