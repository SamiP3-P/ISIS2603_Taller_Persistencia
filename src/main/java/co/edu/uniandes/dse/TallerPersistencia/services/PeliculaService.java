package co.edu.uniandes.dse.TallerPersistencia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.TallerPersistencia.repositories.PeliculaRepository;
import co.edu.uniandes.dse.TallerPersistencia.entities.PeliculaEntity;
import co.edu.uniandes.dse.TallerPersistencia.exceptions.IllegalOperationException;

@Service
public class PeliculaService {
    @Autowired
    private PeliculaRepository peliculaRepository;

    public PeliculaEntity crearPelicula(PeliculaEntity pelicula) throws IllegalOperationException {
        // Validar que el nombre no sea vacío
        if (pelicula.getTitulo() == null || pelicula.getTitulo().isEmpty()) {
            throw new IllegalOperationException("El nombre de la película no puede ser vacío");
        }
        
        // Validar que no exista otra película con el mismo nombre
        PeliculaEntity peliculaExistente = peliculaRepository.findByTitulo(pelicula.getTitulo());
        if (peliculaExistente != null) {
            throw new IllegalOperationException("Ya existe una película con el nombre: " + pelicula.getTitulo());
        }
        
        // Validar que el año sea mayor a 1930
        if (pelicula.getAnioLanzamiento() == null || pelicula.getAnioLanzamiento() <= 1930) {
            throw new IllegalOperationException("El año de lanzamiento de la película debe ser mayor a 1930");
        }
        
        return peliculaRepository.save(pelicula);
    }
}
