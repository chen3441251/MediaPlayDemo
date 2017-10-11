package com.example.cc.mediaplaydemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class VideoViewActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        initView();
        initData();

    }

    private void initData() {
        checkPermission();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1002);
        }else {
            playVideo();
        }
    }

    private void playVideo() {
        mVideoView = (VideoView) findViewById(R.id.videoView);
        File file = new File(Environment.getExternalStorageDirectory(), "testmovie.mp4");
        //设置视频播放路径
        mVideoView.setVideoPath(file.getPath());
    }

    private void initView() {
        Button start = (Button) findViewById(R.id.btn_start);
        Button pause = (Button) findViewById(R.id.btn_pause);
        Button resume = (Button) findViewById(R.id.btn_resume);
        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        resume.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                if (!mVideoView.isPlaying()) {
                    mVideoView.start();
                }
                break;
            case R.id.btn_pause:
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                }
                break;
            case R.id.btn_resume:
//                if(mVideoView.isPlaying()){
                    mVideoView.resume();
//                }
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mVideoView!=null){
            mVideoView.suspend();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1002:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    playVideo();
                }else {
                    Toast.makeText(this,"请开启读写权限",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }
}
