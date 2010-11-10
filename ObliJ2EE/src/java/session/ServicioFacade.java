/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entities.Servicio;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cursoj2ee
 */
@Stateless
public class ServicioFacade extends AbstractFacade<Servicio> {
    @PersistenceContext(unitName = "ObliJ2EEPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServicioFacade() {
        super(Servicio.class);
    }

}
