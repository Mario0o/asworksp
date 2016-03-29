package com.example.yyh.socketdemo2;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class MainActivity extends AppCompatActivity implements Runnable{

    private TextView txtshow;
    private EditText editsend;
    private Button btnsend;
    private static final String HOST = "10.150.80.79";
    private static final int PORT =54321;
    private Socket socket = null;
    private BufferedReader br =null;
    private PrintWriter pw = null;
    private String content ="";


    //定义一个Handler对象，用来刷新界面
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0x123){

                txtshow.setText(txtshow.getText().toString()+content);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtshow = (TextView) findViewById(R.id.txtshow);
        editsend = (EditText) findViewById(R.id.editsend);
        btnsend = (Button) findViewById(R.id.btnsend);

        //当程序一开始运行的时候就实例化Socket对象，与服务端进行链接，获取输入输出流，


        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    socket = new Socket(HOST,PORT);
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editsend.getText().toString();
                if (socket.isConnected()) {
                    if (!socket.isOutputShutdown()) {
                        pw.println(msg);
                    }

                }
            }
        });


        new Thread(MainActivity.this).start();

    }


    @Override
    public void run() {
        try {
        while (true){
            if (socket.isConnected()){
                if (!socket.isInputShutdown()){

                        if((content=br.readLine())!=null){
                            content+="\n";
                            handler.sendEmptyMessage(0x123);
                        }

                }
            }

        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

    }
}
