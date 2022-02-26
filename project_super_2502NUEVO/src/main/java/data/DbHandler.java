package data;

import java.sql.*;
import java.util.LinkedList;

import Entities.*;

public class DbHandler {

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String host = "localhost";
	private String port = "3306";
	private String user = "root";
	private String password = "root";
	private String db = "tpsuper";
	private String options = "?useLegacyDatetimeCode=false&serverTimezone=UTC";
	// private String options="";
	private Connection conn = null;

	public DbHandler() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Connection getConnection() {
		try {
			if (conn == null || conn.isClosed())
				conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db + options, user,
						password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	private void releaseConnection() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public LinkedList<Proveedor> selectProveedor() { // Devuelve todos los proveedores
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn;

		try {
			conn = this.getConnection();
			LinkedList<Proveedor> proveedores = new LinkedList<Proveedor>();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from proveedor");

			while (rs != null && rs.next()) {
				Proveedor p = new Proveedor();

				p.setId(rs.getInt("idProveedor"));
				p.setNombre(rs.getString("nombre"));
				p.setCuil(rs.getString("cuil"));
				p.setEmail(rs.getString("email"));
				p.setTipoTelefono(rs.getString("tipoTelefono"));
				p.setNroTelefono(rs.getString("nroTelefono"));
				p.setNombre(rs.getString("nombre"));

				proveedores.add(p);
			}
			return proveedores;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Proveedor selectProveedor(int id) { // Devuelve todos los proveedores, filtrado por el
																	// nombre
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn;

		try {
			conn = this.getConnection();
			LinkedList<Proveedor> proveedores = new LinkedList<Proveedor>();

			stmt = conn.prepareStatement("select * from proveedor where idProveedor = ?");
			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			if(rs != null && rs.next()) {
				Proveedor p = new Proveedor();

				p.setId(rs.getInt("idProveedor"));
				p.setCuil(rs.getString("cuil"));
				p.setEmail(rs.getString("email"));
				p.setTipoTelefono(rs.getString("tipoTelefono"));
				p.setNroTelefono(rs.getString("nroTelefono"));
				p.setNombre(rs.getString("nombre"));

				return p;
			}


		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	public LinkedList<Categoria> selectCategoria() { // Devuelve todos las categorias

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn;

		try {
			conn = this.getConnection();
			LinkedList<Categoria> categorias = new LinkedList<Categoria>();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from categoria");

			while (rs != null && rs.next()) {
				Categoria c = new Categoria();

				c.setId(rs.getInt("idCategoria"));
				c.setDescripcion(rs.getString("descripcion"));

				categorias.add(c);
			}
			return categorias;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

	public LinkedList<Localidad> selectLocalidades() { // Devuelve todos las categorias
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn;

		try {
			conn = this.getConnection();
			LinkedList<Localidad> localidades = new LinkedList<Localidad>();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from localidad");

			while (rs != null && rs.next()) {
				Localidad l = new Localidad();

				l.setId(rs.getInt("codpostal"));
				l.setNombre(rs.getString("nombre"));

				localidades.add(l);
			}
			return localidades;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Categoria selectCategoria(int id) { // Devuelve todos las categorias, filtrado por
																		// descripcion
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn;

		try {
			conn = this.getConnection();
			LinkedList<Categoria> categorias = new LinkedList<Categoria>();

			stmt = conn.prepareStatement("select * from categoria where idCategoria = ?");
			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			if(rs != null && rs.next()) {
				Categoria c = new Categoria();

				c.setId(rs.getInt("idCategoria"));
				c.setDescripcion(rs.getString("descripcion"));

			
			return c;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	public LinkedList<Producto> selectProducto(String order) { // Devuelve todos los productos

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn;

		try {
			conn = this.getConnection();
			LinkedList<Producto> productos = new LinkedList<Producto>();
			stmt = conn.createStatement();
				String query = "select pr.valor, pr.idProducto, p.descripcion as 'prod-desc', stock, p.idCategoria as 'idCategoria', cat.descripcion as 'cat-desc',\r\n"
						+ " prov.idProveedor as 'idProveedor', Nombre, cuil, tipoTelefono, nroTelefono from producto p\r\n"
						+ "inner join proveedor prov on prov.idProveedor = p.idProveedor\r\n"
						+ "inner join categoria cat on cat.idCategoria = p.idCategoria\r\n"
						+ "inner join (select max(fechaDesde) as fec, idProducto from precio group by precio.idProducto) maxprec2 on p.idProducto = maxprec2.idProducto \r\n"
						+ "inner join precio  pr on pr.idProducto = p.idProducto and\r\n"
						+ "pr.fechaDesde = maxprec2.fec";

			rs = stmt.executeQuery(query);
			while (rs != null && rs.next()) {
				Producto producto = new Producto();
				Categoria categoria = new Categoria();
				Proveedor proveedor = new Proveedor();

				categoria.setId(rs.getInt("idCategoria"));
				categoria.setDescripcion(rs.getString("cat-desc"));

				proveedor.setId(rs.getInt("idProveedor"));
				proveedor.setTipoTelefono(rs.getString("tipoTelefono"));
				proveedor.setNroTelefono(rs.getString("nroTelefono"));
				proveedor.setNombre(rs.getString("Nombre"));
				proveedor.setCuil(rs.getString("cuil"));

				producto.setId(rs.getInt("idProducto"));
				producto.setDescripcion(rs.getString("prod-desc"));
				producto.setStock(rs.getInt("stock"));
				producto.setPrecio(rs.getDouble("valor"));

				producto.setCategoria(categoria);
				producto.setProveedor(proveedor);

				productos.add(producto);
			}
			return productos;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public Producto selectProducto(int id) { // Devuelve un producto

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn;

		try {
			conn = this.getConnection();
			LinkedList<Producto> productos = new LinkedList<Producto>();
			String query = "select precio.valor, max(fechaDesde), precio.idProducto, producto.descripcion as 'prod-desc', stock, producto.idCategoria as 'idCategoria', categoria.descripcion as 'cat-desc', proveedor.idProveedor as 'idProveedor', Nombre, cuil, tipoTelefono, nroTelefono\r\n"
					+ "from precio inner join producto on producto.idProducto = precio.idProducto\r\n"
					+ "inner join categoria on producto.idCategoria = categoria.idCategoria\r\n"
					+ "inner join proveedor on producto.idProveedor = proveedor.idProveedor\r\n"
					+ "where producto.idProducto = ? and precio.fechaDesde = (select max(fechaDesde) from precio p where p.idProducto = ?) group by precio.idProducto\r\n"
					+ "";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.setInt(2, id);
			rs = stmt.executeQuery();

			while (rs != null && rs.next()) {
				Producto producto = new Producto();
				Categoria categoria = new Categoria();
				Proveedor proveedor = new Proveedor();

				categoria.setId(rs.getInt("idCategoria"));
				categoria.setDescripcion(rs.getString("cat-desc"));

				proveedor.setId(rs.getInt("idProveedor"));
				proveedor.setTipoTelefono(rs.getString("tipoTelefono"));
				proveedor.setNroTelefono(rs.getString("nroTelefono"));
				proveedor.setNombre(rs.getString("nombre"));
				proveedor.setCuil(rs.getString("cuil"));

				producto.setId(rs.getInt("idProducto"));
				producto.setDescripcion(rs.getString("prod-desc"));
				producto.setStock(rs.getInt("stock"));
				producto.setPrecio(rs.getDouble("precio.valor"));

				producto.setCategoria(categoria);
				producto.setProveedor(proveedor);

				productos.add(producto);
			}
			return productos.get(0);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void addProducto(String desc, int stock, double precio, int cat, int prov) {

		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		Connection conn = null;
		int idprod;

		try {
			conn = this.getConnection();
			stmt = conn.prepareStatement(
					"insert into producto(descripcion, stock, idCategoria, idProveedor) " + "values(?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, desc);
			stmt.setInt(2, stock);
			stmt.setInt(3, cat);
			stmt.setInt(4, prov);

			stmt.executeUpdate();
			keyRS = stmt.getGeneratedKeys();

			if (keyRS != null && keyRS.next()) {
				idprod = keyRS.getInt(1);
				this.addPrecio(precio, idprod);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (keyRS != null)
					keyRS.close();
				if (stmt != null)
					stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void addPrecio(double precio, int idprod) {

		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		Connection conn = null;

		try {
			conn = this.getConnection();
			stmt = conn.prepareStatement("insert into precio(valor, idProducto)\r\n"
					+ "\r\n"
					+ "select IF(pr.valor*(30/100 + 1) < ? ,pr.valor*(30/100 + 1), ?), pr.idProducto from producto p\r\n"
					+ "inner join proveedor prov on prov.idProveedor = p.idProveedor\r\n"
					+ "inner join categoria cat on cat.idCategoria = p.idCategoria\r\n"
					+ "inner join (select max(fechaDesde) as fec, idProducto from precio where YEAR(fechaDesde) = (YEAR(current_date) - 1) and idProducto = ? group by precio.idProducto) maxprec2 on p.idProducto = maxprec2.idProducto \r\n"
					+ "inner join precio pr on pr.idProducto = p.idProducto and\r\n"
					+ "pr.fechaDesde = maxprec2.fec;\r\n"
					+ "",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(3, idprod);
			stmt.setDouble(2, precio);
			stmt.setDouble(1, precio);

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (keyRS != null)
					keyRS.close();
				if (stmt != null)
					stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void delete(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.getConnection();
			stmt = conn.prepareStatement("delete from producto where idProducto = ?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void modifyProduct(String desc, int stock, int prov, int cat, double precio, int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.getConnection();
			stmt = conn.prepareStatement(
					"UPDATE producto SET descripcion = ?, stock = ?, idProveedor = ?, idCategoria = ? WHERE (idProducto = ?);");
			stmt.setString(1, desc);
			stmt.setInt(2, stock);
			stmt.setInt(3, prov);
			stmt.setInt(4, cat);
			stmt.setInt(5, id);
			stmt.executeUpdate();
			this.addPrecio(precio, id);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void nuevoCli(Cliente c) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tpsuper?user=root&password=admin");
			stmt = conn.prepareStatement("insert into cliente values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS); // chequear
																													// que
																													// sean
																													// 5
																													// ''?''
			stmt.setString(1, c.getDireccion());
			stmt.setString(2, c.getNroDocumento());
			stmt.setString(3, c.getFechaNac().toString());
			stmt.setString(4, c.getNombreApellido());
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void eliminaCli(String id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.getConnection();
			stmt = conn.prepareStatement("delete from cliente where nroDocumento = ?");
			stmt.setString(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void modificarCli(String dni, String tipodni, String nombre, String direccion, String codpostal, String email, String fecha) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.getConnection();
			stmt = conn.prepareStatement(
					"UPDATE cliente set tipoDocumento = ?, nombreApellido = ?, direccion = ?, codpostal = ?, email = ?, fechaNacimiento = ? where (nroDocumento = ?);");
			stmt.setString(1, tipodni);
			stmt.setString(2, nombre);
			stmt.setString(3, direccion);
			stmt.setString(4, codpostal);
			stmt.setString(5, email);
			stmt.setString(6, fecha);
			stmt.setString(7, dni);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				this.releaseConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Cliente buscaCli(String dni) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn;

		try {
			conn = this.getConnection();
			String query = "select c.nroDocumento, c.tipoDocumento, c.nombreApellido, c.email, c.fechaNacimiento, c.telefono, c.direccion, c.codPostal, p.nombre as 'provNombre', p.idProvincia, l.nombre as 'locNombre'"
					+ " from Cliente c " + " inner join localidad l on l.codPostal = c.codPostal "
					+ " inner join provincia p on p.idProvincia = l.idProvincia where nroDocumento = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, dni);

			rs = stmt.executeQuery();
			while (rs != null && rs.next()) {
				Cliente c = new Cliente();
				Provincia p = new Provincia();
				Localidad l = new Localidad();
	
				p.setId(rs.getInt("idProvincia"));
				p.setNombre(rs.getString("provNombre"));
	
				l.setId(rs.getInt("codPostal"));
				l.setProvincia(p);
				l.setNombre(rs.getString("locNombre"));
	
				c.setNroDocumento(rs.getString("nroDocumento"));
				c.setTipDocumento(rs.getString("tipoDocumento"));
				c.setNombreApellido(rs.getString("nombreApellido"));
				c.setEmail(rs.getString("email"));
				c.setFechaNac(rs.getString("fechaNacimiento"));
				c.setNroTelefono(rs.getString("telefono"));
				c.setDireccion(rs.getString("direccion"));
				c.setLocalidad(l);
				
				return c;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	public LinkedList<Cliente> selectCliente() { // Devuelve todos los clientes		
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = this.getConnection();
			LinkedList<Cliente> clientes = new LinkedList<Cliente>();
			/*select c.nroDocumento, c.tipoDocumento, c.nombreApellido, c.email, c.fechaNacimiento, c.telefono, c.direccion, c.codPostal, p.nombre as 'provNombre', p.idProvincia, l.nombre as 'locNombre'"
					+ " from Cliente c " + " inner join localidad l on l.codPostal = c.codPostal "
					+ " inner join provincia p on p.idProvincia = l.idProvincia*/
			String query = "select c.nroDocumento, c.tipoDocumento, c.nombreApellido, c.email, c.fechaNacimiento, c.telefono, c.direccion, c.codPostal, p.nombre as 'provNombre', p.idProvincia, l.nombre as 'locNombre'\r\n"
					+ "from Cliente c inner join localidad l on l.codPostal = c.codPostal \r\n"
					+ "inner join provincia p on p.idProvincia = l.idProvincia";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs != null && rs.next()) {
				Cliente c = new Cliente();
				Provincia p = new Provincia();
				Localidad l = new Localidad();

				p.setId(rs.getInt("idProvincia"));
				p.setNombre(rs.getString("provNombre"));

				l.setId(rs.getInt("codPostal"));
				l.setProvincia(p);
				l.setNombre(rs.getString("locNombre"));

				c.setNroDocumento(rs.getString("nroDocumento"));
				c.setTipDocumento(rs.getString("tipoDocumento"));
				c.setNombreApellido(rs.getString("nombreApellido"));
				c.setEmail(rs.getString("email"));
				c.setFechaNac(rs.getString("fechaNacimiento"));
				c.setNroTelefono(rs.getString("telefono"));
				c.setDireccion(rs.getString("direccion"));
				c.setLocalidad(l);
				clientes.add(c);
			}
			return clientes;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void nuevaCat( String desc) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = this.getConnection();
			stmt = conn.prepareStatement("insert into Categoria (Descripcion) values (?)");/* please ver si esta bien este query*/
			stmt.setString(1, desc);
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
		} catch (SQLException e){
			e.printStackTrace();
		}
		finally{
			try{if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			this.releaseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
	public void nuevoProv( String nombre, String cuil, String nroTelefono, String tipoTelefono) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = this.getConnection();
			stmt = conn.prepareStatement("insert into Proveedor (nombre, cuil, nroTelefono, tipoTelefono) values (?,?,?,?)");/* please ver si esta bien este query*/
			stmt.setString(1, nombre);
			stmt.setString(2, cuil);
			stmt.setString(3, nroTelefono);
			stmt.setString(4, tipoTelefono);
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
		} catch (SQLException e){
			e.printStackTrace();
		}
		finally{
			try{if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			this.releaseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
	public void deleteCat(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.getConnection();
			stmt = conn.prepareStatement("delete from categoria where idCategoria = ?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void modCat(int id, String desc) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.getConnection();
			stmt = conn.prepareStatement("update categoria set descripcion = ? where idCategoria = ?");
			stmt.setString(1, desc);
			stmt.setInt(2, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void modProv(int id, String nombre, String cuil, String nroTelefono, String tipoTelefono) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.getConnection();
			stmt = conn.prepareStatement("update proveedor set nombre = ?, cuil = ?, nroTelefono = ?, tipoTelefono = ? where idProveedor = ?");
			stmt.setString(1, nombre);
			stmt.setString(2, cuil);
			stmt.setString(3, nroTelefono);
			stmt.setString(4, tipoTelefono);
			stmt.setInt(5, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void deleteProv(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.getConnection();
			stmt = conn.prepareStatement("delete from proveedor where idProveedor = ?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void inflation(Double val) {
		LinkedList<Producto> prods = this.selectProducto("a");
		for(Producto p: prods) {
			this.addPrecio(p.getPrecio()*(val/100 + 1), p.getId());
		}
}
}

