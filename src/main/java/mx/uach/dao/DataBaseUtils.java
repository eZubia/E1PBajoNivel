package mx.uach.dao;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Suit de métodos necesarios para la utlizacion del Dao en los modelos.
 * 
 * @author Erik David Zubia Hernández
 * @version 1.0
 * @since 09/Octubre/2016
 */
public interface DataBaseUtils {
 
    /**
     * Retorna el nombre de la tabla para el modelo de base de datos.
     * 
     * @return {@code String} nombre de la tabla en base de datos 
     */
    public String getTableName();
    
    /**
     * Retorna una lista con los campos del modelo actual.
     * 
     * @return {@code List} de {@code Field} con todos los campos del modelo 
     */
    public List<Field> getFieldsToQuery();
    
    /**
     * Retorna una lista con los campos del modelo actual sin el id.
     * 
     * @return {@code List} de {@code Field} con todos los campos del modelo
     * excluyendo el id
     */
    public List<Field> getFieldsToQueryNoId();
}
