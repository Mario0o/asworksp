package com.ggq.bluetoothsocket.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ggq.bluetoothsocket.R;
import com.ggq.bluetoothsocket.vo.MessageBean;

import java.util.List;

/**
 * Created by DYK on 2015/11/15.
 */
public class MyChatAdapter extends BaseAdapter {


    private List<MessageBean> messageList;
    private Context context;
    private LayoutInflater inflater;

    public MyChatAdapter(List<MessageBean> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Boolean ismy = messageList.get(position).getIsMy();
        if (convertView == null) {
            if (ismy)
            {
                convertView = inflater.inflate(R.layout.layout_mymessage_item, null);
                holder = new ViewHolder();
                holder.iv_Head = (ImageView) convertView.findViewById(R.id.iv_my_head);
                holder.tv_message = (TextView) convertView.findViewById(R.id.tv_my_message);
            }
            else
            {
                convertView = inflater.inflate(R.layout.layout_othermessage_item, null);
                holder = new ViewHolder();
                holder.iv_Head = (ImageView) convertView.findViewById(R.id.iv_other_head);
                holder.tv_message = (TextView) convertView.findViewById(R.id.tv_other_message);
            }
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_message.setText(messageList.get(position).getMessage());
        return convertView;
    }
    static class ViewHolder {
        ImageView iv_Head;
        TextView tv_message;
    }
}
