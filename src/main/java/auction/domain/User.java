package auction.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

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
	private Set<Item> offeredItems

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

	public Iterator<Item> getOfferedItems() {
		return offeredItems.iterator();
	}

	public int numberOfOfferdItems() {
		return offeredItems.size();
	}

	private void addItem(Item item) {
		offeredItems.add(item);
	}
}
