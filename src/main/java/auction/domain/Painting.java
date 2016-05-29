package auction.domain;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by guushamm on 29/05/16.
 */
@Entity
public class Painting extends Item implements Serializable{
	private String title;
	private String painter;

	public Painting(User seller, Category category, String description, String title, String painter) {
		super(seller, category, description);
		this.title = title;
		this.painter = painter;
	}

	public Painting() {
	}

	public String getTitle() {
		return title;
	}

	public String getPainter() {
		return painter;
	}
}
