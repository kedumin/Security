package com.xiaobin.security.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.xiaobin.security.R;

/**
 * Created by kedumin on 14-3-30.
 */
public class SetupGuide3Activity extends Activity implements View.OnClickListener {

    private Button bt_next;
    private Button bt_pervious;
    private Button bt_select;
    private EditText et_phoneNumber;

    private SharedPreferences sharedPreferences;

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.setup_guide3);
        sharedPreferences=getSharedPreferences("config", Context.MODE_PRIVATE);

        bt_next=(Button)findViewById(R.id.bt_guide_next);
        bt_pervious=(Button)findViewById(R.id.bt_guide_pervious);
        bt_select=(Button)findViewById(R.id.bt_guide_select);
        bt_next.setOnClickListener(this);
        bt_pervious.setOnClickListener(this);
        bt_select.setOnClickListener(this);

        //et_phoneNumber=(SharedPreferences.Editor)findViewById(R.id.et_guide_phoneNumber);
        et_phoneNumber=(EditText)findViewById(R.id.et_guide_phoneNumber);
        //et_phoneNumber = (EditText) findViewById(R.id.et_guide_phoneNumber);

    }

    //重写这个方法，从activity里拿数据
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //resultCode是乃至区分到的activriy是从那个activity里拿到的
        if(data !=null){
            String number=data.getStringExtra("number");
            et_phoneNumber.setText(number);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_guide_select:
                 Intent intent=new Intent(this,SelectContactActivity.class);
                //启动一个activity来获取数据，获取到的数据是在重写onActivityResult这个方法里拿到的
                startActivityForResult(intent,1);
                break;

            case R.id.bt_guide_next:
                String number=et_phoneNumber.getText().toString().trim();
                if(number.equals("")){
                    Toast.makeText(this,"安全号码不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("number",number);
                    editor.commit();

                    Intent intent1=new Intent(this,SetupGuide4Activity.class);
                    finish();
                    startActivity(intent1);
                    overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);
                }
                break;

            default:
                break;
        }

    }
}
