package com.user.dao;

import com.user.model.Geo;

public interface GeoDao {
	void saveGeoDetails(Geo geo);

	void updateGeo(Geo geo);
}
