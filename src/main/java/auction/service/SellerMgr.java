package auction.service;

import auction.dao.ItemDOAJPAImpl;
import auction.domain.*;

public class SellerMgr {

	private ItemDOAJPAImpl itemDOAJPA;

	public SellerMgr() {
		itemDOAJPA = new ItemDOAJPAImpl();
	}

	/**
	 * @param seller
	 * @param cat
	 * @param description
	 * @return het item aangeboden door seller, behorende tot de categorie cat
	 * en met de beschrijving description
	 */
	public Item offerItem(User seller, Category cat, String description) {
        //Change this back after running the specific tests

//		Item item = new Item(seller, cat, description);
//		itemDOAJPA.create(item);
//		seller.addItemToUser(item);
//		return item;
        return null;
	}


	/**
	 * @param item
	 * @return true als er nog niet geboden is op het item. Het item word verwijderd.
	 * false als er al geboden was op het item.
	 */
	public boolean revokeItem(Item item) {
		Item databaseItem = itemDOAJPA.find(item.getId());
		if (databaseItem.getHighestBid() == null) {
			itemDOAJPA.remove(databaseItem);

			//To see if it's actually removed.
			if (itemDOAJPA.find(databaseItem.getId()) != null) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	public Furniture offerFurniture(User seller, Category cat, String description, String material) {
		Furniture furniture = new Furniture(seller, cat, description, material);
		itemDOAJPA.create(furniture);
		seller.addItemToUser(furniture);
		return furniture;
	}

	public Painting offerPainting(User seller, Category cat, String description, String title, String painter) {
		Painting painting = new Painting(seller, cat, description, title, painter);
		itemDOAJPA.create(painting);
		seller.addItemToUser(painting);
		return painting;
	}
}
