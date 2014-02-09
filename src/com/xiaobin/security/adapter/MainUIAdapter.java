package com.xiaobin.security.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaobin.security.R;

/**
 * Created with IntelliJ IDEA.
 * User: kedumin
 * Date: 14-2-6
 * Time: 下午9:16
 * To change this template use File | Settings | File Templates.
 */
public class MainUIAdapter extends BaseAdapter {

    private static final String[] NAMES=new String[]{"手机防盗","通讯卫士","软件管理","流量管理", "任务管理", "手机杀毒",
            "系统优化", "高级工具", "设置中心"};
    private static final int[] ICONS=new int[]{
        R.drawable.widget01, R.drawable.widget02, R.drawable.widget03,
            R.drawable.widget04, R.drawable.widget05, R.drawable.widget06, R.drawable.widget07,
            R.drawable.widget08, R.drawable.widget09};

    //声明成静态，起到一定的优化作用
    private static ImageView imageView;
    private static TextView textView;

    private Context context;
    private LayoutInflater layoutInflater;
    private SharedPreferences sharedPreferences;

    public MainUIAdapter (Context context){
        this.context=context;
        layoutInflater =LayoutInflater.from(this.context);
        sharedPreferences=context.getSharedPreferences("config",Context.MODE_PRIVATE);
    }


    @Override
    public int getCount() {
        return NAMES.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=layoutInflater.inflate(R.layout.main_item,null);
        imageView=(ImageView)view.findViewById(R.id.iv_main_icon);
        textView=(TextView)view.findViewById(R.id.tv_main_name);
        imageView.setImageResource(ICONS[position]);
        textView.setText(NAMES[position]);

        if(position==0){
            String name=sharedPreferences.getString("lostName","");
            if(!name.equals("")){
                textView.setText(name);
            }
        }
        return view;

    }
}
