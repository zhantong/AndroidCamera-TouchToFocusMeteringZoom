package com.polarxiong.camerademo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by zhantong on 16/4/28.
 */
public class MainActivity extends Activity {
    private CameraPreview mPreview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCamera();
        Button buttonSettings = (Button) findViewById(R.id.button_settings);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.camera_preview, new SettingsFragment()).addToBackStack(null).commit();
            }
        });
        final ImageView mediaPreview = (ImageView) findViewById(R.id.media_preview);
        final Button buttonCapturePhoto = (Button) findViewById(R.id.button_capture_photo);
        buttonCapturePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreview.takePicture(mediaPreview);
            }
        });
        final Button buttonCaptureVideo = (Button) findViewById(R.id.button_capture_video);
        buttonCaptureVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPreview.isRecording()) {
                    mPreview.stopRecording(mediaPreview);
                    buttonCaptureVideo.setText("录像");
                } else {
                    if (mPreview.startRecording()) {
                        buttonCaptureVideo.setText("停止");
                    }
                }
            }
        });
        mediaPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowPhotoVideo.class);
                intent.setDataAndType(mPreview.getOutputMediaFileUri(), mPreview.getOutputMediaFileType());
                startActivityForResult(intent, 0);
            }
        });
    }

    private void initCamera() {
        mPreview = new CameraPreview(this);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        SettingsFragment.passCamera(mPreview.getCameraInstance());
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SettingsFragment.setDefault(PreferenceManager.getDefaultSharedPreferences(this));
        SettingsFragment.init(PreferenceManager.getDefaultSharedPreferences(this));
    }

    public void onPause() {
        super.onPause();
        mPreview = null;
    }

    public void onResume() {
        super.onResume();
        if (mPreview == null) {
            initCamera();
        }
    }
}
