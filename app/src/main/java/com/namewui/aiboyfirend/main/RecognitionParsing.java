package com.namewui.aiboyfirend.main;

/**
 * Created by Administrator on 2017/6/13.
 */

public class RecognitionParsing {
    private String[] lexicon=new String[]{
            "名字","姨妈",
    };
    public RecognitionParsing(){}

    public String getReplyString(String value) {
        if(value.contains("名字")){
            return "我的名字叫吴振宇";
        }else if(value.contains("你好")){
            return "你好！";
        }else if(value.contains("傻")){
            return "我不傻逼，你煞笔！";
        }
        return "你能不能别问我不知道的呀！";
    }
}
