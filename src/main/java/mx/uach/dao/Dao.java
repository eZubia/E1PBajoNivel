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
    
    /**
     * Realiza una búsqueda dentro de la base de datos para retornar el resultado
     * convertido en objeto de java.
     * 
     * @param id {@code Integer} indentificador del objeto a buscar dentro de la
     * base de datos
     * @param clazz {@code Class} clase del objeto a buscar
     * @return {@code Object} objeto mapeado con sus respectivos valores
     */
    public Object getDataById(Integer id, Class clazz);
    
    /**
     * Realiza una búsqueda dentro de la base de datos para retornar los resultados
     * segun el criterio de búsqueda.
     * 
     * @param criterio {@code String} criterio de búesqueda
     * @param clazz {@code Class} clase del los objetos a buscar
     * @return {@code List} de {@code Object} con todos los objetos mapeados
     * al modelo dicho en la {@code Class}
     */
    public List<Object> getDataByCriteria(String criterio, Class clazz);
    
    /**
     * Realiza el proceso de creación, actualización o eliminación dentro de la
     * base de datos para un objeto en concreto.
     * 
     * @param obj {@code Object} objeto al cual se le aplicara alguna de las
     * operaciónes
     * @param crud {@code CRUD} enumerador que dicta el tipo de procedimiento
     * a realizar para el objeto
     */
    public void dataProcces(Object obj, CRUD crud);
    
    
}
