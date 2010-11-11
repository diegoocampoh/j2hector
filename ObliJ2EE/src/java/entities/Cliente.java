/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author cursoj2ee
 */
@Entity
@Table(name = "cliente")
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findById", query = "SELECT c FROM Cliente c WHERE c.id = :id"),
    @NamedQuery(name = "Cliente.findBySaldoCuenta", query = "SELECT c FROM Cliente c WHERE c.saldoCuenta = :saldoCuenta"),
    @NamedQuery(name = "Cliente.findBySaldoReservado", query = "SELECT c FROM Cliente c WHERE c.saldoReservado = :saldoReservado")})
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "SaldoCuenta")
    private Double saldoCuenta;
    @Column(name = "SaldoReservado")
    private Double saldoReservado;
    @JoinColumn(name = "TipoCliente_idTipoCliente", referencedColumnName = "idTipoCliente")
    @ManyToOne
    private Tipocliente tipocliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private Collection<ClienteHasServicio> clienteHasServicioCollection;

    public Cliente() {
    }

    public Cliente(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSaldoCuenta() {
        return saldoCuenta;
    }

    public void setSaldoCuenta(Double saldoCuenta) {
        this.saldoCuenta = saldoCuenta;
    }

    public Double getSaldoReservado() {
        return saldoReservado;
    }

    public void setSaldoReservado(Double saldoReservado) {
        this.saldoReservado = saldoReservado;
    }

    public Tipocliente getTipocliente() {
        return tipocliente;
    }

    public void setTipocliente(Tipocliente tipocliente) {
        this.tipocliente = tipocliente;
    }

    public Collection<ClienteHasServicio> getClienteHasServicioCollection() {
        return clienteHasServicioCollection;
    }

    public void setClienteHasServicioCollection(Collection<ClienteHasServicio> clienteHasServicioCollection) {
        this.clienteHasServicioCollection = clienteHasServicioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ID Cliente:" + id + ", Tipo cliente:" + tipocliente;
    }

}
