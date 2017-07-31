package com.samples.realwear.wearml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTitle;
    private TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = (TextView)findViewById(R.id.title);
        mDescription = (TextView)findViewById(R.id.description);
    }

    public void onBoarClick(View v){
        setContent(R.string.boar_title, R.string.boar_description);
    }

    public void onSunshineClick(View v){
        setContent(R.string.sunshine_title, R.string.sunshine_description);
    }

    public void onAmberClick(View v){
        setContent(R.string.amber_title, R.string.amber_description);
    }

    public void setContent(int resTitle, int resDescription){
        mTitle.setText(resTitle);
        mDescription.setText(resDescription);
    }
}
