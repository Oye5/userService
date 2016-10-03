package com.user.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.user.model.Accounts;

public class AbstractDao<PK extends Serializable, T> {

	private final Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public AbstractDao() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T getByKey(PK key) {
		return (T) getSession().get(persistentClass, key);
	}

	public void persist(T entity) {
		getSession().persist(entity);
	}

	public String save(T entity) {
		return (String) getSession().save(entity);

	}

	public void delete(T entity) {
		getSession().delete(entity);
	}

	protected Criteria createEntityCriteria() {
		return getSession().createCriteria(persistentClass);
	}

	protected void update(T entity) {
		getSession().update(entity);

	}

	protected T merge(T entity) {
		return (T) getSession().merge(entity);

	}

	protected int updateAccounts(Accounts account) {
		String hqlUpdate = "update Accounts a set a.provider_token = :token where a.user = :userid";
		int result = getSession().createQuery(hqlUpdate).setString("token", account.getProvider_token()).setString("userid", account.getUser().getUserId()).executeUpdate();
		return result;

	}

}
