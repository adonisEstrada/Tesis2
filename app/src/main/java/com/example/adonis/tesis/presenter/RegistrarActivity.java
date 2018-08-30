package com.example.adonis.tesis.presenter;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.adonis.tesis.R;
import com.example.adonis.tesis.dto.Usuario;
import com.example.adonis.tesis.viewmodel.UsuarioViewModel;

import org.w3c.dom.Text;

public class RegistrarActivity extends AppCompatActivity {

    private Usuario usuario;
    private EditText editTextEspecialidad;
    private EditText editTextEspecialidadPersonal;
    private EditText editTextUsuario;
    private EditText editTextClave;
    private EditText editTextConfirmeClave;
    private EditText editTextNombreCompleto;
    private EditText editTextCedula;
    private Switch switchSexo;

    private UsuarioViewModel usuarioViewModel;
    private TextView mensaje;
    private boolean validadoUsuario;
    private boolean validadoCedula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        mensaje = (TextView) findViewById(R.id.mensaje);
        editTextClave = (EditText) findViewById(R.id.editTextClave);
        editTextConfirmeClave = (EditText) findViewById(R.id.editTextConfirmarClave);
        editTextEspecialidad = (EditText) findViewById(R.id.editTextEspecialidad);
        editTextEspecialidadPersonal = (EditText) findViewById(R.id.editTextEspecialidadPersonal);
        editTextUsuario = (EditText) findViewById(R.id.editTextUsuario);
        editTextNombreCompleto = (EditText) findViewById(R.id.edictTextNombreCompleto);
        editTextCedula = (EditText) findViewById(R.id.editTextCedula);
        switchSexo = (Switch) findViewById(R.id.switchSexo);

        usuarioViewModel = ViewModelProviders.of(this).get(UsuarioViewModel.class);
    }

    public void buttonGuardarSelectHandler(View v) {
        if (!editTextUsuario.getText().toString().equals("")
                && !editTextClave.getText().toString().equals("")
                && !editTextNombreCompleto.getText().toString().equals("")
                && !editTextEspecialidad.getText().toString().equals("")
                && !editTextConfirmeClave.getText().toString().equals("")
                && !editTextEspecialidadPersonal.getText().toString().equals("")) {
            if (editTextClave.getText().toString()
                    .equals(editTextConfirmeClave.getText().toString())) {
                usuarioViewModel.getUsuarioByName(editTextUsuario.getText().toString())
                        .observe(this, new Observer<Usuario>() {
                            @Override
                            public void onChanged(@Nullable Usuario usuario) {
                                if (usuario != null) {
                                    setMensaje("El nombre de usuario ya esta en uso.");
                                    setValidadoUsuario(false);
                                } else {
                                    setValidadoUsuario(true);
                                }
                                guardarUsuario();
                            }
                        });
                usuarioViewModel.getUsuarioByCedula(Integer.valueOf(
                        editTextCedula.getText().toString())).observe(this,
                        new Observer<Usuario>() {
                            @Override
                            public void onChanged(@Nullable Usuario usuario) {
                                if (usuario != null) {
                                    setMensaje("Ya existe una persona con esa cedula");
                                    setValidadoCedula(false);
                                } else {
                                    setValidadoCedula(true);
                                }
                                guardarUsuario();
                            }
                        });
            } else {
                mensaje.setText("Las claves deben coincidir");
            }
        } else {
            mensaje.setText("Todos los campos son obligatorios.");
        }
    }

    public void setMensaje(String mensaje) {
        this.mensaje.setText(mensaje);
    }

    public boolean isValidadoUsuario() {
        return validadoUsuario;
    }

    public void setValidadoUsuario(boolean validadoUsuario) {
        this.validadoUsuario = validadoUsuario;
    }

    public boolean isValidadoCedula() {
        return validadoCedula;
    }

    public void setValidadoCedula(boolean validadoCedula) {
        this.validadoCedula = validadoCedula;
    }

    public void guardarUsuario() {
        if (validadoCedula && validadoUsuario) {
            Usuario usuario = new Usuario();
            usuario.setClave(editTextClave.getText().toString());
            usuario.setNombreCompleto(editTextNombreCompleto.getText().toString());
            usuario.setCedula(Integer.valueOf(editTextCedula.getText().toString()));
            usuario.setEspecialidad(editTextEspecialidad.getText().toString());
            usuario.setEspecialidadPersonal(editTextEspecialidadPersonal.getText().toString());
            usuario.setNombre(editTextUsuario.getText().toString());
            usuario.setSexo(!switchSexo.isChecked());
            usuarioViewModel.insert(usuario);
            finish();
        }
    }
}
