package mx.uach.dao.models;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import mx.uach.dao.DataBaseUtils;
import mx.uach.dao.models.genericos.Model;

/**
 * Modelo utilizado para las pruebas que simulan un actor de pelicula.
 * 
 * @author Erik David Zubia Hernández
 * @version 1.0
 * @since 09/Octubre/2016
 */
public class Actor extends Model implements DataBaseUtils{

    private final String TABLA = "actores";
    
    private String nombre;
    private String apellido;


    /**
     * Constructor vacío.
     */
    public Actor() {
    }
    
    /**
     * Constructor con los parámetros necesarios para crear un actor.
     * 
     * @param nombre {@code String} nombre del actor
     * @param apellido {@code String} apellido del actor
     */
    public Actor(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }
    
    /**
     * Retorna el nombre del actor.
     * 
     * @return {@code String} con el nombre del actor
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna un nombre al actor.
     * 
     * @param nombre {@code String} con el nuevo nombre del actor
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna el apellido del actor.
     * 
     * @return {@code String} con el apellido del actor
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Asigna un apellido al actor.
     * 
     * @param nombre {@code String} con el nuevo apellido del actor
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public String getTableName() {
        return TABLA;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Field> getFieldsToQuery() {
        List<Field> fields = new ArrayList<>();
        fields.addAll(
                Arrays.asList(Model.class.getDeclaredFields()).stream()
                .filter(field -> field.getModifiers() == Modifier.PRIVATE)
                .collect(Collectors.toList())
        );
        fields.addAll(getFieldsToQueryNoId());
        return fields;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<Field> getFieldsToQueryNoId() {
        List<Field> fields = new ArrayList<>();
        fields.addAll(
                Arrays.asList(Actor.class.getDeclaredFields()).stream()
                .filter(field -> field.getModifiers() == Modifier.PRIVATE)
                .collect(Collectors.toList())
        );
        return fields;
    }
    
}
