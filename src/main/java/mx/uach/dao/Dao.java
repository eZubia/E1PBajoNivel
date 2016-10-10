package mx.uach.dao;

import java.util.List;

/**
 *
 * Declaración de los métodos de escritura y lectura de un modelo a otro;
 * 
 * @author Erik David Zubia Hernández
 * @version 1.0
 */
public interface Dao {
    
    public Object getDataById(Integer id, Class clazz);
    
    public List<Object> getDataByCriteria(String criterio, Class clazz);
    
    public void dataProcces(Object obj, CRUD crud);
    
    
}
