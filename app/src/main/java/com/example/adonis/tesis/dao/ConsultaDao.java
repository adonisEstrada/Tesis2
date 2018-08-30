package com.example.adonis.tesis.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.adonis.tesis.dto.Consulta;

import java.util.List;

@Dao
public interface ConsultaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Consulta consulta);

    @Query("SELECT DISTINCT * FROM consulta WHERE consulta_id=:interconsulta")
    public LiveData<Consulta> getConsultas(int interconsulta);
}
