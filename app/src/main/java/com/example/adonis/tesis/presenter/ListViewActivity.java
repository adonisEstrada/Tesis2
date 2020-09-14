package com.example.adonis.tesis.presenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adonis.tesis.R;
import com.example.adonis.tesis.adapter.GenericAdapter;
import com.example.adonis.tesis.adapter.ItemsListView;
import com.example.adonis.tesis.dto.Interconsulta;
import com.example.adonis.tesis.dto.Paciente;
import com.example.adonis.tesis.viewmodel.InterconsultaViewModel;
import com.example.adonis.tesis.viewmodel.PacienteViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.Constantes;
import util.Converters;
import util.SessionSettings;

public class ListViewActivity extends AppCompatActivity {

    private List<Paciente> pacientes;
    private List<Interconsulta> interconsultas;
    private PacienteViewModel pacienteViewModel;
    private InterconsultaViewModel interconsultaViewModel;
    private ImageView imageViewAgregar;

    private EditText editTextSearch;
    private LinearLayout layoutSearch;
    private Button buttonMostrar;

    private int paciente;
    private int estadoMostrar = 0;
    private ProgressDialog progressDialog;
    private Object itemSelected;

    private boolean isPaciente;

    private ListView listView;
    private GenericAdapter genericAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        listView = (ListView) findViewById(R.id.listView);
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        pacienteViewModel = ViewModelProviders.of(this).get(PacienteViewModel.class);
        interconsultaViewModel = ViewModelProviders.of(this).get(InterconsultaViewModel.class);
        imageViewAgregar = (ImageView) findViewById(R.id.imageViewAgregar);
        layoutSearch = (LinearLayout) findViewById(R.id.layoutSearch);
        buttonMostrar = (Button) findViewById(R.id.buttonMostrar);
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
            final Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                if (bundle.getString("Controlador").equals("pacientes")) {
                    imageViewAgregar.setImageResource(R.mipmap.agregar_paciente_foreground);
                    layoutSearch.setVisibility(View.VISIBLE);
                    showProgressDialog();
                    pacienteViewModel.getPacientes(SessionSettings.getUsuarioIniciado().getUsuarioId()).observe(this, new Observer<List<Paciente>>() {
                        @Override
                        public void onChanged(@Nullable List<Paciente> pacientes) {
                            setAdapterPacientes(pacientes);
                        }
                    });
//                editTextSearch.setOnKeyListener(new View.OnKeyListener() {
//                    @Override
//                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                        buscarPaciente(editTextSearch.getText().toString());
//                        return false;
//                    }
//                });
//                editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                    @Override
//                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                        buscarPaciente(textView.getText().toString());
//                        return false;
//                    }
//                });
                }
                if (bundle.getString("Controlador").equals("interconsulta")) {
                    layoutSearch.setVisibility(View.GONE);
                    if (bundle.getInt("paciente") >= 0) {
                        imageViewAgregar.setImageResource(R.mipmap.agregar_interconsulta_foreground);
                        showProgressDialog();
                        interconsultaViewModel.getInterconsultas(bundle.getInt("paciente"))
                                .observe(this, new Observer<List<Interconsulta>>() {
                                    @Override
                                    public void onChanged(@Nullable List<Interconsulta> interconsultas) {
                                        setAdapterInterconsulta(interconsultas);
                                        setPaciente(bundle.getInt("paciente"));
                                    }
                                });
                    }
                }
            }
        }
    }

    private void setAdapterPacientes(List<Paciente> pacientes) {
        List<ItemsListView> adapters = new ArrayList<>();
        for (Paciente paciente : pacientes) {
            adapters.add(new ItemsListView(
                    paciente.getNombre() + " " + paciente.getApellido(),
                    paciente.getCedula(),
                    Converters.getEdad(paciente.getFechaIngreso()),
                    new SimpleDateFormat("dd/MM/yy hh:mm aa").format(paciente.getFecha())));
        }
        setIsPaciente(true);
        buttonMostrar.setText("Mostrar Atendidos");
        setListAdapter(adapters);
        setListPacientes(pacientes);
        hideProgressDialog();
    }

    private void setAdapterInterconsulta(List<Interconsulta> interconsultas) {
        List<ItemsListView> adapters = new ArrayList<>();
        for (Interconsulta interconsulta : interconsultas) {
            adapters.add(new ItemsListView(interconsulta.getDescripcion(),
                    new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa")
                            .format(interconsulta.getFecha())));
        }
        setListAdapter(adapters);
        setListPacientes(null);
        setIsPaciente(false);
        setInterconsultas(interconsultas);
        buttonMostrar.setText("Mostrar Consultas");
        if (interconsultas.isEmpty()) {
            preguntarCrearNuevaInterconsulta();
        }
        hideProgressDialog();
    }

    public void buscarPaciente(String search) {
        showProgressDialog();
        boolean atendido = estadoMostrar == 0 ? true : false;
        pacienteViewModel.getPaciente(search, atendido).observe(this, new Observer<List<Paciente>>() {
            @Override
            public void onChanged(@Nullable List<Paciente> pacientes) {
                List<ItemsListView> adapters = new ArrayList<>();
                for (Paciente paciente : pacientes) {
                    adapters.add(new ItemsListView(
                            paciente.getNombre() + " " + paciente.getApellido(),
                            paciente.getCedula(),
                            Converters.getEdad(paciente.getFechaIngreso())));
                }
                setIsPaciente(true);
                setListAdapter(adapters);
                setListPacientes(pacientes);
                hideProgressDialog();
            }
        });
    }

    public void setListAdapter(List<ItemsListView> adapters) {
        genericAdapter = new GenericAdapter(this,
                R.id.listView, adapters);
        listView.setAdapter(genericAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ListViewActivity.class);
                intent.putExtra("Controlador", "interconsulta");
                if (getPacientes() != null && getPacientes().get(i) != null) {
                    intent.putExtra("paciente",
                            getPacientes().get(i).getPacienteId());
                    setPaciente(getPacientes().get(i).getPacienteId());
                    startActivity(intent);
                }
                if (getInterconsultas() != null && getInterconsultas().get(i) != null) {
                    Intent intentInterconsulta = null;

                    if (getInterconsultas().get(i).getTipoInterconsulta()
                            == Constantes.TIPO_INTERCONSULTA_CONSULTA) {
                        intentInterconsulta = new Intent(getApplicationContext(),
                                VerInterconsultaActivity.class);
                    }
                    if (getInterconsultas().get(i).getTipoInterconsulta()
                            == Constantes.TIPO_INTERCONSULTA_SIGNO_VITAL) {
                        intentInterconsulta = new Intent(getApplicationContext(),
                                SignosVitalesActivity.class);
                    }
                    if (intentInterconsulta != null) {
                        intentInterconsulta.putExtra("interconsulta",
                                getInterconsultas().get(i).getInterconsultaId());
                        intentInterconsulta.putExtra("paciente",
                                getInterconsultas().get(i).getPaciente());
                        startActivity(intentInterconsulta);
                    }
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (getPacientes() != null && getPacientes().get(i) != null) {
                    setItemSelected(getPacientes().get(i));
                    borrar();
                } else if (getInterconsultas() != null && getInterconsultas().get(i) != null) {
                    setItemSelected(getInterconsultas().get(i));
                    borrar();
                }
                return true;
            }
        });
    }

    public void setListPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Interconsulta> getInterconsultas() {
        return interconsultas;
    }

    public void setInterconsultas(List<Interconsulta> interconsultas) {
        this.interconsultas = interconsultas;
    }

    public void agregarPacienteSelectHandler(View v) {
        if (isPaciente()) {
            Intent intent = new Intent(this, AgregarPacienteActivity.class);
            startActivity(intent);
        } else {
            preguntarCrearTipoInterconsulta();
        }
    }

    public boolean isPaciente() {
        return isPaciente;
    }

    public void setIsPaciente(boolean paciente) {
        isPaciente = paciente;
    }

    public void preguntarCrearNuevaInterconsulta() {
        new AlertDialog.Builder(this)
                .setMessage("Este paciente no contiene nuevos estudios, ¿Desea realizar uno nuevo?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        preguntarCrearTipoInterconsulta();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                }).show();
    }

    public void preguntarCrearTipoInterconsulta() {
        new AlertDialog.Builder(this)
                .setMessage("¿Qué tipo de estudio desea realizar?")
                .setPositiveButton("Información de Consulta", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        iniciarAgregarInterconsultaAcivity();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Signo Vital", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        iniciarAgregarSignoVitalActivity();
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    public int getPaciente() {
        return paciente;
    }

    public void setPaciente(int paciente) {
        this.paciente = paciente;
    }

    public void iniciarAgregarInterconsultaAcivity() {
        Intent intent = new Intent(this,
                AgregarConsultaActivity.class);
        intent.putExtra("paciente", getPaciente());
        startActivity(intent);
    }

    public void iniciarAgregarSignoVitalActivity() {
        Intent intent = new Intent(this,
                AgregarSignoVitalActivity.class);
        intent.putExtra("paciente", getPaciente());
        startActivity(intent);
    }

    public void borrar() {
        if (itemSelected != null) {
            if (itemSelected instanceof Interconsulta) {
                new AlertDialog.Builder(this)
                        .setMessage("¿Está seguro de eliminar este estudio?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Interconsulta interconsulta = (Interconsulta) getItemSelected();
                                interconsulta.setActivo(false);
                                interconsultaViewModel.insertInterconsulta(interconsulta);
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();

            } else if (itemSelected instanceof Paciente) {
                new AlertDialog.Builder(this).setMessage("¿Que desea realizar?")
                        .setNeutralButton("Cambiar de estado", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogConfirmDelete();
                            }
                        })
                        .setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), AgregarPacienteActivity.class);
                                intent.putExtra("paciente", ((Paciente) itemSelected).getPacienteId());
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogConfirmDeletePermanent();
                            }
                        }).show();
            }
        }
    }

    public void dialogConfirmDelete() {
        new AlertDialog.Builder(this)
                .setMessage("¿Desea colocar al paciente como " +
                        (estadoMostrar == 0 ? "" : "no ") +
                        "atendido?. "
                        + "\n" + ((Paciente) itemSelected).getNombre() + " " +
                        ((Paciente) itemSelected).getApellido())
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Paciente paciente = (Paciente) itemSelected;
                        paciente.setActivo(estadoMostrar == 0 ? false : true);
                        paciente.setFechaAtendido(new Date());
                        pacienteViewModel.insertPaciente(paciente);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    public void dialogConfirmDeletePermanent() {
        new AlertDialog.Builder(this)
                .setMessage("¿Seguro desea ELIMINAR este paciente?"
                        + "\n" + ((Paciente) itemSelected).getNombre() + " " +
                        ((Paciente) itemSelected).getApellido())
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Paciente paciente = (Paciente) itemSelected;
                        paciente.setVisible(false);
                        pacienteViewModel.insertPaciente(paciente);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    public Object getItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(Object itemSelected) {
        this.itemSelected = itemSelected;
    }

    public void showProgressDialog() {
        progressDialog.show();
    }

    public void hideProgressDialog() {
        progressDialog.hide();
    }

    public void buttonSearchSelectHandler(View v) {
        buscarPaciente(editTextSearch.getText().toString());
    }

    public void buttonMostrarSelectHandler(View v) {
        if (estadoMostrar == 0 && !isPaciente) {
            interconsultaViewModel.getInterconsultaConsulta(paciente)
                    .observe(this, new Observer<List<Interconsulta>>() {
                        @Override
                        public void onChanged(@Nullable List<Interconsulta> interconsultas) {
                            if (interconsultas != null && !interconsultas.isEmpty()) {
                                setAdapterInterconsulta(interconsultas);
                                estadoMostrar = 1;
                                buttonMostrar.setText("Mostrar Signos Vitales");
                            }
                        }
                    });
        } else if (estadoMostrar == 1 && !isPaciente) {
            interconsultaViewModel.getInterconsultaSignoVital(paciente)
                    .observe(this, new Observer<List<Interconsulta>>() {
                        @Override
                        public void onChanged(@Nullable List<Interconsulta> interconsultas) {
                            if (interconsultas != null && !interconsultas.isEmpty()) {
                                setAdapterInterconsulta(interconsultas);
                                estadoMostrar = 2;
                                buttonMostrar.setText("Mostrar Todos");
                            }
                        }
                    });
        } else if (estadoMostrar == 2 && !isPaciente) {
            interconsultaViewModel.getInterconsultas(paciente)
                    .observe(this, new Observer<List<Interconsulta>>() {
                        @Override
                        public void onChanged(@Nullable List<Interconsulta> interconsultas) {
                            if (interconsultas != null && !interconsultas.isEmpty()) {
                                setAdapterInterconsulta(interconsultas);
                                estadoMostrar = 0;
                                buttonMostrar.setText("Mostrar Consultas");
                            }
                        }
                    });
        } else if (estadoMostrar == 0 && isPaciente) {
            pacienteViewModel.getPacientesAtendidos()
                    .observe(this, new Observer<List<Paciente>>() {
                        @Override
                        public void onChanged(@Nullable List<Paciente> pacientes) {
                            if (pacientes != null && !pacientes.isEmpty()) {
                                setAdapterPacientes(pacientes);
                                estadoMostrar = 1;
                                buttonMostrar.setText("Mostrar no Atendidos");
                            }
                        }
                    });
        } else if (estadoMostrar == 1 && isPaciente) {
            pacienteViewModel.getPacientes(SessionSettings.getUsuarioIniciado().getUsuarioId())
                    .observe(this, new Observer<List<Paciente>>() {
                        @Override
                        public void onChanged(@Nullable List<Paciente> pacientes) {
                            if (pacientes != null && !pacientes.isEmpty()) {
                                setAdapterPacientes(pacientes);
                                estadoMostrar = 0;
                                buttonMostrar.setText("Mostrar Atendidos");
                            }
                        }
                    });
        }
    }
}
