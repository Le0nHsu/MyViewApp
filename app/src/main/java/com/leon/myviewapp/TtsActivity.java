package com.leon.myviewapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.leon.myviewapp.ui.ClearEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TtsActivity extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener {
    private static String TAG = "TtsActivity";
    private TextToSpeech textToSpeech;
    private TextView play;
    private TextView show;
    private float yindiao = 1.0f;
    private float yusu = 1.0f;
    private String GOOGLE_TTS_PACKAGE = "com.google.android.tts";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);
        if(isGoogleTTSInstalled()){
            openTTSSettingsToInstallUnsupportedLanguage();
        }else{
            installGoogleTTS();
        }
        initViews();
    }

    private void initViews() {
        play = (TextView) findViewById(R.id.play);
        play.setOnClickListener(this);
        show = (TextView) findViewById(R.id.show);
        findViewById(R.id.chushihua).setOnClickListener(this);
        findViewById(R.id.yindiaojia).setOnClickListener(this);
        findViewById(R.id.yindiaojian).setOnClickListener(this);
        findViewById(R.id.yusujia).setOnClickListener(this);
        findViewById(R.id.yusujian).setOnClickListener(this);
        if (textToSpeech == null) {
            textToSpeech = new TextToSpeech(this, this,"pico");
            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {
                    Log.d(TAG, String.format("onStart,utteranceId=%s", utteranceId));
                }

                @Override
                public void onDone(String utteranceId) {
                    Log.d(TAG, String.format("onDone,utteranceId=%s", utteranceId));
                }

                @Override
                public void onError(String utteranceId) {
                    Log.d(TAG, String.format("onError,utteranceId=%s", utteranceId));
                }
            });
        }
        updateShow();
    }

    private ClearEditText getEt(){
        return (ClearEditText) findViewById(R.id.et);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chushihua:
                yindiao = 1.0f;
                yusu = 1.0f;
                updateParams();
                break;
            case R.id.yindiaojia:
                yindiao += 0.1;
                updateParams();
                break;
            case R.id.yindiaojian:
                yindiao -= 0.1;
                updateParams();
                break;
            case R.id.yusujia:
                yusu += 0.1;
                updateParams();
                break;
            case R.id.yusujian:
                yusu -= 0.1;
                updateParams();
                break;
            case R.id.play:
                textToSpeech.speak(getEt().getText().toString(), TextToSpeech.QUEUE_FLUSH, null, getEt().getText().toString());
                break;
        }
    }

    private void updateParams() {
        ArrayList<TextToSpeech.EngineInfo> list = new ArrayList<>();
        list.addAll(textToSpeech.getEngines());
        ttsParam();
        updateShow();
    }

    private void updateShow() {
        show.setText("音调： " + yindiao + "\n语速： " + yusu);
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            //释放资源
            if (textToSpeech.isSpeaking()) textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            //初始化tts引擎
            int result = textToSpeech.setLanguage(Locale.FRANCE);
            //设置参数
            ttsParam();
            judgeIfSupported(result);
        } else if (status == TextToSpeech.ERROR) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断是否支持当前语言
     * @param result
     * @return
     */
    private boolean judgeIfSupported(int result) {
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(this, "语音包丢失或语音不支持", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE || result != TextToSpeech.LANG_AVAILABLE) {
            Toast.makeText(this, "TTS暂时不支持这种语音的朗读！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void ttsParam() {
        textToSpeech.setPitch(yindiao);// 设置音调，,1.0是常规
        textToSpeech.setSpeechRate(yusu);//设定语速，1.0正常语速
    }

    private boolean isGoogleTTSInstalled() {
        Intent ttsIntent = new Intent();
        ttsIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        PackageManager pm = this.getPackageManager();
        List<ResolveInfo> listOfInstalledTTSInfo = pm.queryIntentActivities(ttsIntent, PackageManager.GET_META_DATA);
        for (ResolveInfo r : listOfInstalledTTSInfo) {
            String engineName = r.activityInfo.applicationInfo.packageName;
            if (engineName.equals(GOOGLE_TTS_PACKAGE)) {
                return true;
            }
        }
        return false;

    }

    private void installGoogleTTS() {
        Intent goToMarket = new Intent(Intent.ACTION_VIEW)
                .setData(Uri.parse("market://details?id=com.google.android.tts"));
        startActivity(goToMarket);

    }

    private void openTTSSettingsToInstallUnsupportedLanguage() {
        Intent intent = new Intent();
        intent.setAction("com.android.settings.TTS_SETTINGS");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
