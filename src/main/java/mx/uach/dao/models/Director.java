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
 * Modelo para mappear un modelo para las pruebas.
 *
 * @author Erik David Zubia Hernández
 * @version 1.0
 * @since 09/Octubre/2016
 */
public class Director extends Model implements DataBaseUtils{

    public static final String TABLA = "directores";
    private String nombre;

    /**
     * Constructor vacío.
     */
    public Director() {
    }

    /**
     * Constructor con los párametros necesarios para llenar
     * a un Director.
     * 
     * @param nombre {@code String} nombre del director
     */
    public Director(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Constructor para crear un director con su identificador.
     * 
     * @param id {@code Integer} identificador único de un director
     * @param nombre {@code String} nombre del director
     */
    public Director(Integer id, String nombre) {
        this(nombre);
        this.setId(id);
    }

    /**
     * Retorna el nombre del director.
     * 
     * @return {@code String} nombre del director
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna un nombre al director.
     * 
     * @param nombre {@code String} con el nuevo nombre del director
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTableName() {
        return this.TABLA;
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public List<Field> getFieldsToQueryNoId() {
        List<Field> fields = new ArrayList<>();
        fields.addAll(
                Arrays.asList(Director.class.getDeclaredFields()).stream()
                .filter(field -> field.getModifiers() == Modifier.PRIVATE)
                .collect(Collectors.toList())
        );
        return fields;
    }

}

