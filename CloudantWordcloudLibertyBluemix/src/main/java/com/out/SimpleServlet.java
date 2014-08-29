package com.out;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;

/**
 * Servlet implementation class SimpleServlet
 */
public class SimpleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "couchdb/cloudant-sample")
	protected CouchDbInstance _db;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SimpleServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dbname = "comments";
		try {
			// creates a database with the specified name
			CouchDbConnector dbc = _db.createConnector(dbname, true);
			response.getWriter().println("Created database: " + dbname);
			// create a simple doc to place into your new database
			Map<String, Object> doc = new HashMap<String, Object>();
			doc.put("_id", UUID.randomUUID().toString());
			doc.put("comment", request.getParameter("comment"));
			dbc.create(doc);
			response.getWriter().println(dbc.getAllDocIds().size());
		} catch (Exception e) {
			response.getWriter().println(e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
