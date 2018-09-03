package com.example.adonis.tesis.presenter;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.adonis.tesis.R;
import com.example.adonis.tesis.dto.Paciente;
import com.example.adonis.tesis.dto.Usuario;
import com.example.adonis.tesis.viewmodel.PacienteViewModel;

import java.util.Calendar;
import java.util.List;

import util.Pdf;
import util.SessionSettings;

public class MenuActivity extends AppCompatActivity {

    private ImageView imagenDoctor;
    private TextView textViewEspecialidadPersonal;
    private TextView textViewNombreDoctor;
    private TextView textViewBienvenido;
    private PacienteViewModel pacienteViewModel;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        imagenDoctor = (ImageView) findViewById(R.id.imagenDoctor);
        textViewEspecialidadPersonal = (TextView) findViewById(R.id.textViewEspecialidadPersonal);
        textViewNombreDoctor = (TextView) findViewById(R.id.textViewNombreDoctor);
        textViewBienvenido = (TextView) findViewById(R.id.textViewBienvenido);
        pacienteViewModel = ViewModelProviders.of(this).get(PacienteViewModel.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Procesando... Espere por favor.");

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

    public void reporteButtonSelectHandler(View v) {
        Calendar fecha = Calendar.getInstance();
        final Calendar fechaDesde = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                fechaDesde.set(year, month, dayOfMonth);
                datePickerHasta(fechaDesde);
            }
        }, fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH), fecha.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setMessage("Seleccione la fecha desde el cual comenzar la busqueda de pacientes");
        datePickerDialog.show();
    }

    public void asignarPacientes(Calendar desde, Calendar hasta) {
        showProgressDialog();
        pacienteViewModel.getPacientes(desde.getTime(), hasta.getTime()).observe(
                this, new Observer<List<Paciente>>() {
                    @Override
                    public void onChanged(@Nullable List<Paciente> pacientes) {
                        if (pacientes != null && !pacientes.isEmpty()) {
                            generarReporte(pacientes);
                        } else {
                            mostrarMensaje("No hay pacientes en esas fechas.");
                        }
                        hideProgressDialog();
                    }
                }
        );
    }

    public void mostrarMensaje(String mensaje) {
        new AlertDialog.Builder(this).setMessage(mensaje).setCancelable(true).show();
    }

    public void datePickerHasta(final Calendar desde) {
        Calendar fecha = Calendar.getInstance();

        final Calendar fechaHasta = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
//                month = month + 1;
                fechaHasta.set(year, month, dayOfMonth);
                asignarPacientes(desde, fechaHasta);
            }
        }, fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH), fecha.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setMessage("Seleccione la fecha hasta el cual finaliza la busqueda de pacientes");
        datePickerDialog.show();
    }

    public void generarReporte(List<Paciente> pacientes) {
        Pdf pdf = new Pdf();
        pdf.generarPdf(this, pacientes);
        new AlertDialog.Builder(this)
                .setMessage("Reporte generado correctamente en la ruta :" +
                        Pdf.getRuta().getAbsolutePath()).show().setCancelable(true);
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

    public void showProgressDialog() {
        progressDialog.show();
    }

    public void hideProgressDialog() {
        progressDialog.hide();
    }
}
