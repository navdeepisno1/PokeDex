package com.suvidha.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class dashboard extends AppCompatActivity {

    LinearLayout linearLayout_val_A,linearLayout_val_B,linearLayout_val_C,linearLayout_val_D,linearLayout_val_E,linearLayout_val_0,linearLayout_val_1,linearLayout_val_2,linearLayout_val_3,linearLayout_val_4,linearLayout_clear,linearLayout_search;
    Context context = this;
    TextView textView_ssdpanel;
    private String ssdpanel_text="";
    ImageView imageView_key_sign,imageView_camera_start;
    String TAG="tag";
    private Camera mCamera;
    private CameraPreview mPreview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        linearLayout_val_0 = findViewById(R.id.dashboard_btn_0);
        linearLayout_val_1 = findViewById(R.id.dashboard_btn_1);
        linearLayout_val_2 = findViewById(R.id.dashboard_btn_2);
        linearLayout_val_3 = findViewById(R.id.dashboard_btn_3);
        linearLayout_val_4 = findViewById(R.id.dashboard_btn_4);
        linearLayout_val_A = findViewById(R.id.dashboard_btn_A);
        linearLayout_val_B = findViewById(R.id.dashboard_btn_B);
        linearLayout_val_C = findViewById(R.id.dashboard_btn_C);
        linearLayout_val_D = findViewById(R.id.dashboard_btn_D);
        linearLayout_val_E = findViewById(R.id.dashboard_btn_E);

        linearLayout_search = findViewById(R.id.dashboard_ll_search);
        linearLayout_clear = findViewById(R.id.dashboard_ll_clear);

        textView_ssdpanel = findViewById(R.id.dashboard_tv_ssdpanel);

        imageView_key_sign = findViewById(R.id.dashboard_iv_key_sign);
        imageView_camera_start = findViewById(R.id.dashboard_btn_camera_start);

        ssdpanel_text = textView_ssdpanel.getText().toString().trim();

        imageView_camera_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkCameraHardware(context))
                {
                    mCamera = getCameraInstance();
                    Camera.Parameters params = mCamera.getParameters();
                    params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                    mCamera.setParameters(params);
                    // Create our Preview view and set it as the content of our activity.
                    mPreview = new CameraPreview(context, mCamera);
                    FrameLayout preview = (FrameLayout) findViewById(R.id.dashboard_fragment_camera_p);
                    preview.addView(mPreview);

                }
                else
                {
                    Toast.makeText(context,"No Camera Found on this device",Toast.LENGTH_SHORT).show();
                }
            }
        });

        linearLayout_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearText();
            }
        });

        linearLayout_val_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addText("0");
            }
        });
        linearLayout_val_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addText("1");
            }
        });
        linearLayout_val_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addText("2");
            }
        });
        linearLayout_val_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addText("3");
            }
        });
        linearLayout_val_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addText("4");
            }
        });
        linearLayout_val_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addText("A");
            }
        });
        linearLayout_val_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addText("B");
            }
        });
        linearLayout_val_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addText("C");
            }
        });
        linearLayout_val_D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addText("D");
            }
        });
        linearLayout_val_E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addText("E");
            }
        });
    }

    private void addText(String s)
    {
        changeLED();
        ssdpanel_text = ssdpanel_text + s;
        textView_ssdpanel.setText(ssdpanel_text);
    }

    private void clearText()
    {
        changeLED();
        ssdpanel_text = "";
        textView_ssdpanel.setText("");

    }

    private void changeLED()
    {
        imageView_key_sign.setImageResource(R.drawable.icon_radar_green);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView_key_sign.setImageResource(R.drawable.icon_radar_yellow);
            }
        },100);
    }


    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Log.e("Camera","Present");
            return true;
        } else {
            Log.e("Camera","Absent");
            return false;
        }
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    /** A basic Camera preview class */
    public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder;
        private Camera mCamera;

        public CameraPreview(Context context, Camera camera) {
            super(context);
            mCamera = camera;

            // Install a SurfaceHolder.Callback so we get notified when the
            // underlying surface is created and destroyed.
            mHolder = getHolder();
            mHolder.addCallback(this);
            // deprecated setting, but required on Android versions prior to 3.0
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        public void surfaceCreated(SurfaceHolder holder) {
            // The Surface has been created, now tell the camera where to draw the preview.
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.setDisplayOrientation(90);
                mCamera.startPreview();
                mCamera.setAutoFocusMoveCallback(new Camera.AutoFocusMoveCallback() {
                    @Override
                    public void onAutoFocusMoving(boolean b, Camera camera) {

                    }
                });
            } catch (IOException e) {
                Log.d(TAG, "Error setting camera preview: " + e.getMessage());
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // empty. Take care of releasing the Camera preview in your activity.
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            // If your preview can change or rotate, take care of those events here.
            // Make sure to stop the preview before resizing or reformatting it.

            if (mHolder.getSurface() == null){
                // preview surface does not exist
                return;
            }

            // stop preview before making changes
            try {
                mCamera.stopPreview();
            } catch (Exception e){
                // ignore: tried to stop a non-existent preview
            }

            // set preview size and make any resize, rotate or
            // reformatting changes here

            // start preview with new settings
            try {
                mCamera.setPreviewDisplay(mHolder);
                mCamera.setDisplayOrientation(0);
                mCamera.startPreview();

            } catch (Exception e){
                Log.d(TAG, "Error starting camera preview: " + e.getMessage());
            }
        }
    }



}