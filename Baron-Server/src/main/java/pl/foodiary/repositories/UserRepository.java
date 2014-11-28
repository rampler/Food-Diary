package pl.foodiary.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.foodiary.domain.User;

import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-27.
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	User findOneByLogin(String login);
	User findOneById(UUID id);
}
