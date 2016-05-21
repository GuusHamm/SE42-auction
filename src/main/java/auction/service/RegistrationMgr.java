package auction.service;

import auction.dao.UserDAO;
import auction.dao.UserDAOJPAImpl;
import auction.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class RegistrationMgr {

    private UserDAO userDAO;
	private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("auctionPU");
	private EntityManager entityManager;

    public RegistrationMgr() {
	    this.entityManager = entityManagerFactory.createEntityManager();
        userDAO = new UserDAOJPAImpl(entityManager);
    }

    /**
     * Registreert een gebruiker met het als parameter gegeven e-mailadres, mits
     * zo'n gebruiker nog niet bestaat.
     * @param email
     * @return Een Userobject dat geïdentificeerd wordt door het gegeven
     * e-mailadres (nieuw aangemaakt of reeds bestaand). Als het e-mailadres
     * onjuist is ( het bevat geen '@'-teken) wordt null teruggegeven.
     */
    public User registerUser(String email) {
        if (!email.contains("@")) {
            return null;
        }
        User user = userDAO.findByEmail(email);
        if (user != null) {
            return user;
        }

	    try {
		    user = new User(email);
		    userDAO.create(user);

	    }catch (Exception e){
		    entityManager.getTransaction().rollback();
	    }
        return user;
    }

    /**
     *
     * @param email een e-mailadres
     * @return Het Userobject dat geïdentificeerd wordt door het gegeven
     * e-mailadres of null als zo'n User niet bestaat.
     */
    public User getUser(String email) {
        return userDAO.findByEmail(email);
    }

    /**
     * @return Een iterator over alle geregistreerde gebruikers
     */
    public List<User> getUsers() {
        return userDAO.findAll();
    }

	public EntityManager getEntityManager() {
		return entityManager;
	}
}
