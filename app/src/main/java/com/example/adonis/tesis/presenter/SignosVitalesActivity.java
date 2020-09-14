package com.example.adonis.tesis.presenter;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.adonis.tesis.R;
import com.example.adonis.tesis.dto.Interconsulta;
import com.example.adonis.tesis.dto.Paciente;
import com.example.adonis.tesis.dto.SignoVital;
import com.example.adonis.tesis.viewmodel.InterconsultaViewModel;
import com.example.adonis.tesis.viewmodel.PacienteViewModel;
import com.example.adonis.tesis.viewmodel.SignoVitalViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.Converters;
import util.SessionSettings;


public class SignosVitalesActivity extends AppCompatActivity {

    private PacienteViewModel pacienteViewModel;
    //    private SignoVitalViewModel signoVitalViewModel;
    private InterconsultaViewModel interconsultaViewModel;
    private TextView textViewPaciente;
    private TextView textViewFrecuenciaCardiaca;
    private TextView textViewFrecuenciaRespiratoria;
    private TextView textViewTemperatura;
    private TextView textViewSistolica;
    private TextView textViewDiastolica;
    private TextView textViewEdad;

    private LineChart lineChart;

    private SignoVital signoVital;
    private Interconsulta interconsulta;
    private Paciente paciente;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signos_vitales);
        textViewPaciente = (TextView) findViewById(R.id.textViewPaciente);
        textViewFrecuenciaCardiaca = (TextView) findViewById(R.id.textViewFrecuenciaCardiaca);
        textViewFrecuenciaRespiratoria = (TextView) findViewById(R.id.textViewFrecuenciaRespiratoria);
        textViewTemperatura = (TextView) findViewById(R.id.textViewTemperatura);
        textViewSistolica = (TextView) findViewById(R.id.textViewSistolica);
        textViewDiastolica = (TextView) findViewById(R.id.textViewDiastolica);
        textViewEdad = (TextView) findViewById(R.id.textViewEdad);
        lineChart = (LineChart) findViewById(R.id.lineChart);
        pacienteViewModel = ViewModelProviders.of(this).get(PacienteViewModel.class);
        interconsultaViewModel = ViewModelProviders.of(this).get(InterconsultaViewModel.class);
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
            int interconsulta = bundle.getInt("interconsulta");
            interconsultaViewModel.getInterconsulta(interconsulta).observe(
                    this, new Observer<Interconsulta>() {
                        @Override
                        public void onChanged(@Nullable Interconsulta interconsulta) {
                            if (interconsulta != null
                                    && interconsulta.getSignoVital() != null) {
                                setSignoVital(interconsulta.getSignoVital());
                            }
                            setInterconsultaForInterconsultaLessDate(interconsulta);
                        }
                    }
            );
        }
//        List<Entry> entries = new ArrayList<Entry>();
//        entries.add(new Entry(0, 5));
//        entries.add(new Entry(1, 4));
//        entries.add(new Entry(2, 7));
//        LineDataSet dataSet = new LineDataSet(entries, "prueba");
//        List<Entry> dataList = new ArrayList<>();
//        dataList.add(new Entry(0, 2));
//        dataList.add(new Entry(1, 1));
//        dataList.add(new Entry(2, 4));
//        LineDataSet dataSet1 = new LineDataSet(dataList, "prueba2");
//        LineData lineData = new LineData(dataSet, dataSet1);
//
//        lineChart.setData(lineData);
//        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float v, AxisBase axisBase) {
//                return labels.get((int) v);
//            }
//        });
//        YAxis yAxis = lineChart.getAxisLeft();
//        yAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float v, AxisBase axisBase) {
//                return null;
//            }
//        });
//        lineChart.getDescription().setText("Descripcion de prueba");
//        lineChart.invalidate();
    }

    public void setInterconsultaForInterconsultaLessDate(Interconsulta interconsulta) {
        interconsultaViewModel.getInterconsultaLessDate(interconsulta != null
                ? interconsulta.getFecha() : new Date(), interconsulta.getPaciente()).observe(
                this, new Observer<List<Interconsulta>>() {
                    @Override
                    public void onChanged(@Nullable List<Interconsulta> interconsultas) {
                        setDataForLineChart(interconsultas);
                    }
                }
        );
    }

    public void showProgressDialog() {
        progressDialog.show();
    }

    public void hideProgressDialog() {
        progressDialog.hide();
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
        this.textViewEdad.setText(Converters.getEdad(paciente.getFechaIngreso()));
        this.textViewPaciente.setText(paciente.getNombre() + " " + paciente.getApellido());
    }

    public void setSignoVital(SignoVital signoVital) {
        this.signoVital = signoVital;
        int sistole = signoVital.getSistolica();
        int diastole = signoVital.getDiatolica();
        float temperatura = signoVital.getTemperatura();
        int tipoTemperatura = signoVital.getTipoTemperatura();
        int fc = signoVital.getFrecuenciaCardiaca();
        int fr = signoVital.getFecuenciaRespiratoria();
        int colorRojo = getResources().getColor(R.color.colorRojo);
        int colorAzul = getResources().getColor(R.color.colorVerdeAnalogo3);
        int colorGris = getResources().getColor(R.color.colorVerdeTono7);
        if (sistole > 130) {
            textViewSistolica.setTextColor(colorRojo);
        } else if (sistole < 100) {
            textViewSistolica.setTextColor(colorAzul);
        } else {
            textViewSistolica.setTextColor(colorGris);
        }
        if (diastole > 96) {
            textViewDiastolica.setTextColor(colorRojo);
        } else if (diastole < 70) {
            textViewDiastolica.setTextColor(colorAzul);
        } else {
            textViewDiastolica.setTextColor(colorGris);
        }
        if ((temperatura > 37 && tipoTemperatura == 0)
                || (temperatura > 98.6 && tipoTemperatura == 1)) {
            textViewTemperatura.setTextColor(colorRojo);
        } else if ((temperatura < 36 && tipoTemperatura == 0)
                || (temperatura < 96.8 && tipoTemperatura == 1)) {
            textViewTemperatura.setTextColor(colorAzul);
        } else {
            textViewTemperatura.setTextColor(colorGris);
        }
        if (fc > 100) {
            textViewFrecuenciaCardiaca.setTextColor(colorRojo);
        } else if (fc < 60) {
            textViewFrecuenciaCardiaca.setTextColor(colorAzul);
        } else {
            textViewFrecuenciaCardiaca.setTextColor(colorGris);
        }
        if (fr > 12) {
            textViewFrecuenciaRespiratoria.setTextColor(colorRojo);
        } else if (fr < 19) {
            textViewFrecuenciaRespiratoria.setTextColor(colorAzul);
        } else {
            textViewFrecuenciaRespiratoria.setTextColor(colorGris);
        }
        textViewFrecuenciaRespiratoria.setText(fr + "");
        textViewFrecuenciaCardiaca.setText(fc + "");
        textViewTemperatura.setText(String.valueOf(temperatura) + (tipoTemperatura == 0 ? " °C" : " °F"));
        textViewDiastolica.setText(diastole + "");
        textViewSistolica.setText(sistole + "");
    }

    public void setDataForLineChart(final List<Interconsulta> interconsultas) {
        ArrayList<Entry> valoresSistolica = new ArrayList<>();
        ArrayList<Entry> valoresDiastolica = new ArrayList<>();
        ArrayList<Entry> valoresFC = new ArrayList<>();
        ArrayList<Entry> valoresFR = new ArrayList<>();

        for (int i = 0; i < interconsultas.size(); i++) {
            SignoVital signoVital = interconsultas.get(i).getSignoVital();
            valoresSistolica.add(new Entry(i, signoVital.getSistolica()));
            valoresDiastolica.add(new Entry(i, signoVital.getDiatolica()));
            valoresFC.add(new Entry(i, signoVital.getFrecuenciaCardiaca()));
            valoresFR.add(new Entry(i, signoVital.getFecuenciaRespiratoria()));
        }
        LineDataSet lineDataSetSistolica = new LineDataSet(valoresSistolica, "Sistólica");
        lineDataSetSistolica.setColor(getResources().getColor(R.color.colorMujer));
        lineDataSetSistolica.setCircleColor(getResources().getColor(R.color.colorVerdeAnalogo5));
        LineDataSet lineDataSetDiastolica = new LineDataSet(valoresDiastolica, "Diastólica");
        lineDataSetDiastolica.setColor(getResources().getColor(R.color.colorVerdeTriadico2));
        lineDataSetDiastolica.setCircleColor(getResources().getColor(R.color.colorVerdeTinte5));
        LineDataSet lineDataSetFC = new LineDataSet(valoresFC, "FC");
        lineDataSetFC.setColor(getResources().getColor(R.color.colorVerdeAnalogo1));
        lineDataSetFC.setCircleColor(getResources().getColor(R.color.colorVerdeAnalogo1));
        LineDataSet lineDataSetFR = new LineDataSet(valoresFR, "FR");
        lineDataSetFR.setColor(getResources().getColor(R.color.colorVerdeTinte1));
        lineDataSetFR.setCircleColor(getResources().getColor(R.color.colorVerdeTinte5));
        LineData lineData = new LineData(lineDataSetDiastolica,
                lineDataSetSistolica,
                lineDataSetFC,
                lineDataSetFR);
        lineChart.setData(lineData);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                return new SimpleDateFormat("dd/MM").format(interconsultas.get((int) v).getFecha());
            }
        });
        lineChart.getAxisRight().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                return "";
            }
        });
        lineChart.getXAxis().setTextColor(getResources().getColor(R.color.colorVerdeTinte7));
        lineChart.getAxisLeft().setTextColor(getResources().getColor(R.color.colorVerdeTinte7));
        lineChart.getLineData().setValueTextColor(getResources().getColor(R.color.colorVerdeTinte7));
        lineChart.getDescription().setTextColor(getResources().getColor(R.color.colorVerdeTinte7));
        lineChart.getDescription().setText("Signos Vitales");

    }

    public Interconsulta getInterconsulta() {
        return interconsulta;
    }

    public void setInterconsulta(Interconsulta interconsulta) {
        this.interconsulta = interconsulta;
    }
}
