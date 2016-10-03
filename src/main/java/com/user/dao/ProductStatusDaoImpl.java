package com.user.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.user.model.ProductStatus;

@Repository
public class ProductStatusDaoImpl extends AbstractDao<Long, ProductStatus> implements ProductStatusDao {

	@Override
	public ProductStatus getProductStatus(String productId) {
		Criteria criteria = createEntityCriteria();
		// Integer v = Integer.parseInt(productId);
		criteria.add(Restrictions.eq("productId.productId", productId));
		return (ProductStatus) criteria.uniqueResult();
	}

	@Override
	public void deleteProductStatus(String productId) {
		String hql = "delete from ProductStatus where productId= :productId";
		getSession().createQuery(hql).setString("productId", productId).executeUpdate();

	}

	@Override
	public void saveProductStatus(ProductStatus productStatus) {
		persist(productStatus);

	}

	@Override
	public List<ProductStatus> getFavouriteProducts() {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("favourites", true));
		return (List<ProductStatus>) criteria.list();
	}

	@Override
	public void updateProductStatus(ProductStatus status) {
		update(status);

	}

}
