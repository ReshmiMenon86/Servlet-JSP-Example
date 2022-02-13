package org.o7planning.simplewebapp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.o7planning.simplewebapp.beans.Product;
import org.o7planning.simplewebapp.utils.DBUtils;
import org.o7planning.simplewebapp.utils.MyUtils;

@WebServlet(urlPatterns = { "/productList" })
public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProductListServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String errorString = null;
		List<Product> list = null;
		
		try   { 

		    Class.forName("org.postgresql.Driver");

		  }
		  catch(ClassNotFoundException e) {
		     System.out.println("Class not found "+ e);
		  }
		     try {

		     Connection conn = DriverManager.getConnection("jdbc:postgresql:mydb?user=postgres&password=postgres");
		     list = DBUtils.queryProduct(conn);
		     }
		     catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					errorString = e.getMessage();
				}
				//


		
		
		//String url = "jdbc:postgresql://localhost/test?user=postgres&password=postgres&ssl=true";
		
		/*Connection conn = MyUtils.getStoredConnection(request);*/

		
		
		
		// Store info in request attribute, before forward to views
		request.setAttribute("errorString", errorString);
		request.setAttribute("productList", list);
		
		// Forward to /WEB-INF/views/productListView.jsp
		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/productListView.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}