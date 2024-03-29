package pl.foodiary.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.foodiary.domain.Meal;
import pl.foodiary.domain.User;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-27.
 */
@Repository
public interface MealRepository extends PagingAndSortingRepository<Meal, UUID> {
	long countByUser(User user);
	long countByConsumptionDay(Date consumptionDay);
	long countByUserAndConsumptionDay(User user, Date consumptionDay);
	Iterable<Meal> findByUser(User user);
	Iterable<Meal> findByConsumptionDay(Date consumptionDay);
	Iterable<Meal> findByUserAndConsumptionDay(User user, Date consumptionDay);
}
