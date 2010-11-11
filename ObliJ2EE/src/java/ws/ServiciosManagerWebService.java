/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws;

import java.util.Random;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import session.ServicioFacade;


@WebService()
public class ServiciosManagerWebService {
    @EJB
    private ServicioFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")


    /**
     * Web service operation
     */
    @WebMethod(operationName = "getSaldo")
    public Double getSaldo(@WebParam(name = "idServicio")
    Integer idServicio, @WebParam(name = "idUsuario")
    Integer usuarioId) {
        return calcularGasto(mapUserId(usuarioId));

    }

    private Double calcularGasto(Integer usuarioId) {
        Random rand = new Random();
        int min = 300;
        int max = 1000;
        Integer num = rand.nextInt(max-min+1)+min;
        return new Double(num.doubleValue());
    }

    /*
     * Mapeo del id del usuario manejado internamente y externamente, dependiente del servicio.
     */
    private Integer mapUserId(Integer userId){
        return userId;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "pagar")
    public Boolean pagar(@WebParam(name = "servicioID")
    Integer servicioID, @WebParam(name = "clienteID")
    Integer clienteID, @WebParam(name = "monto")
    Double monto) {
        Random rand = new Random();
        int min = 1;
        int max = 1000;
        Integer num = rand.nextInt(max-min+1)+min;
        return num<900;
    }
}
