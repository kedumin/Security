package com.xiaobin.security.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.xiaobin.security.R;
import com.xiaobin.security.domain.ContactInfo;
import com.xiaobin.security.engine.ContactInfoService;

import java.util.List;

/**
 * Created by kedumin on 14-3-30.
 */
public class SelectContactActivity extends Activity {

    private ListView listView;
    private List<ContactInfo> infos;

    protected void onCreate (Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.select_contact);

        infos=new ContactInfoService(this).getContactInfos();
        listView=(ListView)findViewById(R.id.lv_select_contact);
        listView.setAdapter(new SelectContactAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String number=infos.get(position).getPhone();
                Intent intent=new Intent();
                intent.putExtra("number",number);
                //把要返回的数据设置进去，使onActivityResult拿到
                setResult(1,intent);
                finish();
            }
        });
    }

    private class SelectContactAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {
            return infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ContactInfo info=infos.get(position);
            View view;
            ContactViews views;
            if(convertView==null){
                views=new ContactViews();
                view=View.inflate(SelectContactActivity.this,R.layout.contact_item,null);
                views.tv_name=(TextView)view.findViewById(R.id.tv_contact_name);
                views.tv_number=(TextView)view.findViewById(R.id.tv_contact_number);
                views.tv_number.setText("联系电话："+info.getPhone());
                views.tv_name.setText("联系人:"+info.getName());
                view.setTag(views);
            }else{
                view=convertView;
            }
            return view;
        }
    }

    private class ContactViews
    {
        TextView tv_name;
        TextView tv_number;
    }
}
