package com.polarxiong.camerademo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

/**
 * Created by zhantong on 16/5/3.
 */
public class ShowPhotoVideo extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        Uri uri = getIntent().getData();
        if (getIntent().getType().equals("image/*")) {
            ImageView view = new ImageView(this);
            view.setImageURI(uri);
            view.setLayoutParams(layoutParams);
            relativeLayout.addView(view);
        } else {
            MediaController mc = new MediaController(this);
            VideoView view = new VideoView(this);
            mc.setAnchorView(view);
            mc.setMediaPlayer(view);
            view.setMediaController(mc);
            view.setVideoURI(uri);
            view.start();
            view.setLayoutParams(layoutParams);
            relativeLayout.addView(view);
        }
        setContentView(relativeLayout, layoutParams);
    }
}
