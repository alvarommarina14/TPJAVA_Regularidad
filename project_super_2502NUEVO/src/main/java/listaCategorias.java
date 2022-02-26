

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Entities.Categoria;
import Entities.Producto;
import Entities.Proveedor;
import data.DbHandler;

/**
 * Servlet implementation class listaProveedores
 */
@WebServlet("/listaCategorias")
public class listaCategorias extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public listaCategorias() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DbHandler db = new DbHandler();
		String order = request.getParameter("order");
		Categoria categoria = null;
		if(order.equalsIgnoreCase("desc")) {
			
		}else if(order.equalsIgnoreCase("add")) {
			String desc = request.getParameter("desc");
			db.nuevaCat(desc);
		}else if(order.split("-")[0].equalsIgnoreCase("del")) {
		int id = Integer.parseInt(order.split("-")[1]);
		db.deleteCat(id);
		}else if(order.split("-")[0].equalsIgnoreCase("mod")){
			int id = Integer.parseInt(order.split("-")[1]);
			categoria = db.selectCategoria(id);
			
		}
		else if(order.equalsIgnoreCase("modify")){
			int id = Integer.parseInt(request.getParameter("id"));
			String desc = request.getParameter("desc");
			
			db.modCat(id, desc);
		}else if(order.equalsIgnoreCase("volver")) {
			LinkedList<Producto> productos =  db.selectProducto("desc");
			LinkedList<Categoria> categorias = db.selectCategoria();
			LinkedList<Proveedor> proveedores = db.selectProveedor();
			
			request.setAttribute("productos", productos);
			request.setAttribute("categorias", categorias);
			request.setAttribute("proveedores", proveedores);

			request.getRequestDispatcher("listaProductos.jsp").forward(request, response);
		}
		
		request.setAttribute("categoria", categoria);
		LinkedList<Categoria> categorias = db.selectCategoria();
		request.setAttribute("categorias", categorias);

		
		request.getRequestDispatcher("listaCategorias.jsp").forward(request, response);
	}

}
