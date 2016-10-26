package com.user.application;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.user.exception.AuthFailException;
import com.user.model.Accounts;
import com.user.service.AccountService;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	private static String AUTH_TOKEN = "auth";
	@Autowired
	private AccountService accountService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpServletRequest httpRequest = request;
		HashMap<String, String> multivaluedMap = getHeaders(httpRequest);
		String authId = null;
		if (multivaluedMap != null && multivaluedMap.get(AUTH_TOKEN) != null) {
			authId = multivaluedMap.get(AUTH_TOKEN);
		}
		// checking user for authID
		if (httpRequest.getRequestURI().contains("/userservice/v1/get/user")) {
			return true;
		} else if (httpRequest.getRequestURI().contains("/userservice/user/signup")) {
			return true;
		} else if (httpRequest.getRequestURI().contains("/userservice/user/signin")) {
			return true;
		} else if (httpRequest.getRequestURI().contains("/user/facebook/signin")) {
			return true;
		} else if (httpRequest.getRequestURI().contains("/userservice/v1/user/forgetpassword")) {
			return true;
		} else if (httpRequest.getRequestURI().contains("/userservice/v1/user/resetpassword")) {
			return true;
		} else {
			Accounts account = accountService.getAccountByAuthToken(authId);
			if (account == null) {
				throw new AuthFailException("Empty or invalid authId found in header", 500);

			} else {
				return true;
			}
		}

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

	/**
	 * get headers from request
	 * 
	 * @param request
	 * @return
	 */
	private HashMap<String, String> getHeaders(HttpServletRequest request) {
		HashMap<String, String> multivaluedMap = new HashMap<String, String>();
		Enumeration<String> hdr = request.getHeaderNames();
		while (hdr.hasMoreElements()) {
			String hnm = hdr.nextElement();
			String val = request.getHeader(hnm);
			multivaluedMap.put(hnm, val);
		}
		System.out.println("Size of map " + multivaluedMap.size());
		return multivaluedMap;

	}
}
