package pl.foodiary.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.foodiary.domain.Ingredient;

import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-27.
 */
@Repository
public interface IngredientRepository extends PagingAndSortingRepository<Ingredient, Long> {
	Ingredient findOneById(UUID id);
}
