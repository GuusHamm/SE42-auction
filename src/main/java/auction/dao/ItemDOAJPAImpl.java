package auction.dao;

import auction.domain.Item;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by guushamm on 26/05/16.
 */
public class ItemDOAJPAImpl implements ItemDAO{
	private final EntityManager entityManager;

	public ItemDOAJPAImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public int count() {
		Query query = entityManager.createNamedQuery("Item.count",Item.class);
		return ((Long)query.getSingleResult()).intValue();
	}

	@Override
	public void create(Item item) {
		entityManager.getTransaction().begin();

		entityManager.persist(item);

		entityManager.getTransaction().commit();
	}

	@Override
	public void edit(Item item) {
		entityManager.getTransaction().begin();

		entityManager.merge(item);

		entityManager.getTransaction().commit();
	}

	@Override
	public Item find(Long id) {
		Query query = entityManager.createNamedQuery("Item.find",Item.class);
		query.setParameter("id",id);

		Item item;

		try {
			item = (Item) query.getSingleResult();
		}catch (NoResultException e{
			item = null;
		}
		return item;
	}

	@Override
	public List<Item> findAll() {
		return entityManager.createNamedQuery("Item.getAll",Item.class).getResultList();
	}

	@Override
	public List<Item> findByDescription(String description) {
		return entityManager.createNamedQuery("Item.findByDescription",Item.class).getResultList();
	}

	@Override
	public void remove(Item item) {
		entityManager.remove(entityManager.merge(item));
	}
}
