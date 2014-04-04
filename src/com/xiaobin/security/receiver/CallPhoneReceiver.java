package com.xiaobin.security.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaobin.security.ui.LostProtectedActivity;

/**
 * Created by kedumin on 14-2-9.
 */
public class CallPhoneReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String outPhoneNumber=this.getResultData();
        if(outPhoneNumber.equals("1234")){//当监听到1234执行下面的操作
            Intent i=new Intent(context,LostProtectedActivity.class);
            //这个很重要，如果没有这一句，就会报错，这一句是因为我们是在一个receiver里启动activity
            //但activity的启动，都是放到一上栈里面的，
            //但receiver里面没有那个栈，所以我们要在这里启动一个activity，那就必须要指定代码啦
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            setResultData(null);//这行把广播的数据设置为null，这样就不会把刚才那个号码打出去，只启动activity
        }
    }
}
