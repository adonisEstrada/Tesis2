package com.example.adonis.tesis.dto;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//@Entity(foreignKeys = @ForeignKey(entity = Interconsulta.class,
//        parentColumns = "interconsulta_id",
//        childColumns = "consulta_id"))
@Entity
public class Consulta {

    @PrimaryKey()
//    @NonNull
    @ColumnInfo(name = "consulta_id")
    private int consultaId;

    private String informe;

    public int getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(int consultaId) {
        this.consultaId = consultaId;
    }

    public Consulta() {
    }

    public String getInforme() {
        return informe;
    }

    public void setInforme(String informe) {
        this.informe = informe;
    }
}
