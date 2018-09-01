package com.example.adonis.tesis.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.adonis.tesis.dao.SignoVitalDao;
import com.example.adonis.tesis.dao.UsuarioDao;
import com.example.adonis.tesis.db.TesisRoomDataBase;
import com.example.adonis.tesis.dto.SignoVital;
import com.example.adonis.tesis.dto.Usuario;

import java.util.List;

public class SignoVitalRepository {

//    private SignoVitalDao signoVitalDao;
//
//    public SignoVitalRepository(Application application) {
//        TesisRoomDataBase tesisRoomDataBase = TesisRoomDataBase.getDatabase(application);
//        signoVitalDao = tesisRoomDataBase.signoVitalDao();
//    }
//
//    public LiveData<SignoVital> getSignoVitales(int interconsulta) {
//        return signoVitalDao.getSignoVitales(interconsulta);
//    }
//
//    public void insertSignoVital(SignoVital signoVital) {
//        new InsertSignoVitalAsyncTask(signoVitalDao).equals(signoVital);
//    }
//
//    private static class InsertSignoVitalAsyncTask extends AsyncTask<SignoVital, Void, Void> {
//        private SignoVitalDao signoVitalDao;
//
//        public InsertSignoVitalAsyncTask(SignoVitalDao signoVitalDao) {
//            this.signoVitalDao = signoVitalDao;
//        }
//
//        @Override
//        protected Void doInBackground(SignoVital... signoVitals) {
//            signoVitalDao.insert(signoVitals[0]);
//            return null;
//        }
//    }
}
