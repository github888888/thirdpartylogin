package com.bupt.ktc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.view.UMFriendListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SelectPicPopupWindow extends Activity implements OnClickListener {

    private ImageView btn_take_qq, btn_pick_wx, btn_wb;
    private LinearLayout layout;

    private UMShareAPI mShareAPI = null;
    private BeanPersonInfoWeiXin beanPersonIndo;
    private static String profile_image_url = "w";
    private String screen_name = "jj";
    private String openid = "789878";

    private int flag = -1;
    private OnChage onChage;
    private OnChageListener chageListener;

//	private OnChageListener chageListener;
//
//
//	public  void setChageListener(OnChageListener chageListener) {
//		this.chageListener = chageListener;
//	}
//	 public  static SelectPicPopupWindow getInstance(){
//		 return instance;
//	 }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);
        //instance = new SelectPicPopupWindow();
        chageListener = onChage.getInstance().chageListener;
        btn_take_qq = (ImageView) this.findViewById(R.id.btn_take_qq);
        btn_pick_wx = (ImageView) this.findViewById(R.id.btn_pick_weixin);
        btn_wb = (ImageView) this.findViewById(R.id.btn_weibo);

        layout = (LinearLayout) findViewById(R.id.pop_layout);

        layout.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
            }
        });
        btn_wb.setOnClickListener(this);
        btn_pick_wx.setOnClickListener(this);
        btn_take_qq.setOnClickListener(this);
        mShareAPI = UMShareAPI.get(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    public void onClick(View v) {
        SHARE_MEDIA platform = null;
        switch (v.getId()) {
            case R.id.btn_take_qq:
                platform = SHARE_MEDIA.QQ;
                flag = 3;
                mShareAPI.doOauthVerify(this, platform, umAuthListener);
                break;
            case R.id.btn_pick_weixin:
                platform = SHARE_MEDIA.WEIXIN;
                flag = 2;
                mShareAPI.doOauthVerify(this, platform, umAuthListener);
                break;
            case R.id.btn_weibo:
                platform = SHARE_MEDIA.SINA;
                flag = 1;
                mShareAPI.doOauthVerify(this, platform, umAuthListener);
                break;
            default:
                break;
        }

    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action,
                               Map<String, String> data) {

            Toast.makeText(getApplicationContext(),
                    "Authorize succeed" + data.toString(), Toast.LENGTH_LONG)
                    .show();
            Log.i("result", ">>>>>>>>>" + data.toString());

            mShareAPI.getPlatformInfo(SelectPicPopupWindow.this, platform,
                    umAuthListener1);

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel",
                    Toast.LENGTH_SHORT).show();
        }
    };
    /**
     * delauth callback interface
     **/

    private UMAuthListener umAuthListener1 = new UMAuthListener() {

        @Override
        public void onComplete(SHARE_MEDIA platform, int action,
                               Map<String, String> data) {

            beanPersonIndo = new BeanPersonInfoWeiXin();
            JSONObject obj = null;
            JSONObject obj1 = null;
            if (data != null) {
                if (flag == 2) {
                    try {
                        Map<String, String> map = new HashMap<String, String>();
                        String[] datas = data.toString().substring(1, data.toString().length() - 1).split(",");
                        for (int i = 0; i <= datas.length - 1; i++) {
                            String[] dataSub = new String[2];
                            dataSub = datas[i].split("=");
                            if (dataSub.length == 1) {
                                String[] dataSubs = new String[2];
                                String[] a = dataSub;
                                dataSubs[0] = a[0];
                                dataSubs[1] = "q";
                                dataSub = dataSubs;
                            }
                            map.put(dataSub[0].trim(), dataSub[1].trim());

                        }
                        obj = new JSONObject(map);
                        //String datas = data.toString().replace("=",":");
                        profile_image_url = obj.getString("headimgurl");
                        Log.i("wwwww1", ">>>>>>>>>" + profile_image_url);
                        screen_name = obj.getString("nickname");
                        openid = obj.getString("openid");
                        chageListener.chage(profile_image_url, "WeiXin");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), data.toString(),
                            Toast.LENGTH_LONG).show();
                    asyTask("WeiXin");
                } else if (flag == 3) {

                    try {
                        Map<String, String> map = new HashMap<String, String>();
                        String[] datas = data.toString().substring(1, data.toString().length() - 1).split(",");
                        for (int i = 0; i <= datas.length - 1; i++) {
                            String[] dataSub = new String[2];
                            dataSub = datas[i].split("=");
                            if (dataSub.length == 1) {
                                String[] dataSubs = new String[2];
                                String[] a = dataSub;
                                dataSubs[0] = a[0];
                                dataSubs[1] = "q";
                                dataSub = dataSubs;
                            }
                            map.put(dataSub[0].trim(), dataSub[1].trim());

                        }
                        obj = new JSONObject(map);
                        profile_image_url = obj.getString("profile_image_url");
                        Log.i("wwwww2", ">>>>>>>>>" + profile_image_url);
                        screen_name = obj.getString("screen_name");
                        openid = obj.getString("openid");
                        chageListener.chage(profile_image_url, "QQ");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), data.toString(),
                            Toast.LENGTH_LONG).show();
                    asyTask("QQ");

                } else if (flag == 4) {

                    try {
                        Map<String, String> map = new HashMap<String, String>();
                        String[] datas = data.toString().substring(1, data.toString().length() - 1).split(",");
                        for (int i = 0; i <= datas.length - 1; i++) {
                            String[] dataSub = new String[2];
                            dataSub = datas[i].split("=");
                            map.put(dataSub[0].trim(), dataSub[1].trim());

                        }
                        obj = new JSONObject(map);
                        profile_image_url = obj.getString("profile_image_url");
                        Log.i("wwwww3", ">>>>>>>>>" + profile_image_url);
                        screen_name = obj.getString("screen_name");
                        openid = obj.getString("openid");
                        chageListener.chage(profile_image_url, "TencentWeiBo");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), data.toString(),
                            Toast.LENGTH_LONG).show();
                    asyTask("TencentWeiBo");
                } else if (flag == 1) {
                    try {

                        // >>>>>>>>>{com.sina.weibo.intent.extra.NICK_NAME=我的生活录I,
                        // access_token=2.00Mwu8YFLc7xVD39d81bb070OQN5tD,
                        // com.sina.weibo.intent.extra.USER_ICON=null,
                        // uid=5089713312, userName=我的生活录I,
                        // _weibo_appPackage=com.sina.weibo, expires_in=128638,
                        // _weibo_transaction=1464851760973,
                        // refresh_token=2.00Mwu8YFLc7xVD986b9b447aU8AUBE}
                        // >>>>>>>>>{result={"id":3268094933,"idstr":"3268094933","class":1,"screen_name":"旷铁成","name":"旷铁成","province":"11","city":"1","location":"北京 东城区","description":"","url":"","profile_image_url":"http://tva2.sinaimg.cn/default/images/default_avatar_male_50.gif","profile_url":"u/3268094933","domain":"","weihao":"","gender":"m","followers_count":6,"friends_count":109,"pagefriends_count":0,"statuses_count":3,"favourites_count":1,"created_at":"Sun Mar 31 12:07:58 +0800 2013","following":false,"allow_all_act_msg":false,"geo_enabled":true,"verified":false,"verified_type":-1,"remark":"","status":{"created_at":"Thu Jun 02 14:20:17 +0800 2016","id":3981925226173774,"mid":"3981925226173774","idstr":"3981925226173774","text":"http://t.cn/RbnQEZ7","textLength":19,"source_allowclick":0,"source_type":1,"source":"<a href=\"http://app.weibo.com/t/feed/5Aau9j\" rel=\"nofollow\">荣耀6</a>","favorited":false,"truncated":false,"in_reply_to_status_id":"","in_reply_to_user_id":"","in_reply_to_screen_name":"","pic_urls":[],"geo":null,"annotations":[{"shooting":1,"client_mblogid":"4babc508-b9a1-4fd5-bd46-48331f1ce166"},{"mapi_request":true}],"reposts_count":0,"comments_count":0,"attitudes_count":0,"isLongText":false,"mlevel":0,"visible":{"type":0,"list_id":0},"biz_feature":0,"darwin_tags":[],"hot_weibo_tags":[],"text_tag_tips":[],"userType":0},"ptype":0,"allow_all_comment":true,"avatar_large":"http://tva2.sinaimg.cn/default/images/default_avatar_male_180.gif","avatar_hd":"http://tva2.sinaimg.cn/default/images/default_avatar_male_180.gif","verified_reason":"","verified_trade":"","verified_reason_url":"","verified_source":"","verified_source_url":"","follow_me":false,"online_status":0,"bi_followers_count":2,"lang":"zh-cn","star":0,"mbtype":0,"mbrank":0,"block_word":0,"block_app":0,"credit_score":80,"user_ability":0,"urank":9}}

                        obj = new JSONObject(data.toString());
                        String re = obj.getString("result");
                        obj = new JSONObject(re);
                        profile_image_url = obj.getString("profile_image_url");
                        Log.i("wwwww4", ">>>>>>>>>" + profile_image_url);
                        screen_name = obj.getString("screen_name");
                        openid = obj.getString("id");
                        chageListener.chage(profile_image_url, "xinlangWeiBo");
                        Toast.makeText(getApplicationContext(),
                                data.toString(), Toast.LENGTH_LONG).show();
                        asyTask("xinlangWeiBo");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                App.myprofile_image_url = profile_image_url;

                Log.d("auth callbacl", "getting data");
                Log.i("result1", ">>>>>>>>>" + data.toString());
                finish();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            if (platform.equals(SHARE_MEDIA.SINA)) {
                asyTask("weibo");
            } else {
                Toast.makeText(getApplicationContext(), "get fail",
                        Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "get cancel",
                    Toast.LENGTH_SHORT).show();
        }
    };
    private UMFriendListener umGetfriendListener1 = new UMFriendListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action,
                               Map<String, Object> data) {
            if (data != null) {
                Toast.makeText(getApplicationContext(),
                        data.get("json").toString(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "get fail",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "get cancel",
                    Toast.LENGTH_SHORT).show();
        }
    };

    private void asyTask(String typ) {
        if (beanPersonIndo == null) {
            return;
        }
        AsyLogin asyLogin = new AsyLogin(SelectPicPopupWindow.this, typ,
                openid, profile_image_url) {

            @Override
            protected void onPostExecute(String result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);
                if (result == null)
                    return;
                String name = "";
                String imgUrl = null;
                int id;
                JSONObject json = null;
                try {
                    json = new JSONObject(result);
                    name = json.getString("name");
                    imgUrl = json.getString("imgUrl");
                    id = json.getInt("id");

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Log.i("rrrrrr", profile_image_url + ">>>>>" + imgUrl);
                if (!name.equals("")) {
                    Toast.makeText(getApplicationContext(), "登陆成功",
                            Toast.LENGTH_LONG).show();
                }

            }

        };
        asyLogin.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("auth", "on activity re 2");
        mShareAPI.onActivityResult(requestCode, resultCode, data);
        Log.d("auth", "on activity re 3");
        // ImageLoader.getInstance().displayImage(App.myprofile_image_url,
        // image,
        // App.initImageOptions(R.drawable.default_user_icon, true));

    }
}
