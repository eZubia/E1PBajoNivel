package mx.uach.dao.jdbc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.uach.dao.CRUD;
import mx.uach.dao.Dao;
import mx.uach.dao.conexiones.Conexion;
import mx.uach.dao.jdbc.helper.DaoJdbcHelper;
import mx.uach.dao.models.genericos.Model;


/**
 *
 * @author ezubia
 */
public class DaoJDBC implements Dao{

    /**
     * {@inheritDoc}
     * 
     * @see Model
     */
    @Override
    public Object getDataById(Integer id, Class clazz) {
        try {
            Object obj = clazz.newInstance();
            Method method = clazz.getMethod("getTableName");
            String table = (String) method.invoke(obj);
            method = clazz.getMethod("getFieldsToQuery");
            List<Field> fields = (List<Field>) method.invoke(obj);
            ResultSet rs;
            Statement st = Conexion.getInstance().getCon().createStatement();
            String query = String.format("%s %s %s %s %s %s", Model.SELECT, 
                    Model.fieldsToQuery(fields, Boolean.FALSE), Model.FROM, table, Model.Q_WHERE_ID, id);
            rs = st.executeQuery(query);
            
            while (rs.next()) {                
                obj = DaoJdbcHelper.makeObject(rs, clazz);
            }
            return obj;
        } catch (SQLException ex) {
            Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see Model
     */
    @Override
    public void dataProcces(Object obj, CRUD crud) {
        try {
            Method method = obj.getClass().getMethod("getTableName");
            String table = (String) method.invoke(obj);
            PreparedStatement ps = null;
            String query = "";
            List<Field> fields = null;
            switch (crud) {
                case CREATE:
                    method = obj.getClass().getMethod("getFieldsToQueryNoId");
                    fields = (List<Field>) method.invoke(obj);
                    query = String.format("%s %s (%s) VALUES (%s)", Model.INSERT, table, 
                            Model.fieldsToQuery(fields, Boolean.TRUE), Model.paramsToStatement(fields, Boolean.TRUE));
                    ps = Conexion.getInstance().getCon().prepareStatement(query);
                    Model.putParametersInQuery(ps, fields, obj, false);
                    break;
                case UPDATE:
                    method = obj.getClass().getMethod("getFieldsToQueryNoId");
                    fields = (List<Field>) method.invoke(obj);
                    query = String.format("%s %s SET %s WHERE %s = ?", Model.UPDATE, table, Model.paramsToStatementToCreate(fields, Boolean.TRUE), Model.ID);
                    ps = Conexion.getInstance().getCon().prepareStatement(query);
                   Model.putParametersInQuery(ps, fields, obj, true);
                    break;
                case DELETE:
                    query = String.format("%s %s %s ?", Model.DELETE, table, Model.Q_WHERE_ID);
                    ps = Conexion.getInstance().getCon().prepareStatement(query);
                    if(obj instanceof Model){
                       Model m = (Model) obj;
                       ps.setInt(1, m.getId());
                    }
                    break;
                default:
                    break;
            }
            
            ps.execute();
        } catch (SQLException ex) {
            System.out.println("" + ex.getMessage());
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see Model
     */
    @Override
    public List<Object> getDataByCriteria(String criterio, Class clazz) {
        List<Object> objects = new ArrayList<>();
        try {
            Object obj = clazz.newInstance();
            Method method = clazz.getMethod("getTableName");
            String table = (String) method.invoke(obj);
            method = clazz.getMethod("getFieldsToQuery");
            List<Field> fields = (List<Field>) method.invoke(obj);
            ResultSet rs;
            Statement st = Conexion.getInstance().getCon().createStatement();
            rs = st.executeQuery(String.format("%s %s %s %s %s %s", Model.SELECT, 
                    Model.fieldsToQuery(fields, Boolean.FALSE), Model.FROM, table, Model.Q_WHERE, criterio));
            Object newObj = null;
            while (rs.next()) {                
                obj = DaoJdbcHelper.makeObject(rs, clazz);
                objects.add(obj);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objects;
    }
    
}
