package ws;

import entities.ClienteHasServicio;
import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import session.ClienteHasServicioFacade;

@WebService()
public class ClienteServicioWebService {
    @EJB
    private ClienteHasServicioFacade ejbRef;

    @WebMethod(operationName = "create")
    @Oneway
    public void create(@WebParam(name = "entity")
    ClienteHasServicio entity) {
        ejbRef.create(entity);
    }

    @WebMethod(operationName = "edit")
    @Oneway
    public void edit(@WebParam(name = "entity")
    ClienteHasServicio entity) {
        ejbRef.edit(entity);
    }

    @WebMethod(operationName = "remove")
    @Oneway
    public void remove(@WebParam(name = "entity")
    ClienteHasServicio entity) {
        ejbRef.remove(entity);
    }    
}
