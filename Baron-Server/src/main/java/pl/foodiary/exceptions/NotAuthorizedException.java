package pl.foodiary.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Mateusz on 2014-11-30.
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class NotAuthorizedException extends RuntimeException  {
	private String userName;

	public NotAuthorizedException(String userName) {
		this.userName = userName;
	}
	/**
	 * Returns the detail message string of this throwable.
	 *
	 * @return the detail message string of this {@code Throwable} instance
	 * (which may be {@code null}).
	 */
	@Override
	public String getMessage() {
		return "User "+userName+" unauthorized.";
	}
}
