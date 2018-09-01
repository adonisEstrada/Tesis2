package com.example.adonis.tesis.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.adonis.tesis.dto.Interconsulta;
import com.example.adonis.tesis.repository.InterconsultaRepository;

import java.util.List;

public class InterconsultaViewModel extends AndroidViewModel {

    InterconsultaRepository interconsultaRepository;

    public InterconsultaViewModel(@NonNull Application application) {
        super(application);
        interconsultaRepository = new InterconsultaRepository(application);
    }

    public LiveData<List<Interconsulta>> getInterconsultas(int paciente) {
        return interconsultaRepository.getInterconsultas(paciente);
    }

    public LiveData<Interconsulta> getInterconsulta(int interconsulta) {
        return interconsultaRepository.getInterconsulta(interconsulta);
    }

    public void insertInterconsulta(Interconsulta interconsulta) {
        interconsultaRepository.insertInterconsulta(interconsulta);
    }
}
