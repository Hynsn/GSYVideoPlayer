package com.example.gsyvideoplayer.simple;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gsyvideoplayer.R;
import com.example.gsyvideoplayer.fragment.VideoFragment;
import com.example.gsyvideoplayer.fragment.VideoFragment1;
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;
import com.shuyu.gsyvideoplayer.video.ListGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单详情实现模式1
 */
public class SimpleDetailActivityMode1 extends GSYBaseActivityDetail<ListGSYVideoPlayer> {

    ListGSYVideoPlayer detailPlayer;

    //    private String url = "http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4";
    //    private String url = "http://alvideo.ippzone.com/zyvd/98/90/b753-55fe-11e9-b0d8-00163e0c0248";
    private String url = "https://testonline-image.vesync.com/videoStreaming/recipeVideoData/v1/ee8ca41550fda4d1739b004b1cd42fdd.m3u8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_detail_player);

        detailPlayer = (ListGSYVideoPlayer) findViewById(R.id.detail_player);
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.GONE);
        detailPlayer.getBackButton().setVisibility(View.GONE);

//        initVideoBuilderMode();
        List<GSYVideoModel> urls = new ArrayList<>();
        urls.add(new GSYVideoModel(url, "标题1"));
        detailPlayer.setUp(urls, true, 0);
        //允许window 的内容可以上移到刘海屏状态栏
        if (getWindow() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }
        detailPlayer.startPlayLogic();
        //        detailPlayer.startAfterPrepared();
        findViewById(R.id.btn_replace).setOnClickListener(v -> {
            replace();
        });
    }

    public void replace() {
        Fragment newFragment = new VideoFragment1();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public ListGSYVideoPlayer getGSYVideoPlayer() {
        return detailPlayer;
    }

    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        //内置封面可参考SampleCoverVideo
        ImageView imageView = new ImageView(this);
        //loadCover(imageView, url);
        return new GSYVideoOptionBuilder()
            .setThumbImageView(imageView)
            .setUrl(url)
            .setCacheWithPlay(true)
            .setVideoTitle("这里是一个竖直方向的视频")
            .setIsTouchWiget(true)
            //.setAutoFullWithSize(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setShowFullAnimation(false)//打开动画
            .setNeedLockFull(true)
            .setSeekRatio(1);
    }

    @Override
    public void clickForFullScreen() {

    }


    /**
     * 是否启动旋转横屏，true表示启动
     */
    @Override
    public boolean getDetailOrientationRotateAuto() {
        return true;
    }

    private void loadCover(ImageView imageView, String url) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.xxx1);
        Glide.with(this.getApplicationContext())
            .setDefaultRequestOptions(
                new RequestOptions()
                    .frame(3000000)
                    .centerCrop()
                    .error(R.mipmap.xxx2)
                    .placeholder(R.mipmap.xxx1))
            .load(url)
            .into(imageView);
    }

}
