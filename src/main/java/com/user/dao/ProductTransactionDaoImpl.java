package com.user.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.user.model.FavouriteProducts;
import com.user.model.ProductTransaction;

@Repository
public class ProductTransactionDaoImpl extends AbstractDao<String, ProductTransaction> implements ProductTransactionDao {

	@Override
	public String saveBuyingDetails(ProductTransaction productTransaction) {
		return save(productTransaction);
	}

	@Override
	public void deleteProductTransaction(String productId) {
		String hql = "delete from ProductTransaction where product= :productId";
		getSession().createQuery(hql).setString("productId", productId).executeUpdate();

	}

	@Override
	public List<ProductTransaction> getBuyingProductByUserId(String userId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("buyerUserId.userId", userId));
		return (List<ProductTransaction>) criteria.list();
	}

	@Override
	public List<ProductTransaction> getSoldProductByUserId(String userId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("sellerUserId.userId", userId));
		return (List<ProductTransaction>) criteria.list();
	}

}
