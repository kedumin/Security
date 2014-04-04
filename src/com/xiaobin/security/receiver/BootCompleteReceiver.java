package com.xiaobin.security.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

/**
 * Created by kedumin on 14-4-3.
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    private SharedPreferences sharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        boolean isProtected=sharedPreferences.getBoolean("isProtected",false);
        //看看是不是开启了保护
        if(isProtected){
            TelephonyManager telephonyManager=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            //开机后，拿到当前sim卡的标识，与我们之前存放的数据对比
            String currentSim=telephonyManager.getSimSerialNumber();
            String protectedSim=sharedPreferences.getString("simSerial","");
            if (!currentSim.equals(protectedSim)){
                //拿到一个短信管理器，要注意不要导错包，是在android.telphony下
                SmsManager smsManager=SmsManager.getDefault();
                String number=sharedPreferences.getString("number","");
                //发送短信，有5个参数，第一个是要发送到的地址，第二个是发送人，可以设置为null，第三个是要发送的信息，第四个是发送状态，第五个是发送后的，都可以置为null
                smsManager.sendTextMessage(number,null,"sim卡已变更，手机可能没了",null,null);
            }
        }

    }
}
