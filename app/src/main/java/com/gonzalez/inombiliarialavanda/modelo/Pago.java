package com.gonzalez.inombiliarialavanda.modelo;

import java.io.Serializable;
import java.util.Date;

public class Pago implements Serializable {

    private int idPago;
    private int numPago;
    private Contrato contrato;
    private double importe;
    private String fechaPago;

    public Pago() {}

    public Pago(int idPago, int numPago, Contrato contrato, double importe, String fechaPago) {
        this.idPago = idPago;
        this.numPago = numPago;
        this.contrato = contrato;
        this.importe = importe;
        this.fechaPago = fechaPago;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getNumPago() {
        return numPago;
    }

    public void setNumPago(int numPago) {
        this.numPago = numPago;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }
}
