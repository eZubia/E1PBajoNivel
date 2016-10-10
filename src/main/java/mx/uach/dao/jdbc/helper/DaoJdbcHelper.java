package mx.uach.dao.jdbc.helper;

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
     * Crea un {@code Object} mapeando un {@code ResultSet} a objeto de la clase
     * mandada.
     * 
     * @param rs {@code ResultSet} con el iterador en la línea del cúal se planea
     * mapear a objeto
     * @param clazz {@code Class} del objeto a mapear
     * @return {@code Object} ya mapeado según el {@code ResultSet} mandado
     * @throws NoSuchMethodException cuando la clase del objeto no tiene los
     * Get para todos sus campos
     * @throws java.lang.InstantiationException en caso de que no se haya
     * colocado el contructor vacío dentro del modelo
     * @throws IllegalAccessException cuando los métodos Get no estan como 
     * publicos
     * @throws IllegalArgumentException si alguno de los Get tiene algún parámetro
     * @throws InvocationTargetException en caso de que el objeto al cual se 
     * le este invocando el método no lo contenga
     * @throws SQLException no tener correctamente el modelo entidad relación o 
     * el nombre de los atributos en java
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
