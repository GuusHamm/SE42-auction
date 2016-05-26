package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDOAJPAImpl;
import auction.domain.Bid;
import auction.domain.Item;
import auction.domain.User;
import nl.fontys.util.Money;

import java.util.List;

public class AuctionMgr  {


    ItemDAO itemDOA;

    public AuctionMgr() {
        this.itemDOA = new ItemDOAJPAImpl();
    }

    /**
     * @param id
     * @return het item met deze id; als dit item niet bekend is wordt er null
     *         geretourneerd
     */
    public Item getItem(Long id) {
        return itemDOA.find(id);
    }

  
   /**
     * @param description
     * @return een lijst met items met @desciption. Eventueel lege lijst.
     */
    public List<Item> findItemByDescription(String description) {
        return itemDOA.findByDescription(description);
    }

    /**
     * @param item
     * @param buyer
     * @param amount
     * @return het nieuwe bod ter hoogte van amount op item door buyer, tenzij
     *         amount niet hoger was dan het laatste bod, dan null
     */
    public Bid newBid(Item item, User buyer, Money amount) {
        return item.newBid(buyer,amount);
    }
}
