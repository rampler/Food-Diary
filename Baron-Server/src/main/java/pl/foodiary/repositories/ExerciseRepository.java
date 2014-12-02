package pl.foodiary.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.foodiary.domain.Exercise;

import java.util.UUID;

/**
 * Created by Mateusz on 2014-12-02.
 */
@Repository
public interface ExerciseRepository extends PagingAndSortingRepository<Exercise, UUID> {
	Iterable<Exercise> findByUnit(String unit);
	Exercise findOneByName(String name);
}
