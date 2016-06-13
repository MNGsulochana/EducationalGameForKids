package com.example.educationalgameforkids;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomDialogFragment extends DialogFragment {

    Dialog d;
    Button ok;
    String picturePath;
    ImageButton camera,galry;
    ImageView iv_photo;
    EditText et;
    TextView tv_welco;
    String name;
    Bitmap rotateimage;
    public static final int REQ_CD =1 ;
    private static final int RESULT_LOAD_IMAGE = 1;

    public CustomDialogFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        d=null;
            AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
            View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_custom_dialog, null);
            et = (EditText) v.findViewById(R.id.editText);
            tv_welco = (TextView) v.findViewById(R.id.text_welcome);
            camera = (ImageButton) v.findViewById(R.id.imageButton_camera);
            galry = (ImageButton) v.findViewById(R.id.imgbut_galry);
            iv_photo = (ImageView) v.findViewById(R.id.imageView_photo);


            ok = (Button) v.findViewById(R.id.but_ok);
            camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cam, REQ_CD);
                }
            });
            galry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, RESULT_LOAD_IMAGE);
                }
            });

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   String name = et.getText().toString();
                    if(name.equals(""))
                    {
                        Toast.makeText(getActivity(), "enter the name of kid", Toast.LENGTH_SHORT).show();
                    }
                   else {
                        MainActivity m = (MainActivity) getActivity();
                        m.calname(name,rotateimage);
                        d.dismiss();
                    }
                    //d.dismiss();
                }
            });

            ad.setView(v);
            d = ad.create();

        return d;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode==MainActivity.RESULT_OK)
        {
            if(requestCode==REQ_CD)
            {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

//HERE WE ARE GETTING THE IMAGE FROM GALLERY

                Cursor cursor = getActivity().managedQuery(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                getActivity().startManagingCursor(cursor);

//HERE I AM GETTING THE IMAGE FROM STRING VARIABLE "PICTUREPATH"
                Bitmap bmp=BitmapFactory.decodeFile(picturePath);

/*
//HERE  I AM SETTING THE ROTATION OF PICTURE BY USING EXIFINTERFACE

                ExifInterface e=null;
                try {
                    e=new ExifInterface(picturePath);//HERE WE ARE GIVING PICTURE LOCATION
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                int orientation=e.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED);

//HERE I AM CREATING OBJECT FOR MATRIX CLASS FOR SCALING,THIS CLASS IS FOR GRAPHICS
                Matrix m=new Matrix();
                switch (orientation)
                {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                         m.setRotate(90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        m.setRotate(180);
                        break;
                    default:
                        break;
                }
*/

                rotateimage=getResizedBitmap(bmp,1000,800);
                //rotateimage=Bitmap.createBitmap(bmp,0,0,bmp.getWidth(),bmp.getHeight(),m,true);
                //rotateimage=Bitmap.createScaledBitmap(bmp,0,0)
                iv_photo.setImageBitmap(rotateimage);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public Bitmap getResizedBitmap(Bitmap bmp,int newwidth,int newheight)
    {
        int width=bmp.getWidth();
        int height=bmp.getHeight();
        Log.d("UCHANDR",""+width);
        Log.d("UCHANDRAAA",""+height);
        float scalewidth,scaleheight;
        /*scalewidth=width/newwidth;
        scaleheight=height/newheight;*/
        scalewidth=((float)newwidth/width);
        scaleheight=((float)newheight/height);
        Log.d("UCHANDRSCWID",""+scalewidth);
        Log.d("UCHANDRAAASCHEIGHT",""+scaleheight);
        Matrix m=new Matrix();
       // m.postScale(scalewidth,scaleheight);
        //HERE  I AM SETTING THE ROTATION OF PICTURE BY USING EXIFINTERFACE

        ExifInterface e=null;
        try {
            e=new ExifInterface(picturePath);//HERE WE ARE GIVING PICTURE LOCATION
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        int orientation=e.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED);

//HERE I AM CREATING OBJECT FOR MATRIX CLASS FOR SCALING,THIS CLASS IS FOR GRAPHICS

        switch (orientation)
        {
            case ExifInterface.ORIENTATION_ROTATE_90:
                m.setRotate(90);
                m.postScale(scalewidth,scaleheight);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                m.setRotate(180);
                m.postScale(scalewidth,scaleheight);
                break;
            default:
                break;
        }

        Bitmap resizebitmap=Bitmap.createBitmap(bmp,0,0,width,height,m,false);
        return resizebitmap;
    }
}
