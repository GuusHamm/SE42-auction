package auction.service;

import auction.dao.ItemDOAJPAImpl;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
     *         en met de beschrijving description
     */
    public Item offerItem(User seller, Category cat, String description) {
        Item item = new Item(seller, cat, description);
        itemDOAJPA.create(item);
        return item;
    }
    
     /**
     * @param item
     * @return true als er nog niet geboden is op het item. Het item word verwijderd.
     *         false als er al geboden was op het item.
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
}
