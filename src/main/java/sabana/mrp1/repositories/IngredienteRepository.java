package sabana.mrp1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sabana.mrp1.entities.Ingrediente;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {
}
