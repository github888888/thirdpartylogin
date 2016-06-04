package com.bupt.ktc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;

import java.util.Map;

public class ActivityLogion extends Activity implements OnChageListener {
    private CircleImageView image;
    private Button button_c;
    private TextView tv;
    private String typs;
    private UMShareAPI mShareAPI = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OnChage.getInstance().setChageListener(this);//setChageListener.getInstance().setChageListener(this);
        tv = (TextView) this.findViewById(R.id.text);
        image = (CircleImageView) findViewById(R.id.app_auth_image);
        button_c = (Button) findViewById(R.id.button_c);
        mShareAPI = UMShareAPI.get(this);
        tv.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ActivityLogion.this,
                        SelectPicPopupWindow.class));
            }
        });
        //tuichu
        button_c.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                SHARE_MEDIA platform = null;
                if (typs.equals("QQ")) {
                    platform = SHARE_MEDIA.QQ;
                    mShareAPI.deleteOauth(ActivityLogion.this, platform, umdelAuthListener);
                } else if (typs.equals("xinlangWeiBo")) {
                    platform = SHARE_MEDIA.SINA;
                    mShareAPI.deleteOauth(ActivityLogion.this, platform, umdelAuthListener);
                } else if (typs.equals("WeiXin")) {
                    platform = SHARE_MEDIA.WEIXIN;
                    mShareAPI.deleteOauth(ActivityLogion.this, platform, umdelAuthListener);
                }
                button_c.setVisibility(View.GONE);
                tv.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(
                        "",
                        image,
                        App.initImageOptions(R.drawable.umeng_socialize_wechat,
                                true));

            }
        });
    }

    private UMAuthListener umdelAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action,
                               Map<String, String> data) {
//			button_c.setVisibility(View.GONE);
//			tv.setVisibility(View.VISIBLE);

            Toast.makeText(getApplicationContext(), "退出成功",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "delete Authorize fail",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "delete Authorize cancel",
                    Toast.LENGTH_SHORT).show();
        }
    };

    //登陆成功回调
    @Override
    public void chage(String imageUrl, String typ) {
        // TODO Auto-generated method stub
        typs = typ;
        tv.setVisibility(View.GONE);
        button_c.setVisibility(View.VISIBLE);
        ImageLoader.getInstance().displayImage(
                imageUrl,
                image,
                App.initImageOptions(R.drawable.default_user_icon,
                        true));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("auth", "on activity re 2");
        mShareAPI.onActivityResult(requestCode, resultCode, data);
        Log.d("auth", "on activity re 3");
//			ImageLoader.getInstance().displayImage(App.myprofile_image_url, image,
//					App.initImageOptions(R.drawable.default_user_icon, true));

    }
}
