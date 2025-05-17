package com.example.fedegan;

import com.example.fedegan.model.Usuario;
import com.example.fedegan.repository.UsuarioRepository;
import com.example.fedegan.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        // Inicializa los mocks antes de cada prueba
        MockitoAnnotations.openMocks(this);

        // Crear un usuario de ejemplo
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Perez");
        usuario.setEmail("juan.perez@example.com");
        usuario.setContraseña("password123");
        usuario.setRol("usuario");
    }

    @Test
    public void testRegistrarUsuario() {
        // Simula el comportamiento del PasswordEncoder para que devuelva un hash
        when(passwordEncoder.encode(usuario.getContraseña())).thenReturn("hashedPassword123");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Llama al método del servicio
        Usuario registrado = usuarioService.registrarUsuario(usuario);

        // Verifica que la contraseña fue cifrada y el usuario fue guardado
        assertNotNull(registrado);
        assertEquals("hashedPassword123", registrado.getContraseña());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void testBuscarPorEmail() {
        // Simula el comportamiento del repositorio para encontrar un usuario por su email
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(usuario);

        // Llama al método del servicio
        Usuario encontrado = usuarioService.buscarPorEmail(usuario.getEmail());

        // Verifica que el usuario devuelto sea el correcto
        assertNotNull(encontrado);
        assertEquals("juan.perez@example.com", encontrado.getEmail());
        verify(usuarioRepository, times(1)).findByEmail(usuario.getEmail());
    }

    @Test
    public void testBuscarPorId() {
        // Simula el comportamiento del repositorio para encontrar un usuario por su id
        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuario));

        // Llama al método del servicio
        Usuario encontrado = usuarioService.buscarPorId(1L);

        // Verifica que el usuario devuelto sea el correcto
        assertNotNull(encontrado);
        assertEquals(1L, encontrado.getId());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    public void testBuscarTodos() {
        // Simula el comportamiento del repositorio para devolver una lista de usuarios
        when(usuarioRepository.findAll()).thenReturn(java.util.List.of(usuario));

        // Llama al método del servicio
        java.util.List<Usuario> usuarios = usuarioService.buscarTodos();

        // Verifica que la lista no esté vacía
        assertNotNull(usuarios);
        assertFalse(usuarios.isEmpty());
        assertEquals(1, usuarios.size());
        verify(usuarioRepository, times(1)).findAll();
    }
}

