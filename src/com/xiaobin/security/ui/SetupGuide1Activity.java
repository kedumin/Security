package com.xiaobin.security.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.xiaobin.security.R;

/**
 * Created by kedumin on 14-3-29.
 */
public class SetupGuide1Activity extends Activity implements View.OnClickListener{
    private Button next;

    protected void onCreate(Bundle saeInstanceState){
        super.onCreate(saeInstanceState);
        setContentView(R.layout.setup_guide1);

        next=(Button)findViewById(R.id.bt_guide_next);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bt_guide_next:
                Intent intent=new Intent(this,SetupGuide2Activity.class);
                finish();
                startActivity(intent);
                //这里定义activity切换动画效果
                overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);
                break;
            default:
                break;
        }

    }
}
