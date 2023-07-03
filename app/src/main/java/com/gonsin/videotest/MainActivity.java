package com.gonsin.videotest;

import static xyz.doikki.videoplayer.player.BaseVideoView.STATE_PLAYBACK_COMPLETED;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.danikula.videocache.HttpProxyCacheServer;
import com.gonsin.videotest.cache.ProxyVideoCacheManager;

import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videocontroller.component.CompleteView;
import xyz.doikki.videoplayer.player.BaseVideoView;
import xyz.doikki.videoplayer.player.VideoView;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = findViewById(R.id.player);
        HttpProxyCacheServer cacheServer = ProxyVideoCacheManager.getProxy(this);
        String proxyUrl = cacheServer.getProxyUrl("http://192.168.2.166/files/kguAWFc47vzOCrqs.mp4");

        videoView.setUrl(proxyUrl);
        StandardVideoController controller = new StandardVideoController(this);
        controller.addControlComponent(new CompleteView(this));
        controller.setGestureEnabled(false);
        controller.setDoubleTapTogglePlayEnabled(false);
        videoView.setOnStateChangeListener(new BaseVideoView.OnStateChangeListener() {
            @Override
            public void onPlayerStateChanged(int playerState) {
                Log.e("OnStateChangeListener", "onPlayerStateChanged: " + playerState);
            }

            @Override
            public void onPlayStateChanged(int playState) {
                Log.e("OnStateChangeListener", "onPlayStateChanged: " + playState);
                if(playState == STATE_PLAYBACK_COMPLETED){
                    Log.e("OnStateChangeListener", "onPlayStateChanged: 停止播放");
                    videoView.replay(true);
                }
            }
        });
        videoView.setVideoController(controller);
        videoView.startFullScreen();
        videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.release();
    }


    @Override
    public void onBackPressed() {
        if (!videoView.onBackPressed()) {
            super.onBackPressed();
        }
    }
}