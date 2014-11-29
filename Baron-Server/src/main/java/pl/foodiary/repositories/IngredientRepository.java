package pl.foodiary.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.foodiary.domain.Ingredient;

/**
 * Created by Mateusz on 2014-11-27.
 */
public interface IngredientRepository extends PagingAndSortingRepository<Ingredient, Long> {}
