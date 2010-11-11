/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entities.Debito;
import java.util.Date;
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
public class DebitoFacade extends AbstractFacade<Debito> {
    @PersistenceContext(unitName = "ObliJ2EEPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public DebitoFacade() {
        super(Debito.class);
    }

     public List<Debito> findByNoConfirmadoToday(Date fecha)
    {
        Query nq = em.createNamedQuery("Debito.findByNoConfirmadoToday");
        nq.setParameter("fechaEjecucion", fecha);
        nq.setParameter("confirmado", false);
        return nq.getResultList();
    }

}
