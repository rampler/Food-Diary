package pl.foodiary.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.foodiary.domain.Ingredient;
import pl.foodiary.domain.Meal;
import pl.foodiary.domain.Product;
import pl.foodiary.domain.User;

import java.util.List;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-27.
 */
@Repository
public interface IngredientRepository extends PagingAndSortingRepository<Ingredient, UUID> {
	long countByMeal(Meal meal);
	Iterable<Ingredient> findByMeal(Meal meal);
}
