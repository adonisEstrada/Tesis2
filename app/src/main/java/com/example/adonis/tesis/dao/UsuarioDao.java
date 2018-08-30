package com.example.adonis.tesis.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.adonis.tesis.dto.Usuario;

@Dao
public interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Usuario usuario);

    @Query("SELECT DISTINCT * FROM usuario WHERE usuario.nombre=:nombre AND usuario.clave=:clave")
    LiveData<Usuario> getUsuario(String nombre, String clave);

    @Query("SELECT DISTINCT * FROM usuario WHERE usuario.nombre=:name")
    LiveData<Usuario> getUsuarioByName(String name);

    @Query("SELECT DISTINCT * FROM usuario WHERE usuario.cedula=:cedula")
    LiveData<Usuario> getUsuarioByCedula(Integer cedula);


}
