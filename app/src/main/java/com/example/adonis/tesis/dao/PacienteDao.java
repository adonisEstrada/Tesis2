package com.example.adonis.tesis.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.adonis.tesis.dto.Paciente;

import java.util.List;

@Dao
public interface PacienteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Paciente paciente);

    @Query("SELECT DISTINCT * FROM paciente WHERE paciente.usuario_id=:usuario AND paciente.activo=1 ORDER BY nombre ASC")
    LiveData<List<Paciente>> getPacientes(int usuario);

    @Query("SELECT DISTINCT * FROM paciente WHERE paciente.paciente_id=:paciente")
    LiveData<Paciente> getPaciente(int paciente);

    @Query("SELECT DISTINCT * FROM paciente WHERE " +
            "nombre like :search " +
            "or apellido like :search " +
            "or cedula like :search")
    LiveData<List<Paciente>> getPacientes(String search);

//    public List<Paciente> getPacientes() {
//
////        AsyncTask<Paciente, Integer, List<Paciente>> obtenerPacientes =
////                new AsyncTask<Paciente, Integer, List<Paciente>>() {
////                    @Override
////                    protected List<Paciente> doInBackground(Paciente... pacientes) {
////                        try {
////
////                            URL url = new URL(Constantes.URL);
////                            HttpURLConnection httpURLConnection
////                                    = (HttpURLConnection) url.openConnection();
////
////                        } catch (MalformedURLException e) {
////                            e.printStackTrace();
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
////                        return null;
////                    }
////                };
//
////        return null;
////    }
}