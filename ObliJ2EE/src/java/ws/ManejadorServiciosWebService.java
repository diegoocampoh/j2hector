package ws;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ejb.Stateless;


@WebService()
@Stateless()
public class ManejadorServiciosWebService {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getSaldos")
    public java.util.Map<Integer ,Double> getSaldos(@WebParam(name = "idServicio")
    Integer idServicio, @WebParam(name = "usuarios")
    List<Integer> usuarios) {
        HashMap<Integer,Double> toReturn = new HashMap<Integer, Double>();
        for (Integer usuarioId : usuarios) {
            toReturn.put(usuarioId,calcularGasto(mapUserId(usuarioId)));
        }
        return toReturn;
    }

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

  


}
