package mx.uach.dao.models.genericos;

import com.google.common.base.CaseFormat;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Modelo general de todo el sistema.
 *
 * @author Erik David Zubia Hernández
 * @version 1.0
 */
public class Model {

    public static final String Q_WHERE_ID = "WHERE id = ";
    public static final String Q_WHERE = "WHERE";
    public static final String INSERT = "INSERT INTO";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE FROM";
    public static final String ID = "id";
    public static final String SELECT = "SELECT";
    public static final String FROM = "FROM";

    private Integer id;

    /**
     * Constructor vacío.
     */
    public Model() {
    }

    /**
     * Constructor con el identificador del objeto.
     * 
     * @param id {@code Integer} identificador único del objeto
     */
    public Model(Integer id) {
        this.id = id;
    }

    /**
     * Retorna el identificador del objeto.
     * 
     * @return {@code Integer} con el identificador del objeto
     */
    public Integer getId() {
        return id;
    }

    /**
     * Asigna un identificador al objeto.
     * 
     * @param id {@code Integer} con el identificador del objeto
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Convierte la lista de {@code Field} para una {@code Class} en un formato
     * para ser utilizado en una query de base de datos sql.
     *
     * @param fields {@code List} de {@code Field} que son los atributos de una clase
     * @param noId {@code Boolean} si requiere o no el identificador
     * @return {@code String} con los atributos de la clase en formato para una
     * consulta de slq
     */
    public final static String fieldsToQuery(List<Field> fields, Boolean noId) {
        String fieldsFromat = "";
        List<Field> fieldsFilter = noId
                ? fields.stream()
                .filter(field -> !field.getName().equals(ID))
                .collect(Collectors.toList()) : fields;

        for (Field field : fieldsFilter) {
            fieldsFromat = String.format("%s, %s", fieldsFromat, convertCamelToSnake(field.getName()));
        }
        return fieldsFromat.substring(1);
    }

    /**
     * Convierte la lista de {@code Field} para una {@code Class} en un formato
     * de ? para ser utilizado en una query de base de datos sql.
     *
     * @param fields {@code List} de {@code Field} que son los atributos de una clase
     * @param noId {@code Boolean} si requiere o no el identificador
     * @return {@code String} con tantos ? como campos existan para la clase 
     * separados por comas
     */
    public static String paramsToStatement(List<Field> fields, Boolean noId) {
        String campos = "";
        fields = noId
                ? fields.stream()
                .filter(field -> !field.getName().equals(ID))
                .collect(Collectors.toList())
                : fields;
        for (Field field : fields) {
            campos = String.format("%s, ?", campos);
        }
        return campos.substring(1);
    }

    /**
     * Convierte la lista de {@code Field} para una {@code Class} en un formato
     * de ? para ser utilizado en una query de base de datos sql.
     *
     * @param fields {@code List} de {@code Field} que son los atributos de una clase
     * @param noId {@code Boolean} si requiere o no el identificador
     * @return {@code String} con los atributos de la clase con un igualadaro a ?
     * para ser utilizado en la consulta
     */
    public static String paramsToStatementToCreate(List<Field> fields, Boolean noId) {
        String campos = "";
        fields = noId
                ? fields.stream()
                .filter(field -> !field.getName().equals(ID))
                .collect(Collectors.toList())
                : fields;
        for (Field field : fields) {
            campos = String.format("%s, %s = ?", campos, field.getName());
        }
        return campos.substring(1);
    }

    /**
     * Convierte un nombre de atributo que se encuentra en CamelCase a SnakeCase
     * para poder ser insertado en la query de la base de datos.
     *
     * @param fieldName {@code String} nombre del atributo a cambiar
     * @return {@code String} con el nombre del atributo en SnakeCase
     * (snake_case)
     */
    public final static String convertCamelToSnake(String fieldName) {
        String snakeCase = "";
        snakeCase = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
        return snakeCase;
    }

    /**
     * Convierte un nombre de una columna de un modelo entidad-relacion que se
     * encuentra en SnakeCase a CamelCase para poder ser utilizado como atributo
     * en cuna clase de java.
     *
     * @param columnName {@code String} nombre de la columna a convertir
     * @return {@code String} con el nombre de la columna en CamelCase
     * (camelCase)
     */
    public final static String convertSnakeToCamel(String columnName) {
        String snakeCase = "";
        snakeCase = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, columnName);
        return snakeCase;
    }

    /**
     * Realiza un cambio de minuscula o mayusucula a la primera letra del
     * {@code String}.
     * 
     * @param line {@code String} línea a cambiar
     * @return {@code String} con la oración empezando por mayúscula
     */
    public static String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    /**
     * Realiza una asignación a los parámetros del {@code PreparedStatement}
     * formado en base a una lista de campos y los valores de un objeto.
     * 
     * @param ps {@code PreparedStatement} a la que se le van a asignar los valores
     * @param fields {@code List} de {@code Field} con los atributos del objeto
     * @param obj {@code Object} objeto del cual se sacaran los valores a
     * asignar
     * @param forUpdate {@code Boolean} si se va a utlizar en una actualización 
     * o en otra cosa
     * @throws NoSuchMethodException cuando la clase del objeto no tiene los
     * Get para todos sus campos
     * @throws IllegalAccessException cuando los métodos Get no estan como 
     * publicos
     * @throws IllegalArgumentException si alguno de los Get tiene algún parámetro
     * @throws InvocationTargetException en caso de que el objeto al cual se 
     * le este invocando el método no lo contenga
     * @throws SQLException no tener correctamente el modelo entidad relación o 
     * el nombre de los atributos en java
     */
    public static void putParametersInQuery(PreparedStatement ps, List<Field> fields, Object obj, Boolean forUpdate) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        for (Field field : fields) {
            String methodName = String.format("get%s", Model.capitalize(field.getName()));
            Method method = obj.getClass().getMethod(methodName);
            Object value = method.invoke(obj);
            ps.setObject(fields.indexOf(field)+1, value);
        }
        if(forUpdate){
            if(obj instanceof Model){
                Model m = (Model) obj;
                ps.setInt(fields.size()+1, m.getId());
            }
        }
    }

}
