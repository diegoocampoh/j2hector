/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws;

import entities.Cliente;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ejb.Stateless;
import session.ClienteFacade;

/**
 *
 * @author cursoj2ee
 */
@WebService()
@Stateless()
public class CoreBancoWebService {
    @EJB
    private ClienteFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

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

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getSaldoById")
    public Double getSaldoById(@WebParam(name = "id")
    Integer id) throws Exception {
       Cliente cliente =  this.ejbRef.findById(id);
       if(cliente!=null) return cliente.getSaldoCuenta();
       return null;
    }

    
}
