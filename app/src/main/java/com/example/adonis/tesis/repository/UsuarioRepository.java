package com.example.adonis.tesis.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.graphics.LightingColorFilter;
import android.os.AsyncTask;

import com.example.adonis.tesis.dao.UsuarioDao;
import com.example.adonis.tesis.db.TesisRoomDataBase;
import com.example.adonis.tesis.dto.Usuario;

public class UsuarioRepository {

    private UsuarioDao usuarioDao;


    public UsuarioRepository(Application application) {
        TesisRoomDataBase tesisRoomDataBase = TesisRoomDataBase.getDatabase(application);
        usuarioDao = tesisRoomDataBase.usuarioDao();
    }

    public LiveData<Usuario> getUsuarioLiveData(Usuario usuario) {
        return usuarioDao.getUsuario(usuario.getNombre(), usuario.getClave());
    }

    public void insertUsuario(Usuario usuario) {
        new getUsuarioAsyncTask(usuarioDao).execute(usuario);
    }

    public LiveData<Usuario> getUsuarioByName(String name) {
        return usuarioDao.getUsuarioByName(name);
    }

    public LiveData<Usuario> getUsuarioByCedula(Integer cedula) {
        return usuarioDao.getUsuarioByCedula(cedula);
    }


    private static class getUsuarioAsyncTask extends AsyncTask<Usuario, Void, Void> {

        private UsuarioDao usuarioDao;

        public getUsuarioAsyncTask(UsuarioDao usuarioDao) {
            this.usuarioDao = usuarioDao;
        }

        @Override
        protected Void doInBackground(Usuario... usuarios) {
            usuarioDao.insert(usuarios[0]);
            return null;
        }
    }
}
