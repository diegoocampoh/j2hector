/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entities.Cliente;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cursoj2ee
 */
@Stateless
public class ClienteFacade extends AbstractFacade<Cliente> {
    @PersistenceContext(unitName = "ObliJ2EEPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }

     public Cliente findById(Integer id) {
        Query nq = em.createNamedQuery("Cliente.findById");
        nq.setParameter("id", id);
        return (Cliente)nq.getResultList().get(0);
    }

}
