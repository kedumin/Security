package com.xiaobin.security.engine;

import android.app.ProgressDialog;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: kedumin
 * Date: 14-2-6
 * Time: 上午10:39
 * To change this template use File | Settings | File Templates.
 */
public class DownloadTask {

    public static File getFile(String path,String filePath,ProgressDialog progressDialog) throws IOException {
        Log.v("DownloadTask","path="+path);
        Log.v("DownloadTask","filePath="+filePath);

        URL url=new URL(path);
        HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
        httpURLConnection.setConnectTimeout(2000);
        httpURLConnection.setRequestMethod("GET");
        if (httpURLConnection.getResponseCode()==200){
            int total=httpURLConnection.getContentLength();
            progressDialog.setMax(total);

            InputStream is=httpURLConnection.getInputStream();
            File file=new File(filePath);
            FileOutputStream fos=new FileOutputStream(file);
            byte[] buffer=new byte[1024];
            int len;
            int process=0;
            while((len=is.read(buffer))!=-1){
                fos.write(buffer,0,len);
                process+=len;
                progressDialog.setProgress(process);
            }
            fos.flush();
            fos.close();
            is.close();
            return file;
        }
        return null;
    }
}
