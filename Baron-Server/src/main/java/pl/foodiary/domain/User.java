package pl.foodiary.domain;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-11-27.
 */
@Entity
@Table(name = "user_data")
public class User {
	@Id
	@Type(type = "pg-uuid")
	private UUID id;

	@Column(unique = true)
	private String login;

	@Column
	private String password;

	@Column
	private String mailAddress;

	public User() {
	}

	public User(UUID id, String login, String password, String mailAddress) {
		this.id = id;
		this.login = login.toLowerCase();
		this.password = password;
		this.mailAddress = mailAddress;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login.toLowerCase();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
}
