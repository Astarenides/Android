package com.curso.pruebas.threads;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

    ProgressBar progress;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            progress.incrementProgressBy(5);
        }
    };
    boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = (ProgressBar) findViewById(R.id.progress);
    }

    @Override
    protected void onStart(){
        super.onStart();
        progress.setProgress(0);

        Thread background = new Thread(new Runnable(){
            public void run(){
                try {
                    for (int i=0; i < 20; i++) {
                        Thread.sleep(1000);
                        handler.sendMessage(handler.obtainMessage());
                    }
                } catch (Throwable t) {
                    //kjhasja
                }
            }
        });
        isRunning = true;
        background.start();
    }

    public void stop() {
        super.onStop();
        isRunning = false;
    }
}
