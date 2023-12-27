package com.virtualforce.virtual_force.Dao;

import com.virtualforce.virtual_force.ClasesCompartidas.FullDates;
import com.virtualforce.virtual_force.ClasesCompartidas.FullServerDates;
import com.virtualforce.virtual_force.Modelo.Atendido;
import com.virtualforce.virtual_force.Modelo.admins;
import com.virtualforce.virtual_force.Modelo.encargos;
import com.virtualforce.virtual_force.Modelo.usuarios;

import java.util.List;

public interface DaoCliente {
    public void regVip(admins registro);

    public admins logVip(admins logVip);

    public List<usuarios> getNewUsers();

    public FullDates getUsuarioDatos(usuarios usuarioDatos,encargos usuarioEncargos);

    public void tomarPedido(Long id, encargos respuesta, Atendido listaAtendido);
    public List<Atendido> getPedidosTomados();

    public FullServerDates getPedidoRetomado(Atendido UsuariosTomados, encargos EncargosTomados);
    public void setTerminarTrabajo(Long id);
    public String getPantallaPanel();
    public String gananciasTotales();
    public void setNormalRegister(usuarios registro);
    public usuarios getNormalLogin(usuarios login);
    public void enviarEncargo(usuarios email,String encargo,String nombre_imagen,String comentario,String tipo_imagen,encargos fecha_encargo);
    public String checkNotificaciones(usuarios email);
    public String verMensaje(usuarios email);
    public String AdminEnviarMensaje(String respuestaString, encargos respuestaObjeto);
    public void EliminarMensaje(usuarios email);
    public boolean verificarPedido(usuarios email);
}
