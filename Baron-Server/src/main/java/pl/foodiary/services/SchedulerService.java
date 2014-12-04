package pl.foodiary.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.foodiary.repositories.SessionRepository;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by Mateusz on 2014-12-04.
 */
@Service
public class SchedulerService {

	@Autowired
	private SessionRepository sessionRepository;
	private final static Logger log = LoggerFactory.getLogger(SchedulerService.class);

	@Scheduled(fixedRate = 15 * 60 * 1000)
	@Transactional
	public void eraseOldSessions() {
		sessionRepository.deleteByLastActivityDateLessThan(new Date(System.currentTimeMillis() - 30 * 60 * 1000));
		log.info("Cleared old sessions!");
	}
}
