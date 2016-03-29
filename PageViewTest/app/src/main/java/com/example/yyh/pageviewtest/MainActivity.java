package com.example.yyh.pageviewtest;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
   private Button one;
    private Button two;
    private Button three;
    private Button four;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       setupView();



    }

    private void setupView() {

        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.one:
                startActivity(new Intent(this, OneActivity.class));
                break;
            case R.id.two:
                startActivity(new Intent(this,TwoActivity.class));
                break;
            case R.id.three:
                startActivity(new Intent(this,ThreeActivity.class));
                break;
            case R.id.four:
                startActivity(new Intent(this,FourActivity.class));
                break;

        }

    }
}
