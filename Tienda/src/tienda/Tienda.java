package tienda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Tienda{  

    private String NombreTienda;
    private String Direccion;
    private String Telefono;
    private String Localidad;

    public Tienda(String NombreTienda, String Direccion, String Telefono, String Localidad) {
        this.NombreTienda = NombreTienda;
        this.Direccion = Direccion;
        this.Telefono = Telefono;
        this.Localidad = Localidad;
    }

    public String getNombreTienda() {
        return NombreTienda;
    }

    public void setNombreTienda(String NombreTienda) {
        this.NombreTienda = NombreTienda;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getLocalidad() {
        return Localidad;
    }

    public void setLocalidad(String Localidad) {
        this.Localidad = Localidad;
    }


    
    static Connection conexion;
    
    public static void abrirConexion(){
        try {
            Class.forName("org.h2.Driver");
            conexion = DriverManager.getConnection("jdbc:h2:c:/EANguia4/basededatos", "sa", "");          
        }
        catch (Exception ex){
            conexion = null;
            System.err.println("Error de conexion con la Base de datos");
            ex.printStackTrace();
        }
    }
    
    public static void cerrarConexion(){
        try{
            if(conexion != null){
                conexion.close();                
            }
        }
        catch (Exception ex){}
    }
    
    public static void nuevoProducto(Producto x){
        PreparedStatement ps;
        String sql = "INSERT INTO BASEDEDATOS VALUES (?, ?, ?, ?)";
        
        try {    
            ps = conexion.prepareStatement(sql);
            ps.setString(1, x.getNombre());
            ps.setString(2, x.getTipo());
            ps.setString(3, x.getUbicacion());
            ps.setInt(4, x.getCantidad());
            
            ps.executeUpdate();
            
            System.out.println("Producto almacenado correctamente");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }    
    }
    
    public static void modificarProducto(Producto y){
        PreparedStatement qs;
        String sql = "UPDATE BASEDEDATOS SET CANTIDAD=? WHERE NOMBRE=?;";
        
        try {
            qs = conexion.prepareStatement(sql);
            qs.setInt(1, y.getCantidad());
            qs.setString(2, y.getNombre());
            
            qs.executeUpdate();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    public static List<Producto> obtenerProductos(){
        List<Producto> productos = new ArrayList<>();
        try {
             Statement st;
             String sql = "SELECT * FROM BASEDEDATOS";
             
             st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql);
             
             while (rs.next()){
                 String nombre = rs.getString("NOMBRE");
                 String tipo = rs.getString("TIPO");
                 String ubicacion = rs.getString("UBICACION");
                 int cantidad = rs.getInt("CANTIDAD");
                 
                 Producto x = new Producto(nombre, tipo, ubicacion, cantidad);
                 
                 productos.add(x);
             }
             
             rs.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }        
        return productos;
    }
        
}