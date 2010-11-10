package session;

import entities.Cliente;
import entities.ClienteHasServicio;
import entities.Debito;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.xml.ws.WebServiceRef;
import ws.CoreBancoWebService;
import ws.ManejadorServiciosWebService;


@Singleton
@Startup
public class TareasProgramadasSessionBean {
    @EJB
    private ClienteHasServicioFacade clienteHasServicioFacade;
    @EJB
    private DebitoFacade debitoFacade;
    @Resource
    TimerService timerService;
    private Date lastAutomaticTimeout;
    private Date lastProgrammaticTimeout;
    @WebServiceRef public ManejadorServiciosWebService manejadorServiciosWebService;
    @WebServiceRef public CoreBancoWebService coreWebService;


    
    public void setTimer(long intervalDuration) {
        System.out.println(
                "Seteando el tiempo " + intervalDuration
                + " millisegundos a partir de ahora.");

        Timer timer = timerService.createTimer(
                    intervalDuration,
                    "Creando timer");
    }

    @Timeout
    public void programmaticTimeout(Timer timer) {
        this.setLastProgrammaticTimeout(new Date());
        System.out.println("Ocurrio el evento.");
        realizaDebito();
    }

    @Schedule(second="*/7", minute = "*", hour = "*")
    public void reservarDinero() {
        Date currentExecutionTime = new Date();
        this.setLastAutomaticTimeout(currentExecutionTime);
        System.out.println("Evento reserva");

        reservarDineroImpl(this.clienteHasServicioFacade.getClientesHasServiciosADebitarHoy(),currentExecutionTime);

        System.out.println(new Date());
        setTimer(2000);
    }

    public void realizaDebito() {
        this.setLastAutomaticTimeout(new Date());
        System.out.println("Evento debito");
        System.out.println(new Date());
    }

    /**
     * @return the lastTimeout
     */
    public String getLastProgrammaticTimeout() {
        if (lastProgrammaticTimeout != null) {
            return lastProgrammaticTimeout.toString();
        } else {
            return "never";
        }
    }

    /**
     * @param lastTimeout the lastTimeout to set
     */
    public void setLastProgrammaticTimeout(Date lastTimeout) {
        this.lastProgrammaticTimeout = lastTimeout;
    }

    /**
     * @return the lastAutomaticTimeout
     */
    public String getLastAutomaticTimeout() {
        if (lastAutomaticTimeout != null) {
            return lastAutomaticTimeout.toString();
        } else {
            return "never";
        }
    }

    /**
     * @param lastAutomaticTimeout the lastAutomaticTimeout to set
     */
    public void setLastAutomaticTimeout(Date lastAutomaticTimeout) {
        this.lastAutomaticTimeout = lastAutomaticTimeout;
    }

    private void reservarDineroImpl(List<ClienteHasServicio> clientesHasServiciosADebitarHoy,Date fechaActual) {
        List<Integer> listaIds = new ArrayList<Integer>();
        Map<Integer, Cliente> mapaUsuarios = new HashMap<Integer, Cliente>();
        Map<Integer, Double> aPagarCliente = new HashMap<Integer, Double>();

        for (ClienteHasServicio clienteHasServicio : clientesHasServiciosADebitarHoy) {
            Double montoActualDeServicio = manejadorServiciosWebService.getSaldo(clienteHasServicio.getServicio().getId(), clienteHasServicio.getCliente().getId());
            Integer reservaId = coreWebService.reservar(clienteHasServicio.getCliente().getId(), montoActualDeServicio);
            Debito reservaDebito = new Debito(Integer.SIZE, clienteHasServicio.getCliente().getId(), clienteHasServicio.getServicio().getId());   
            reservaDebito.setConfirmado(Boolean.FALSE); 
            debitoFacade.create(reservaDebito);
        }
    }
}
