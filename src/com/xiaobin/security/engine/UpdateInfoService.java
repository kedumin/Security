package com.xiaobin.security.engine;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import com.xiaobin.security.domain.UpdateInfo;
import com.xiaobin.security.ui.SplashActivity;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: kedumin
 * Date: 14-2-2
 * Time: 下午11:06
 * To change this template use File | Settings | File Templates.
 */
public class UpdateInfoService{

    private Context context;

    public UpdateInfoService(Context context) {

        this.context = context;
    }

    public UpdateInfo getUpdateInfo(int urlId) throws IOException, XmlPullParserException {
        System.out.println("urlId="+urlId);
        Log.d("getUpdateInfo-urlId=",String.valueOf(urlId));
        String path=context.getResources().getString(urlId);
        System.out.println("path="+path);

        //path="http://www.baidu.com";
        Log.d("getUpdateInfo-path=",path);
        URL url=new URL(path);
        HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setRequestMethod("GET");
        InputStream is=httpURLConnection.getInputStream();
        //UpdateInfo updateInfo= UpdateInfoParser.getUpdateInfo(is);
        //mHandler.obtainMessage(MSG_SUCCESS, bitmap).sendToTarget();
        return UpdateInfoParser.getUpdateInfo(is);


    }

//    @Override
//    public void run() {
//        Log.d("path=","sdfafsdfsdfsdfsdfs");
//        Message msg = new Message();
//        UpdateInfo updateInfo= null;
//        try {
//            updateInfo = this.getUpdateInfo();
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        Bundle b = new Bundle();// 存放数据
//        b.putString("version",updateInfo.getVersion());
//        //msg.setData(b);
//        msg.obj=updateInfo;
//        //SplashActivity.this.mHandler.sendMessage(msg);
//        //msg.setTarget(SplashActivity.mHandler);
//        SplashActivity.mHandler.sendMessage(msg);
//        //To change body of implemented methods use File | Settings | File Templates.
//    }
}
