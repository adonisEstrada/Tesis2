package com.example.adonis.tesis.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.adonis.tesis.dto.Usuario;
import com.example.adonis.tesis.repository.UsuarioRepository;

public class UsuarioViewModel extends AndroidViewModel {

    private UsuarioRepository usuarioRepository;

    public UsuarioViewModel(@NonNull Application application) {
        super(application);
        usuarioRepository = new UsuarioRepository(application);
    }

    public LiveData<Usuario> getUsuarioLiveData(Usuario usuario) {
        return usuarioRepository.getUsuarioLiveData(usuario);
    }

    public LiveData<Usuario> getUsuarioByName(String name) {
        return usuarioRepository.getUsuarioByName(name);
    }

    public LiveData<Usuario> getUsuarioByCedula(Integer cedula) {
        return usuarioRepository.getUsuarioByCedula(cedula);
    }

    public void insert(Usuario usuario) {
        usuarioRepository.insertUsuario(usuario);
    }
}
