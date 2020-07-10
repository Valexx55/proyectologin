package proyectologin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BaseDatos {
	
	private static final String CADENA_CONEXION = "jdbc:mysql://localhost:3306/hedima";
	private static final String USUARIO = "root";
	private static final String CONTRASENIA_BD = "nrmnct29";
	
	static {
		
		//esta sección es como para iniciaizar la clase y se puede usar
		//cuando se necesite. las instrucciones que metamos aquí en la sección
		//static se ejecutan automáticamente cuando aparece por primera vez
		//el nombre en el codigo
		System.out.println("estamos en la sección static");
	
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BaseDatos() {
		// TODO Auto-generated constructor stub
		System.out.println("pasa por el constructor");
	}
	

	
	public Connection obtenerConexion () throws SQLException
	{
		Connection connection = null;
		
			connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, CONTRASENIA_BD);
		
		return connection;
	}
	
	public void liberarRecursos (Connection connection, Statement statement, ResultSet resultSet)
	{
		try {
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	

	
	public List<Usuario> obtenerListaUsuarios ()
	{
		List<Usuario> lu = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		Usuario usuario_aux = null;
		
			try {
				lu = new ArrayList<Usuario>();
				connection = this.obtenerConexion();
				statement = connection.createStatement();
				resultSet = statement.executeQuery(InstruccionesSelect.SELECCIONAR_TODOS_USUARIOS);
				while (resultSet.next())
				{
					//crear el usuario
					usuario_aux = new Usuario(resultSet);
					//add a la lista
					lu.add(usuario_aux);
				}
				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally {
				this.liberarRecursos(connection, statement, resultSet);
				
			}
		
		return lu;
	}
	
	public boolean insertarUsuario (Usuario u)
	{
		return false;
	}
	
	public boolean borrarUsuario (int id)
	{
		return false;
	}
	
	/**
	 * Método que consulta el usuario de la base de datos
	 * @param nombre el nombre buscado
	 * @param pwd la password del usuario
	 * @return el usuario si existe o null si no lo encontró
	 * @throws SQLException 
	 * 
	 */
	public Usuario login (String nombre, String pwd) throws SQLException
	{
		Usuario usuario = null;
		PreparedStatement ps = null;
		Connection connection = null;
		ResultSet rs = null;
			
			//tengo que hacer la consulta
			//1 pillo conexión
		try {
			connection = this.obtenerConexion();
			ps = connection.prepareStatement(InstruccionesSelect.LOGIN_USUARIOS);
			ps.setString(1,  nombre);
			ps.setString(2,  pwd);
			rs = ps.executeQuery();
			if (rs.next())
			{
				usuario = new Usuario(nombre, pwd);
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		} finally {
			liberarRecursos(connection, ps, rs);
		}
			
			
		return usuario;
	}
	

}
