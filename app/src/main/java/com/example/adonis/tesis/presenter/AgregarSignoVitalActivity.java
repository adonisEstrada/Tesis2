package com.example.adonis.tesis.presenter;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.adonis.tesis.R;
import com.example.adonis.tesis.dto.Interconsulta;
import com.example.adonis.tesis.dto.Paciente;
import com.example.adonis.tesis.dto.SignoVital;
import com.example.adonis.tesis.viewmodel.InterconsultaViewModel;
import com.example.adonis.tesis.viewmodel.PacienteViewModel;
import com.example.adonis.tesis.viewmodel.SignoVitalViewModel;

import org.w3c.dom.Text;

import java.util.Date;

import util.Constantes;
import util.Converters;

public class AgregarSignoVitalActivity extends AppCompatActivity {

    private PacienteViewModel pacienteViewModel;
    private InterconsultaViewModel interconsultaViewModel;
    private SignoVitalViewModel signoVitalViewModel;

    private EditText editTextFrecuenciaCardiaca;
    private EditText editTextTemperatura;
    private EditText editTextDiastolica;
    private EditText editTextSistolica;
    private EditText editTextFrecuenciaRespiratoria;

    private TextView textViewPaciente;
    private TextView textViewEdad;
    private TextView mensaje;
    private int tipoTemperatura;

    private Spinner spinnerTemperatura;

    private ProgressDialog progressDialog;

    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_signo_vital);
        editTextFrecuenciaCardiaca = (EditText) findViewById(R.id.editTextFrecuenciaCardiaca);
        editTextTemperatura = (EditText) findViewById(R.id.editTextTemperatura);
        editTextDiastolica = (EditText) findViewById(R.id.editTextDiastolica);
        editTextSistolica = (EditText) findViewById(R.id.editTextSistolica);
        editTextFrecuenciaRespiratoria = (EditText) findViewById(R.id.editTextFrecuenciaRespiratoria);
        textViewPaciente = (TextView) findViewById(R.id.textViewPaciente);
        textViewEdad = (TextView) findViewById(R.id.textViewEdad);
        mensaje = (TextView) findViewById(R.id.mensaje);
        spinnerTemperatura = (Spinner) findViewById(R.id.spinnerTemperatura);
        pacienteViewModel = ViewModelProviders.of(this).get(PacienteViewModel.class);
        interconsultaViewModel = ViewModelProviders.of(this).get(InterconsultaViewModel.class);
        signoVitalViewModel = ViewModelProviders.of(this).get(SignoVitalViewModel.class);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(this, R.array.temperature,
                        android.R.layout.simple_spinner_item);
        spinnerTemperatura.setAdapter(spinnerAdapter);
        spinnerTemperatura.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setTipoTemperatura(i);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando... Por favor espere.");

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

    public void showProgressDialog() {
        progressDialog.show();
    }

    public void hideProgressDialog() {
        progressDialog.hide();
    }

    public void guardarSignoVitalSelectHandler(View v) {
        if (!editTextDiastolica.getText().toString().equals("")
                && !editTextFrecuenciaCardiaca.getText().toString().equals("")
                && !editTextFrecuenciaRespiratoria.getText().toString().equals("")
                && !editTextSistolica.getText().toString().equals("")
                && !editTextTemperatura.getText().toString().equals("")) {
            Interconsulta interconsulta = new Interconsulta();
            interconsulta.setActivo(true);
            interconsulta.setPaciente(paciente.getPacienteId());
            interconsulta.setDescripcion("Signo Vital");
            interconsulta.setFecha(new Date());
            interconsulta.setTipoInterconsulta(Constantes.TIPO_INTERCONSULTA_SIGNO_VITAL);
            interconsultaViewModel.insertInterconsulta(interconsulta);
            SignoVital signoVital = new SignoVital();
            signoVital.setDiatolica(Integer.valueOf(editTextDiastolica.getText().toString()));
            signoVital.setSistolica(Integer.valueOf(editTextSistolica.getText().toString()));
            signoVital.setFecuenciaRespiratoria(
                    Integer.valueOf(editTextFrecuenciaRespiratoria.getText().toString()));
            signoVital.setFrecuenciaCardiaca(
                    Integer.valueOf(editTextFrecuenciaCardiaca.getText().toString()));
            signoVital.setTemperatura(Float.valueOf(editTextTemperatura.getText().toString()));
            signoVital.setTipoTemperatura(tipoTemperatura);
            signoVitalViewModel.insertSignoVital(signoVital);
            finish();
        } else {
            mensaje.setText("Todos los campos son obligatorios");
        }
    }

    public void setTipoTemperatura(int tipoTemperatura) {
        this.tipoTemperatura = tipoTemperatura;
    }
}
