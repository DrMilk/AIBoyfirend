package com.namewui.aiboyfirend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/4/27.
 */

public class ChaListtAdapter extends BaseAdapter {
    private String TAG="ChaListtAdapter";
    private LayoutInflater minflater;
    private ArrayList<ChatInfo> mlist;
    private WuViewHolder viewHolder;
    private ChatInfo charinfo;
//    private MyUpload myUpload;
    public ChaListtAdapter(Context context, ArrayList<ChatInfo> mlist){
        minflater= LayoutInflater.from(context);
        this.mlist=mlist;
//        myUpload=new MyUpload(context);
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return mlist.get(position).getKinds();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        charinfo=mlist.get(position);
        if(convertView==null){
            switch (charinfo.getKinds()) {
                case 1:
                    convertView = minflater.inflate(R.layout.listitme_right, null);break;
                case 2:
                    convertView = minflater.inflate(R.layout.listitme_left, null);break;
            }
            viewHolder = new WuViewHolder();
            viewHolder.img_head = (ImageView) convertView.findViewById(R.id.header_headimg);
            viewHolder.text_time = (TextView) convertView.findViewById(R.id.char_text_time);
            viewHolder.text_content = (TextView) convertView.findViewById(R.id.char_text_content);
            viewHolder.flag=mlist.get(position).getKinds();
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(WuViewHolder) convertView.getTag();
            if(viewHolder.flag!=mlist.get(position).getKinds()){
                switch (charinfo.getKinds()) {
                    case 1:
                        convertView = minflater.inflate(R.layout.listitme_right, null);break;
                    case 2:
                        convertView = minflater.inflate(R.layout.listitme_left, null);break;
                }
                viewHolder = new WuViewHolder();
                viewHolder.img_head = (ImageView) convertView.findViewById(R.id.header_headimg);
                viewHolder.text_time = (TextView) convertView.findViewById(R.id.char_text_time);
                viewHolder.text_content = (TextView) convertView.findViewById(R.id.char_text_content);
                viewHolder.flag=mlist.get(position).getKinds();
                convertView.setTag(viewHolder);
            }
        }
        viewHolder.text_time.setVisibility(View.VISIBLE);
        viewHolder.text_time.setText(charinfo.getText_time());
        if(position!=0){
            int count= (int) ((mlist.get(position).getText_data()-mlist.get(position-1).getText_data())/3600);
            if(count<6){
                viewHolder.text_time.setVisibility(View.GONE);
            }
        }
        viewHolder.text_content.setText(charinfo.getText_context());
        viewHolder.flag=mlist.get(position).getKinds();
//        myUpload.download_asynchronous_head("wangweibodata", "headimg/" +charinfo.getImg_id(),viewHolder.img_head);
       // viewHolder.img_head.setImageResource(charinfo.getImg_id());
        return convertView;
    }
    private class WuViewHolder{
        private TextView text_time;
        private TextView text_content;
        private ImageView img_head;
        private LinearLayout line_time;
        private int flag;
    }
}
