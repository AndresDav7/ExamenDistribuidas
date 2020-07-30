package com.reserva.managedbean;

import com.reserva.entidades.Reserva;
import com.reserva.session.ReservaFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;


@Named(value = "reservaManagedBean")
@ViewScoped
public class ReservaManagedBean implements Serializable, ReservaInterface<Reserva>  {

     //paso1
    @EJB
    private ReservaFacadeLocal reservaFacadeLocal;

//variable de listacargos
    private List<Reserva> ListaReserva;  
    
    private Reserva reserva;
    
    public ReservaManagedBean() {
    }
    
    //paso2
    @PostConstruct
    public void init() {
        //lista de los cargos que estan en la BDD
        ListaReserva = reservaFacadeLocal.findAll();
        
    }

    @Override
    public void nuevo() {
        reserva = new Reserva();
    }

    @Override
    public void grabar() {
        try {
            if (reserva.getNombre().equals("")) {
                System.out.println("Reserva Vacio");
                mostrarMensajeTry("NO INGRESO NOMBRE", FacesMessage.SEVERITY_ERROR);
            } else {
                if (reserva.getId()== null) {
                    reservaFacadeLocal.create(reserva);
                    reserva = null;
                } else {
                    reservaFacadeLocal.edit(reserva);
                    reserva = null;
                }
            }
            ListaReserva = reservaFacadeLocal.findAll();
            mostrarMensajeTry("INFORMACIÓN EXITOSA", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            mostrarMensajeTry("OCURRIO UN ERROR", FacesMessage.SEVERITY_ERROR);
        }
    }

    @Override
    public void seleccionar(Reserva t) {
        reserva = t;
    }

    @Override
    public void eliminar(Reserva c) {
        try {
            reservaFacadeLocal.remove(c);
            ListaReserva = reservaFacadeLocal.findAll();
            mostrarMensajeTry("ELIMIADO EXITOSAMENTE", FacesMessage.SEVERITY_INFO);

        } catch (Exception e) {
            mostrarMensajeTry("OCURRIO UN ERROR", FacesMessage.SEVERITY_ERROR);
        }
    }

    @Override
    public void cancelar() {
        System.out.println("CANCELAR");
        ListaReserva = reservaFacadeLocal.findAll();
        reserva = null;
    }

    public List<Reserva> getListaReserva() {
        return ListaReserva;
    }

    public void setListaReserva(List<Reserva> ListaReserva) {
        this.ListaReserva = ListaReserva;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
 
    
}
