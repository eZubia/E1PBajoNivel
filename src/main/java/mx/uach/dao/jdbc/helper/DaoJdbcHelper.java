package mx.uach.dao.jdbc.helper;

import com.google.common.base.CaseFormat;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import mx.uach.dao.models.genericos.Model;

/**
 *
 * Helper que ayuda en la creación de objetos
 * 
 * @author Erik David Zubia Hernádez
 * @since 09/Octubre/2016
 * @version 1.0
 */
public class DaoJdbcHelper {
    
    /**
     * 
     * @param rs
     * @param clazz
     * @return
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public final static Object makeObject(ResultSet rs, Class clazz) throws SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
        Object newObject = clazz.newInstance();
        Method m = clazz.getMethod("getFieldsToQuery");
        
        List<Field> fieldsFromClass = (List<Field>) m.invoke(newObject);
        for (Field field : fieldsFromClass) {
            String methodName = String.format("set%s", Model.capitalize(field.getName()));
            Class[] params = new Class[1];
            Object value = rs.getObject(fieldsFromClass.indexOf(field)+1);
            params[0] = value.getClass();
            Method method = clazz.getMethod(methodName, params);
            method.invoke(newObject, value);
        }
        return newObject;
    }
 
}
