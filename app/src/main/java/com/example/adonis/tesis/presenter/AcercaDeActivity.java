package com.example.adonis.tesis.presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.adonis.tesis.R;

import util.SessionSettings;

public class AcercaDeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);
        if (SessionSettings.getUsuarioIniciado() == null) {
            Intent intent = new Intent(this, ValidacionActivity.class);
            startActivity(intent);
        }
    }
}
