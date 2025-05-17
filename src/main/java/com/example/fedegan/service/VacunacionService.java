package com.example.fedegan.service;

import com.example.fedegan.model.Vacunacion;
import com.example.fedegan.repository.VacunacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacunacionService {

    @Autowired
    private VacunacionRepository vacunacionRepository;

    public Vacunacion registrarVacunacion(Vacunacion vacunacion) {
        return vacunacionRepository.save(vacunacion);
    }

    public List<Vacunacion> obtenerVacunacionesPorVacunadorId(Long vacunadorId) {
        return vacunacionRepository.findAllByIdVacunador(vacunadorId);
    }

    public List<Vacunacion> obtenerTodasVacunaciones() {
        return vacunacionRepository.findAll();
    }
}
