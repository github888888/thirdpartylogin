package com.bupt.ktc;

import java.util.HashMap;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * 获取用户个人信息
 *
 * @author chenkeliang
 */
public class AsyLogin extends AsyncTask<Integer, Integer, String> {

    String resultUrl = "";
    private Activity act;
    private static final String tempUrl = "http://123.56.232.80:8586/login";
    private String thirdpartycode;
    private String uid;
    private String imgurl;

    public AsyLogin(Activity act, String thirdpartycode, String uid, String imgurl) {
        this.act = act;
        this.thirdpartycode = thirdpartycode;
        this.uid = uid;
        this.imgurl = imgurl;
    }

    @Override
    protected String doInBackground(Integer... params) {
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("thirdpartycode", thirdpartycode);
        param.put("uid", uid);
        param.put("imgurl", imgurl);
        try {
            resultUrl = NetTool.sendPostRequest(tempUrl, param, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultUrl;
    }

    @Override
    protected void onPostExecute(String result) {

    }

}
