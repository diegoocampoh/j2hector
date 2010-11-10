package session;

import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;


@Singleton
@Startup
public class TareasProgramadasSessionBean {
    @EJB
    private ClienteHasServicioFacade clienteHasServicioFacade;
    @Resource
    TimerService timerService;
    private Date lastAutomaticTimeout;
    private Date lastProgrammaticTimeout;

    
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
}
