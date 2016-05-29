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
		@NamedQuery(name = "User.findByEmail", query = "select u from User as u where u.email = :email"),
		@NamedQuery(name = "User.getOfferings", query = "select i from Item as i where i.seller = :user ")
})
public class User implements Serializable {

	@Id
	@GeneratedValue
	private long Id;
	@Column(unique = true)
	private String email;
	@OneToMany(mappedBy = "seller")
	private Set<Item> offeredItems;

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
		item.setSeller(this);
	}

	public void addItemToUser(Item item){
		if (item != null && !item.getDescription().isEmpty()){
			addItem(item);
		}
	}
}
