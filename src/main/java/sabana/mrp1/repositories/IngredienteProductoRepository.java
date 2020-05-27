package sabana.mrp1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sabana.mrp1.entities.IngredienteProducto;

@Repository
public interface IngredienteProductoRepository extends JpaRepository<IngredienteProducto, Integer> {
}
