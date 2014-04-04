package com.xiaobin.security.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.xiaobin.security.R;

/**
 * Created by kedumin on 14-2-22.
 */
public class SetupGuide2Activity extends Activity implements View.OnClickListener {

    private Button bt_bind;
    private Button bt_next;
    private Button bt_perviout;
    private CheckBox cb_bind;
    private SharedPreferences sharedPreferences;

    protected void  onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.setup_guide2);
        sharedPreferences=getSharedPreferences("config", Context.MODE_PRIVATE);

        bt_bind=(Button)findViewById(R.id.bt_guide_bind);
        bt_next=(Button)findViewById(R.id.bt_guide_next);
        bt_perviout=(Button)findViewById(R.id.bt_guide_pervious);

        bt_bind.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        bt_perviout.setOnClickListener(this);

        cb_bind=(CheckBox)findViewById(R.id.cb_guide_check);
        //初始化checkBox状态
        String sim=sharedPreferences.getString("simSerial",null);
        if(sim!=null){
            cb_bind.setText("已经绑定");
            cb_bind.setChecked(true);
        }else{
            cb_bind.setText("没有绑定");
            cb_bind.setChecked(false);
        }
        cb_bind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //那个解除绑定的还没做，
                if(isChecked){
                    cb_bind.setText("已经绑定");
                    setSimInfo();
                }else{
                    cb_bind.setText("没有绑定");
                }
            }
        });

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bt_guide_bind:
                 setSimInfo();
                cb_bind.setText("已经绑定");
                cb_bind.setChecked(true);
                break;

            case R.id.bt_guide_next:
                Intent intent=new Intent(this,SetupGuide3Activity.class);
                finish();
                startActivity(intent);
                //这个是定义activity动画
                overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);
                break;

            default:
                break;
        }
    }

    private void setSimInfo(){
        TelephonyManager telephonyManager=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String simSerial=telephonyManager.getSimSerialNumber();
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("simSerial",simSerial);
        editor.commit();
    }
}
