package com.xiaobin.security.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.xiaobin.security.R;
import com.xiaobin.security.adapter.MainUIAdapter;

/**
 * Created by kedumin on 14-2-6.
 */
public class MainActivity extends Activity implements AdapterView.OnItemClickListener{

    private GridView gridView;
    private MainUIAdapter adapter;
    private SharedPreferences sharedPreferences;

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.main);

        sharedPreferences=this.getSharedPreferences("config", Context.MODE_PRIVATE);
        gridView=(GridView)findViewById(R.id.gv_main);
        adapter=new MainUIAdapter(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                if(position==0){//这个是因为，如果我们手机被盗了，用户一看到第一个手机防盗，那样肯定会先卸载我们的程序，所以我们在手机防盗这个item里，设置一个重命名功能
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("设置");
                    builder.setMessage("请输入要理性的名字");
                    final EditText editText=new EditText(MainActivity.this);
                    editText.setHint("新名称");
                    builder.setView(editText);
                    builder.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String name=editText.getText().toString();
                            if(name.equals("")){
                                Toast.makeText(MainActivity.this,"输入内容不能为空",Toast.LENGTH_SHORT).show();

                            }else{
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString("lostName",name);
                                editor.commit();

                                TextView tv=(TextView) view.findViewById(R.id.tv_main_name);
                                tv.setText(name);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
                return false;
            }
        });

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(position)
        {
            case 0 : //手机防盗
                break;

            case 1 : //通讯卫士
                break;

            case 2 : //软件管理
                break;

            case 3 : //流量管理
                break;

            case 4 : //任务管理
                break;

            case 5 : //手机杀毒
                break;

            case 6 : //系统优化
                break;

            case 7 : //高级工具
                break;

            case 8 : //设置中心
                break;

            default :
                break;
        }

    }
}
