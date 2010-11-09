/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entity.Tipocliente;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cursoj2ee
 */
@Stateless
public class TipoclienteFacade extends AbstractFacade<Tipocliente> {
    @PersistenceContext(unitName = "BancoObligatorioPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoclienteFacade() {
        super(Tipocliente.class);
    }

}
