package com.example.adonis.tesis.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.adonis.tesis.dao.ConsultaDao;
import com.example.adonis.tesis.db.TesisRoomDataBase;
import com.example.adonis.tesis.dto.Consulta;

public class ConsultaRepository {

    private ConsultaDao consultaDao;

    public ConsultaRepository(Application application) {
        TesisRoomDataBase tesisRoomDataBase = TesisRoomDataBase.getDatabase(application);
        consultaDao = tesisRoomDataBase.consultaDao();
    }

    public LiveData<Consulta> getConsulta(int interconsulta) {
        return consultaDao.getConsultas(interconsulta);
    }

    public void insertConsulta(Consulta consulta) {
        new InsertConsultaAsyncTask(consultaDao).execute(consulta);
    }

    private static class InsertConsultaAsyncTask extends AsyncTask<Consulta, Void, Void> {
        private ConsultaDao consultaDao;

        private InsertConsultaAsyncTask(ConsultaDao consultaDao) {
            this.consultaDao = consultaDao;
        }

        @Override
        protected Void doInBackground(Consulta... consultas) {
            consultaDao.insert(consultas[0]);
            return null;
        }
    }
}
