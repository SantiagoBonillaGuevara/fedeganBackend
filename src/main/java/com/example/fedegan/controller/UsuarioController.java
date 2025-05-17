package com.example.fedegan.controller;

import com.example.fedegan.config.JwtUtil;
import com.example.fedegan.model.Usuario;
import com.example.fedegan.repository.UsuarioRepository;
import com.example.fedegan.service.UsuarioService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtils;

    @GetMapping("/perfil")
    public ResponseEntity<?> perfil(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");

        if (!jwtUtils.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }

        String id = jwtUtils.getIdFromToken(token);
        Usuario usuario = usuarioService.buscarPorId(Long.parseLong(id));

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        Map<String, String> perfil = new HashMap<>();
        perfil.put("nombre", usuario.getNombre());
        perfil.put("email", usuario.getEmail());
        perfil.put("rol", usuario.getRol());

        return ResponseEntity.ok(perfil);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<?> obtenerTodosLosUsuarios(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");

        if (!jwtUtils.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }

        Claims claims = jwtUtils.parseToken(token);
        String rol = claims.get("role", String.class);

        if (!"admin".equalsIgnoreCase(rol)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado: solo administradores");
        }

        List<Usuario> usuarios = usuarioService.buscarTodos();

        // Si quieres devolver solo nombre y email para cada usuario, puedes mapearlo así:
        List<Map<String, String>> listaUsuarios = usuarios.stream().map(u -> {
            Map<String, String> info = new HashMap<>();
            info.put("id", u.getId().toString());
            info.put("nombre", u.getNombre());
            info.put("email", u.getEmail());
            info.put("rol", u.getRol());
            return info;
        }).toList();

        return ResponseEntity.ok(listaUsuarios);
    }


    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }
}
