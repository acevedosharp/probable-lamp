package sabana.mrp1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sabana.mrp1.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}