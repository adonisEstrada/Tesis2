package com.example.adonis.tesis.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.adonis.tesis.dao.InterconsultaDao;
import com.example.adonis.tesis.dao.UsuarioDao;
import com.example.adonis.tesis.db.TesisRoomDataBase;
import com.example.adonis.tesis.dto.Interconsulta;
import com.example.adonis.tesis.dto.Usuario;

import java.util.List;

public class InterconsultaRepository {

    private InterconsultaDao interconsultaDao;

    public InterconsultaRepository(Application application) {
        TesisRoomDataBase tesisRoomDataBase = TesisRoomDataBase.getDatabase(application);
        interconsultaDao = tesisRoomDataBase.interconsultaDao();
    }

    public LiveData<List<Interconsulta>> getInterconsultas(int paciente) {
        return interconsultaDao.getInterconsultaByPaciente(paciente);
    }

    public void insertInterconsulta(Interconsulta interconsulta) {
        new InsertInterconsultaAsyncTask(interconsultaDao).execute(interconsulta);
    }

    private static class InsertInterconsultaAsyncTask extends AsyncTask<Interconsulta, Void, Void> {

        private InterconsultaDao interconsultaDao;

        public InsertInterconsultaAsyncTask(InterconsultaDao interconsultaDao) {
            this.interconsultaDao = interconsultaDao;
        }

        @Override
        protected Void doInBackground(Interconsulta... interconsultas) {
            interconsultaDao.insert(interconsultas[0]);
            return null;
        }
    }
}
