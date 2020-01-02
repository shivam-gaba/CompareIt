package com.example.compareit;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity
{

    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    ivBack=findViewById(R.id.ivBack);
    ivBack.isClickable();
    ivBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AboutActivity.this.finish();
        }
    });

    }
}
