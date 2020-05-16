package sabana.mrp1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sabana.mrp1.entities.ProductoPersist;

public interface ProductoPersistRepository extends JpaRepository<ProductoPersist, Integer> {
}
