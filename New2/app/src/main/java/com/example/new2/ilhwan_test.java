package com.example.new2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.ColorUtils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.new2.R;
import com.example.new2.result;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ilhwan_test extends AppCompatActivity{

    //얼굴인식을 위한 변수
    Button btn;
    Bitmap retBitmap = null;
    Bitmap finalBitmap1 = null;
    Bitmap finalBitmap2 = null;

    //카메라 구동,사진 선택을 위한 변수
    private static final int MY_PERMISSION_CAMERA = 1111;
    private static final int REQUEST_TAKE_PHOTO = 2222;
    private static final int REQUEST_TAKE_ALBUM = 3333;
    private static final int REQUEST_IMAGE_CROP = 4444;

    Button btn_capture, btn_album;
    ImageView iv_view;

    String mCurrentPhotoPath;

    Uri imageUri;
    Uri photoURI, albumURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilhwan_test);

        //이전, 홈버튼
        Button bt_prev = findViewById(R.id.bt_prev);
        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_prev=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_prev);
            }
        });

        Button bt_home = findViewById(R.id.bt_home);
        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_home=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_home);
            }
        });

        btn = findViewById(R.id.btn);
        iv_view = findViewById(R.id.iv_view);

        btn_capture = (Button) findViewById(R.id.btn_capture);
        btn_album = (Button) findViewById(R.id.btn_album);

        btn_capture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                captureCamera();
            }
        });

        btn_album.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getAlbum();
            }
        });

        checkPermission();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable = true;
                BitmapDrawable bitDraw = (BitmapDrawable) iv_view.getDrawable();
                Bitmap myBitmap = bitDraw.getBitmap();

                Paint myRectPaint = new Paint();
                myRectPaint.setStrokeWidth(5);
                myRectPaint.setColor(Color.RED);
                myRectPaint.setStyle(Paint.Style.STROKE);

                Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(),
                        myBitmap.getHeight(),
                        Bitmap.Config.RGB_565);
                Canvas tempCanvas = new Canvas(tempBitmap);
                tempCanvas.drawBitmap(myBitmap,0,0,null);

                FaceDetector faceDetector = new
                        FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false)
                        .build();
                if(!faceDetector.isOperational()){
                    new AlertDialog.Builder(v.getContext()).setMessage("Could not set up the face detector!").show();

                    return;
                }
                Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                SparseArray<Face> faces = faceDetector.detect(frame);

                //A4용지 색상 추출
                int A4Red = 0;
                int A4Green = 0;
                int A4Blue = 0;
                int A4pixelCount = 0;

                int A4W = myBitmap.getWidth();
                int A4H = myBitmap.getHeight();

                Bitmap A4Bitmap =  Bitmap.createBitmap(myBitmap,(int)(A4W/2),(int)(A4H/3),(int)(A4W/2),(int)(A4H/3));

                //A4용지만 추출하는 알고리즘
                for (int y = 0; y < A4Bitmap.getHeight(); y++){
                    for (int x = 0; x < A4Bitmap.getWidth(); x++){
                        int c = A4Bitmap.getPixel(x,y);
                        if(Color.red(c) >180 && Color.green(c) >180 && Color.blue(c) > 180) {
                            if(Color.red(c) >= Color.green(c) - 15 && Color.red(c) >= Color.blue(c) - 15) {
                                if(Color.green(c) >= Color.red(c) - 15 && Color.green(c) >= Color.blue(c) - 15) {
                                    if(Color.blue(c) >= Color.red(c) - 15 && Color.blue(c) >= Color.green(c) - 15) {
                                        A4pixelCount++;
                                        A4Red += Color.red(c);
                                        A4Green += Color.green(c);
                                        A4Blue += Color.blue(c);
                                    }
                                }
                            }
                        }
                    }
                }
                int A4red = (A4Red/A4pixelCount);
                int A4green = (A4Green/A4pixelCount);
                int A4blue = (A4Blue/A4pixelCount);

                //얼굴인식
                for(int i=0; i<faces.size();i++){
                    Face thisFace = faces.valueAt(i);
                    float x1 = thisFace.getPosition().x;
                    float y1 = thisFace.getPosition().y;
                    float x2 = x1 + thisFace.getWidth();
                    float y2 = y1 + thisFace.getHeight();
                    tempCanvas.drawRoundRect(new RectF(x1,y1,x2,y2),2,2,myRectPaint);
                    retBitmap = Bitmap.createBitmap(myBitmap,(int)x1,(int)y1,(int)thisFace.getWidth(),(int)thisFace.getHeight());
                    int bitW = retBitmap.getWidth();
                    int bitH = retBitmap.getHeight();

                    //얼굴인식 자른것에서 양쪽 광대에서 볼까지 피부 색 추출
                    finalBitmap1 = Bitmap.createBitmap(retBitmap,(int)(bitW/5),(int)(bitH/1.8),(int)(bitW/5),(int)(bitH/5));
                    finalBitmap2 = Bitmap.createBitmap(retBitmap,(int)(bitW/1.65),(int)(bitH/1.8),(int)(bitW/5),(int)(bitH/5));
                }
                iv_view.setImageBitmap(retBitmap);
                //피부 추출한 것에서 RGB값 뽑아내기
                int redColors = 0;
                int greenColors = 0;
                int blueColors = 0;
                int pixelCount = 0;

                for (int y = 0; y < finalBitmap1.getHeight(); y++){
                    for (int x = 0; x < finalBitmap1.getWidth(); x++){
                        int c = finalBitmap1.getPixel(x,y);
                        pixelCount++;
                        redColors += Color.red(c);
                        greenColors += Color.green(c);
                        blueColors += Color.blue(c);
                    }
                }

                for (int y = 0; y < finalBitmap2.getHeight(); y++){
                    for (int x = 0; x < finalBitmap2.getWidth(); x++){
                        int c = finalBitmap2.getPixel(x,y);
                        pixelCount++;
                        redColors += Color.red(c);
                        greenColors += Color.green(c);
                        blueColors += Color.blue(c);
                    }
                }

                int red = (redColors/pixelCount);
                int green = (greenColors/pixelCount);
                int blue = (blueColors/pixelCount);
                //red,green,blue는 사진에서 피부 RGB A4red,A4green,A4blue는 사진에서 A4용지 RGB
                //보정 컬러 A4의 디폴트 값은 RGB(230 230 230) (L = 92 a,b = 0)으로 잡았음
                //Lab로 변환해서 보정하나 rgb값에서 보정하나같은 결과가 나왔기 때문에 먼저 보정해줌
                int faceRGB_R = 230 - A4red + red;
                int faceRGB_G = 230 - A4green + green;
                int faceRGB_B = 230 - A4blue + blue;

                //rgb -> Lab
                double [] LabFace = new double[3];
                LabFace = rgbToLab(faceRGB_R,faceRGB_G,faceRGB_B);

                //rgb -> HSV
                float[] HSVFace = new float[3];
                Color.RGBToHSV(faceRGB_R,faceRGB_G,faceRGB_B,HSVFace);

                int[] percent = new int[4];
                int y = 0;

                if (LabFace[2] >= 15){
                    percent[1] = 0;
                    percent[3] = 0;

                    if(HSVFace[2] <= 88){
                        y = (int)(250*HSVFace[2] - 170);
                        percent[0] = y;
                        percent[2] = 100 - y;
                    }
                    else{
                        y = (int)(500 * HSVFace[2] - 390);
                        percent[0] = 100 - y;
                        percent[2] = y;
                    }
                }
                else{
                    percent[0] = 0;
                    percent[2] = 0;
                    y = (int)(500 * HSVFace[1] - 85);
                    percent[1] = 100 - y;
                    percent[3] = y;
                }

                Intent intent = new Intent(getApplicationContext(), result.class);
                intent.putExtra("percent",percent);
                startActivity(intent);
            }
        });
    }

    //RGB를 Lab로 바꿔주는 함수
    String TAG ="RGB";
    public double[] rgbToLab(int R, int G, int B) {
        double[] lab=new double[3];
        double r, g, b, X, Y, Z, xr, yr, zr;
        ColorUtils.RGBToLAB(R,G,B,lab);
        //Core.absdiff();

        // D65/2°
        double Xr = 95.047;
        double Yr = 100.0;
        double Zr = 108.883;


        // --------- RGB to XYZ ---------//

        r = R/255.0;
        g = G/255.0;
        b = B/255.0;

        if (r > 0.04045)
            r = Math.pow((r+0.055)/1.055,2.4);
        else
            r = r/12.92;

        if (g > 0.04045)
            g = Math.pow((g+0.055)/1.055,2.4);
        else
            g = g/12.92;

        if (b > 0.04045)
            b = Math.pow((b+0.055)/1.055,2.4);
        else
            b = b/12.92 ;

        r*=100;
        g*=100;
        b*=100;
        Log.d(TAG,"R:"+r+" G:"+g+" B:"+b);
        X =  0.4124*r + 0.3576*g + 0.1805*b;
        Y =  0.2126*r + 0.7152*g + 0.0722*b;
        Z =  0.0193*r + 0.1192*g + 0.9505*b;


        // --------- XYZ to Lab --------- //

        xr = X/Xr;
        yr = Y/Yr;
        zr = Z/Zr;

        if ( xr > 0.008856 )
            xr =  (float) Math.pow(xr, 1/3.);
        else
            xr = (float) ((7.787 * xr) + 16 / 116.0);

        if ( yr > 0.008856 )
            yr =  (float) Math.pow(yr, 1/3.);
        else
            yr = (float) ((7.787 * yr) + 16 / 116.0);

        if ( zr > 0.008856 )
            zr =  (float) Math.pow(zr, 1/3.);
        else
            zr = (float) ((7.787 * zr) + 16 / 116.0);

        lab[0] = (116*yr)-16;
        lab[1] = 500*(xr-yr);
        lab[2] = 200*(yr-zr);

        return lab;

    }

    //사진찍기를 위함 메소드
    private void captureCamera(){
        String state = Environment.getExternalStorageState();
        //외장 메모리 검사임
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Log.e("captureCamera Error", ex.toString());
                }
                if (photoFile != null) {
                    // getUriForFile의 두 번째 인자는 Manifest provier의 authorites와 일치해야 함

                    Uri providerURI = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    imageUri = providerURI;

                    // 인텐트에 전달할 때는 FileProvier의 Return값인 content://로만!!, providerURI의 값에 카메라 데이터를 넣어 보냄
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);

                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        } else {
            Toast.makeText(this, "저장공간이 접근 불가능한 기기입니다", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File imageFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "gyeom");

        if (!storageDir.exists()){
            Log.i("mCurrentPhotoPath1", storageDir.toString());
            storageDir.mkdirs();
        }
        imageFile = new File(storageDir,imageFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();

        return imageFile;
    }

    private void getAlbum(){
        Log.i("getAlbum", "Call");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }

    private void galleryAddPic(){
        Log.i("galleryAddPic", "Call");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // 해당 경로에 있는 파일을 객체화(새로 파일을 만든다는 것으로 이해하면 안 됨)
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(this, "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    // 카메라 전용 크랍
    public void cropImage(){
        Log.i("cropImage", "Call");
        Log.i("cropImage", "photoURI : " + photoURI + " / albumURI : " + albumURI);

        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        // 50x50픽셀미만은 편집할 수 없다는 문구 처리 + 갤러리, 포토 둘다 호환하는 방법
        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropIntent.setDataAndType(photoURI, "image/*");
        //cropIntent.putExtra("outputX", 200); // crop한 이미지의 x축 크기, 결과물의 크기
        //cropIntent.putExtra("outputY", 200); // crop한 이미지의 y축 크기
        cropIntent.putExtra("aspectX", 1); // crop 박스의 x축 비율, 1&1이면 정사각형
        cropIntent.putExtra("aspectY", 1); // crop 박스의 y축 비율
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("output", albumURI); // 크랍된 이미지를 해당 경로에 저장
        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Log.i("REQUEST_TAKE_PHOTO", "OK");
                        galleryAddPic();

                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap = MediaStore.Images.Media
                                .getBitmap(getContentResolver(),imageUri);

                        if (bitmap != null){
                            ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
                            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                    ExifInterface.ORIENTATION_UNDEFINED);

                            Bitmap rotatedBitmap = null;
                            switch(orientation){
                                case ExifInterface.ORIENTATION_ROTATE_90:
                                    rotatedBitmap = rotateImage(bitmap,90);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_180:
                                    rotatedBitmap = rotateImage(bitmap,180);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_270:
                                    rotatedBitmap = rotateImage(bitmap,270);
                                    break;

                                case ExifInterface.ORIENTATION_NORMAL:
                                default:
                                    rotatedBitmap = bitmap;
                            }
                            iv_view.setImageBitmap(rotatedBitmap);
                        }
                    } catch (Exception e) {
                        Log.e("REQUEST_TAKE_PHOTO", e.toString());
                    }
                } else {
                    Toast.makeText(ilhwan_test.this, "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show();
                }
                break;

            case REQUEST_TAKE_ALBUM:
                if (resultCode == Activity.RESULT_OK) {

                    if(data.getData() != null){
                        try {
                            File albumFile = null;
                            albumFile = createImageFile();
                            photoURI = data.getData();
                            albumURI = Uri.fromFile(albumFile);
                            cropImage();
                        }catch (Exception e){
                            Log.e("TAKE_ALBUM_SINGLE ERROR", e.toString());
                        }
                    }
                }
                break;

            case REQUEST_IMAGE_CROP:
                if (resultCode == Activity.RESULT_OK) {

                    galleryAddPic();
                    iv_view.setImageURI(albumURI);
                }
                break;
        }
    }

    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) ||
                    (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))) {
                new AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSION_CAMERA);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_CAMERA:
                for (int i = 0; i < grantResults.length; i++) {
                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
                    if (grantResults[i] < 0) {
                        Toast.makeText(ilhwan_test.this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면 이 부분에서..

                break;
        }
    }

    //카메라로 찍으면 회전하는 경우 방지해주기
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
}