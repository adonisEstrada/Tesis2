package com.example.adonis.tesis.presenter;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
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

    private Paciente paciente;

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

        /**
         * Validar que el usuario este ingresado
         */
        if (SessionSettings.getUsuarioIniciado() == null) {
            Intent intent = new Intent(this, ValidacionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                pacienteViewModel.getPaciente(bundle.getInt("paciente")).observe(
                        this, new Observer<Paciente>() {
                            @Override
                            public void onChanged(@Nullable Paciente paciente) {
                                setPaciente(paciente);
                            }
                        }
                );
            }
        }
    }

    public void buttonGuardarPacienteSelectHandler(View v) {
        showProgressDialog();
        if (!editTextNombrePaciente.getText().toString().equals("")
                && !editTextApellidoPaciente.getText().toString().equals("")
                && !editTextCedulaPaciente.getText().toString().equals("")
                && fechaNacimiento != null) {
            Paciente pacienteAGuardar = null;

            if (paciente != null) {
                pacienteAGuardar = paciente;
            } else {
                pacienteAGuardar = new Paciente();
            }
            pacienteAGuardar.setFechaIngreso(fechaNacimiento);
            pacienteAGuardar.setUsuario(SessionSettings.getUsuarioIniciado().getUsuarioId());
            pacienteAGuardar.setApellido(editTextApellidoPaciente.getText().toString());
            pacienteAGuardar.setNombre(editTextNombrePaciente.getText().toString());
            pacienteAGuardar.setCedula(editTextCedulaPaciente.getText().toString());
            pacienteAGuardar.setActivo(true);
            pacienteAGuardar.setSexo(!sexo);
            pacienteAGuardar.setFecha(new Date());
            pacienteAGuardar.setVisible(true);
            pacienteViewModel.insertPaciente(pacienteAGuardar);
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
                setEditTextFechaNacimiento(date);
            }
        }, fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH), fecha.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void setEditTextFechaNacimiento(Date date) {
        String fecha = new SimpleDateFormat("dd/MM/yyyy").format(date);
        editTextFechaNacimiento.setText(fecha);
        setFechaNacimiento(date);
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
        setSexo(sexo);
    }

    private void setSexo(boolean sexo) {
        if (sexo) {
            buttonSexo.setBackgroundColor(getResources().getColor(R.color.colorMujer));
            buttonSexo.setText("Mujer");
        } else {
            buttonSexo.setBackgroundColor(getResources().getColor(R.color.colorVerdeAnalogo1));
            buttonSexo.setText("Hombre");
        }
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
        editTextNombrePaciente.setText(paciente.getNombre());
        editTextApellidoPaciente.setText(paciente.getApellido());
        editTextCedulaPaciente.setText(paciente.getCedula());
        setEditTextFechaNacimiento(paciente.getFechaIngreso());
        setSexo(!paciente.isSexo());
    }
}
