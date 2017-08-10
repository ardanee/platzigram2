package com.rubik.platzigram.post;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.rubik.platzigram.R;
import com.squareup.picasso.Picasso;

public class PostActivity extends AppCompatActivity {
private ImageView imgPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        imgPhoto = (ImageView)findViewById(R.id.ivFoto);
        if(getIntent().getExtras() != null) {
            String photoPath = getIntent().getExtras().getString("PHOTO_PATH_TEMP");
            //Picasso.with(this).load(photoPath).into(imgPhoto);
           setPic(photoPath);
           // Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
            //imgPhoto.setImageBitmap(bitmap);
        }
    }

    private void setPic(String mCurrentPhotoPath) {
        // Get the dimensions of the View
        int targetW = 100;//imgPhoto.getWidth();
        int targetH = 100;//imgPhoto.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        //Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imgPhoto.setImageBitmap(bitmap);
    }

}
