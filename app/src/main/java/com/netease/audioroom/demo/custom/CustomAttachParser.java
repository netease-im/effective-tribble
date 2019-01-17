package com.netease.audioroom.demo.custom;

import com.netease.audioroom.demo.util.JsonUtil;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachmentParser;

import org.json.JSONException;
import org.json.JSONObject;


public class CustomAttachParser implements MsgAttachmentParser {

    private static final String KEY_TYPE = "type";
    private static final String KEY_DATA = "data";

    @Override
    public MsgAttachment parse(String json) {
        CustomAttachment attachment = null;
        JSONObject object = JsonUtil.parse(json);
        if (object == null) {
            return null;
        }
        int type = object.optInt(KEY_TYPE);
        JSONObject data = object.optJSONObject(KEY_DATA);
        switch (type) {
            case CustomAttachmentType.CLOSER_ROOM:
                attachment = new CloseRoomAttach();
                break;
        }

        if (attachment != null) {
            attachment.fromJson(data);
        }

        return attachment;
    }

    public static String packData(int type, JSONObject data) {
        JSONObject object = new JSONObject();
        try {
            object.put(KEY_TYPE, type);
            if (data != null) {
                object.put(KEY_DATA, data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object.toString();
    }
}
