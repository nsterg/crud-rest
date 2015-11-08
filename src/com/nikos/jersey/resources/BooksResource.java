package com.nikos.jersey.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.nikos.jersey.dao.BookDao;
import com.nikos.jersey.model.Book;

@Path("/books")
public class BooksResource {

	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// Return the list of books to the user in the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Book> getBooksBrowser() {
		List<Book> books = new ArrayList<Book>();
		books.addAll(BookDao.instance.getModel().values());
		return books;
	}

	// Return the list of books for applications
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Book> getBooks() {
		List<Book> books = new ArrayList<Book>();
		books.addAll(BookDao.instance.getModel().values());
		return books;
	}

	// retuns the number of books
	// Use http://localhost:<port>/<webapp-name>/rest/books/count
	// to get the total number of records
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = BookDao.instance.getModel().size();
		return String.valueOf(count);
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newBook(@FormParam("id") String id,
			@FormParam("title") String title,
			@FormParam("author") String author,
			@Context HttpServletResponse servletResponse) throws IOException {
		Book book = new Book(id, title);
		if (author != null) {
			book.setAuthor(author);
		}
		BookDao.instance.getModel().put(id, book);

		servletResponse.sendRedirect("../createBook.html");
	}

	// Defines that the next path parameter after books is
	// treated as a parameter and passed to the BookResources
	// Allows to type http://localhost:<port>/<webapp-name>/rest/books/1
	// 1 will be treaded as parameter book and passed to BookResource
	@Path("{book}")
	public BookResource getBook(@PathParam("book") String id) {
		return new BookResource(uriInfo, request, id);
	}

}
