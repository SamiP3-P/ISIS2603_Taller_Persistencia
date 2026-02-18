package co.edu.uniandes.dse.TallerPersistencia.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.TallerPersistencia.entities.PeliculaEntity;
import co.edu.uniandes.dse.TallerPersistencia.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.TallerPersistencia.repositories.PeliculaRepository;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(PeliculaService.class)
public class PeliculaServiceTest {
    
    /**
     * Servicio a probar: Contiene la lógica de negocio para crear películas
     * Se inyecta automáticamente por Spring
     */
    @Autowired
    private PeliculaService peliculaService;
    
    /**
     * Repositorio de películas: Acceso directo a la base de datos
     * Se usa para verificar que los datos se guardaron correctamente
     * y para limpiar la BD antes y después de cada prueba
     */
    @Autowired
    private PeliculaRepository peliculaRepository;
    
    /**
     * Entidad de película base para pruebas: Representa una película válida
     * Se crea en el método setUp() y se reutiliza en las pruebas
     * como punto de partida para crear variaciones
     */
    private PeliculaEntity peliculaValida;

    /**
     * Configuración inicial antes de cada prueba
     * Limpia el repositorio y crea una película válida de referencia
     */
    @BeforeEach
    void setUp() {
        peliculaRepository.deleteAll();
        
        peliculaValida = new PeliculaEntity();
        peliculaValida.setTitulo("Inception");
        peliculaValida.setAnioLanzamiento(2010);
    }

    /**
     * Prueba: Crear una película válida debe guardarse correctamente
     */
    @Test
    void testCrearPeliculaValida() throws IllegalOperationException {
        PeliculaEntity peliculaGuardada = peliculaService.crearPelicula(peliculaValida);
        
        assertNotNull(peliculaGuardada);
        assertEquals("Inception", peliculaGuardada.getTitulo());
        assertEquals(2010, peliculaGuardada.getAnioLanzamiento());
    }

    /**
     * Prueba: Intentar crear una película con nombre vacío lanza excepción
     */
    @Test
    void testCrearPeliculaConNombreVacio() {
        peliculaValida.setTitulo("");
        
        assertThrows(IllegalOperationException.class, () -> {
            peliculaService.crearPelicula(peliculaValida);
        });
    }

    /**
     * Prueba: Intentar crear una película con nombre nulo lanza excepción
     */
    @Test
    void testCrearPeliculaConNombreNulo() {
        peliculaValida.setTitulo(null);
        
        assertThrows(IllegalOperationException.class, () -> {
            peliculaService.crearPelicula(peliculaValida);
        });
    }

    /**
     * Prueba: No puede existir otra película con el mismo nombre
     */
    @Test
    void testCrearPeliculaDuplicada() throws IllegalOperationException {
        // Crear la primera película
        peliculaService.crearPelicula(peliculaValida);
        
        // Intentar crear otra con el mismo nombre
        PeliculaEntity peliculaDuplicada = new PeliculaEntity();
        peliculaDuplicada.setTitulo("Inception");
        peliculaDuplicada.setAnioLanzamiento(2015);
        
        assertThrows(IllegalOperationException.class, () -> {
            peliculaService.crearPelicula(peliculaDuplicada);
        });
    }

    /**
     * Prueba: El año de lanzamiento debe ser mayor a 1930
     */
    @Test
    void testCrearPeliculaConAnioBajo() {
        peliculaValida.setAnioLanzamiento(1930);
        
        assertThrows(IllegalOperationException.class, () -> {
            peliculaService.crearPelicula(peliculaValida);
        });
    }

    /**
     * Prueba: El año de lanzamiento debe ser válido (mayor a 1930)
     */
    @Test
    void testCrearPeliculaConAnioValido() throws IllegalOperationException {
        peliculaValida.setAnioLanzamiento(1931);
        
        PeliculaEntity peliculaGuardada = peliculaService.crearPelicula(peliculaValida);
        
        assertNotNull(peliculaGuardada);
        assertEquals(1931, peliculaGuardada.getAnioLanzamiento());
    }

    /**
     * Prueba: El año nulo debe lanzar excepción
     */
    @Test
    void testCrearPeliculaConAnioNulo() {
        peliculaValida.setAnioLanzamiento(null);
        
        assertThrows(IllegalOperationException.class, () -> {
            peliculaService.crearPelicula(peliculaValida);
        });
    }
//----------------------------------------------------------------------
// Escenarios adicionales para cubrir más casos y aumentar la cobertura de pruebas
// Punto del parcila
//----------------------------------------------------------------------
    /**
     * Escenario OK: Crear una película con datos correctos
     * Verifica que una película con título válido y año mayor a 1930 se crea correctamente
     */
    @Test
    void testDatosCorrectos() throws IllegalOperationException {
        PeliculaEntity peliculaGuardada = peliculaService.crearPelicula(peliculaValida);
        
        assertNotNull(peliculaGuardada);
        assertEquals("Inception", peliculaGuardada.getTitulo());
        assertEquals(2010, peliculaGuardada.getAnioLanzamiento());
    }

    /**
     * Escenario no OK: Crear una película con año 1900
     * Verifica que el año 1900 no sea válido (menor a 1930) y lance excepción
     */
    @Test
    void testAnio1900NoValido() {
        peliculaValida.setAnioLanzamiento(1900);
        
        assertThrows(IllegalOperationException.class, () -> {
            peliculaService.crearPelicula(peliculaValida);
        });
    }
}
