package com.out;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.impl.StdCouchDbConnector;

/**
 * Servlet implementation class List
 */
public class List extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "couchdb/cloudant-sample")
	protected CouchDbInstance _db;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public List() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ViewQuery query = new ViewQuery().designDocId("_design/comments")
				.viewName("allComments");
		CouchDbConnector db = new StdCouchDbConnector("comments", _db);
		ViewResult result = db.queryView(query);
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (ViewResult.Row row : result.getRows()) {
			if ("comment".equals(row.getKey())) {
				if (map.get(row.getValue()) == null) {
					map.put(row.getValue(), 0);
				}
				int count = map.get(row.getValue());
				count++;
				map.put(row.getValue(), count);
			}
		}
		// response.getOutputStream().print("[");
		// boolean first = true;
		// for (String comment : map.keySet()) {
		// if (!first) {
		// response.getOutputStream().print(",");
		// }
		// response.getOutputStream().print(
		// "{\"text\": \"" + comment + "\",\"size\": "
		// + map.get(comment) + "}");
		// if (first) {
		// first = false;
		// }
		//
		// }
		// response.getOutputStream().print("]");
		Map<Integer, java.util.List<String>> reverse = new TreeMap<Integer, java.util.List<String>>(
				Collections.reverseOrder());
		for (String comment : map.keySet()) {
			if (reverse.get(map.get(comment)) == null) {
				reverse.put(map.get(comment), new ArrayList<String>());
			}
			java.util.List<String> list = reverse.get(map.get(comment));
			list.add(comment);

		}
		for (Integer count : reverse.keySet()) {
			response.getOutputStream()
					.println(count + " " + reverse.get(count));
		}
		// for (String comment : map.keySet()) {
		// response.getOutputStream()
		// .println(comment + " " + map.get(comment));
		// }
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
