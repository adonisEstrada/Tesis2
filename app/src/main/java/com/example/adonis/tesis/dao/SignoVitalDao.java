package com.example.adonis.tesis.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.adonis.tesis.dto.SignoVital;

import java.util.List;

@Dao
public interface SignoVitalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SignoVital signoVital);

    @Query("SELECT DISTINCT * FROM signovital WHERE signo_vital_id=:interconsulta")
    public LiveData<SignoVital> getSignoVitales(int interconsulta);
}
