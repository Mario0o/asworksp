package com.example.yyh.pagetest2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button one,two,three,four;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews() {
        one = (Button) findViewById(R.id.one);
        one.setOnClickListener(this);
        two = (Button) findViewById(R.id.two);
        two.setOnClickListener(this);
        three = (Button) findViewById(R.id.three);
        three.setOnClickListener(this);
        four = (Button) findViewById(R.id.four);
        four.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.one:
                startActivity(new Intent(this,OneAcivity.class));
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
