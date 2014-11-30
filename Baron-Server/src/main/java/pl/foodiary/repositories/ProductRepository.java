package pl.foodiary.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.foodiary.domain.Product;
import pl.foodiary.domain.ProductCategory;

import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-27.
 */
@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
	Product findOneById(UUID id);
	Iterable<Product> findByCategory(ProductCategory category);
}
