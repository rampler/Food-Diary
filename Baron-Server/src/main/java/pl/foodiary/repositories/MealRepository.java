package pl.foodiary.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.foodiary.domain.Meal;

/**
 * Created by Mateusz on 2014-11-27.
 */
public interface MealRepository extends PagingAndSortingRepository<Meal, Long> {}
