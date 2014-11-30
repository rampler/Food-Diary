package pl.foodiary.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.foodiary.domain.Profile;
import pl.foodiary.domain.User;

import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-30.
 */
@Repository
public interface ProfileRepository extends PagingAndSortingRepository<Profile, Long> {
	Long countByUser(User user);

	Profile findOneById(UUID id);
	Profile findOneByUser(User user);
}
