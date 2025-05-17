package com.example.fedegan.repository;

import com.example.fedegan.model.Vacunacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VacunacionRepository extends JpaRepository<Vacunacion, Long> {
    List<Vacunacion> findAllByIdVacunador(Long idVacunador);
}

