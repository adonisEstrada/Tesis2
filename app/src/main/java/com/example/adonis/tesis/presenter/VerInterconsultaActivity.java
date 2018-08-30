package com.example.adonis.tesis.presenter;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.media.MediaCas;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.adonis.tesis.R;
import com.example.adonis.tesis.dto.Consulta;
import com.example.adonis.tesis.dto.Paciente;
import com.example.adonis.tesis.viewmodel.ConsultaViewModel;
import com.example.adonis.tesis.viewmodel.PacienteViewModel;

import org.w3c.dom.Text;

import java.util.Date;

import util.Converters;
import util.SessionSettings;

public class VerInterconsultaActivity extends AppCompatActivity {

    private ConsultaViewModel consultaViewModel;
    private PacienteViewModel pacienteViewModel;
    private TextView textViewEspecialidad;
    private TextView textViewMedico;
    private TextView textViewInforme;
    private TextView textViewEdad;
    private TextView textViewPaciente;
    private Consulta consulta;
    private Paciente paciente;

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
        this.textViewInforme.setText(consulta.getDescripcion());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_interconsulta);
        textViewInforme = (TextView) findViewById(R.id.textViewInforme);
        textViewEspecialidad = (TextView) findViewById(R.id.textViewEspecialidad);
        textViewMedico = (TextView) findViewById(R.id.textViewMedico);
        textViewEdad = (TextView) findViewById(R.id.textViewEdad);
        textViewPaciente = (TextView) findViewById(R.id.textViewPaciente);
        consultaViewModel = ViewModelProviders.of(this).get(ConsultaViewModel.class);
        pacienteViewModel = ViewModelProviders.of(this).get(PacienteViewModel.class);

        textViewMedico.setText(SessionSettings.getUsuarioIniciado().getNombreCompleto());
        textViewEspecialidad.setText(SessionSettings.getUsuarioIniciado().getEspecialidad());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            consultaViewModel.getConsultas(bundle.getInt("interconsulta"))
                    .observe(this, new Observer<Consulta>() {
                        @Override
                        public void onChanged(@Nullable Consulta consulta) {
                            if (consulta != null) {
                                setConsulta(consulta);
                            }
                        }
                    });
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

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
        this.textViewEdad.setText(Converters.getEdad(paciente.getFechaIngreso()));
        this.textViewPaciente.setText(paciente.getNombre() + " " + paciente.getApellido());
    }
}
