package co.edu.uniandes.dse.TallerPersistencia.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
public class DirectorEntity extends BaseEntity {
    private String nombre;
    private String biografia;
    
    @ManyToMany
    private java.util.List<PeliculaEntity> peliculas;
}
