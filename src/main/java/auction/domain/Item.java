package auction.domain;

import nl.fontys.util.Money;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name="Item.count", query = "select count(i) from Item as i"),
		@NamedQuery(name = "Item.find", query = "select i from Item as i where i.id = :id"),
		@NamedQuery(name = "Item.findByDescription", query = "select i from Item as i where i.description = :description"),
		@NamedQuery(name = "Item.getAll", query = "select i from Item as i")
})
public class Item implements Comparable {

    @Id
    @GeneratedValue
    private Long id;
	
	@ManyToOne
	private User seller;

	public void setSeller(User seller) {
		this.seller = seller;
	}

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "description",
					column = @Column(name = "c_description"))
	})
	@OneToOne(cascade = CascadeType.PERSIST)
	private Category category;

	private String description;
	@OneToOne(cascade = CascadeType.PERSIST, mappedBy = "bettedOnItem")
	private Bid highest;

    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;
    }

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public User getSeller() {
        return seller;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Bid getHighestBid() {
        return highest;
    }

    public Bid newBid(User buyer, Money amount) {
        if (highest != null && highest.getAmount().compareTo(amount) >= 0) {
            return null;
        }
        highest = new Bid(buyer, amount, this);
        return highest;
    }

	/**
     * Compares this object to the parameter object
     * @param other The object which you want to compare it with
     * @return 0 if the object has the same ID, return 1 if the object is an item with another id and return -1 when it's not an Item
     */
    public int compareTo(Object other) {
        if (other instanceof Item) {
            if (this.id == ((Item) other).getId()) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return -1;
        }
    }

    public boolean equals(Object o) {
        if (o instanceof Item) {
            return this == o;
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = id.hashCode();
        result = 21 * result + seller.hashCode();
        result = 21 * result + category.hashCode();
        result = 21 * result + description.hashCode();
		if (highest != null) {
			result = 21 * result + highest.hashCode();
		}
        return result;
    }
}
