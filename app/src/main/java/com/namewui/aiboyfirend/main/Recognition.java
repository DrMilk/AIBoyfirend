package com.namewui.aiboyfirend.main;

/**
 * Created by Administrator on 2017/6/13.
 */

public class Recognition {
    private RecognitionParsing recognitionParsing=new RecognitionParsing();
    public Recognition(){}
    public String getreply(String value){
        String result=recognitionParsing.getReplyString(value);
        return result;
    }
}
