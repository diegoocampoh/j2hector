/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author cursoj2ee
 */
@Entity
@Table(name = "cliente_has_servicio")
@NamedQueries({
    @NamedQuery(name = "ClienteHasServicio.findAll", query = "SELECT c FROM ClienteHasServicio c"),
    @NamedQuery(name = "ClienteHasServicio.findByClienteid", query = "SELECT c FROM ClienteHasServicio c WHERE c.clienteHasServicioPK.clienteid = :clienteid"),
    @NamedQuery(name = "ClienteHasServicio.findByServicioid", query = "SELECT c FROM ClienteHasServicio c WHERE c.clienteHasServicioPK.servicioid = :servicioid"),
    @NamedQuery(name = "ClienteHasServicio.findByMontoDebitar", query = "SELECT c FROM ClienteHasServicio c WHERE c.montoDebitar = :montoDebitar"),
    @NamedQuery(name = "ClienteHasServicio.findByFechaVencimiento", query = "SELECT c FROM ClienteHasServicio c WHERE c.fechaVencimiento = :fechaVencimiento")})
public class ClienteHasServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClienteHasServicioPK clienteHasServicioPK;
    @Column(name = "MontoDebitar")
    private Double montoDebitar;
    @Column(name = "FechaVencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @JoinColumn(name = "Servicio_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Servicio servicio;
    @JoinColumn(name = "Cliente_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteHasServicio")
    private Collection<Debito> debitoCollection;

    public ClienteHasServicio() {
         this.clienteHasServicioPK = new ClienteHasServicioPK();
    }

    public ClienteHasServicio(ClienteHasServicioPK clienteHasServicioPK) {
        this.clienteHasServicioPK = clienteHasServicioPK;
    }

    public ClienteHasServicio(int clienteid, int servicioid) {
        this.clienteHasServicioPK = new ClienteHasServicioPK(clienteid, servicioid);
    }

    public ClienteHasServicioPK getClienteHasServicioPK() {
        return clienteHasServicioPK;
    }

    public void setClienteHasServicioPK(ClienteHasServicioPK clienteHasServicioPK) {
        this.clienteHasServicioPK = clienteHasServicioPK;
    }

    public Double getMontoDebitar() {
        return montoDebitar;
    }

    public void setMontoDebitar(Double montoDebitar) {
        this.montoDebitar = montoDebitar;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
        this.clienteHasServicioPK.setServicioid(servicio.getId());
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        this.clienteHasServicioPK.setClienteid(cliente.getId());
    }

    public Collection<Debito> getDebitoCollection() {
        return debitoCollection;
    }

    public void setDebitoCollection(Collection<Debito> debitoCollection) {
        this.debitoCollection = debitoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clienteHasServicioPK != null ? clienteHasServicioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteHasServicio)) {
            return false;
        }
        ClienteHasServicio other = (ClienteHasServicio) object;
        if ((this.clienteHasServicioPK == null && other.clienteHasServicioPK != null) || (this.clienteHasServicioPK != null && !this.clienteHasServicioPK.equals(other.clienteHasServicioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ClienteHasServicio[clienteHasServicioPK=" + clienteHasServicioPK + "]";
    }

}
