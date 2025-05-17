package com.example.fedegan;

import com.example.fedegan.model.Vacunacion;
import com.example.fedegan.repository.VacunacionRepository;
import com.example.fedegan.service.VacunacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VacunacionServiceTest {

    @Mock
    private VacunacionRepository vacunacionRepository;

    @InjectMocks
    private VacunacionService vacunacionService;

    private Vacunacion vacunacion;

    @BeforeEach
    public void setUp() {
        // Inicializa los mocks antes de cada prueba
        MockitoAnnotations.openMocks(this);

        // Crear una vacunación de ejemplo
        vacunacion = new Vacunacion();
        vacunacion.setId(1L);
        vacunacion.setIdVacunador(1L);
        vacunacion.setFinca("Finca 1");
        vacunacion.setCampaign("Campaña 2025");
        vacunacion.setFecha(java.time.LocalDateTime.now());
        vacunacion.setTipoVacuna("Tipo A");
        vacunacion.setObservaciones("Observaciones ejemplo");
    }

    @Test
    public void testRegistrarVacunacion() {
        // Simula el comportamiento del repositorio para guardar una vacunación
        when(vacunacionRepository.save(vacunacion)).thenReturn(vacunacion);

        // Llama al método del servicio
        Vacunacion registrada = vacunacionService.registrarVacunacion(vacunacion);

        // Verifica que la vacunación se guardó correctamente
        assertNotNull(registrada);
        assertEquals(vacunacion.getId(), registrada.getId());
        verify(vacunacionRepository, times(1)).save(vacunacion);
    }

    @Test
    public void testObtenerVacunacionesPorVacunadorId() {
        // Simula el comportamiento del repositorio para devolver una lista de vacunaciones por id de vacunador
        when(vacunacionRepository.findAllByIdVacunador(1L)).thenReturn(List.of(vacunacion));

        // Llama al método del servicio
        List<Vacunacion> vacunaciones = vacunacionService.obtenerVacunacionesPorVacunadorId(1L);

        // Verifica que la lista no esté vacía
        assertNotNull(vacunaciones);
        assertFalse(vacunaciones.isEmpty());
        assertEquals(1, vacunaciones.size());
        assertEquals(vacunacion.getId(), vacunaciones.get(0).getId());
        verify(vacunacionRepository, times(1)).findAllByIdVacunador(1L);
    }

    @Test
    public void testObtenerTodasVacunaciones() {
        // Simula el comportamiento del repositorio para devolver una lista de todas las vacunaciones
        when(vacunacionRepository.findAll()).thenReturn(List.of(vacunacion));

        // Llama al método del servicio
        List<Vacunacion> vacunaciones = vacunacionService.obtenerTodasVacunaciones();

        // Verifica que la lista no esté vacía
        assertNotNull(vacunaciones);
        assertFalse(vacunaciones.isEmpty());
        assertEquals(1, vacunaciones.size());
        assertEquals(vacunacion.getId(), vacunaciones.get(0).getId());
        verify(vacunacionRepository, times(1)).findAll();
    }
}
