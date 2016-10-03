package com.user.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.user.model.ThumbNail;

@Repository
public class ThumbNailDaoImpl extends AbstractDao<String, ThumbNail> implements ThumbNailDao {

	@Override
	public void saveThumbNail(ThumbNail thumb) {
		persist(thumb);

	}

	@Override
	public ThumbNail getThumbByProductId(String productId) {
		Criteria criteria = createEntityCriteria();
		System.out.println("productId==" + productId);
		criteria.add(Restrictions.eq("productId.productId", productId));
		return (ThumbNail) criteria.uniqueResult();
	}

	@Override
	public void deleteThumbNail(String productId) {
		String hql = "delete from ThumbNail where productId= :productId";
		getSession().createQuery(hql).setString("productId", productId).executeUpdate();

	}

}
