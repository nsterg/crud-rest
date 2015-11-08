package com.nikos.jersey.client;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.nikos.jersey.model.Book;

public class Tester {
	public static void main(String[] args) {

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget service = client.target(getBaseURI());

		// create one book
		Book book = new Book("3", "The Myth of Sisyphus");
		Response response = service
				.path("rest")
				.path("books")
				.path(book.getId())
				.request(MediaType.APPLICATION_XML)
				.put(Entity.entity(book, MediaType.APPLICATION_XML),
						Response.class);

		// Return code should be 201 == created resource
		System.out.println(response.getStatus());

		// Get the Books
		System.out.println(service.path("rest").path("books").request()
				.accept(MediaType.TEXT_XML).get(String.class));

		// Get XML for application
		System.out.println(service.path("rest").path("books").request()
				.accept(MediaType.APPLICATION_XML).get(String.class));

		// Delete Book with id 1
		service.path("rest").path("books/1").request().delete();

		// Get get all Books -  id 1 should be deleted
		System.out.println(service.path("rest").path("books").request()
				.accept(MediaType.APPLICATION_XML).get(String.class));

		// Create a Book
		Form form = new Form();
		form.param("id", "4");
		form.param("title", "The rebel");
		response = service
				.path("rest")
				.path("books")
				.request()
				.post(Entity
						.entity(form, MediaType.APPLICATION_FORM_URLENCODED),
						Response.class);
		System.out.println("Form response " + response.getStatus());

		// Get all the books, id 4 should have been created
		System.out.println(service.path("rest").path("books").request()
				.accept(MediaType.APPLICATION_XML).get(String.class));

	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:7080/CrudRest").build();
	}
}
