package session;

import entities.ClienteHasServicio;
import entities.Debito;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
import ws.ServiciosManagerWebService;



@Singleton
@Startup
public class TareasProgramadasSessionBean {

    public TareasProgramadasSessionBean() {
    }
    



    @EJB
    private ClienteHasServicioFacade clienteHasServicioFacade;
    @EJB
    private DebitoFacade debitoFacade;
    @Resource
    TimerService timerService;
    private Date lastAutomaticTimeout;
    private Date lastProgrammaticTimeout;


    CoreBancoWebService coreBanco = new CoreBancoWebService();
    ServiciosManagerWebService serviciosManager = new ServiciosManagerWebService();

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
            sePago = pagar(debito.getServicioid(), debito.getClienteid(), new Double(debito.getMontoDebitado()));

            //Realizar debito contra el banco con el numero de reserva
            if(sePago)
            {
                try {
                    //Confirmamos la reserva con numeroReservaActual
                    if (confirmarRerva(debito.getId())) {
                        debito.setConfirmado(true);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(TareasProgramadasSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("No se pudo hacer la reserva para cliente " + debito.getClienteid() + " en el servicio "
                            + debito.getClienteHasServicio().getServicio().getNombre());
                }
            }else
            {
                 coreBancoWebService.liberarReserva(debito.getId());
            }
        }
        System.out.println("Proceso de debitos finalizado");
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
            Double montoActualDeServicio = getSaldo(clienteHasServicio.getServicio().getId(), clienteHasServicio.getCliente().getId());

            //realizamos la reserva, si retorna -1 es porque no se puede hacer
            Integer reservaId = reservar(clienteHasServicio.getCliente().getId(), montoActualDeServicio);
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




    /*
     * WEB SERVICE OPERATIONS INVOCATIONS
     */


    private Integer reservar(java.lang.Integer usuarioId, java.lang.Double monto) {
        return coreBanco.reservar(usuarioId, monto);
    }

    public Boolean confirmarRerva(java.lang.Integer id){
        try {
            return coreBanco.confirmarReserva(id);
        } catch (Exception ex) {
            Logger.getLogger(TareasProgramadasSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Boolean.FALSE;
    }
    
    private Double getSaldo(java.lang.Integer idServicio, java.lang.Integer idUsuario) {
        return serviciosManager.getSaldo(idServicio, idUsuario);
    }

    private Boolean pagar(java.lang.Integer servicioID, java.lang.Integer clienteID, java.lang.Double monto) {        
        return serviciosManager.pagar(servicioID, clienteID, monto);
    }

}
