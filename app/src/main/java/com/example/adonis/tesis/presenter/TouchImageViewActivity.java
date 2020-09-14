package com.example.adonis.tesis.presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.adonis.tesis.R;

import util.SessionSettings;
import util.TouchImageView;

public class TouchImageViewActivity extends AppCompatActivity {

    TouchImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_image_view);
        img = (TouchImageView) findViewById(R.id.img);
        if (SessionSettings.getUsuarioIniciado() == null) {
            Intent intent = new Intent(this, ValidacionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                img.setImageResource(bundle.getInt("image"));
            }
        }
    }
}
