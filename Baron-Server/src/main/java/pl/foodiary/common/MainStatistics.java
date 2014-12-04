package pl.foodiary.common;

/**
 * Created by Mateusz on 2014-12-04.
 */
public class MainStatistics {
	private Long usersCounter;
	private Long onlineUsersCounter;
	private Long unactiveUsersCounter;

	public MainStatistics() {
	}

	public MainStatistics(Long usersCounter, Long onlineUsersCounter, Long unactiveUsersCounter) {
		this.usersCounter = usersCounter;
		this.onlineUsersCounter = onlineUsersCounter;
		this.unactiveUsersCounter = unactiveUsersCounter;
	}

	public Long getUsersCounter() {
		return usersCounter;
	}

	public void setUsersCounter(Long usersCounter) {
		this.usersCounter = usersCounter;
	}

	public Long getOnlineUsersCounter() {
		return onlineUsersCounter;
	}

	public void setOnlineUsersCounter(Long onlineUsersCounter) {
		this.onlineUsersCounter = onlineUsersCounter;
	}

	public Long getUnactiveUsersCounter() {
		return unactiveUsersCounter;
	}

	public void setUnactiveUsersCounter(Long unactiveUsersCounter) {
		this.unactiveUsersCounter = unactiveUsersCounter;
	}
}
