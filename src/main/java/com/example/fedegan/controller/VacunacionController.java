package com.example.fedegan.controller;

import com.example.fedegan.config.JwtUtil;
import com.example.fedegan.model.Vacunacion;
import com.example.fedegan.service.VacunacionService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacunaciones")
public class VacunacionController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private VacunacionService vacunacionService;

    @PostMapping
    public ResponseEntity<Vacunacion> agregarVacunacion(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Vacunacion vacunacion) {

        // Extraer token
        String token = authHeader.replace("Bearer ", "");

        // Validar y parsear token (usando tu clase JWT utilitaria)
        Claims claims = jwtUtil.parseToken(token);
        String rol = claims.get("role", String.class);

        if (!"vacunador".equalsIgnoreCase(rol)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Obtener id del vacunador del token (subject o claim "id")
        String vacunadorIdStr = claims.getSubject();
        Long vacunadorId = Long.parseLong(vacunadorIdStr);

        // Asignar el idVacunador al objeto Vacunacion
        vacunacion.setIdVacunador(vacunadorId);

        Vacunacion guardada = vacunacionService.registrarVacunacion(vacunacion);
        return ResponseEntity.ok(guardada);
    }

    @GetMapping("/mis-vacunaciones")
    public ResponseEntity<List<Vacunacion>> obtenerVacunacionesPorVacunador(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        Claims claims = jwtUtil.parseToken(token);

        String rol = claims.get("role", String.class);
        if (!"vacunador".equalsIgnoreCase(rol)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Supongamos que el JWT guarda el id del vacunador como subject o en otro claim
        String vacunadorIdStr = claims.getSubject(); // o claims.get("id", String.class);
        Long vacunadorId;
        try {
            vacunadorId = Long.parseLong(vacunadorIdStr);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }

        List<Vacunacion> vacunaciones = vacunacionService.obtenerVacunacionesPorVacunadorId(vacunadorId);
        return ResponseEntity.ok(vacunaciones);
    }

    @GetMapping("/vacunaciones")
    public ResponseEntity<List<Vacunacion>> obtenerTodasLasVacunaciones(
            @RequestHeader("Authorization") String authHeader) {

        // Extraer token
        String token = authHeader.replace("Bearer ", "");

        // Validar token
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Parsear claims
        Claims claims = jwtUtil.parseToken(token);
        String rol = claims.get("role", String.class);

        if (!"admin".equalsIgnoreCase(rol)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Obtener todas las vacunaciones
        List<Vacunacion> vacunaciones = vacunacionService.obtenerTodasVacunaciones();
        return ResponseEntity.ok(vacunaciones);
    }

}

