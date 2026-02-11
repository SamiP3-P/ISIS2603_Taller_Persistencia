package co.edu.uniandes.dse.TallerPersistencia.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class PeliculaEntity extends BaseEntity {
    private String titulo;
    private Integer anioLanzamiento;  
    
    @ManyToMany
    private java.util.List<ActorEntity> actores;

    @ManyToOne
    private DirectorEntity director;
}
