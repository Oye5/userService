package com.user.controller;

import io.searchbox.client.JestClient;
import io.searchbox.core.Index;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.elasticsearch.common.geo.GeoPoint;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.user.dto.request.UpdateUserProfileRequest;
import com.user.dto.response.GenericResponse;
import com.user.dto.response.GeoResponse;
import com.user.dto.response.ProductImageResponse;
import com.user.dto.response.ProductResponse;
import com.user.dto.response.ProductTransactionResponse;
import com.user.dto.response.SellerResponse;
import com.user.dto.response.ThumbResponse;
import com.user.dto.response.UserResponse;
import com.user.model.FavouriteProducts;
import com.user.model.Product;
import com.user.model.ProductImages;
import com.user.model.ProductStatus;
import com.user.model.ProductTransaction;
import com.user.model.Seller;
import com.user.model.ThumbNail;
import com.user.model.User;
import com.user.service.FavouriteProductService;
import com.user.service.ProductImageService;
import com.user.service.ProductService;
import com.user.service.ProductStatusService;
import com.user.service.ProductTransactionService;
import com.user.service.SellerService;
import com.user.service.ThumbNailService;
import com.user.service.UserService;
import com.user.util.ElasticUtil;
import com.user.util.SetProductResponse;
import com.user.util.UploadImage;

@RestController
public class UserController {

	@Autowired
	private FavouriteProductService favouriteProductService;

	@Autowired
	private ProductStatusService productStatusService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductImageService productImageService;

	@Autowired
	private ThumbNailService thumbNailService;

	@Autowired
	private SellerService sellerService;

	@Autowired
	private UserService userService;

	@Autowired
	private UploadImage uploadImage;

	@Autowired
	private SetProductResponse setproductResponse;

	@Autowired
	private ProductTransactionService productTransactionService;

	/**
	 * make product favourite for given user
	 * 
	 * @param userId
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/v1/{userid}/favorites/products/{productid}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> makeProductAsFavourite(@PathVariable("userid") String userId, @PathVariable("productid") String productId) {
		GenericResponse response = new GenericResponse();
		try {
			FavouriteProducts favouriteProducts = new FavouriteProducts();
			Product product = new Product();
			product.setProductId(productId);
			favouriteProducts.setProductId(product);
			User user = new User();
			user.setUserId(userId);
			favouriteProducts.setUserId(user);
			favouriteProducts.setFavourite(true);
			List<FavouriteProducts> listFavouriteProducts = favouriteProductService.getFavouriteProducts(favouriteProducts);
			if (listFavouriteProducts.size() == 0) {
				favouriteProductService.saveFavouriteProduct(favouriteProducts);
				ProductStatus status = productStatusService.getProductStatus(productId);
				if (status == null) {
					status = new ProductStatus();
					status.setFavourites(1);
					status.setId(UUID.randomUUID().toString());
					status.setProductId(product);
					status.setViews(1);
					productStatusService.saveProductStatus(status);
				} else {
					status.setFavourites(status.getFavourites() + 1);
					status.setViews(status.getViews() + 1);
					productStatusService.updateProductStatus(status);
				}
			} else {
				response.setCode("V001");
				response.setMessage("This productId is already marked as favourites by the given userId");
				return new ResponseEntity<GenericResponse>(response, HttpStatus.ALREADY_REPORTED);
			}
			response.setCode("S001");
			response.setMessage("product marked as favourites");
			return new ResponseEntity<GenericResponse>(response, HttpStatus.OK);
		} catch (org.springframework.dao.DataIntegrityViolationException ex) {
			response.setCode("E001");
			response.setMessage("please check userid and productId: " + ex.getMessage());
			ex.printStackTrace();
			return new ResponseEntity<GenericResponse>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception ex) {
			response.setCode("E002");
			response.setMessage(ex.getMessage());
			ex.printStackTrace();
			return new ResponseEntity<GenericResponse>(response, HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * get favourite item list for particular user
	 * 
	 * @param userId
	 * @return
	 */

	// http://52.43.30.248:8080/productapi/v1//{userid}/favourites/products/{productid}
	@RequestMapping(value = "/v1/{userid}/favorites/products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getFavouriteProducts(@PathVariable("userid") String userId, @RequestParam("num_results") Integer numResults) {
		GenericResponse response = new GenericResponse();
		try {
			FavouriteProducts favouriteProducts = new FavouriteProducts();
			User user = new User();
			user.setUserId(userId);
			favouriteProducts.setUserId(user);
			List<FavouriteProducts> listFavouriteProducts = favouriteProductService.getFavouriteProductsByUserId(favouriteProducts);

			List<ProductResponse> listProductResponse = new ArrayList<ProductResponse>();
			ProductResponse productResponse = null;
			for (int i = 0; i < listFavouriteProducts.size() && i < numResults; i++) {
				productResponse = new ProductResponse();
				productResponse.setCategory_id(listFavouriteProducts.get(i).getProductId().getCategoryId());
				productResponse.setCreated_at(listFavouriteProducts.get(i).getProductId().getCreatedAt());
				productResponse.setCurrency(listFavouriteProducts.get(i).getProductId().getCurrency());
				productResponse.setDescription(listFavouriteProducts.get(i).getProductId().getDescription());
				productResponse.setCondition(listFavouriteProducts.get(i).getProductId().getCondition());
				productResponse.setQuantity(listFavouriteProducts.get(i).getProductId().getQuantity());
				// geo
				GeoResponse geoResponse = new GeoResponse();
				geoResponse.setCity(listFavouriteProducts.get(i).getProductId().getGeo().getCity());
				geoResponse.setCountry_code(listFavouriteProducts.get(i).getProductId().getGeo().getCountryCode());
				geoResponse.setDistance(10);
				geoResponse.setLat(listFavouriteProducts.get(i).getProductId().getGeo().getLattitude());
				geoResponse.setLng(listFavouriteProducts.get(i).getProductId().getGeo().getLongitude());
				geoResponse.setZip_code(listFavouriteProducts.get(i).getProductId().getGeo().getZipCode());
				productResponse.setGeo(geoResponse);
				productResponse.setProduct_id(listFavouriteProducts.get(i).getProductId().getProductId());
				List<ProductImages> img = productImageService.getProductImagesByProductId(listFavouriteProducts.get(i).getProductId().getProductId());
				// image
				List<ProductImageResponse> listImageRes = new ArrayList<ProductImageResponse>();
				ProductImageResponse imgRes = null;
				for (int j = 0; j < img.size(); j++) {
					imgRes = new ProductImageResponse();
					imgRes.setId(img.get(j).getId());
					imgRes.setUrl(img.get(j).getUrl());
					listImageRes.add(imgRes);
				}
				productResponse.setImages(listImageRes);
				ThumbNail thumbNail = thumbNailService.getThumbByProductId(listFavouriteProducts.get(i).getProductId().getProductId());
				ThumbResponse thumb = null;
				if (thumbNail != null) {
					thumb = new ThumbResponse();
					thumb.setHeight(thumbNail.getHeight());
					thumb.setUrl(thumbNail.getUrl());
					thumb.setWidth(thumbNail.getWidth());
				}
				productResponse.setThumb(thumb);

				productResponse.setLanguage_code(listFavouriteProducts.get(i).getProductId().getLanguageCode());
				productResponse.setDisplay_name(listFavouriteProducts.get(i).getProductId().getDisplayName());
				// owner
				SellerResponse sellerResponse = new SellerResponse();
				Seller seller = sellerService.getSellerById(listFavouriteProducts.get(i).getProductId().getUser().getUserId());
				if (seller != null) {
					sellerResponse.setProfile_pic_url(seller.getProfilePic());
					sellerResponse.setBanned(seller.getBanned());
					sellerResponse.setCity(seller.getCity());
					sellerResponse.setCountry_code(seller.getCountryCode());
					sellerResponse.setId(seller.getId());
					sellerResponse.setName(seller.getFirstName());
					sellerResponse.setStatus(seller.getStatus());
					sellerResponse.setZip_code(seller.getZipCode());
					productResponse.setSeller(sellerResponse);
				}

				// productResponse.setOwner(sellerResponse);
				productResponse.setPrice(listFavouriteProducts.get(i).getProductId().getPrice());
				productResponse.setStatus(listFavouriteProducts.get(i).getProductId().getStatus());
				productResponse.setUpdated_at(listFavouriteProducts.get(i).getProductId().getUpdatedAt());

				listProductResponse.add(productResponse);

			}

			return new ResponseEntity<List<ProductResponse>>(listProductResponse, HttpStatus.OK);
		} catch (Exception ex) {
			response.setCode("E001");
			response.setMessage(ex.getMessage());
			ex.printStackTrace();
			return new ResponseEntity<GenericResponse>(response, HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * unmark favorite product
	 * 
	 * @param userId
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/v1/{userid}/unfavorites/products/{productid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> makeProductAsUnFavourite(@PathVariable("userid") String userId, @PathVariable("productid") String productId) {
		GenericResponse response = new GenericResponse();
		try {
			int res = favouriteProductService.deleteFavouriteProductByUserId(userId, productId);
			if (res != 0) {
				ProductStatus status = productStatusService.getProductStatus(productId);
				if (status != null) {
					status.setFavourites(status.getFavourites() - 1);
					productStatusService.updateProductStatus(status);
				}

			}
			response.setCode("S001");
			response.setMessage("product unmarked  as favourites");
			return new ResponseEntity<GenericResponse>(response, HttpStatus.OK);
		} catch (org.springframework.dao.DataIntegrityViolationException ex) {
			response.setCode("E001");
			response.setMessage("please check userid and productId: " + ex.getMessage());
			ex.printStackTrace();
			return new ResponseEntity<GenericResponse>(response, HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			response.setCode("E002");
			response.setMessage(ex.getMessage());
			ex.printStackTrace();
			return new ResponseEntity<GenericResponse>(response, HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * update user profile
	 * 
	 * @param userId
	 * @param file
	 * @param userProfileRequest
	 * @return
	 */
	@Value("${aws.s3.folder.user}")
	public String folder;// = "user";

	@RequestMapping(value = "/v1/updateUser/{userid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUserProfile(@PathVariable("userid") String userId, @RequestParam(value = "image", required = false) MultipartFile file, UpdateUserProfileRequest userProfileRequest) {
		GenericResponse response = new GenericResponse();
		try {
			User user = userService.getUserById(userId);
			if (user == null) {
				response.setCode("V001");
				response.setMessage("please check User id");
				return new ResponseEntity<GenericResponse>(response, HttpStatus.BAD_REQUEST);
			}
			Seller seller = new Seller();
			seller.setCity(userProfileRequest.getCity());
			seller.setCountryCode(userProfileRequest.getCountryCode());
			seller.setFirstName(userProfileRequest.getFirstName());
			seller.setId(userId);
			seller.setUserId(user);
			seller.setLastName(userProfileRequest.getLastName());
			if (file != null) {
				String ProfilePicUrl = uploadImage.uploadImage(file, folder);
				seller.setProfilePic(ProfilePicUrl);
			}

			seller.setZipCode(userProfileRequest.getZipCode());
			sellerService.updateSeller(seller);

			response.setCode("S001");
			response.setMessage("User updated successfully");
			return new ResponseEntity<GenericResponse>(response, HttpStatus.OK);

		} catch (org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException ex) {

			return new ResponseEntity<GenericResponse>(response, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("E002");
			response.setMessage(e.getMessage());
			return new ResponseEntity<GenericResponse>(response, HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * get user profile by user id
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/v1/get/user/{userid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserByUserId(@PathVariable("userid") String userId) {
		GenericResponse response = new GenericResponse();
		try {
			User user = userService.getUserById(userId);
			if (user == null) {
				response.setCode("V001");
				response.setMessage("please check userID");
				return new ResponseEntity<GenericResponse>(response, HttpStatus.EXPECTATION_FAILED);
			}
			Seller seller = sellerService.getSellerById(userId);
			UserResponse userResponse = new UserResponse();
			if (seller != null) {
				userResponse.setBanned(seller.getBanned());
				userResponse.setCity(seller.getCity());
				userResponse.setCountry_code(seller.getCountryCode());
				userResponse.setFirst_name(seller.getFirstName());
				userResponse.setLast_name(seller.getLastName());
				userResponse.setProfile_pic_url(seller.getProfilePic());
				userResponse.setStatus(seller.getStatus());
				userResponse.setZip_code(seller.getZipCode());
			}

			userResponse.setEmail(user.getEmail());
			userResponse.setUser_id(user.getUserId());
			userResponse.setUser_name(user.getUserName());

			return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
		} catch (Exception e) {
			response.setCode("E001");
			response.setMessage(e.getMessage());
			return new ResponseEntity<GenericResponse>(response, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * get user products by userId
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/v1/get/user/sellingProduct/{userid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserByUserIda(@PathVariable("userid") String userId) {
		GenericResponse response = new GenericResponse();
		try {
			List<Product> productList = productService.getProductByUserId(userId);
			List<ProductResponse> productResponse = setproductResponse.prepareResponse(productList);
			return new ResponseEntity<List<ProductResponse>>(productResponse, HttpStatus.OK);
		} catch (Exception e) {
			response.setCode("E001");
			response.setMessage(e.getMessage());
			return new ResponseEntity<GenericResponse>(response, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/v1/product/{productId}/buyer/{buyerid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> buyProduct(@PathVariable("productId") String productId, @PathVariable("buyerid") String buyerId) {
		GenericResponse response = new GenericResponse();
		try {
			List<Product> productList = productService.getProductByProductId(productId);
			Product product = null;
			if (productList.size() != 0) {
				product = productList.get(0);
			} else {
				response.setCode("V001");
				response.setMessage("please check productiD ");
				return new ResponseEntity<GenericResponse>(response, HttpStatus.EXPECTATION_FAILED);
			}
			if (product.getQuantity() <= 0) {
				response.setCode("V002");
				response.setMessage("product is out of stock");
				return new ResponseEntity<GenericResponse>(response, HttpStatus.EXPECTATION_FAILED);
			}
			if (product.getUser().getUserId().equals(buyerId)) {
				response.setCode("V003");
				response.setMessage("seller can't be buyer");
				return new ResponseEntity<GenericResponse>(response, HttpStatus.EXPECTATION_FAILED);
			}
			ProductTransaction productTransaction = new ProductTransaction();
			productTransaction.setCreatedDate(new DateTime(new Date().getTime(), DateTimeZone.forID(null)).toString());
			productTransaction.setId(UUID.randomUUID().toString());
			productTransaction.setProduct(product);
			User user = new User();
			user.setUserId(buyerId);
			productTransaction.setUserId(user);
			String result = productTransactionService.saveBuyingDetails(productTransaction);
			if (result != null) {
				product.setQuantity(product.getQuantity() - 1);
				if (product.getQuantity() == 0) {
					product.setStatus("out of stock");
				}
				productService.updateProduct(product);
			}
			ProductTransactionResponse transactionResponse = new ProductTransactionResponse();
			transactionResponse.setBuyerId(buyerId);
			transactionResponse.setDate(productTransaction.getCreatedDate());
			transactionResponse.setProductId(productId);
			transactionResponse.setSellerId(product.getUser().getUserId());
			// index to elastic
			GeoPoint gp = new GeoPoint(product.getGeo().getLattitude(), product.getGeo().getLongitude());
			product.setLocation(gp);
			JestClient client = ElasticUtil.getClient();
			Index index = new Index.Builder(product).index("product").type("Product").id(product.getProductId().toString()).build();
			client.execute(index);

			return new ResponseEntity<ProductTransactionResponse>(transactionResponse, HttpStatus.OK);
		} catch (org.springframework.dao.DataIntegrityViolationException ex) {
			ex.printStackTrace();
			response.setCode("E001");
			response.setMessage("please check productiD and userId");
			return new ResponseEntity<GenericResponse>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("E002");
			response.setMessage(e.getMessage());
			return new ResponseEntity<GenericResponse>(response, HttpStatus.BAD_REQUEST);
		}
	}
}
