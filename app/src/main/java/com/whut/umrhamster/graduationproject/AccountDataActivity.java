package com.whut.umrhamster.graduationproject;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

public class AccountDataActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CHOOSE = 111;
    private RelativeLayout rlIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_data);
        rlIcon = findViewById(R.id.ac_account_data_rl_icon);
        rlIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matisse
                        .from(AccountDataActivity.this)
                        //选择图片
                        .choose(MimeType.ofImage())
                        //是否只显示选择的类型的缩略图，就不会把所有图片视频都放在一起，而是需要什么展示什么
                        .showSingleMediaType(true)
                        //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                        .capture(false)
//                        .captureStrategy(new CaptureStrategy(true,"PhotoPicker"))
                        //有序选择图片 123456...
                        .countable(true)
                        //最大选择数量为9
                        .maxSelectable(9)
                        //选择方向
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                        //界面中缩略图的质量
                        .thumbnailScale(0.8f)
                        //黑色主题
                        .theme(R.style.Matisse_Dracula)
                        //Picasso加载方式
                        .imageEngine(new PicassoEngine())
                        //请求码
                        .forResult(REQUEST_CODE_CHOOSE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK){
            List<String> pathList = Matisse.obtainPathResult(data);
            for (int i=0;i<pathList.size();i++){
                Log.d(""+i,pathList.get(i));
            }
        }
    }
}
