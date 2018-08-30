package com.example.adonis.tesis.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.adonis.tesis.dao.PacienteDao;
import com.example.adonis.tesis.dao.UsuarioDao;
import com.example.adonis.tesis.db.TesisRoomDataBase;
import com.example.adonis.tesis.dto.Paciente;
import com.example.adonis.tesis.dto.Usuario;

import java.util.List;

public class PacienteRepository {

    private PacienteDao pacienteDao;

    public PacienteRepository(Application application) {
        TesisRoomDataBase tesisRoomDataBase = TesisRoomDataBase.getDatabase(application);
        this.pacienteDao = tesisRoomDataBase.pacienteDao();
    }

    public LiveData<List<Paciente>> getPacientes(int usuario) {
        return pacienteDao.getPacientes(usuario);
    }

    public void insertPaciente(Paciente paciente) {
        new insertPacienteAsyncTask(pacienteDao).execute(paciente);
    }

    public LiveData<Paciente> getPaciente(int paciente) {
        return pacienteDao.getPaciente(paciente);
    }

    private static class insertPacienteAsyncTask extends AsyncTask<Paciente, Void, Void> {

        private PacienteDao pacienteDao;

        public insertPacienteAsyncTask(PacienteDao pacienteDao) {
            this.pacienteDao = pacienteDao;
        }

        @Override
        protected Void doInBackground(Paciente... pacientes) {
            pacienteDao.insert(pacientes[0]);
            return null;
        }
    }
}