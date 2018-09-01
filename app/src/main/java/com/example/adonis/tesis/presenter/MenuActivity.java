package com.example.adonis.tesis.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.adonis.tesis.R;
import com.example.adonis.tesis.dto.SignoVital;
import com.example.adonis.tesis.dto.Usuario;
import com.example.adonis.tesis.presenter.AcercaDeActivity;
import com.example.adonis.tesis.presenter.ListViewActivity;

import java.io.InputStreamReader;
import java.net.URL;

import util.SessionSettings;

public class MenuActivity extends AppCompatActivity {

    private ImageView imagenDoctor;
    private TextView textViewEspecialidadPersonal;
    private TextView textViewNombreDoctor;
    private TextView textViewBienvenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        imagenDoctor = (ImageView) findViewById(R.id.imagenDoctor);
        textViewEspecialidadPersonal = (TextView) findViewById(R.id.textViewEspecialidadPersonal);
        textViewNombreDoctor = (TextView) findViewById(R.id.textViewNombreDoctor);
        textViewBienvenido = (TextView) findViewById(R.id.textViewBienvenido);

        Usuario usuario = SessionSettings.getUsuarioIniciado();
        if (usuario.isSexo()) {
            imagenDoctor.setImageResource(R.mipmap.doctor_foreground);
        } else {
            imagenDoctor.setImageResource(R.mipmap.doctora_foreground);
        }
        String genero = usuario.isSexo() ? "Dr. " : "Dra. ";
        textViewBienvenido.setText(usuario.isSexo() ? "Bienvenido" : "Bienvenida");
        textViewNombreDoctor.setText(genero + usuario.getNombreCompleto());
        textViewEspecialidadPersonal.setText(usuario.getEspecialidadPersonal());


    }

    public void acercaDeButtonHandler(View v) {
        Intent intent = new Intent(this, AcercaDeActivity.class);
        startActivity(intent);
    }

    public void aprenderButtonHandler(View v) {
//        MyClass myClass = new MyClass();
//        myClass.generarReporte();
    }

    public void pacienteButtonHandler(View v) {
        Intent intent = new Intent(this, ListViewActivity.class);
        intent.putExtra("Controlador", "pacientes");
        startActivity(intent);
    }

    public void agregarPacienteButtonHandler(View v) {
        Intent intent = new Intent(this, AgregarPacienteActivity.class);
        startActivity(intent);
    }

    public void agregarSignoButtonHandler(View v) {
        Intent intent = new Intent(this, SignosVitalesActivity.class);
        startActivity(intent);
    }
}
