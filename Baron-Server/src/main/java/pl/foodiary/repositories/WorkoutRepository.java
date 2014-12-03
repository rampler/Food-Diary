package pl.foodiary.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.foodiary.domain.User;
import pl.foodiary.domain.Workout;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-12-02.
 */
public interface WorkoutRepository extends PagingAndSortingRepository<Workout, UUID> {
	long countByUser(User user);
	long countByWorkoutDate(Date workoutDate);
	long countByUserAndWorkoutDate(User user, Date workoutDate);
	Iterable<Workout> findByWorkoutDate(Date workoutDate);
	Iterable<Workout> findByUser(User user);
	Iterable<Workout> findByUserAndWorkoutDate(User user, Date workoutDate);
}
