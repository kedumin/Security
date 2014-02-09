package com.xiaobin.security.engine;

import android.util.Xml;
import com.xiaobin.security.domain.UpdateInfo;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import static org.xmlpull.v1.XmlPullParser.*;

/**
 * Created with IntelliJ IDEA.
 * User: kedumin
 * Date: 14-2-2
 * Time: 下午11:12
 * To change this template use File | Settings | File Templates.
 */
public class UpdateInfoParser {
    public static UpdateInfo getUpdateInfo(InputStream is) throws XmlPullParserException, IOException {
        UpdateInfo info = new UpdateInfo();
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(is, "utf-8");
        int type = xmlPullParser.getEventType();
        while (type != END_DOCUMENT) {
            switch (type) {
                case START_TAG:
                    if (xmlPullParser.getName().equals("version")) {
                        info.setVersion(xmlPullParser.nextText());
                    } else if (xmlPullParser.getName().equals("description")) {
                        info.setDescription(xmlPullParser.nextText());
                    } else if (xmlPullParser.getName().equals("apkurl")) {
                        info.setUrl(xmlPullParser.nextText());
                    }
                    break;

                default:
                    break;
            }
            type=xmlPullParser.next();
        }
        return info;
    }
}























