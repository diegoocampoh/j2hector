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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author cursoj2ee
 */
@Entity
@Table(name = "servicio")
@NamedQueries({
    @NamedQuery(name = "Servicio.findAll", query = "SELECT s FROM Servicio s"),
    @NamedQuery(name = "Servicio.findById", query = "SELECT s FROM Servicio s WHERE s.id = :id"),
    @NamedQuery(name = "Servicio.findByNombre", query = "SELECT s FROM Servicio s WHERE s.nombre = :nombre")})
public class Servicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "Nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servicio")
    private Collection<ClienteHasServicio> clienteHasServicioCollection;

    public Servicio() {
    }

    public Servicio(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        if (!(object instanceof Servicio)) {
            return false;
        }
        Servicio other = (Servicio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Nomnre Servicio" + nombre + ", ID:" + id;
    }

}
