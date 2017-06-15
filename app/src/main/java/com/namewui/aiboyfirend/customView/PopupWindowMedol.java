package com.namewui.aiboyfirend.customView;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.namewui.aiboyfirend.R;

/**
 * Created by Administrator on 2017/6/15.
 */

public class PopupWindowMedol extends PopupWindow implements View.OnClickListener{
    private Context mcontext;
    private View.OnClickListener listener;
    private View view;
    private LayoutInflater inflater;
    private WuTagCloudLayout wuTagCloudLayout;
    private String[] value;
    public PopupWindowMedol (Context mcontext, View.OnClickListener listener,int width, int height,String[] value){
        this.mcontext=mcontext;
        this.listener=listener;
        this.value=value;
        inflater=LayoutInflater.from(mcontext);
        view=inflater.inflate(R.layout.popup_medol,null);
        // 设置弹出窗体可点击
        this.setTouchable(true);
        this.setFocusable(true);
        this.setContentView(view);
        // 设置点击是否消失
        this.setOutsideTouchable(true);
        //设置弹出窗体动画效果
        this.setAnimationStyle(R.style.my_popup_anim);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
//        Log.i("Wu",mMenu.getHeight()+"");
        this.setHeight(height/3);
//        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable background = new ColorDrawable(0x4f000000);
//        //设置弹出窗体的背景
//        this.setBackgroundDrawable(background);
        initView();
    }

    private void initView() {
        wuTagCloudLayout= (WuTagCloudLayout) view.findViewById(R.id.popup_medolcloud);
        initCloudLayout();
      // 重写onKeyListener
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    PopupWindowMedol.this.dismiss();
//                    accountDialog = null;
                    return true;
                }
                return false;
            }
        });
    }

    private void initCloudLayout() {
        wuTagCloudLayout.removeAllViews();
        ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ViewGroup.MarginLayoutParams marginLayoutParams= new ViewGroup.MarginLayoutParams(lp.width,lp.height);
        marginLayoutParams.setMargins(10,10,10,10);
        for(int i=0;i<value.length;i++){
            View linear=LayoutInflater.from(mcontext).inflate(R.layout.item_medol,null);
            LinearLayout linear_bg= (LinearLayout) linear.findViewById(R.id.medol_linear);
            linear_bg.setBackgroundResource(R.drawable.tab_reward_bgcircle);
            TextView text_keyword= (TextView) linear.findViewById(R.id.medol_key);
            text_keyword.setText(value[i]);
            wuTagCloudLayout.addView(linear,marginLayoutParams);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
