package com.nikos.jersey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import com.nikos.jersey.dao.BookDao;
import com.nikos.jersey.model.Book;

public class BookResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;

	public BookResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}

	// Application integration
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Book getTodo() {
		Book book = BookDao.instance.getModel().get(id);
		if (book == null)
			throw new RuntimeException("Get: Book with " + id + " not found");
		return book;
	}

	// for the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public Book getTodoHTML() {
		Book book = BookDao.instance.getModel().get(id);
		if (book == null)
			throw new RuntimeException("Get: Book with " + id + " not found");
		return book;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putTodo(JAXBElement<Book> book) {
		Book c = book.getValue();
		return putAndGetResponse(c);
	}

	@DELETE
	public void deleteTodo() {
		Book c = BookDao.instance.getModel().remove(id);
		if (c == null)
			throw new RuntimeException("Delete: Book with " + id + " not found");
	}

	private Response putAndGetResponse(Book book) {
		Response res;
		if (BookDao.instance.getModel().containsKey(book.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		BookDao.instance.getModel().put(book.getId(), book);
		return res;
	}

}
