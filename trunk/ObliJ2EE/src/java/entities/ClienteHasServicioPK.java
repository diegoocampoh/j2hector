/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author cursoj2ee
 */
@Embeddable
public class ClienteHasServicioPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Cliente_id")
    private int clienteid;
    @Basic(optional = false)
    @Column(name = "Servicio_id")
    private int servicioid;

    public ClienteHasServicioPK() {
    }

    public ClienteHasServicioPK(int clienteid, int servicioid) {
        this.clienteid = clienteid;
        this.servicioid = servicioid;
    }

    public int getClienteid() {
        return clienteid;
    }

    public void setClienteid(int clienteid) {
        this.clienteid = clienteid;
    }

    public int getServicioid() {
        return servicioid;
    }

    public void setServicioid(int servicioid) {
        this.servicioid = servicioid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) clienteid;
        hash += (int) servicioid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteHasServicioPK)) {
            return false;
        }
        ClienteHasServicioPK other = (ClienteHasServicioPK) object;
        if (this.clienteid != other.clienteid) {
            return false;
        }
        if (this.servicioid != other.servicioid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ClienteHasServicioPK[clienteid=" + clienteid + ", servicioid=" + servicioid + "]";
    }

}
