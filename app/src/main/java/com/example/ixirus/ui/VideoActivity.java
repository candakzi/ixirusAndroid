package com.example.ixirus.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.ixirus.R;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        String videoUrl = getIntent().getExtras().getString("videoUrl");
        String extension = MimeTypeMap.getFileExtensionFromUrl(videoUrl);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
        mediaIntent.setDataAndType(Uri.parse(videoUrl), mimeType);
        startActivity(mediaIntent);


//        String videoUrl = getIntent().getExtras().getString("videoUrl");
//        VideoView videoView = (VideoView) findViewById(R.id.videoView);
//        MediaController mediaController = new MediaController(this);
//        mediaController.setAnchorView(videoView);
//        videoView.setMediaController(mediaController);
//        videoView.setVideoURI(Uri.parse(videoUrl));
//        videoView.requestFocus();
//
//        videoView.start();

//        WebView mWb = (WebView) findViewById(R.id.webView);
//        mWb.loadUrl("https://developers.google.com/training/images/tacoma_narrows.mp4");
//        mWb.getSettings().setJavaScriptEnabled(true);
    }
}
