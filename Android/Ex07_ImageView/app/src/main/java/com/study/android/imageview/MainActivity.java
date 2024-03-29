package com.study.android.imageview;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    ScrollView scrollView01;
    ImageView imageView01;
    ImageView imageView02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView01 = findViewById(R.id.imageView01);
        imageView02 = findViewById(R.id.imageView02);

        imageView01.setImageResource(R.drawable.background2);
        imageView02.setImageResource(0);

        imageView01.invalidate();
        imageView02.invalidate();

        scrollView01 = findViewById(R.id.scrollView01);
        scrollView01.setVerticalScrollBarEnabled(true);
        scrollView01.setHorizontalScrollBarEnabled(true);

    }

    public void onBtn1Clicked(View v){ imageDown(); }
    public void onBtn2Clicked(View v){ imageUp(); }

    public void imageDown(){
        imageView01.setImageResource(0);
        imageView02.setImageResource(R.drawable.background2);

        imageView01.invalidate();
        imageView02.invalidate();
    }
    public void imageUp(){
        imageView01.setImageResource(R.drawable.background2);
        imageView02.setImageResource(0);

        imageView01.invalidate();
        imageView02.invalidate();
    }
}
