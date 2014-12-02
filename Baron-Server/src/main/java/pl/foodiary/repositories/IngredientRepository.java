package pl.foodiary.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.foodiary.domain.Ingredient;
import pl.foodiary.domain.Meal;

import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-27.
 */
@Repository
public interface IngredientRepository extends PagingAndSortingRepository<Ingredient, UUID> {
	Iterable<Ingredient> findByMeal(Meal meal);
}
