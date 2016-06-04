package com.bupt.ktc.share_auth;

/**
 * Created by wangfei on 15/9/14.
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bupt.ktc.App;
import com.bupt.ktc.AsyLogin;
import com.bupt.ktc.BeanPersonInfoWeiXin;
import com.bupt.ktc.CircleImageView;
import com.bupt.ktc.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.view.UMFriendListener;

public class AuthActivity extends Activity {

    private UMShareAPI mShareAPI = null;
    private BeanPersonInfoWeiXin beanPersonIndo;
    private static String profile_image_url = "w";
    private String screen_name = "jj";
    private String openid = "789878";
    private CircleImageView image;
    private int flag = -1;

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
//		ImageLoader.getInstance().displayImage(App.myprofile_image_url, image,
//				App.initImageOptions(R.drawable.default_user_icon, true));
    }

    public void onClickAuth(View view) {
        SHARE_MEDIA platform = null;
        if (view.getId() == R.id.app_auth_sina) {
            platform = SHARE_MEDIA.SINA;
            flag = 1;
        } else if (view.getId() == R.id.app_auth_renren) {
            platform = SHARE_MEDIA.RENREN;
        } else if (view.getId() == R.id.app_auth_douban) {
            platform = SHARE_MEDIA.DOUBAN;
        } else if (view.getId() == R.id.app_auth_qq) {
            platform = SHARE_MEDIA.QQ;
            flag = 3;
        } else if (view.getId() == R.id.app_auth_weixin) {
            platform = SHARE_MEDIA.WEIXIN;
            flag = 2;
        } else if (view.getId() == R.id.app_auth_facebook) {
            platform = SHARE_MEDIA.FACEBOOK;
        } else if (view.getId() == R.id.app_auth_twitter) {
            platform = SHARE_MEDIA.TWITTER;
        } else if (view.getId() == R.id.app_auth_tencent) {
            platform = SHARE_MEDIA.TENCENT;
            flag = 4;
        } else if (view.getId() == R.id.app_auth_linkedin) {
            platform = SHARE_MEDIA.LINKEDIN;
        }
        /** begin invoke umeng api **/

        mShareAPI.doOauthVerify(AuthActivity.this, platform, umAuthListener);
        // mShareAPI.getPlatformInfo(AuthActivity.this, platform,
        // umAuthListener1);
    }

    public void onClickDeletAuth(View view) {
        SHARE_MEDIA platform = null;
        if (view.getId() == R.id.app_del_auth_sina) {
            platform = SHARE_MEDIA.SINA;
        } else if (view.getId() == R.id.app_del_auth_renren) {
            platform = SHARE_MEDIA.RENREN;
        } else if (view.getId() == R.id.app_del_auth_douban) {
            platform = SHARE_MEDIA.DOUBAN;
        } else if (view.getId() == R.id.app_del_auth_qq) {
            platform = SHARE_MEDIA.QQ;
        } else if (view.getId() == R.id.app_del_auth_weixin) {
            platform = SHARE_MEDIA.WEIXIN;
        } else if (view.getId() == R.id.app_del_auth_facebook) {
            platform = SHARE_MEDIA.FACEBOOK;
        } else if (view.getId() == R.id.app_del_auth_twitter) {
            platform = SHARE_MEDIA.TWITTER;
        } else if (view.getId() == R.id.app_auth_linkedin_del) {
            platform = SHARE_MEDIA.LINKEDIN;
        }
        /** begin invoke umeng api **/
        mShareAPI.deleteOauth(AuthActivity.this, platform, umdelAuthListener);
    }

    public Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_auth);
        /** init auth api **/
        mShareAPI = UMShareAPI.get(this);
        image = (CircleImageView) findViewById(R.id.app_auth_image);
        // asyTask("QQ");
        //	String url = "http://wx.qlogo.cn/mmopen/ajNVdqHZLLAjk7KAia27Ym5cAs8dkmTVT4FJC7T2ibevumk8wJCZMbuUz8UhzUcXhq7b0HeKTicClbmRnqJVmrMLg/0";

        // image.setImageBitmap(returnBitMap("http://wx.qlogo.cn/mmopen/ajNVdqHZLLAjk7KAia27Ym5cAs8dkmTVT4FJC7T2ibevumk8wJCZMbuUz8UhzUcXhq7b0HeKTicClbmRnqJVmrMLg/0"
        // ));
    }

    /**
     * auth callback interface
     **/
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action,
                               Map<String, String> data) {
            Toast.makeText(getApplicationContext(),
                    "Authorize succeed" + data.toString(), Toast.LENGTH_LONG)
                    .show();
            Log.i("result", ">>>>>>>>>" + data.toString());

            mShareAPI.getPlatformInfo(AuthActivity.this, platform,
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
    private UMAuthListener umdelAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action,
                               Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "delete Authorize succeed",
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

    private UMAuthListener umAuthListener1 = new UMAuthListener() {

        @Override
        public void onComplete(SHARE_MEDIA platform, int action,
                               Map<String, String> data) {
            beanPersonIndo = new BeanPersonInfoWeiXin();
            JSONObject obj = null;
            if (data != null) {
                if (flag == 2) {
                    try {
                        obj = new JSONObject(data.toString());
                        profile_image_url = obj.getString("headimgurl");
                        Log.i("wwwww1", ">>>>>>>>>" + profile_image_url);
                        screen_name = obj.getString("nickname");
                        openid = obj.getString("openid");

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), data.toString(),
                            Toast.LENGTH_LONG).show();
                    ImageLoader.getInstance().displayImage(profile_image_url, image,
                            App.initImageOptions(R.drawable.default_user_icon, true));
                    asyTask("WeiXin");
                } else if (flag == 3) {

                    try {
                        obj = new JSONObject(data.toString());
                        profile_image_url = obj.getString("profile_image_url");
                        Log.i("wwwww2", ">>>>>>>>>" + profile_image_url);
                        screen_name = obj.getString("screen_name");
                        openid = obj.getString("openid");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), data.toString(),
                            Toast.LENGTH_LONG).show();
                    ImageLoader.getInstance().displayImage(profile_image_url, image,
                            App.initImageOptions(R.drawable.default_user_icon, true));
                    asyTask("QQ");

                } else if (flag == 4) {

                    try {
                        obj = new JSONObject(data.toString());
                        profile_image_url = obj.getString("profile_image_url");
                        Log.i("wwwww3", ">>>>>>>>>" + profile_image_url);
                        screen_name = obj.getString("screen_name");
                        openid = obj.getString("openid");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), data.toString(),
                            Toast.LENGTH_LONG).show();
                    ImageLoader.getInstance().displayImage(profile_image_url, image,
                            App.initImageOptions(R.drawable.default_user_icon, true));
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
                        Toast.makeText(getApplicationContext(),
                                data.toString(), Toast.LENGTH_LONG).show();
                        ImageLoader.getInstance().displayImage(profile_image_url, image,
                                App.initImageOptions(R.drawable.default_user_icon, true));
                        asyTask("xinlangWeiBo");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                App.myprofile_image_url = profile_image_url;

                Log.d("auth callbacl", "getting data");
                Log.i("result1", ">>>>>>>>>" + data.toString());

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
        AsyLogin asyLogin = new AsyLogin(AuthActivity.this, typ, openid,
                profile_image_url) {

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
//			ImageLoader.getInstance().displayImage(App.myprofile_image_url, image,
//					App.initImageOptions(R.drawable.default_user_icon, true));

    }
}
