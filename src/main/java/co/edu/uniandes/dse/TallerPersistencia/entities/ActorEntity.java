package co.edu.uniandes.dse.TallerPersistencia.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
public class ActorEntity extends BaseEntity {
    private String nombre;
    private String  nacionalidad;
    
    @ManyToMany
    private java.util.List<PeliculaEntity> peliculas;
}
