package com.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user.dao.GeoDao;
import com.user.model.Geo;

@Service
@Transactional
public class GeoServiceImpl implements GeoService {

	@Autowired
	GeoDao geoDao;
	
	
	@Override
	public void saveGeoDetails(Geo geo) {
		geoDao.saveGeoDetails(geo); 
		
	}


	@Override
	public void updateGeo(Geo geo) {
		geoDao.updateGeo(geo);
		
	}

}
