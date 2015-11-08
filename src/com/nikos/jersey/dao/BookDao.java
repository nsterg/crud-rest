package com.nikos.jersey.dao;

import java.util.HashMap;
import java.util.Map;

import com.nikos.jersey.model.Book;

public enum BookDao {
	instance;

	private Map<String, Book> contentProvider = new HashMap<>();

	private BookDao() {

		Book todo = new Book("1", "For whom the bell tolls");
		todo.setAuthor("Ernest Hemingway");
		contentProvider.put("1", todo);
		todo = new Book("2", "The old man and the sea");
		todo.setAuthor("Ernest Hemingway");
		contentProvider.put("2", todo);

	}

	public Map<String, Book> getModel() {
		return contentProvider;
	}

}
