package com.namewui.aiboyfirend.engine;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;
import com.namewui.aiboyfirend.R;
import com.namewui.aiboyfirend.main.Recognition;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Recognition recognition=new Recognition();
    private SpeechRecognizer mIat;
    private SpeechSynthesizer mTts;
    private ListView listview;
    private ChaListtAdapter adapter;
    private ImageView img_send;
    private EditText edit_context;
    private Context mcontext;
    private AnimationDrawable anim_draw;
    private LinearLayout linear_line;
    private SoundPool mtinksong;
    private HashMap<Integer,Integer> soundPoolMap;
    private ArrayList<ChatInfo> listdata=new ArrayList<>();
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private ImageView img_yuyin;
    private int count_speech=0;
    private RecognizerListener mRecognizerListener = new RecognizerListener() {
        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            //   showTip("开始说话");
            Log.i("Wu","开始说话");
        }

        @Override
        public void onError(SpeechError var1) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限
            Log.i("Wu",var1.getErrorCode()+"");
            Log.i("Wu",var1.getErrorDescription());
            anim_draw.stop();
            linear_line.setVisibility(View.INVISIBLE);
            mtinksong.play(soundPoolMap.get(3),1,1,0,0,1);
            //      showTip(error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            //   showTip("结束说话");
            anim_draw.stop();
            linear_line.setVisibility(View.INVISIBLE);
        }
        @Override
        public void onResult(com.iflytek.cloud.RecognizerResult var1, boolean var2) {
            anim_draw.stop();
            linear_line.setVisibility(View.INVISIBLE);
            count_speech=count_speech%2;
            Log.i("Wu",count_speech+"");
            if(count_speech==0) {
                mtinksong.play(soundPoolMap.get(2),1,1,0,0,1);
                updatelistview(printResult(var1),"AI男朋友",2);
//                wuspeechreflect(var1);
                Log.i("Wu", "成功了 叫你爸爸1" + var1.getResultString());
                Log.i("Wu", "成功了 叫你爸爸2" + var1.toString());
                Log.i("Wu", "成功了 叫你爸爸3" + var1.describeContents());
                //     Log.i("Wu","成功了 叫你爸爸3"+var1.writeToParcel(););
            }
            count_speech++;
        }
        @Override
        public void onVolumeChanged(int var1, byte[] var2) {
            if(var1>1){
                anim_draw.start();
            }
        }
        @Override
        public void onEvent(int var1, int var2, int var3, Bundle var4) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };
    private SynthesizerListener mSynthesizerListener=new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };
    private void updatelistview(String str,String name,int kind){
        ChatInfo chat = new ChatInfo(getTime(),str,name, kind);
        listdata.add(chat);
        String replystr=recognition.getreply(str);
        ChatInfo chat1 = new ChatInfo(getTime(),replystr,name, 1);
        listdata.add(chat);
        listdata.add(chat1);
        adapter.notifyDataSetChanged();
//            listview.setAdapter(char_list_baseadapter);
        listview.setSelection(ListView.FOCUS_DOWN);
        mTts.startSpeaking(replystr,mSynthesizerListener);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        mcontext=this;
        initView();
        initsound();
        initListenSpeech();
        initSpeakSpeech();
    }

    private void initsound() {
        //   AudioAttributes attr=new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
        mtinksong=new SoundPool(2, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new HashMap<Integer, Integer>();
        soundPoolMap.put(0,mtinksong.load(this, R.raw.bdspeech_recognition_start,1));
        soundPoolMap.put(1,mtinksong.load(this, R.raw.bdspeech_recognition_cancel,1));
        soundPoolMap.put(2,mtinksong.load(this, R.raw.bdspeech_recognition_success,1));
        soundPoolMap.put(3,mtinksong.load(this, R.raw.bdspeech_recognition_error,1));
        soundPoolMap.put(4,mtinksong.load(this, R.raw.bdspeech_speech_end,1));
    }

    private void initListenSpeech() {
//        // 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
//// 请勿在“=”与appid之间添加任何空字符或者转义符
//        SpeechUtility.createUtility(mcontext, SpeechConstant.APPID +"=593df70d");
//        //设置引擎类型
//        mAsr.setParameter( SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD  );
////// 设置引擎类型
////        mAsr.setParameter( SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD );
////
/////* 其中 "abnf" 指定语法类型为 ABNF,  grammarContent 为语法内容，grammarListener 为构建结果监听器*/
//        ret = mAsr.buildGrammar( "abnf", grammarContent, grammarListener );
//        if( SpeechConstant.TYPE_LOCAL.equals(engineType) ){
//            //离线引擎，指定使用语记，还是 MSC 模式
//            mAsr.setParameter( SpeechConstant.ENGINE_MODE, engineMode );
//            if( SpeechConstant.MODE_MSC.equals(engineMode) ){
//                // MSC 模式时，设置本地识别资源，需下载使用对应的离线识别SDK
//                mAsr.setParameter( ResourceUtil.ASR_RES_PATH, asrResPath );
//                // MSC 模式时，设置语法构建路径(构建语法时指定的路径值)
//                mAsr.setParameter( ResourceUtil.GRM_BUILD_PATH, grmPath );
//            }//end of if msc mode
//
//            // 设置本地语法名字，在语法文件中定义了此名字值
//            mAsr.setParameter( SpeechConstant.LOCAL_GRAMMAR, grammarName );
//        }else{
//            //在线引擎使用 MSC 模式即可
//            mAsr.setParameter( SpeechConstant.ENGINE_MODE, SpeechConstant.MODE_MSC );
//
//            //使用网站上传的语法文件时，只明确指定 SUBJECT，不用指定语法ID；使用在应用上传的则相反。
//            if( usingWebsideGrammar ){
//                mAsr.setParameter( SpeechConstant.CLOUD_GRAMMAR, null );
//                mAsr.setParameter( SpeechConstant.SUBJECT, "asr" );
//            }else{
//                mAsr.setParameter( SpeechConstant.CLOUD_GRAMMAR, cloudGrammarID );
//            }//end of if-else using grammar in webside or not
//        }//end of if-else local or not
//
//        ret = mAsr.startListening( mRecognizerListener );
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=593df70d");
        FlowerCollector.setDebugMode(true);
        FlowerCollector.setCaptureUncaughtException(true);
        //1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        mIat= SpeechRecognizer.createRecognizer(this, null);
        Log.i("Wu","静鸡巴事");
//2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
//3.开始听写
//听写监听器
    }
    private void initSpeakSpeech(){
        mTts=SpeechSynthesizer.createSynthesizer(this,null);
        mTts.setParameter(SpeechConstant.VOICE_NAME,"xiaoxin");
        mTts.setParameter(SpeechConstant.VOLUME,"25");
        mTts.setParameter(SpeechConstant.SPEED,"50");
    //    mTts.setParameter(SpeechConstant.PITCH,);

//        SpeechConstant.VOICE_NAME: 发音人
//        SpeechConstant.SPEED: 合成语速
//        SpeechConstant.VOLUME: 合成音量
//        SpeechConstant.PITCH: 合成语调
//        SpeechConstant.BACKGROUND_SOUND: 背景音乐
//        SpeechConstant.TTS_BUFFER_TIME: 合成音频缓冲时间
//        SpeechConstant.STREAM_TYPE: 播放类型
//        SpeechConstant.SAMPLE_RATE: 采样率
//        SpeechConstant.TTS_AUDIO_PATH: 合成录音保存路径
//        SpeechConstant.ENGINE_TYPE：引擎类型；
//        ResourceUtil.TTS_RES_PATH：离线资源路径；
//        ResourceUtil.ENGINE_START：启动离线引擎；
    }
    private void initView() {
        listview= (ListView) findViewById(R.id.list_chat);
        img_send= (ImageView) findViewById(R.id.chat_send);
        edit_context= (EditText) findViewById(R.id.chat_context);
        img_yuyin= (ImageView) findViewById(R.id.yuyin_img);
        anim_draw= (AnimationDrawable) img_yuyin.getDrawable();
        listdata.add(new ChatInfo(getTime(),"我是你的AI男盆友！你想问点什么吗~","吴振宇男朋友",1));
        adapter=new ChaListtAdapter(mcontext,listdata);
        linear_line= (LinearLayout) findViewById(R.id.main_liner_tink);
        linear_line.setVisibility(View.INVISIBLE);
        listview.setAdapter(adapter);
    //    img_send.setOnClickListener(this);
        ImageView yuyin = (ImageView) findViewById(R.id.fab_voice);
        yuyin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    mIat.cancel();
//                    mtinksong.play(soundPoolMap.get(0),     //声音资源
//                            volumnRatio,         //左声道
//                            volumnRatio,         //右声道
//                            1,             //优先级，0最低
//                            0,         //循环次数，0是不循环，-1是永远循环
//                            1);            //回放速度，0.5-2.0之间。1为正常速度
//                };
                    mtinksong.play(soundPoolMap.get(0),1,1,1,0,1);
                    linear_line.setVisibility(View.VISIBLE);
                    //  img_yuyin.setVisibility(View.VISIBLE);
                    Log.i("Wu", mIat.startListening(mRecognizerListener)+"");
                    mIat.startListening(mRecognizerListener);
                    Log.i("Wu","按了");
                }
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    mIat.stopListening();
                    //        char_list_baseadapter.notify();
                    Log.i("Wu","松开");
                    anim_draw.stop();
                    linear_line.setVisibility(View.INVISIBLE);
                    //    img_yuyin.setVisibility(View.INVISIBLE);
                    mtinksong.play(soundPoolMap.get(4),1,1,1,0,1);
                }
                return false;
            }
        });
    }

    private String getTime(){
        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyy-MM-dd HH:mm");
        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    str    =    formatter.format(curDate);
        return str.substring(0,16);
    }
    private String printResult(com.iflytek.cloud.RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());
        Log.i("Wu","text"+text);
        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
            Log.i("Wu","textsn"+sn);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mIatResults.put(sn, text);
        StringBuffer resultBuffer = new StringBuffer();
        int i=0;
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
            Log.i("Wu","key"+mIatResults.get(key)+i);
            i++;
        }
        Log.i("Wu",resultBuffer.toString());
        return resultBuffer.toString();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.chat_send:
                String s=edit_context.getText().toString().trim();
                listdata.add(new ChatInfo(getTime(),s,"吴振宇男朋友",2));
                listdata.add(new ChatInfo(getTime(),s,"我",1));
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
