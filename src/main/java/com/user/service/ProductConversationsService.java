package com.user.service;

import java.util.List;

import com.user.model.ProductChat;
import com.user.model.ProductConversations;

public interface ProductConversationsService {

	ProductConversations saveMessages(ProductConversations conv);

	List<ProductConversations> getConversationById(String chatId);
	
	void deleteConversations(List<ProductChat> list);

	List<ProductConversations> getConversationsByUserId(String userId);
}
