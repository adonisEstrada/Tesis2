package com.example.adonis.tesis.dto;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(foreignKeys = @ForeignKey(entity = Interconsulta.class,
        parentColumns = "interconsulta_id",
        childColumns = "consulta_id"))
public class Consulta {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "consulta_id")
    private int consultaId;

    private String descripcion;

    public int getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(int consultaId) {
        this.consultaId = consultaId;
    }

    public Consulta() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
