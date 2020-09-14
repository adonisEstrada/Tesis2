package com.example.adonis.tesis.presenter;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.adonis.tesis.R;
import com.example.adonis.tesis.dto.Consulta;
import com.example.adonis.tesis.dto.Interconsulta;
import com.example.adonis.tesis.dto.Paciente;
import com.example.adonis.tesis.viewmodel.ConsultaViewModel;
import com.example.adonis.tesis.viewmodel.InterconsultaViewModel;
import com.example.adonis.tesis.viewmodel.PacienteViewModel;

import java.util.Date;

import util.Constantes;
import util.Converters;
import util.SessionSettings;

public class AgregarConsultaActivity extends AppCompatActivity {

    private PacienteViewModel pacienteViewModel;
    private InterconsultaViewModel interconsultaViewModel;
//    private ConsultaViewModel consultaViewModel;
    private TextView textViewPaciente;
    private TextView textViewEdad;
    private TextView mensaje;
    private EditText editTextInforme;
    private Paciente paciente;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_consulta);
        textViewPaciente = (TextView) findViewById(R.id.textViewPaciente);
        textViewEdad = (TextView) findViewById(R.id.textViewEdad);
        mensaje = (TextView) findViewById(R.id.mensaje);
        editTextInforme = (EditText) findViewById(R.id.editTextInforme);
        pacienteViewModel = ViewModelProviders.of(this).get(PacienteViewModel.class);
        interconsultaViewModel = ViewModelProviders.of(this).get(InterconsultaViewModel.class);
//        consultaViewModel = ViewModelProviders.of(this).get(ConsultaViewModel.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando... Por favor espere.");
        /**
         * Validar que el usuario este ingresado
         */
        if (SessionSettings.getUsuarioIniciado() == null) {
            Intent intent = new Intent(this, ValidacionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            showProgressDialog();
            int paciente = bundle.getInt("paciente");
            pacienteViewModel.getPaciente(paciente).observe(this,
                    new Observer<Paciente>() {
                        @Override
                        public void onChanged(@Nullable Paciente paciente) {
                            hideProgressDialog();
                            if (paciente != null) {
                                setPaciente(paciente);
                            }
                        }
                    });
        }
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
        this.textViewEdad.setText(Converters.getEdad(paciente.getFechaIngreso()));
        this.textViewPaciente.setText(paciente.getNombre() + " " + paciente.getApellido());
    }

    public void guardarInterconsultaSelectHandler(View v) {
        showProgressDialog();

        if (!editTextInforme.getText().toString().equals("") && paciente != null) {
            Interconsulta interconsulta = new Interconsulta();
            interconsulta.setTipoInterconsulta(Constantes.TIPO_INTERCONSULTA_CONSULTA);
            interconsulta.setDescripcion("Información de consulta");
            interconsulta.setPaciente(paciente.getPacienteId());
            interconsulta.setFecha(new Date());
            interconsulta.setActivo(true);
            Consulta consulta = new Consulta();
            consulta.setInforme(editTextInforme.getText().toString());
            interconsulta.setConsulta(consulta);
            interconsultaViewModel.insertInterconsulta(interconsulta);

//            consultaViewModel.insertConsulta(consulta);
            finish();
        } else {
            mensaje.setText("El campo no debe estar vacío");
        }
        hideProgressDialog();
    }

    public void showProgressDialog() {
        progressDialog.show();
    }

    public void hideProgressDialog() {
        progressDialog.hide();
    }

}
