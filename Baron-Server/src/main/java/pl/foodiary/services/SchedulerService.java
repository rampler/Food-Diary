package pl.foodiary.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${foodiary.session.erase.older.than}")
	private final long eraseDeadline = 30 * 60 * 1000;

	@Autowired
	private SessionRepository sessionRepository;
	private final static Logger log = LoggerFactory.getLogger(SchedulerService.class);

	@Scheduled(cron = "${foodiary.session.erase.cron}")
	@Transactional
	public void eraseOldSessions() {
		sessionRepository.deleteByLastActivityDateLessThan(new Date(System.currentTimeMillis() - eraseDeadline));
		log.info("Cleared old sessions!");
	}
}
