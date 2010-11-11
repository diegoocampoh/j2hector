/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws;

import entities.Cliente;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import session.ClienteFacade;

/**
 *
 * @author cursoj2ee
 */
@WebService()
public class CoreBancoWebService {
    @EJB
    private ClienteFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    HashMap<Integer, Double> saldosClientes = new HashMap<Integer,Double>();
    HashMap<Integer, Boolean> resrvasConfirmadas = new HashMap<Integer,Boolean>();

    public HashMap<Integer, Double> getSaldosClientes() {
        saldosClientes.put(1,50002.0);
        saldosClientes.put(2,2736.0);
        saldosClientes.put(3,283782.0);
        saldosClientes.put(4,92840.0);
        saldosClientes.put(5,762619.0);
        saldosClientes.put(6,0029378.0);

        return saldosClientes;
    }



    @WebMethod(operationName = "create")
    @Oneway
    public void create(@WebParam(name = "entity")
    Cliente entity) {
        ejbRef.create(entity);
    }

    @WebMethod(operationName = "edit")
    @Oneway
    public void edit(@WebParam(name = "entity")
    Cliente entity) {
        ejbRef.edit(entity);
    }

    @WebMethod(operationName = "remove")
    @Oneway
    public void remove(@WebParam(name = "entity")
    Cliente entity) {
        ejbRef.remove(entity);
    }

    @WebMethod(operationName = "find")
    public Cliente find(@WebParam(name = "id")
    Object id) {
        return ejbRef.find(id);
    }

    @WebMethod(operationName = "findAll")
    public List<Cliente> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<Cliente> findRange(@WebParam(name = "range")
    int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int count() {
        return ejbRef.count();
    }


    public Double getSaldoById(Integer id) throws Exception {
        Random rand = new Random();
        int min = 300;
        int max = 1000;
        Integer num = rand.nextInt(max-min+1)+min;
        return new Double(num.doubleValue());
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "reservar")
    public Integer reservar(@WebParam(name = "usuarioId")
    Integer usuarioId, @WebParam(name = "monto")
    Double monto) {
        Double montoDisponible = getSaldosClientes().get(usuarioId);
        if ((montoDisponible)!= null & montoDisponible>monto){
            montoDisponible -= monto;
            getSaldosClientes().put(usuarioId,montoDisponible);
            Long l = new Date().getTime();
            Integer numeroReserva = (l.intValue());
            this.resrvasConfirmadas.put(numeroReserva, Boolean.FALSE);
            return numeroReserva;
        }
        return -1;
    }

     /**
     * Web service operation
     */
    @WebMethod(operationName = "confirmarRerva")
    public Boolean confirmarReserva(@WebParam(name = "id")
    Integer id) throws Exception {
        return true;
    }


}
