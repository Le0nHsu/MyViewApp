package com.leon.myviewapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.leon.myviewapp.R;
import com.leon.myviewapp.ui.MyToggleView;


public class MainActivity extends Activity implements View.OnClickListener {

    private MyToggleView myToggleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tts).setOnClickListener(this);
//        ActivityThread

        myToggleView = findViewById(R.id.myToggleView);
        myToggleView.setChoosedColor(getResources().getColor(R.color.cciblue));
        myToggleView.setUnChoosedColor(getResources().getColor(R.color.ccigray_a1));
        myToggleView.setStateBackground(getResources().getDrawable(R.drawable.ic_launcher_background));
        myToggleView.setDefaultState(false);
        myToggleView.setOnStateChangeListener(new MyToggleView.OnStateChangeListener(){

            @Override
            public void onStateChange(boolean state) {
                Toast.makeText(getApplicationContext(),"state:" + state,Toast.LENGTH_LONG).show();
            }
        });
        myToggleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tts:
                toTtsAct();
                break;
        }
    }

    private void toTtsAct() {
        startActivity(new Intent(this, TtsActivity.class));
    }
}
