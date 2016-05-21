package auction.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@NamedQueries({
		@NamedQuery(name = "User.count", query = "select count(u) from User as u"),
		@NamedQuery(name = "User.getAll", query = "select u from User as u"),
		@NamedQuery(name = "User.findByEmail", query = "select u from User as u where u.email = :email")
})
public class User implements Serializable {

	@Id
	@GeneratedValue
	private long Id;
	@Column(unique = true)
	private String email;

	public User(String email) {
		this.email = email;
	}

	public User() {
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		this.Id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
