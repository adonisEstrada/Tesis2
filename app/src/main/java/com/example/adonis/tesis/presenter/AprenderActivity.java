package com.example.adonis.tesis.presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.adonis.tesis.R;

import util.SessionSettings;

public class AprenderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprender);
        if (SessionSettings.getUsuarioIniciado() == null) {
            Intent intent = new Intent(this, ValidacionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void buttonContinuarSelectHandler(View v) {
        finish();
//        Intent intent = new Intent(this, MenuActivity.class);
//        startActivity(intent);
    }

    public void imageLearning1SelectHandler(View v) {
        Intent intent = new Intent(this, TouchImageViewActivity.class);
        intent.putExtra("image", R.drawable.learning_1);
        startActivity(intent);
    }

    public void imageLearning2SelectHandler(View v) {
        Intent intent = new Intent(this, TouchImageViewActivity.class);
        intent.putExtra("image", R.drawable.learning_2);
        startActivity(intent);
    }

    public void imageLearning3selectHandler(View v) {
        Intent intent = new Intent(this, TouchImageViewActivity.class);
        intent.putExtra("image", R.drawable.learning_3);
        startActivity(intent);
    }

    public void imageLearning4SelectHandler(View v) {
        Intent intent = new Intent(this, TouchImageViewActivity.class);
        intent.putExtra("image", R.drawable.learning_4);
        startActivity(intent);
    }

    public void imageLearning5selectHandler(View v) {
        Intent intent = new Intent(this, TouchImageViewActivity.class);
        intent.putExtra("image", R.drawable.learning_5);
        startActivity(intent);
    }

    public void imageLearning6SelectHandler(View v) {
        Intent intent = new Intent(this, TouchImageViewActivity.class);
        intent.putExtra("image", R.drawable.learning_6);
        startActivity(intent);
    }

    public void imageLearning7selectHandler(View v) {
        Intent intent = new Intent(this, TouchImageViewActivity.class);
        intent.putExtra("image", R.drawable.learning_7);
        startActivity(intent);
    }

    public void imageLearning8SelectHandler(View v) {
        Intent intent = new Intent(this, TouchImageViewActivity.class);
        intent.putExtra("image", R.drawable.learning_8);
        startActivity(intent);
    }

    public void imageLearning9selectHandler(View v) {
        Intent intent = new Intent(this, TouchImageViewActivity.class);
        intent.putExtra("image", R.drawable.learning_9);
        startActivity(intent);
    }

    public void imageLearning10SelectHandler(View v) {
        Intent intent = new Intent(this, TouchImageViewActivity.class);
        intent.putExtra("image", R.drawable.learning_10);
        startActivity(intent);
    }

    public void imageLearning11selectHandler(View v) {
        Intent intent = new Intent(this, TouchImageViewActivity.class);
        intent.putExtra("image", R.drawable.learning_11);
        startActivity(intent);
    }

    public void imageLearning12SelectHandler(View v) {
        Intent intent = new Intent(this, TouchImageViewActivity.class);
        intent.putExtra("image", R.drawable.learning_12);
        startActivity(intent);
    }

    public void imageLearning13selectHandler(View v) {
        Intent intent = new Intent(this, TouchImageViewActivity.class);
        intent.putExtra("image", R.drawable.learning_13);
        startActivity(intent);
    }
}
