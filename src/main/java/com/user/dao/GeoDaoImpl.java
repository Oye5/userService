package com.user.dao;

import org.springframework.stereotype.Repository;

import com.user.model.Geo;

@Repository
public class GeoDaoImpl extends AbstractDao<String, Geo> implements GeoDao {

	@Override
	public void saveGeoDetails(Geo geo) {
		persist(geo);

	}

	@Override
	public void updateGeo(Geo geo) {
		update(geo);
		
	}

}
