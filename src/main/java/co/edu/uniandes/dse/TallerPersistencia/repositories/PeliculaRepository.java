package co.edu.uniandes.dse.TallerPersistencia.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.uniandes.dse.TallerPersistencia.entities.PeliculaEntity;

@Repository
public interface PeliculaRepository extends JpaRepository<PeliculaEntity, Long> {
    PeliculaEntity findByTitulo(String titulo);
}
