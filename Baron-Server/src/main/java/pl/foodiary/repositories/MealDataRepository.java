package pl.foodiary.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.foodiary.domain.MealData;
import pl.foodiary.domain.User;

/**
 * Created by Mateusz on 2014-11-27.
 */
public interface MealDataRepository extends PagingAndSortingRepository<MealData, Long> {
	Iterable<MealData> findByUser(User user);
}
