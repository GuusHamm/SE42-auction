package auction.service;

import auction.domain.*;
import auction.service.util.DatabaseCleaner;
import nl.fontys.util.Money;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Iterator;

import static org.junit.Assert.*;

public class FurnitureAndPaintingTest {

    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("auctionPU");
    final EntityManager em = emf.createEntityManager();
    private AuctionMgr auctionMgr;
    private RegistrationMgr registrationMgr;
    private SellerMgr sellerMgr;

    public FurnitureAndPaintingTest() {
    }

//    Database without @Inheritance
//    bid, item, sequence, users

//    Database with @Inheritance Joined
//    bid, furniture item, painting, sequence, users

//    Database with @Inheritance SINGLE_TABLE
//    bid, item, sequence, users
    //Database with @Inheritance TABLE_PER_CLASS -- Niet getest aangezien Find verkeerd gaat, oplossing is findFurniture maken
    //bid, furniture, item, painting, sequence, users




    //After abtract

    //Database without @Inheritance
    //bid, item, sequence, users

    //Database with @Inheritance Joined
    //bid, furniture item, painting, sequence, users

    //Database with @Inheritance SINGLE_TABLE
    //bid, item, sequence, users

    //Database with @Inheritance TABLE_PER_CLASS -- Niet getest aangezien Find verkeerd gaat, oplossing is findFurniture maken
    //bid, furniture, item, painting, sequence, users

    //De resultaten zijn voor alle 3 gelijk.

    @Before
    public void setUp() throws Exception {
        registrationMgr = new RegistrationMgr();
        auctionMgr = new AuctionMgr();
        sellerMgr = new SellerMgr();
        new DatabaseCleaner(em).clean();
    }

    @Test
    public void newFurniture() {
        String omsch = "omsch1";
        String iemand1 = "iemand1@def";
        User u1 = registrationMgr.registerUser(iemand1);
        User u2 = registrationMgr.registerUser("iemand2@def");
        Category cat = new Category("cat2");

        Item furniture1 = sellerMgr.offerFurniture(u1, cat, "broodkast", "ijzer");
        assertEquals("seller of item correct", furniture1.getSeller(), u1);

        User foundUser = registrationMgr.getUser(iemand1);
        Iterator<Item> it = foundUser.getOfferedItems();
        Item firstItem = it.next();
   //        int xxx = 22;
        assertEquals("item added in offeredItems", furniture1, firstItem);
        Item item2 = sellerMgr.offerPainting(u1, cat, omsch, "Nachtwacht", "Rembrandt");
        it = registrationMgr.getUser(iemand1).getOfferedItems();
        assertTrue(it.hasNext());
        it.next();
        assertTrue(it.hasNext());
        it.next();
        assertFalse(it.hasNext());

//        //de volgende code verwijderen als Item abstract is
//        Item item3 = sellerMgr.offerItem(u1, new Category("boek"), "The science of Discworld");
//        it = registrationMgr.getUser(iemand1).getOfferedItems();
//        assertTrue(it.hasNext());
//        it.next();
//        assertTrue(it.hasNext());
//        it.next();
//        assertTrue(it.hasNext());
//        it.next();
//        assertFalse(it.hasNext());

        assertNull(furniture1.getHighestBid());
        Bid bid = auctionMgr.newBid(furniture1, u2, new Money(150000, Money.EURO));
        assertNotNull(furniture1.getHighestBid());

        Item foundFurniture = auctionMgr.getItem(furniture1.getId());
        int i = 3;
        assertEquals(foundFurniture.getHighestBid().getAmount(), bid.getAmount());
        assertTrue(foundFurniture.getClass() == Furniture.class);
    }
}
