package com.example.pcd_double_exp;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.provider.MediaStore;

import java.io.IOException;


//glide

public class MainActivity extends AppCompatActivity {

    ImageView imageView, imageView2;
    SeekBar seekBar;
    TextView textView;
    Uri imageUri;


    Drawable drawable;
    Bitmap bitmap, bitmap2, bitmap3;

    Button button_img1, button_img2;

    int curProgress = 50;
    private static final int PICK_IMAGE = 100;
    private static final int PICK_IMAGE2 = 200;
    BitmapFactory.Options options;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //configureNextButton();

        button_img1 = (Button)findViewById(R.id.goNext);
        button_img2 = (Button)findViewById(R.id.changeImage);

        button_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery1();
            }
        });

        button_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery2();
            }
        });


        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView);

        seekBar.setMax(100);
        seekBar.setProgress(curProgress);

        imageView.setImageResource(R.drawable.peisaj);

        drawable = ContextCompat.getDrawable(this,R.drawable.portret);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        bitmap2 = convertImage(bitmap,curProgress);
        imageView2.setImageBitmap(bitmap2);

        textView.setText(""+curProgress+"%");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                curProgress = progress;
                textView.setText(""+curProgress+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                bitmap2 = convertImage(bitmap2,curProgress);
                imageView2.setImageBitmap(bitmap2);
            }
        });
    }


    private void openGallery1(){
//        //Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
////        //gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
////        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
////        intent.addCategory(Intent.CATEGORY_OPENABLE);
////        intent.setType("image/*");
////        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE);
        //Create an Intent with action as ACTION_PICK
        Intent intent1=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent1.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent1.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent1,PICK_IMAGE);
    }

    private void openGallery2(){
//        //Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        //gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
//        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("image/*");
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE2);
        //Create an Intent with action as ACTION_PICK
        Intent intent2=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent2.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent2.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent2,PICK_IMAGE2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //ArrayList<Uri> arrayUri = new ArrayList<Uri>();
        if(resultCode  != RESULT_CANCELED && requestCode == PICK_IMAGE){
            //ClipData mClipData = data.getClipData();
            //for(int i=0; i<mClipData.getItemCount();i++){
            //ClipData.Item item = mClipData.getItemAt(i);
            //imageUri = item.getUri();
            //arrayUri.add(imageUri);
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
        else if(resultCode  != RESULT_CANCELED && requestCode == PICK_IMAGE2){
            //ClipData mClipData = data.getClipData();
            //for(int i=0; i<mClipData.getItemCount();i++){
            //ClipData.Item item = mClipData.getItemAt(i);
            //imageUri = item.getUri();
            //arrayUri.add(imageUri);
            //imageUri = data.getData();
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imgDecodableString = cursor.getString(columnIndex);
            cursor.close();
            options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            bitmap2 = BitmapFactory.decodeFile(imgDecodableString, options);
            seekBar.setProgress(50);
            bitmap2 =convertImage(bitmap2,50);
            imageView2.setImageBitmap(bitmap2);


            //imageView2.setImageURI(imageUri);
//            try {
//                 bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }





    public static Bitmap convertImage(Bitmap original, int value){



        float A;
        int R,G,B;
        int colorPixel;
        int width = original.getWidth();
        int height = original.getHeight();
        Bitmap finalImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);


        for(int i=0; i<width; i++){
            for(int j=0; j<height;j++){
                colorPixel=original.getPixel(i,j);
                A = 255f/(100f/value);
                R= Color.red(colorPixel);
                G=Color.green(colorPixel);
                B=Color.blue(colorPixel);

                finalImage.setPixel(i,j,Color.argb((int) A,R,G,B));
            }
        }
        return finalImage;
    }

//    private void configureNextButton(){
//        Button nextButton = (Button) findViewById(R.id.goNext);
//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, Main2Activity.class));
//                //startActivity(new Intent(MainActivity.this, SelectPhotos.class));
//            }
//        });
//    }
}
