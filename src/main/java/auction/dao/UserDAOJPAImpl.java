package auction.dao;

import auction.domain.User;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class UserDAOJPAImpl implements UserDAO {

    private final EntityManager entityManager;

    public UserDAOJPAImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public int count() {
	    Query query = entityManager.createNamedQuery("User.count",User.class);
        return ((Long)query.getSingleResult()).intValue();
    }

    @Override
    public void create(User user) {
         if (findByEmail(user.getEmail()) != null) {
            throw new EntityExistsException();
        }

	    entityManager.getTransaction().begin();

	    entityManager.persist(user);

	    entityManager.getTransaction().commit();
    }

    @Override
    public void edit(User user) {
        if (findByEmail(user.getEmail()) == null) {
            throw new IllegalArgumentException();
        }

	    entityManager.getTransaction().begin();

	    entityManager.merge(user);

	    entityManager.getTransaction().commit();
    }


    @Override
    public List<User> findAll() {
	    return entityManager.createNamedQuery("User.getAll",User.class).getResultList();
    }

    @Override
    public User findByEmail(String email) {
        Query query = entityManager.createNamedQuery("User.findByEmail",User.class);
        query.setParameter("email",email);
	    User user = null;

	    try{
		    user = (User) query.getSingleResult();
	    }catch (NoResultException e){
		    user = null;
	    }

	    return user;
    }

    @Override
    public void remove(User user) {
        entityManager.remove(entityManager.merge(user));
    }
}
