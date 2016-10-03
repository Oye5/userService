package com.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user.dao.ProductConversationsDao;
import com.user.model.ProductChat;
import com.user.model.ProductConversations;

@Service
@Transactional
public class ProductConversationsServiceImpl implements ProductConversationsService {

	@Autowired
	ProductConversationsDao convDao;

	@Override
	public ProductConversations saveMessages(ProductConversations conv) {
		return convDao.saveMessages(conv);
	}

	@Override
	public List<ProductConversations> getConversationById(String chatId) {
		return convDao.getConversationById(chatId);
	}

	@Override
	public void deleteConversations(List<ProductChat> list) {
		convDao.deleteConversations(list);

	}

	@Override
	public List<ProductConversations> getConversationsByUserId(String userId) {
		return convDao.getConversationsByUserId(userId);
	}

}
