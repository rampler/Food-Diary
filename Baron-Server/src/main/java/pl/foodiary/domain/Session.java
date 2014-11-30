package pl.foodiary.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-30.
 */
@Entity
@Table(name = "session")
public class Session {
	@Id
	@Type(type = "pg-uuid")
	private UUID id;

	@ManyToOne
	private User user;

	@Column
	@Type(type = "timestamp")
	private Date lastActivityDate;

	@Column
	private String ipAddress;

	public Session() {
	}

	public Session(UUID id, User user, Date lastActivityDate, String ipAddress) {
		this.id = id;
		this.user = user;
		this.lastActivityDate = lastActivityDate;
		this.ipAddress = ipAddress;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getLastActivityDate() {
		return lastActivityDate;
	}

	public void setLastActivityDate(Date lastActivityDate) {
		this.lastActivityDate = lastActivityDate;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
