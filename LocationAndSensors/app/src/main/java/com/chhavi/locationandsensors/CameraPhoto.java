package com.chhavi.locationandsensors;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * Created by chhavi on 17/4/16.
 */
public class CameraPhoto extends AppCompatActivity {

    ImageView displayImage;

    public final static int CAPTURE_IMAGE = 102;
    public final static int SELECT_FILE = 103;
    public String  =  "photo.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_photo);

        displayImage = (ImageView) findViewById(R.id.display_image);
        Button chooseImage = (Button) findViewById(R.id.choice_button);
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }


    private void selectImage() {
        final CharSequence[] choices = {"Take Photo", "Pick From Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(CameraPhoto.this);
        builder.setTitle("Add Image");
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choices[which].equals("Take Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoName));

                    if(intent.resolveActivity(getPackageManager())!=null){
                        startActivityForResult(intent, CAPTURE_IMAGE);
                    }


                } else if (choices[which].equals("Pick From Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");

                    if(intent.resolveActivity(getPackageManager())!=null){
                        startActivityForResult(intent, SELECT_FILE);
                    }


                } else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public Uri getPhotoFileUri(String fileName) {
        if (isExternalStorageAvailable()) {

            File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "My APP");

            return Uri.fromFile(new File(mediaStorageDir.getPath()
                    + File.separator + fileName));
        }


        return null;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == CAPTURE_IMAGE){
                Uri uri = getPhotoFileUri(photoName);

                Bitmap  takenImage  = BitmapFactory.decodeFile(uri.getPath());

                displayImage.setImageBitmap(takenImage);
            }
            else if(requestCode == SELECT_FILE){
                if(data!=null){
                    Uri uri = data.getData();
                    Bitmap selectedImage;
                    try {
                        selectedImage= MediaStore.Images.Media.getBitmap(this.getContentResolver()
                        , uri);
                        displayImage.setImageBitmap(selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }
        }
    }
}