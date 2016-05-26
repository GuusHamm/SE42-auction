package auction.domain;

import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Bid {
	@Id
	@GeneratedValue
	private Long id;


    private FontysTime time;
	@OneToOne
    private User buyer;
    private Money amount;

	public Bid() {
	}

	public Bid(User buyer, Money amount) {
		this.buyer = buyer;
		this.amount = amount;
	}

	public FontysTime getTime() {
        return time;
    }

    public User getBuyer() {
        return buyer;
    }

    public Money getAmount() {
        return amount;
    }
}
