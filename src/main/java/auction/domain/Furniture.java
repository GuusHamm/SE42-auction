package auction.domain;

import javax.persistence.Entity;

/**
 * Created by Nekkyou on 29-5-2016.
 */
@Entity
public class Furniture extends Item{
	private String material;

	public Furniture(User seller, Category category, String description, String material) {
		super(seller, category, description);
		this.material = material;
	}

	public Furniture() {

	}

	public String getMaterial() {
		return material;
	}
}
