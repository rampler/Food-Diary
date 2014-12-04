package pl.foodiary.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.foodiary.domain.Session;
import pl.foodiary.domain.User;
import pl.foodiary.exceptions.NotAuthorizedException;
import pl.foodiary.repositories.SessionRepository;
import pl.foodiary.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
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

	@Value("${foodiary.session.erase.older.than}")
	private final long eraseDeadline = 30 * 60 * 1000;

	private final static Logger log = LoggerFactory.getLogger(SessionService.class);

	/**
	 * Method checks is session is still active.
	 *
	 * @param sessionId - Session ID
	 * @param request   - HttpServletRequest
	 * @return User / Code 403
	 */
	public User checkSession(UUID sessionId, HttpServletRequest request) {
		try {
			Session session = sessionRepository.findOne(sessionId);
			String ipAddress = getIpAddress(request);

			if ((session.getLastActivityDate().getTime() - System.currentTimeMillis()) < eraseDeadline && session.getIpAddress().equals(ipAddress)) {
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
			log.error(e.getMessage());
			throw new NotAuthorizedException();
		}
	}

	public String getIpAddress(HttpServletRequest request) {
		String ipAddress = request.getRemoteAddr();

		String requestId = request.getHeader("X-Forwarded-For");
		if (requestId != null) {
			String[] list = requestId.split(",");
			ipAddress = list[list.length - 1];
		}

		return ipAddress;
	}
}
