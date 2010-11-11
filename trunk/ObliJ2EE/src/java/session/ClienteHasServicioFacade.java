/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import collection.util.JsfUtil;
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
        return getClientesHasServiciosADebitar(JsfUtil.fechaInicioHoy(),JsfUtil.fechaFinHoy());
    }

    public List<ClienteHasServicio> getClientesHasServiciosADebitar(Date fechaInicial,Date fechaFinal){
        Query nq = em.createNamedQuery("ClienteHasServicio.findByFechaHoy");
        nq.setParameter("fechaInicio", fechaInicial);
        nq.setParameter("fechaFin", fechaFinal);
        return nq.getResultList();
    }

}
