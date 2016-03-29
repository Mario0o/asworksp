package com.example.yyh.test6.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yyh.test6.R;
import com.example.yyh.test6.ui.adapter.MyChatAdapter;
import com.example.yyh.test6.util.Constact;
import com.example.yyh.test6.util.UtilPool;
import com.example.yyh.test6.vo.MessageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DYK on 2015/11/14.
 */
public class FragmentChat extends Fragment implements View.OnClickListener {

    private View contentView;
    private Button bt_Send;
    private EditText ed_Write;
    private ListView lv_Chat;
    private BluetoothSocket mSocket;
    private UtilPool utilPool;
    private List<MessageBean> messageList = new ArrayList<MessageBean>();
    private MyChatAdapter myChatAdapter;

    protected static final int SUCCESS_CONNECT = 0;
    protected static final int SUCCESS_ACCEPT = 1;
    protected static final int SUCCESS_EXCHANGE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_chat, null);
        initView();
        return contentView;
    }

    private void initView() {
        utilPool = new UtilPool(BluetoothAdapter.getDefaultAdapter(), mHandler);
        utilPool.Accept();
        bt_Send = (Button) contentView.findViewById(R.id.bt_send);
        ed_Write = (EditText) contentView.findViewById(R.id.ed_write);
        lv_Chat = (ListView) contentView.findViewById(R.id.lv_chat);
        myChatAdapter = new MyChatAdapter(messageList, getActivity());
        lv_Chat.setAdapter(myChatAdapter);
        bt_Send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!ed_Write.getText().toString().trim().equals("")) {
            MessageBean bean = new MessageBean();
            bean.setFrom("I");
            bean.setTo("Y");
            bean.setMessage(ed_Write.getText().toString());
            bean.setIsMy(true);
            messageList.add(bean);
            myChatAdapter.notifyDataSetInvalidated();
            utilPool.Exchange(Constact.NowSocket,ed_Write.getText().toString());
        } else
            Toast.makeText(getActivity(), "you input null", Toast.LENGTH_LONG).show();
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SUCCESS_CONNECT:
                    Toast.makeText(getActivity(), "connect OK", Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS_ACCEPT:
                    Toast.makeText(getActivity(), "accept OK", Toast.LENGTH_LONG).show();
                    break;
                case SUCCESS_EXCHANGE:
                    byte[] readBuf = (byte[]) msg.obj;
                    MessageBean bean = new MessageBean();
                    bean.setFrom("Y");
                    bean.setTo("I");
                    bean.setMessage(new String(readBuf, 0, msg.arg1));
                    bean.setIsMy(false);
                    messageList.add(bean);
                    myChatAdapter.notifyDataSetInvalidated();
                    break;

            }
        }
    };
}
