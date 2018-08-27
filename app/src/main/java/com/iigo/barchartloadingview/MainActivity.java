package com.iigo.barchartloadingview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.iigo.library.BarChartLoadingView;

public class MainActivity extends AppCompatActivity {
    private BarChartLoadingView barChartLoadingView1;
    private BarChartLoadingView barChartLoadingView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barChartLoadingView1 = findViewById(R.id.bclv_loading1);
        barChartLoadingView2 = findViewById(R.id.bclv_loading2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        barChartLoadingView1.release();
        barChartLoadingView2.release();
    }

    public void onStart(View view) {
        barChartLoadingView1.start();
        barChartLoadingView2.start();
    }

    public void onStop(View view) {
        barChartLoadingView1.stop();
        barChartLoadingView2.stop();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        barChartLoadingView1.setBarNumber(6);
//        barChartLoadingView1.setColorSchemeColors(new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA,
//            Color.GRAY, Color.YELLOW});
//
//        return super.onKeyDown(keyCode, event);
//    }
}
