package auction.service;

import auction.domain.Bid;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import auction.service.util.DatabaseCleaner;
import nl.fontys.util.Money;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Iterator;

import static org.junit.Assert.*;

public class ItemsFromSellerTest {

    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("auctionPU");
    final EntityManager em = emf.createEntityManager();
    private AuctionMgr auctionMgr;
    private RegistrationMgr registrationMgr;
    private SellerMgr sellerMgr;

    public ItemsFromSellerTest() {
    }

    @Before
    public void setUp() throws Exception {
        registrationMgr = new RegistrationMgr();
        auctionMgr = new AuctionMgr();
        sellerMgr = new SellerMgr();
        new DatabaseCleaner(em).clean();
    }

    @Test
 //   @Ignore
    public void numberOfOfferdItems() {

        String email = "ifu1@nl";
        String omsch1 = "omsch_ifu1";
        String omsch2 = "omsch_ifu2";

        User user1 = registrationMgr.registerUser(email);
        assertEquals(0, user1.numberOfOfferdItems());

        Category cat = new Category("cat2");
        Item item1 = sellerMgr.offerItem(user1, cat, omsch1);

       
        // test number of items belonging to user1
		// assertEquals(0, user1.numberOfOfferdItems());
        assertEquals(1, user1.numberOfOfferdItems());
        
        /*
         *  expected: which one of te above two assertions do you expect to be true?
         *  QUESTION:
         *    Explain the result in terms of entity manager and persistance context.
         *
         *    The correct one is the second one because in this persistance context the user has one item thus the second
         *    statement is correct
         */
         
         
        assertEquals(1, item1.getSeller().numberOfOfferdItems());


        User user2 = registrationMgr.getUser(email);
        assertEquals(1, user2.numberOfOfferdItems());
        Item item2 = sellerMgr.offerItem(user2, cat, omsch2);
        assertEquals(2, user2.numberOfOfferdItems());

        User user3 = registrationMgr.getUser(email);
        assertEquals(2, user3.numberOfOfferdItems());

        User userWithItem = item2.getSeller();
        assertEquals(2, userWithItem.numberOfOfferdItems());
//        assertEquals(3, userWithItem.numberOfOfferdItems());
        /*
         *  expected: which one of te above two assertions do you expect to be true?
         *  QUESTION:
         *    Explain the result in terms of entity manager and persistance context.
         *
         *    The correct one here is the first one, because in this persistance context it is the correct one, the user
          *   at this point has 2 items and thus the first assert is the correct one.
         */
        
        
//        assertNotSame(user3, userWithItem);
        assertEquals(user3, userWithItem);
        /*
        The first one was true because they were referencing to the same object, the equals will ofcourse succeed because they are the same.

         */

    }

    @Test
//    @Ignore
    public void getItemsFromSeller() {
        String email = "ifu1@nl";
        String omsch1 = "omsch_ifu1";
        String omsch2 = "omsch_ifu2";

        Category cat = new Category("cat2");

        User user10 = registrationMgr.registerUser(email);
        Item item10 = sellerMgr.offerItem(user10, cat, omsch1);
        Iterator<Item> it = user10.getOfferedItems();
        // testing number of items of java object
        assertTrue(it.hasNext());
        
        // now testing number of items for same user fetched from db.
        User user11 = registrationMgr.getUser(email);
        Iterator<Item> it11 = user11.getOfferedItems();
        assertTrue(it11.hasNext());
        it11.next();
        assertFalse(it11.hasNext());

        // Explain difference in above two tests for te iterator of 'same' user

        
        
        User user20 = registrationMgr.getUser(email);
        Item item20 = sellerMgr.offerItem(user20, cat, omsch2);
        Iterator<Item> it20 = user20.getOfferedItems();
        assertTrue(it20.hasNext());
        it20.next();
        assertTrue(it20.hasNext());


        User user30 = item20.getSeller();
        Iterator<Item> it30 = user30.getOfferedItems();
        assertTrue(it30.hasNext());
        it30.next();
        assertTrue(it30.hasNext());
    }

    @Test
    public void newBidTest() {
        //Creating an item
        Category category = new Category("testCat");
        User user = registrationMgr.registerUser("testUser@test.nl");
        Item item = sellerMgr.offerItem(user, category, "testDescription");

        assertEquals("User has not offered the item", 1, user.numberOfOfferdItems());
        assertNull("Highest bid should be null at this point", item.getHighestBid());

        Bid bid = auctionMgr.newBid(item, user, new Money(10, "Euro"));

        //After placing the bet, check if it's actually there
        assertNotNull("Item does not have a bet", item.getHighestBid());
        assertEquals("The item doesn't equal the item of the bet", item, bid.getBettedOnItem());
        assertEquals("The better isn't the same", user, bid.getBuyer());
        assertEquals("The money does not match", bid.getAmount(), new Money(10, "Euro"));

        Bid bid1 = auctionMgr.newBid(item, user, new Money(20, "Euro"));
        assertEquals("Check if the new bid has the correct money", bid1.getAmount(), new Money(20, "Euro"));
        assertEquals("Check the buyer", bid1.getBuyer(), user);
        assertEquals("This bet should be on the item", bid1.getBettedOnItem(), item);

        Bid bid2 = auctionMgr.newBid(item, user, new Money(15, "Euro"));
        assertNull("As bid1 is higher, bid2 should not be placed so it should be null", bid2);
        assertEquals("Check if bid 2 hasnt come through by some magical accident", item.getHighestBid().getAmount(), new Money(20, "Euro"));


    }

}
