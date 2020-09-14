package com.example.adonis.tesis.presenter;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.adonis.tesis.R;
import com.example.adonis.tesis.dto.Consulta;
import com.example.adonis.tesis.dto.Interconsulta;
import com.example.adonis.tesis.dto.Paciente;
import com.example.adonis.tesis.dto.SignoVital;
import com.example.adonis.tesis.viewmodel.ConsultaViewModel;
import com.example.adonis.tesis.viewmodel.InterconsultaViewModel;
import com.example.adonis.tesis.viewmodel.PacienteViewModel;

import java.util.List;

import util.Converters;
import util.Pdf;
import util.SessionSettings;

public class VerInterconsultaActivity extends AppCompatActivity {

    //    private ConsultaViewModel consultaViewModel;
    private PacienteViewModel pacienteViewModel;
    private InterconsultaViewModel interconsultaViewModel;
    private TextView textViewEspecialidad;
    private TextView textViewMedico;
    private TextView textViewInforme;
    private TextView textViewEdad;
    private TextView textViewPaciente;
    private Consulta consulta;
    private Paciente paciente;

    /**
     * uso exclusivo para el reporte
     */
    private SignoVital signoVital;
    private ProgressDialog progressDialog;

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
        this.textViewInforme.setText(consulta.getInforme());
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
        interconsultaViewModel = ViewModelProviders.of(this).get(InterconsultaViewModel.class);
        pacienteViewModel = ViewModelProviders.of(this).get(PacienteViewModel.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando... Por favor espere.");

        /**
         * Validar que el usuario este ingresado
         */
        if (SessionSettings.getUsuarioIniciado() == null) {
            Intent intent = new Intent(this, ValidacionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            textViewMedico.setText(SessionSettings.getUsuarioIniciado().getNombreCompleto());
            textViewEspecialidad.setText(SessionSettings.getUsuarioIniciado().getEspecialidad());
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            interconsultaViewModel.getInterconsulta(bundle.getInt("interconsulta"))
                    .observe(this, new Observer<Interconsulta>() {
                        @Override
                        public void onChanged(@Nullable Interconsulta interconsulta) {
                            if (interconsulta != null
                                    && interconsulta.getConsulta() != null) {
                                setConsulta(interconsulta.getConsulta());
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
            interconsultaViewModel.getInterconsultaSignoVital(bundle.getInt("paciente"))
                    .observe(this, new Observer<List<Interconsulta>>() {
                        @Override
                        public void onChanged(@Nullable List<Interconsulta> interconsultas) {
                            if (interconsultas != null && !interconsultas.isEmpty()) {
                                setSignoVital(interconsultas.get(0).getSignoVital());
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

    public void imprimirSelectHandler(View v) {
        new AlertDialog.Builder(this).setMessage("Será generado un pdf del informe medico")
                .setNeutralButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        generarReporte();
                    }
                }).show();
    }

    private void generarReporte() {
        showProgressDialog();
        Pdf pdf = new Pdf();
        pdf.generarPdf(this, consulta, paciente, getSignoVital());
        hideProgressDialog();
        new AlertDialog.Builder(this)
                .setMessage("Reporte generado correctamente en la ruta :" +
                        Pdf.getRuta().getAbsolutePath()).show().setCancelable(true);
    }

    public void showProgressDialog() {
        progressDialog.show();
    }

    public void hideProgressDialog() {
        progressDialog.hide();
    }

    public SignoVital getSignoVital() {
        return signoVital;
    }

    public void setSignoVital(SignoVital signoVital) {
        this.signoVital = signoVital;
    }
}
