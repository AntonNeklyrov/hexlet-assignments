package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import exercise.model.Product;

import org.springframework.data.domain.Sort;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN
    List<Product> findAllByPriceBetween(int min, int max, Sort sort);

    List<Product> findAllByPriceGreaterThan(int min, Sort sort);

    List<Product> findAllByPriceLessThan(int max, Sort sort);
}
