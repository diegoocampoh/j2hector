package ws;

public class DatosReserva {

    private Boolean confirmado;
    private Double montoReservado;

    public Boolean getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(Boolean confirmado) {
        this.confirmado = confirmado;
    }

    public Double getMontoReservado() {
        return montoReservado;
    }

    public void setMontoReservado(Double montoReservado) {
        this.montoReservado = montoReservado;
    }

    public DatosReserva(Boolean confirmado, Double montoReservado) {
        this.confirmado = confirmado;
        this.montoReservado = montoReservado; 
    }
}
