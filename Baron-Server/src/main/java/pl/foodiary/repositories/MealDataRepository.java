package pl.foodiary.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.foodiary.domain.MealData;

/**
 * Created by Mateusz on 2014-11-27.
 */
public interface MealDataRepository extends PagingAndSortingRepository<MealData, Long> {}
