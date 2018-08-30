package com.example.adonis.tesis.presenter;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.adonis.tesis.R;
import com.example.adonis.tesis.dto.Usuario;
import com.example.adonis.tesis.viewmodel.UsuarioViewModel;

import util.SessionSettings;

public class MainActivity extends AppCompatActivity {

    private UsuarioViewModel usuarioViewModel;
    private EditText editTextUsuario;
    private EditText editTextClave;
    private TextView textViewRed;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUsuario = (EditText) findViewById(R.id.editTextUsuario);
        editTextClave = (EditText) findViewById(R.id.editTextClave);
        textViewRed = (TextView) findViewById(R.id.textViewRed);
        usuarioViewModel = ViewModelProviders.of(this).get(UsuarioViewModel.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando... Por favor espere.");

    }

    public void ingresarButtonSelectHandler(View v) {

        if (editTextUsuario.getText() != null && !editTextUsuario.getText().toString().equals("")
                && editTextClave.getText() != null && !editTextClave.getText().toString().equals("")) {
            showProgressDialog();
            Usuario usuario = new Usuario();
            usuario.setNombre(editTextUsuario.getText().toString());
            usuario.setClave(editTextClave.getText().toString());
            usuarioViewModel.getUsuarioLiveData(usuario).observe(this,
                    new Observer<Usuario>() {
                        @Override
                        public void onChanged(@Nullable Usuario usuario) {
                            if (usuario != null) {
                                SessionSettings.setUsuarioIniciado(usuario);
                                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                startActivity(intent);
                            } else {
                                setAlertText("Usted no es un usuario.");
                            }
                            hideProgressDialog();
                        }
                    });
        } else {
            setAlertText("Los campos no puede estar vacíos.");
        }
    }

    public void registrarButtonSelectHandler(View v) {
        Intent intent = new Intent(this, RegistrarActivity.class);
        startActivity(intent);
    }

    public void setAlertText(String message) {
        textViewRed.setText(message);
    }

    public void showProgressDialog() {
        progressDialog.show();
    }

    public void hideProgressDialog() {
        progressDialog.hide();
    }
}
