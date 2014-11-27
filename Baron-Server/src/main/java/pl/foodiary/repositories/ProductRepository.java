package pl.foodiary.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.foodiary.domain.Product;

/**
 * Created by Mateusz on 2014-11-27.
 */
@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {}
