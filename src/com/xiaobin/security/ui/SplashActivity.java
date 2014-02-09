package com.xiaobin.security.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.xiaobin.security.R;
import com.xiaobin.security.domain.UpdateInfo;
import com.xiaobin.security.engine.DownloadTask;
import com.xiaobin.security.engine.UpdateInfoService;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import android.os.Handler;

import java.nio.CharBuffer;
import java.util.logging.LogRecord;

public class SplashActivity extends Activity
{
    /** Called when the activity is first created. */
    private TextView tv_version;
    private LinearLayout ll;
    private String version;
    private ProgressDialog progressDialog;

    private UpdateInfo info;

    private static final String TAG="Security";

    public static Handler mHandler;

    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            if(isNeedUpdate(version)){
                showUpdateDialog();
            }
        }
    };



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //设置不要显示标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash);
        //设置全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tv_version=(TextView)findViewById(R.id.tv_splash_version);
        version=getVersion();
        tv_version.setText("版本号"+version);

        ll=(LinearLayout)findViewById(R.id.ll_splash_main);
        AlphaAnimation alphaAnimation=new AlphaAnimation(0.0f,1.0f);
        alphaAnimation.setDuration(2000);
        ll.startAnimation(alphaAnimation);

        progressDialog=new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("正在下载。。。。。");

        new Thread(){
            public void run(){
                try{
                    sleep(3000);
                    handler.sendEmptyMessage(0);

                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }.start();

        //isNeedUpdate(version);
        //if(isNeedUpdate(version)){
            //showUpdateDialog();
        //}

//        mHandler=new Handler(){
//
//            public void handleMessage(Message msg){
//                Bundle b = msg.getData();
//                info=(UpdateInfo)msg.obj;
//                String v=info.getVersion();
//                if(v.equals(version)){
//                    System.out.println("不用更新");
//                }else{
//                    System.out.println("要更新");
//                    showUpdateDialog(info);
//                }
//            }
//
//        };
    }

    private String getVersion(){
        try{
            PackageManager packageManager=getPackageManager();
            PackageInfo packageInfo=packageManager.getPackageInfo(getPackageName(),0);
            return packageInfo.versionName;
        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return "未知版本";
        }
    }

    private void showUpdateDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("升级提醒");
        builder.setMessage(info.getDescription());
        builder.setCancelable(false);

        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //To change body of implemented methods use File | Settings | File Templates.
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    File dir=new File(Environment.getExternalStorageDirectory(),"/security/update");
                    Log.i(TAG,"dir："+dir.toString());
                    if(!dir.exists()){
                        dir.mkdirs();
                    }
                    String apkPath=Environment.getExternalStorageDirectory()+"/security/update/new.apk";
                    Log.i(TAG,"apkPath："+apkPath);
                    UpdateTask task=new UpdateTask(info.getUrl(),apkPath);
                    progressDialog.show();
                    new Thread(task).start();
                }else {
                    Toast.makeText(SplashActivity.this,"SD卡不可用，请插入SD卡",Toast.LENGTH_SHORT).show();;
                    loadMainUI();
                }
            }
        });
        builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //To change body of implemented methods use File | Settings | File Templates.
                loadMainUI();
            }
        });
        builder.create().show();
    }

    private boolean isNeedUpdate(String version){
        UpdateInfoService updateInfoService=new UpdateInfoService(this);
        try{
            info=updateInfoService.getUpdateInfo(R.string.serverUrl);
            String v=info.getVersion();
            if(v.equals(version)){
                Log.i(TAG,"当前版本："+version);
                Log.i(TAG,"最新版本："+v);
                return false;
            }else{
                Log.i(TAG,"需要更新");
                return true;
            }
        }catch(Exception e){
            System.out.println("print exception");
            e.printStackTrace();
            Toast.makeText(this,"获取更新信息异常，请稍后再试",Toast.LENGTH_SHORT).show();
            loadMainUI();
        }
        return false;
    }

    private void loadMainUI(){
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 安装apk
     * @param file 要安装的apk目录
     */
    private void install(File file){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        finish();
        startActivity(intent);
    }

    /**
     * 下载线程
     */
    class UpdateTask implements Runnable{
        private String path;
        private String filePath;

        public UpdateTask(String path,String filePath){
            this.path=path;
            this.filePath=filePath;
        }

        @Override
        public void run() {
            //To change body of implemented methods use File | Settings | File Templates.
            try{
                File file= DownloadTask.getFile(path,filePath,progressDialog);
                progressDialog.dismiss();
                install(file);
            }catch (Exception e){
                e.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(SplashActivity.this,"更新失败",Toast.LENGTH_SHORT).show();
                loadMainUI();
            }
        }
    }


}
