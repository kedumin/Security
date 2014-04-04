package com.xiaobin.security.engine;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.xiaobin.security.domain.ContactInfo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kedumin on 14-3-29.
 */
public class ContactInfoService {

    private Context context;

    public ContactInfoService(Context context){
       this.context=context;
    }

    public List<ContactInfo> getContactInfos(){
        List<ContactInfo> infos=new ArrayList<ContactInfo>();
        ContactInfo contactInfo;

        ContentResolver contentResolver=context.getContentResolver();
        Uri uri= Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri=Uri.parse("content://com.android.contacts/data");
        Cursor cursor=contentResolver.query(uri ,null,null,null,null);
        while (cursor.moveToNext()){
            contactInfo=new ContactInfo();
            String id=cursor.getString(cursor.getColumnIndex("_id"));
            String name=cursor.getString(cursor.getColumnIndex("display_name"));
            contactInfo.setName(name);
            //通过raw_contacts里的id拿到data里对应的数据
            Cursor dataCursor=contentResolver.query(dataUri,null,"raw_contact_id=?",new String[]{id},null);
            while (dataCursor.moveToNext()){
                String type=dataCursor.getString(dataCursor.getColumnIndex("mimetype"));
                //根据类型，只要电话这种类型的数据
                if(type.equals("vna.android.cursor.item/phone_v2")){
                    String number=dataCursor.getString(dataCursor.getColumnIndex("data1"));
                    contactInfo.setPhone(number);
                }
            }
            dataCursor.close();
            infos.add(contactInfo);
            contactInfo=null;
        }
        cursor.close();
        return infos;

    }
}
