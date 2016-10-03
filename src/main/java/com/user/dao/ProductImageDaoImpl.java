package com.user.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.user.model.ProductImages;

@Repository
public class ProductImageDaoImpl extends AbstractDao<String, ProductImages> implements ProductImageDao {

	@Override
	public void updateImageDetails(ProductImages productImages) {
		update(productImages);

	}

	@Override
	public void saveUploadedImage(ProductImages productImages) {
		persist(productImages);

	}

	@Override
	public void deleteProductImages(String productId) {
		String hql = "delete from ProductImages where productId= :productId";
		getSession().createQuery(hql).setString("productId", productId).executeUpdate();
	}

	@Override
	public List<ProductImages> getProductImagesByProductId(String productId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("productId.productId", productId));
		return (List<ProductImages>) criteria.list();
	}

}
