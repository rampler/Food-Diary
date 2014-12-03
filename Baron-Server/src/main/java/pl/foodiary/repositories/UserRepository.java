package pl.foodiary.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.foodiary.domain.User;

import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-27.
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, UUID> {
	User findOneByLogin(String login);
}
