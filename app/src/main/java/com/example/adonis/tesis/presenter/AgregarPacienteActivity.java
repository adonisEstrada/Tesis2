package com.example.adonis.tesis.presenter;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.adonis.tesis.R;
import com.example.adonis.tesis.dto.Paciente;
import com.example.adonis.tesis.viewmodel.PacienteViewModel;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

import util.SessionSettings;


public class AgregarPacienteActivity extends AppCompatActivity {

    private EditText editTextFechaNacimiento;
    private EditText editTextNombrePaciente;
    private EditText editTextApellidoPaciente;
    private EditText editTextCedulaPaciente;
    private TextView mensaje;
    private Button buttonSexo;
    private Date fechaNacimiento;
    private boolean sexo;
    private ProgressDialog progressDialog;
    private PacienteViewModel pacienteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_paciente);
        editTextFechaNacimiento = (EditText) findViewById(R.id.editTextFechaNacimiento);
        editTextNombrePaciente = (EditText) findViewById(R.id.editTextNombrePaciente);
        editTextApellidoPaciente = (EditText) findViewById(R.id.editTextApellidoPaciente);
        editTextCedulaPaciente = (EditText) findViewById(R.id.editTextCedulaPaciente);
        buttonSexo = (Button) findViewById(R.id.buttonSexo);
        mensaje = (TextView) findViewById(R.id.mensaje);
        progressDialog = new ProgressDialog(this);
        pacienteViewModel = ViewModelProviders.of(this).get(PacienteViewModel.class);
    }

    public void buttonGuardarPacienteSelectHandler(View v) {
        showProgressDialog();
        if (!editTextNombrePaciente.getText().toString().equals("")
                && !editTextApellidoPaciente.getText().toString().equals("")
                && !editTextCedulaPaciente.getText().toString().equals("")
                && fechaNacimiento != null) {

            Paciente paciente = new Paciente();
            paciente.setFechaIngreso(fechaNacimiento);
            paciente.setUsuario(SessionSettings.getUsuarioIniciado().getUsuarioId());
            paciente.setApellido(editTextApellidoPaciente.getText().toString());
            paciente.setNombre(editTextNombrePaciente.getText().toString());
            paciente.setCedula(editTextCedulaPaciente.getText().toString());
            paciente.setActivo(true);
            paciente.setSexo(!sexo);
            pacienteViewModel.insertPaciente(paciente);
            hideProgressDialog();
            finish();
        } else {
            hideProgressDialog();
            mensaje.setText("Todos los campos so obligatorios");
        }
    }

    public void mostrarDatePicker(View v) {
        Calendar fecha = Calendar.getInstance();
        fecha.set(2000, 1, 1);
        if (fechaNacimiento != null) {
            fecha.setTime(fechaNacimiento);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
//                month = month + 1;
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                Date date = calendar.getTime();
                String fecha = new SimpleDateFormat("dd/MM/yyyy").format(date);
                setEditTextFechaNacimiento(fecha);
                setFechaNacimiento(date);
            }
        }, fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH), fecha.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void setEditTextFechaNacimiento(String fecha) {
        editTextFechaNacimiento.setText(fecha);
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void showProgressDialog() {
        progressDialog.show();
    }

    public void hideProgressDialog() {
        progressDialog.hide();
    }

    public void buttonSexoSelectHandler(View v) {
        sexo = !sexo;
        if (sexo) {
            buttonSexo.setBackgroundColor(getResources().getColor(R.color.colorMujer));
            buttonSexo.setText("Mujer");
        } else {
            buttonSexo.setBackgroundColor(getResources().getColor(R.color.colorVerdeAnalogo1));
            buttonSexo.setText("Hombre");
        }
    }
}
