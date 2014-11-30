package pl.foodiary.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.foodiary.domain.Session;
import pl.foodiary.domain.User;

import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-30.
 */
@Repository
public interface SessionRepository extends PagingAndSortingRepository<Session, Long> {
	Session findOneById(UUID id);
	Iterable<Session> findByUser(User user);
}
