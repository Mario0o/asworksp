package socketClient;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yyh.socketdemo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class MainActivity extends AppCompatActivity {

    private Button btnlogin;
    private TextView txtshow;
    private String send;

    //定义ip地址与端口号常量
    private static final String HOST = "10.150.80.79";
    private static final int PORT =54321;
    //定义Handler对象

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            txtshow.setText(send);

        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnlogin = (Button) findViewById(R.id.btnlogin);
        txtshow = (TextView) findViewById(R.id.txtshow);
        btnlogin.setOnClickListener(new AccpetListener());


    }


    private class AccpetListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            new Thread(){

                @Override
                public void run() {
                    try {

                        Socket socket = new Socket(HOST,PORT);
                        Log.e("errormessange", "链接成功！！");
                        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        send = br.readLine();

                        br.close();


                    } catch (IOException e) {
                        Log.e("errormessange","链接不成功！！");
                        e.printStackTrace();
                    }
                    //要发送消息，handler才会启用，在这里吃了大苦头了。
                  handler.sendEmptyMessage(0);
                }
            }.start();
        }
    }
}
