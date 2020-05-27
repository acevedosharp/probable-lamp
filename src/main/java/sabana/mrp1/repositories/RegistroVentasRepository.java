package sabana.mrp1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sabana.mrp1.entities.RegistroVentas;

import java.util.Set;

@Repository
public interface RegistroVentasRepository extends JpaRepository<RegistroVentas, Integer> {
    Set<RegistroVentas> findAllByTiempoEquals(String str);
}
