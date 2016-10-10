package mx.uach.dao.jdbc;

import java.util.List;
import mx.uach.dao.CRUD;
import mx.uach.dao.Dao;
import mx.uach.dao.models.Actor;
import mx.uach.dao.models.Director;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author ezubia
 */
public class DaoJdbcJUnitTest {
    
    
//    @Test
//    public void testGetById() {
//        VideoDao dao = new VideoDaoJDBC();
//        Director dir = (Director) dao.getDataById(2, Director.class);
//        assertTrue(dir.getNombre().equals("MAMA"));
//    }
    
    @Test
    public void testDAOModel1(){
        Dao dao = new DaoJDBC();
        Director dir = new Director("Prueba");
        dao.dataProcces(dir, CRUD.CREATE);
        List<Director> dirs = (List) dao.getDataByCriteria("nombre like 'Prueba'", Director.class);
        assertTrue(dirs.size() != 0);
        assertTrue(dirs.get(0).getNombre().equals("Prueba"));
        
        Director dir2 = dirs.get(0);
        dir = (Director) dao.getDataById(dir2.getId(), Director.class);
        assertTrue(dir2.getNombre().equals(dir.getNombre()));
        
        dir.setNombre("Prueba2");
        dao.dataProcces(dir, CRUD.UPDATE);
        dir2 = (Director) dao.getDataById(dir.getId(), Director.class);
        assertTrue(dir2.getNombre().equals("Prueba2"));
        
        dao.dataProcces(dir, CRUD.DELETE);
        dirs = (List) dao.getDataByCriteria("nombre like 'Prueba2'", Director.class);
        assertTrue(dirs.size() == 0);
        
    }
    
    @Test
    public void testDAOModel2(){
        Dao dao = new DaoJDBC();
        Actor dir = new Actor("Super", "Prueba");
        dao.dataProcces(dir, CRUD.CREATE);
        List<Actor> actores = (List) dao.getDataByCriteria("nombre like 'Super'", Actor.class);
        assertTrue(actores.size() != 0);
        assertTrue(actores.get(0).getNombre().equals("Super"));
        
        Actor actor2 = actores.get(0);
        dir = (Actor) dao.getDataById(actor2.getId(), Actor.class);
        assertTrue(actor2.getNombre().equals(dir.getNombre()));
        
        dir.setNombre("Prueba2");
        dao.dataProcces(dir, CRUD.UPDATE);
        actor2 = (Actor) dao.getDataById(dir.getId(), Actor.class);
        assertTrue(actor2.getNombre().equals("Prueba2"));
        
        dao.dataProcces(dir, CRUD.DELETE);
        actores = (List) dao.getDataByCriteria("nombre like 'Prueba2'", Actor.class);
        assertTrue(actores.size() == 0);
        
    }
    
//    @Test
//    public void testGetByCriterio() {
//        VideoDao dao = new VideoDaoJDBC();
//        List<Director> dirs = (List) dao.getDataByCriteria("nombre like 'MAMA'", Director.class);
//        assertTrue(dirs.get(0).getNombre().equals("MAMA"));
//    }
    
//    @Test
//    public void testCreate() {
//        VideoDao dao = new VideoDaoJDBC();
//        Director dir = new Director("Erik");
//        dao.dataProcces(dir, CRUD.CREATE);
//        List<Director> dirs = (List) dao.getDataByCriteria("nombre like 'Erik'", Director.class);
//        assertTrue(dirs.size() != 0);
//    }
}
