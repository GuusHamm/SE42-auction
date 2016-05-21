package auction.service;

import auction.domain.User;
import auction.service.util.DatabaseCleaner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class RegistrationMgrTest {

    private RegistrationMgr registrationMgr;

    @Before
    public void setUp() throws Exception {
        registrationMgr = new RegistrationMgr();
    }

	@After
	public void tearDown() throws Exception {
		DatabaseCleaner cleaner = new DatabaseCleaner(registrationMgr.getEntityManager());
		try {
			cleaner.clean();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}


	@Test
    public void registerUser() {
	    String email1 = "xxx1@yyy";
	    String email2 = "xxx2@yyy2";

        User user1 = registrationMgr.registerUser(email1);
        User user2 = registrationMgr.registerUser(email2);
	    User user2bis = registrationMgr.registerUser(email2);

	    assertTrue(user1.getEmail().equals("xxx1@yyy"));
	    assertTrue(user2.getEmail().equals("xxx2@yyy2"));
	    assertEquals(user2bis.getId(), user2.getId());
        //geen @ in het adres
        assertNull(registrationMgr.registerUser("abc"));
    }

    @Test
    public void getUser() {
        User user1 = registrationMgr.registerUser("xxx5@yyy5");
        User userGet = registrationMgr.getUser("xxx5@yyy5");
        assertSame(userGet, user1);
        assertNull(registrationMgr.getUser("aaa4@bb5"));
        registrationMgr.registerUser("abc");
        assertNull(registrationMgr.getUser("abc"));
    }

    @Test
    public void getUsers() {
        List<User> users = registrationMgr.getUsers();
        assertEquals(0, users.size());

        User user1 = registrationMgr.registerUser("xxx8@yyy");
        users = registrationMgr.getUsers();
        assertEquals(1, users.size());
        assertSame(users.get(0), user1);


        User user2 = registrationMgr.registerUser("xxx9@yyy");
        users = registrationMgr.getUsers();
        assertEquals(2, users.size());

        registrationMgr.registerUser("abc");
        //geen nieuwe user toegevoegd, dus gedrag hetzelfde als hiervoor
        users = registrationMgr.getUsers();
        assertEquals(2, users.size());
    }
}
