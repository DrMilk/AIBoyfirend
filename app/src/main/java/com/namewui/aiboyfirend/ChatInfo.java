package com.namewui.aiboyfirend;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/4/27.
 */

public class ChatInfo {
    private String text_time;
    private Long text_data;
    private String text_context;
    private String img_id;
    private int kinds;
    private boolean status;
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public ChatInfo(){}
    public ChatInfo(String text_time, String text_context, String img_id, int kinds) {
        this.text_time = text_time;
        this.text_context = text_context;
        this.img_id = img_id;
        this.kinds = kinds;
        try {
            this.text_data=simpleDateFormat.parse(text_time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getText_time() {
        return text_time;
    }

    public void setText_time(String text_time) {
        this.text_time = text_time;
    }

    public String getText_context() {
        return text_context;
    }

    public void setText_context(String text_context) {
        this.text_context = text_context;
    }

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public int getKinds() {
        return kinds;
    }

    public void setKinds(int kinds) {
        this.kinds = kinds;
    }

    public Long getText_data() {
        return text_data;
    }

    public void setText_data(Long text_data) {
        this.text_data = text_data;
    }
}
