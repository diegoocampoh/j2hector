/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entities.ClienteHasServicio;
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
public class ClienteHasServicioFacade extends AbstractFacade<ClienteHasServicio> {
    @PersistenceContext(unitName = "ObliJ2EEPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteHasServicioFacade() {
        super(ClienteHasServicio.class);
    }

    public List<ClienteHasServicio> getClientesHasServiciosADebitarHoy(){
        return getClientesHasServiciosADebitar(new Date());
    }

    public List<ClienteHasServicio> getClientesHasServiciosADebitar(Date date){
        Query nq = em.createNamedQuery("ClienteHasServicio.findByFechaInicio");
        nq.setParameter("fechaInicio", date);
        return nq.getResultList();
    }

}
