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
 * Servlet implementation class servlet
 */
@WebServlet(name = "listaProductos", urlPatterns = { "/listaProductos" })
public class listaProductos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public listaProductos() {
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
		Producto producto = null;
		if(order.equalsIgnoreCase("desc")) {
			
		}else if(order.equalsIgnoreCase("add")) {
			String desc = request.getParameter("desc");
			int stock = Integer.parseInt(request.getParameter("stock"));
			double precio = Double.parseDouble(request.getParameter("precio"));
			int cat = Integer.parseInt(request.getParameter("cat"));
			int prov = Integer.parseInt(request.getParameter("prov"));
			db.addProducto(desc, stock, precio, cat, prov);
			
		}else if(order.split("-")[0].equalsIgnoreCase("del")) {
			int id = Integer.parseInt(order.split("-")[1]);
			db.delete(id);
			
		}else if(order.split("-")[0].equalsIgnoreCase("mod")){
			int id = Integer.parseInt(order.split("-")[1]);
			producto = db.selectProducto(id);
			
		}else if(order.equalsIgnoreCase("modify")){
			int id = Integer.parseInt(request.getParameter("id"));
			String desc = request.getParameter("desc");
			int stock = Integer.parseInt(request.getParameter("stock"));
			double precio = Double.parseDouble(request.getParameter("precio"));
			int cat = Integer.parseInt(request.getParameter("cat"));
			int prov = Integer.parseInt(request.getParameter("prov"));
			db.modifyProduct(desc, stock, prov, cat, precio, id);
		}else if(order.equalsIgnoreCase("updatePrices")){
			Double valor = Double.parseDouble(request.getParameter("valor"));
			db.inflation(valor);
		}else if(order.equalsIgnoreCase("addCat")) {

			LinkedList<Categoria> categorias = db.selectCategoria();
			request.setAttribute("categorias", categorias);
			request.getRequestDispatcher("listaCategorias.jsp").forward(request, response);
		}else if(order.equalsIgnoreCase("addProv")) {

			LinkedList<Proveedor> proveedores = db.selectProveedor();
			request.setAttribute("proveedores", proveedores);
			request.getRequestDispatcher("listaProveedores.jsp").forward(request, response);
		}
		
		LinkedList<Producto> productos =  db.selectProducto("desc");
		LinkedList<Categoria> categorias = db.selectCategoria();
		LinkedList<Proveedor> proveedores = db.selectProveedor();
		
		request.setAttribute("productos", productos);
		request.setAttribute("categorias", categorias);
		request.setAttribute("proveedores", proveedores);
		request.setAttribute("producto", producto);
	
		request.getRequestDispatcher("listaProductos.jsp").forward(request, response);
	
	}

}
