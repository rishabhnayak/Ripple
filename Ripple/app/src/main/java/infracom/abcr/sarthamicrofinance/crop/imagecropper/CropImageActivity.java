/*
 *  COPYRIGHT NOTICE  
 *  Copyright (C) 2015, Jhuster <lujun.hust@gmail.com>
 *  https://github.com/Jhuster/ImageCropper
 *   
 *  @license under the Apache License, Version 2.0 
 *
 *  @file    CropImageActivity.java
 *  @brief   Image Cropper Activity
 *  
 *  @version 1.0     
 *  @author  Jhuster
 *  @date    2015/01/09    
 */
package infracom.abcr.sarthamicrofinance.crop.imagecropper;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

import infracom.abcr.sarthamicrofinance.R;


public class CropImageActivity extends Activity {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private View view;

    // PICK_PHOTO_CODE is a constant integer
    public final static int PICK_PHOTO_CODE = 1046;

    private Uri mImageCaptureUri=null, mImageCaptureUriTo;
    private Bitmap mBitmap;
    private static Bitmap bm;
    private static Bitmap bitmap;
    private Uri mInputPath  = null;
    private Uri mOutputPath = null;
    private CropImageView mCropImageView;
    private String selectedImagePath = "";
    TextView saveid;
int s=0;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;

    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";


    public static class CropParam {
        public int mAspectX = 0;
        public int mAspectY = 0;
        public int mOutputX = 0;
        public int mOutputY = 0;
        public int mMaxOutputX = 0;
        public int mMaxOutputY = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);           
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cropimage);
        mCropImageView = (CropImageView)findViewById(R.id.CropWindow);
        saveid=(TextView)findViewById(R.id.saveid);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras == null) {
            setResult(RESULT_CANCELED);
            return;
        }

        mOutputPath = extras.getParcelable(MediaStore.EXTRA_OUTPUT);
        if (mOutputPath == null) {
            String defaultPath = getCacheDir().getPath() + "tmp.jpg";
            mOutputPath = Uri.fromFile(new File(defaultPath));
        }

        mInputPath = intent.getData();
        if (mInputPath == null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermission();
            }
            else{
                startPickImage();
            }
           //
            return;
        }

        mBitmap = loadBitmap(mInputPath);
        if (mBitmap == null) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }

        mCropImageView.initialize(mBitmap,getCropParam(intent));
    }
    
    @Override
    protected void onDestroy() {
        mBitmap = null;
        mCropImageView.destroy();               
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

                if (s == 1) {
                    if (resultCode == RESULT_OK) {
                        Uri takenPhotoUri = getPhotoFileUri(photoFileName);
                        // by this point we have the camera photo on disk
                        Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
                        // RESIZE BITMAP, see section below
                        // Load the taken image into a preview
                        //ImageView ivPreview = (ImageView) findViewById(R.id.ivPreview);
                        //ivPreview.setImageBitmap(takenImage);

                        mCropImageView.initialize(takenImage, getCropParam(getIntent()));
                    } else { // Result was a failure
                        Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                    }
                }else{


                    mBitmap = loadBitmap(mInputPath);
                    mCropImageView.initialize(mBitmap, getCropParam(getIntent()));
/*
                    if (data != null) {
                        Uri photoUri = data.getData();
                        // Do something with the photo based on Uri
                        Bitmap selectedImage = null;
                        try {
                            selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);

                            mCropImageView.initialize(selectedImage, getCropParam(getIntent()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // Load the selected image into a preview

                    }
                    */
                }
            }
            }else {
        if (requestCode == 0) {

            if(s==2) {
                mInputPath = data.getData();
                mBitmap = loadBitmap(mInputPath);
                if (mBitmap == null) {
                    setResult(RESULT_CANCELED);
                    finish();
                    return;
                }
                mCropImageView.initialize(mBitmap, getCropParam(getIntent()));
            }else {

              //  selectedImagePath = mImageCaptureUri.getPath();
              //  Bitmap bm = decodeFile(selectedImagePath);

                mBitmap = loadBitmap(mInputPath);
                mCropImageView.initialize(mBitmap, getCropParam(getIntent()));

            }

            saveid.setVisibility(View.VISIBLE);
        }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public static Bitmap decodeFile(String path) {
        int orientation;
        try {
            if (path == null) {
                return null;
            }
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            // while (true) {
            // if (width_tmp / 2 < REQUIRED_SIZE
            // || height_tmp / 2 < REQUIRED_SIZE)
            // break;
            // width_tmp /= 2;
            // height_tmp /= 2;
            // scale++;
            // }
            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            bm = BitmapFactory.decodeFile(path, o2);
            bitmap = bm;

            ExifInterface exif = new ExifInterface(path);

            orientation = exif
                    .getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

            Log.e("ExifInteface .........", "rotation =" + orientation);

            // exif.setAttribute(ExifInterface.ORIENTATION_ROTATE_90, 90);

            Log.e("orientation", "" + orientation);
            Matrix m = new Matrix();

            if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
                m.postRotate(180);
                // m.postScale((float) bm.getWidth(), (float) bm.getHeight());
                // if(m.preRotate(90)){
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                m.postRotate(90);
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                m.postRotate(270);
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                return bitmap;
            }
            return bitmap;
        } catch (Exception e) {
            return null;
        }

    }

    public void onClickBack(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }
    
    public void onClickSave(View v) {
    	new SaveImageTask().execute(mCropImageView.getCropBitmap());
    }

    public void onClickRotate(View v) {
        mCropImageView.rotate();
        mCropImageView.invalidate();
    }
    
    public void onClickReset(View v) {
        mCropImageView.reset();
    }
    
    public void onClickCrop(View v) {
        mCropImageView.crop();
      //  TextView saveid =(TextView)findViewById(R.id.saveid);
       // saveid.setVisibility(View.VISIBLE);
    }
    
    private class SaveImageTask extends AsyncTask<Bitmap,Void,Boolean> {

    	private ProgressDialog mProgressDailog;
    	
    	private SaveImageTask() {
            mProgressDailog = new ProgressDialog(CropImageActivity.this);
            mProgressDailog.setCanceledOnTouchOutside(false);
            mProgressDailog.setCancelable(false);
    	}
    	
    	@Override
        protected void onPreExecute() {
            mProgressDailog.setTitle(getString(R.string.save));
            mProgressDailog.setMessage(getString(R.string.saving));
            mProgressDailog.show();
    	}
    	
    	@Override
        protected void onPostExecute(Boolean result) {
            if (mProgressDailog.isShowing()) {
            	mProgressDailog.dismiss();
            }
            setResult(RESULT_OK, new Intent().putExtra(MediaStore.EXTRA_OUTPUT,mOutputPath));
            finish();
    	}
    	
        @Override
        protected Boolean doInBackground(Bitmap... params) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mOutputPath);
                if (outputStream != null) {
                	params[0].compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } 
            catch (IOException e) {
            
            }
            finally {
                closeSilently(outputStream);
            }
            
            return Boolean.TRUE;
       }
    }
    
    protected Bitmap loadBitmap(Uri uri) {

    	Bitmap bitmap = null;
    	try {
            InputStream in = getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
    	}
    	catch (FileNotFoundException e) {
            Toast.makeText(this,"Can't found image file !",Toast.LENGTH_LONG).show();
        } 
        catch (IOException e) {
            Toast.makeText(this,"Can't load source image !",Toast.LENGTH_LONG).show();
        }
    	return bitmap;
    }
    
    protected Bitmap loadBitmapWithInSample(Uri uri) {
            
        final int MAX_VIEW_SIZE = 1024;
            
        InputStream in = null;
        try {
            in = getContentResolver().openInputStream(uri);
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();
            
            int scale = 1;
            if (o.outHeight > MAX_VIEW_SIZE || o.outWidth > MAX_VIEW_SIZE) {
                scale = (int) Math.pow(2,(int) Math.round(Math.log(MAX_VIEW_SIZE/(double) Math.max(o.outHeight, o.outWidth))/Math.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = getContentResolver().openInputStream(uri);
            Bitmap b = BitmapFactory.decodeStream(in, null, o2);
            in.close();

            return b;
        } 
        catch (FileNotFoundException e) {
                
        } 
        catch (IOException e) {
                
        }
        return null;
    }
        
    protected static void closeSilently(Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } 
        catch (Throwable t) {
        }
    }
    
    public static CropParam getCropParam(Intent intent) {
        CropParam params = new CropParam();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey(CropIntent.ASPECT_X) && extras.containsKey(CropIntent.ASPECT_Y)) {
                params.mAspectX = extras.getInt(CropIntent.ASPECT_X);
                params.mAspectY = extras.getInt(CropIntent.ASPECT_Y);
            }
            if (extras.containsKey(CropIntent.OUTPUT_X) && extras.containsKey(CropIntent.OUTPUT_Y)) {
                params.mOutputX = extras.getInt(CropIntent.OUTPUT_X);
                params.mOutputY = extras.getInt(CropIntent.OUTPUT_Y);
            }
            if (extras.containsKey(CropIntent.MAX_OUTPUT_X) && extras.containsKey(CropIntent.MAX_OUTPUT_Y)) {
                params.mMaxOutputX = extras.getInt(CropIntent.MAX_OUTPUT_X);
                params.mMaxOutputY = extras.getInt(CropIntent.MAX_OUTPUT_Y);
            }
        }               
        return params;
    }

    protected void startPickImage() {
        //Intent intent = new Intent(Intent.ACTION_PICK);
        //intent.setType("image/*");
        //startActivityForResult(intent,0);

        final String[] items = new String[] { "Camera",
                "Gallery" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { // pick from
                // camera
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (item == 0) {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName)); // set the image file name

                        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
                        // So as long as the result is not null, it's safe to use the intent.
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            // Start the image capture intent to take photo
                            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

                            s = 1;
                        }
                    }else{

                        s = 2;
                        //intent.setType("image/*");
                        //intent.setAction(Intent.ACTION_GET_CONTENT);
                        // Create intent for picking a photo from the gallery
                      /*  Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
                        // So as long as the result is not null, it's safe to use the intent.
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            // Bring up gallery to select a photo
                            startActivityForResult(intent, PICK_PHOTO_CODE);
                        }


*/


                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, 0);
                    }
                }else {

                    if (item == 0) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photo));
                        mInputPath = Uri.fromFile(photo);
                        startActivityForResult(intent, 0);


                        // intent.putExtra("return-data", true);

                        s = 1;

                    } else { // pick from file
                        //Intent intent = new Intent();
                        s = 2;


                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, 0);
                    }
                }
            }
        });

        final AlertDialog dialog = builder.create();

        dialog.show();

    }

    public void onBackPressed() {
        // Write your code here
        setResult(RESULT_CANCELED);
        finish();
       // super.onBackPressed();
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted){

                        Toast.makeText(this,"Permission Granted !",Toast.LENGTH_LONG).show();
                       // Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    startPickImage();}
                    else {

                        Toast.makeText(this,"Permission Denied, You cannot access location data and camera. !",Toast.LENGTH_LONG).show();
                       // Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(CropImageActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    // Returns the Uri for a photo stored on disk given the fileName
    public Uri getPhotoFileUri(String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use `getExternalFilesDir` on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(APP_TAG, "failed to create directory");
            }

            // Return the file target for the photo based on filename
            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
        }
        return null;
    }

    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
}
