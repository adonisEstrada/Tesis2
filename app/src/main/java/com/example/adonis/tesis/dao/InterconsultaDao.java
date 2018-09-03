package com.example.adonis.tesis.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.adonis.tesis.dto.Interconsulta;

import java.util.Date;
import java.util.List;

@Dao
public interface InterconsultaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Interconsulta interconsulta);

    @Query("SELECT DISTINCT * FROM interconsulta WHERE paciente=:paciente AND activo = 1 ORDER BY fecha DESC")
    LiveData<List<Interconsulta>> getInterconsultaByPaciente(int paciente);

    @Query("SELECT DISTINCT * FROM interconsulta WHERE interconsulta_id=:interconsulta")
    LiveData<Interconsulta> getInterconsulta(int interconsulta);

    @Query("SELECT DISTINCT * FROM interconsulta " +
            "WHERE tipo_interconsulta=2 " +
            "AND fecha<=:fecha " +
            "AND paciente=:paciente " +
            "ORDER BY fecha ASC")
    LiveData<List<Interconsulta>> getInterconsultaLessDate(Date fecha, int paciente);
}
