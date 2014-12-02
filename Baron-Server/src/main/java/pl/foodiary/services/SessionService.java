package pl.foodiary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.foodiary.domain.Session;
import pl.foodiary.domain.User;
import pl.foodiary.exceptions.NotAuthorizedException;
import pl.foodiary.repositories.SessionRepository;
import pl.foodiary.repositories.UserRepository;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-30.
 */
@Service
public class SessionService {

	@Autowired
	private SessionRepository sessionRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Method checks is session is still active.
	 *
	 * @param sessionId - Session ID
	 * @param ipAddress - Ip Address
	 * @return User / Code 403
	 */
	public User checkSession(UUID sessionId, String ipAddress) {
		try {
			Session session = sessionRepository.findOne(sessionId);
			if ((session.getLastActivityDate().getTime() - System.currentTimeMillis()) / (1000 * 60) < 30 && session.getIpAddress().equals(ipAddress)) {
				session.setLastActivityDate(new Date());
				sessionRepository.save(session);
				return session.getUser();
			}
			else {
				sessionRepository.delete(session);
				throw new NotAuthorizedException();
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			throw new NotAuthorizedException();
		}
	}
}
