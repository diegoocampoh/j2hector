/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author cursoj2ee
 */
@Entity
@Table(name = "debito")
@NamedQueries({
    @NamedQuery(name = "Debito.findAll", query = "SELECT d FROM Debito d"),
    @NamedQuery(name = "Debito.findById", query = "SELECT d FROM Debito d WHERE d.id = :id"),
    @NamedQuery(name = "Debito.findByFechaEjecucion", query = "SELECT d FROM Debito d WHERE d.fechaEjecucion = :fechaEjecucion"),
    @NamedQuery(name = "Debito.findByMontoDebitado", query = "SELECT d FROM Debito d WHERE d.montoDebitado = :montoDebitado"),
    @NamedQuery(name = "Debito.findByClienteid", query = "SELECT d FROM Debito d WHERE d.clienteid = :clienteid"),
    @NamedQuery(name = "Debito.findByServicioid", query = "SELECT d FROM Debito d WHERE d.servicioid = :servicioid"),
    @NamedQuery(name = "Debito.findByConfirmado", query = "SELECT d FROM Debito d WHERE d.confirmado = :confirmado"),
    @NamedQuery(name = "Debito.findByNoConfirmadoToday", query = "SELECT d FROM Debito d WHERE d.confirmado = :confirmado AND d.fechaEjecucion = :fechaEjecucion"),
    @NamedQuery(name = "Debito.findByResultado", query = "SELECT d FROM Debito d WHERE d.resultado = :resultado")})
public class Debito implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "FechaEjecucion")
    @Temporal(TemporalType.DATE)
    private Date fechaEjecucion;
    @Column(name = "MontoDebitado")
    private String montoDebitado;
    @Basic(optional = false)
    @Column(name = "Cliente_id")
    private int clienteid;
    @Basic(optional = false)
    @Column(name = "Servicio_id")
    private int servicioid;
    @Column(name = "Confirmado")
    private Boolean confirmado;
    @Column(name = "Resultado")
    private String resultado;
    @JoinColumns({
        @JoinColumn(name = "Cliente_has_Servicio_Cliente_id", referencedColumnName = "Cliente_id"),
        @JoinColumn(name = "Cliente_has_Servicio_Servicio_id", referencedColumnName = "Servicio_id")})
    @ManyToOne(optional = false)
    private ClienteHasServicio clienteHasServicio;

    public Debito() {
    }

    public Debito(Integer id) {
        this.id = id;
    }

    public Debito(Integer id, int clienteid, int servicioid) {
        this.id = id;
        this.clienteid = clienteid;
        this.servicioid = servicioid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public String getMontoDebitado() {
        return montoDebitado;
    }

    public void setMontoDebitado(String montoDebitado) {
        this.montoDebitado = montoDebitado;
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

    public Boolean getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(Boolean confirmado) {
        this.confirmado = confirmado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public ClienteHasServicio getClienteHasServicio() {
        return clienteHasServicio;
    }

    public void setClienteHasServicio(ClienteHasServicio clienteHasServicio) {
        this.clienteHasServicio = clienteHasServicio;
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
        if (!(object instanceof Debito)) {
            return false;
        }
        Debito other = (Debito) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Debito[id=" + id + "]";
    }

}
