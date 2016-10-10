/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.uach.dao;

import java.lang.reflect.Field;
import java.util.List;

/**
 *
 * @author ezubia
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
