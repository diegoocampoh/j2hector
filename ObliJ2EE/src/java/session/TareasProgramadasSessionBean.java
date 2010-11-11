package session;

import entities.Cliente;
import entities.ClienteHasServicio;
import entities.Debito;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    @WebServiceRef public ManejadorServiciosWebService manejadorServiciosWebServices;
    @WebServiceRef public CoreBancoWebService coreBancoWebService;


    
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

        this.setLastAutomaticTimeout(new Date());
        System.out.println("Evento reserva");

        reservarDineroImpl(this.clienteHasServicioFacade.getClientesHasServiciosADebitarHoy());

        System.out.println(new Date());
        setTimer(2000);
    }

    public void realizaDebito() {
        this.setLastAutomaticTimeout(new Date());

        List<Debito> debitosRealizar = debitoFacade.findByNoConfirmadoToday(lastAutomaticTimeout);
        boolean sePago;
        for(Debito debito : debitosRealizar)
        {
            //Realizar el pago contra el servicio
            sePago = manejadorServiciosWebServices.pagar(debito.getServicioid(), debito.getClienteid(), new Double(debito.getMontoDebitado()));

            //Realizar debito contra el banco con el numero de reserva
            if(sePago)
            {
                try {
                    //Confirmamos la reserva con numeroReservaActual
                    if (coreBancoWebService.confirmarReserva(debito.getId())) {
                        debito.setConfirmado(true);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(TareasProgramadasSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("No se pudo hacer la reserva");
                }
            }
        }

        System.out.println("Debitos realizados debito");
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

    private void reservarDineroImpl(List<ClienteHasServicio> clientesHasServiciosADebitarHoy) {

        for (int i = 0; i < clientesHasServiciosADebitarHoy.size(); i++) {
            ClienteHasServicio clienteHasServicio = clientesHasServiciosADebitarHoy.get(i);

            //Obtenemos monto a pagar por servicio
            Double montoActualDeServicio = manejadorServiciosWebServices.getSaldo(clienteHasServicio.getServicio().getId(), clienteHasServicio.getCliente().getId());

            //realizamos la reserva, si retorna -1 es porque no se puede hacer
            Integer reservaId = coreBancoWebService.reservar(clienteHasServicio.getCliente().getId(), montoActualDeServicio);
            if (reservaId == -1) break;

            //Creamos el debito y lo almacenamos
            Debito reservaDebito = new Debito(reservaId, clienteHasServicio.getCliente().getId(), clienteHasServicio.getServicio().getId());
            reservaDebito.setConfirmado(Boolean.FALSE);
            reservaDebito.setMontoDebitado(montoActualDeServicio.toString());

            //Actualizamos la fecha inicio del clienteHasServicio, para que se vuelva a ejecutar en un futuro
            Calendar cal = new GregorianCalendar();
            cal.setTime(clienteHasServicio.getFechaInicio());
            cal.add(Calendar.DAY_OF_MONTH, clienteHasServicio.getPeriodicidad().intValue());
            clienteHasServicio.setFechaInicio(cal.getTime());

            debitoFacade.create(reservaDebito);
            clienteHasServicioFacade.edit(clienteHasServicio);
        }
    }
}
