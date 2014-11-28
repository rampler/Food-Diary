package pl.foodiary.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
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

	public User() {
	}

	public User(UUID id, String login, String password) {
		this.id = id;
		this.login = login;
		this.password = password;
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
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
