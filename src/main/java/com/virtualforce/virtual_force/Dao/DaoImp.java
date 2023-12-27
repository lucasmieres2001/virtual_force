package com.virtualforce.virtual_force.Dao;

import com.virtualforce.virtual_force.ClasesCompartidas.FullDates;
import com.virtualforce.virtual_force.ClasesCompartidas.FullServerDates;
import com.virtualforce.virtual_force.Modelo.Atendido;
import com.virtualforce.virtual_force.Modelo.admins;
import com.virtualforce.virtual_force.Modelo.encargos;
import com.virtualforce.virtual_force.Modelo.usuarios;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class DaoImp implements DaoCliente {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public void regVip(admins registro) {
        entityManager.merge(registro);

    }

    @Override
    public admins logVip(admins logVip) {
        String verificar = "FROM admins WHERE usuario = :usuario";
        List<admins> lista = entityManager.createQuery(verificar).
                setParameter("usuario",logVip.getUsuario()).
                getResultList();
        if(lista.isEmpty()){return null;}
            String PassHashed = lista.get(0).getContrasenia();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if(argon2.verify(PassHashed,logVip.getContrasenia())){
            return lista.get(0);
        }else {
            return null;
        }
    }

    @Override
    public List<usuarios> getNewUsers() {
        String buscar = "FROM usuarios WHERE id_encargo IS NOT NULL";
        return entityManager.createQuery(buscar).getResultList();

    }

    @Override
    public FullDates getUsuarioDatos(usuarios usuarioDatos, encargos usuarioEncargos) {
        String buscar = "FROM encargos WHERE id_encargo = " + usuarioDatos.getId_encargo();
        List<encargos> listaEncargo = entityManager.createQuery(buscar).getResultList();
        if(listaEncargo.isEmpty()){return null;}
        usuarioEncargos = listaEncargo.get(0);
        FullDates fullDates = new FullDates();
        fullDates.setUsuarios(usuarioDatos);
        fullDates.setEncargos(usuarioEncargos);
        return fullDates;
    }

    @Override
    public void tomarPedido(Long id, encargos respuesta,Atendido atendido) {
        //Agregamos una respuesta y la fecha que se tomó el pediodo en la base de datos;
        String guardarComentario = "FROM encargos WHERE id_encargo ="+id;
        List<encargos> listaEncargos = entityManager.createQuery(guardarComentario).getResultList();
        if(listaEncargos.isEmpty()){return;};
        listaEncargos.get(0).setRespuesta(respuesta.getRespuesta());
        listaEncargos.get(0).setFecha_encargo(respuesta.getFecha_encargo());
        //Pasamos un encargo a un atendido;
        String buscarUsuario = "FROM usuarios WHERE id_encargo ="+id;
        List<usuarios> listaUsuarios = entityManager.createQuery(buscarUsuario).getResultList();
        if(listaUsuarios.isEmpty()){return;};
        listaUsuarios.get(0);
        atendido.setId_encargo(listaUsuarios.get(0).getId_encargo());
        atendido.setNombre(listaUsuarios.get(0).getNombre());
        atendido.setApellido(listaUsuarios.get(0).getApellido());
        atendido.setEmail(listaUsuarios.get(0).getEmail());
        atendido.setTelefono(listaUsuarios.get(0).getTelefono());
        atendido.setContrasenia(listaUsuarios.get(0).getContrasenia());
        atendido.setFecha_encargo(listaEncargos.get(0).getFecha_encargo());
        atendido.setId_usuario(listaUsuarios.get(0).getId_usuario());
        entityManager.merge(atendido);
        //Eliminamos el encargo del usuario para que no aparezca como "nuevo encargo" pero a su vez pueda seguir iniciando sesion;
        usuarios eliminar = entityManager.find(usuarios.class,listaUsuarios.get(0).getId_usuario());
        eliminar.setId_encargo(null);
    }

    @Override
    public List<Atendido> getPedidosTomados() {
        String query ="FROM Atendido";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public FullServerDates getPedidoRetomado(Atendido UsuariosTomados, encargos EncargosTomados) {
        String buscar = "FROM encargos WHERE id_encargo = " + UsuariosTomados.getId_encargo();
        List<encargos> listaEncargo = entityManager.createQuery(buscar).getResultList();
        if(listaEncargo.isEmpty()){return null;}
        EncargosTomados = listaEncargo.get(0);
        FullServerDates fullServerDates = new FullServerDates();
        fullServerDates.setAtendido(UsuariosTomados);
        fullServerDates.setEncargos(EncargosTomados);
        return fullServerDates;
    }

    @Override
    public void setTerminarTrabajo(Long id) {
    String buscarAtendido = "FROM Atendido WHERE id_atendido = :id";
    List<Atendido> listaAtendido = entityManager.createQuery(buscarAtendido).
            setParameter("id",id).getResultList();
    Long id_engargo = listaAtendido.get(0).getId_encargo();
    //Ahora buscamos la lista "Encargo" para borrar toda la informacion perteneciente a ese ususario en cuestión;
        String buscarEncargo = "FROM encargos WHERE id_encargo = :idEncargo";
        List<encargos> listaEncargos = entityManager.createQuery(buscarEncargo).
                setParameter("idEncargo",id_engargo).getResultList();
        //Ahora que tenemos todos los datos, nos toca borrarlos;
        Atendido eliminarAtendido = entityManager.find(Atendido.class,listaAtendido.get(0).getId_atendido());
        entityManager.remove(eliminarAtendido);
        encargos eliminarEncargo = entityManager.find(encargos.class,listaEncargos.get(0).getId_encargo());
        entityManager.remove(eliminarEncargo);
    }

    @Override
    public String getPantallaPanel() {
        String buscar = "SELECT COUNT(id_atendido) FROM Atendido";
        Object query = entityManager.createQuery(buscar).getSingleResult();
        String TotalPendientes = query.toString();
        return TotalPendientes;
    }

    @Override
    public String gananciasTotales() {
        String buscar = "SELECT SUM(pago) FROM encargos";
        Object object = entityManager.createQuery(buscar).getSingleResult();
        if(object != null){
            return object.toString();
        }
        return "";
    }

    @Override
    public void setNormalRegister(usuarios registro) {
        entityManager.merge(registro);
    }

    @Override
    public usuarios getNormalLogin(usuarios login) {
        String buscarId = "FROM usuarios WHERE email = :email";
        List<usuarios> listaUsuarios = entityManager.createQuery(buscarId).
                setParameter("email",login.getEmail()).getResultList();
        if(listaUsuarios.isEmpty()){return null;}
        String hashPass = listaUsuarios.get(0).getContrasenia();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if(argon2.verify(hashPass,login.getContrasenia())){
        return listaUsuarios.get(0);
        }else {
            return null;
        }
    }

    @Override
    public void enviarEncargo(usuarios email, String tipo_imagen, String nombre_imagen, String comentario, String encargo,encargos fecha_encargo) {
        //Creamos un nuevo encargo en la base de datos;
        encargos nuevoEncargo = new encargos();
        nuevoEncargo.setComentario(nombre_imagen);// = comentario
        nuevoEncargo.setTipo_imagen(comentario);// = tipo_imagen
        nuevoEncargo.setEncargo(tipo_imagen);// = encargo
        nuevoEncargo.setFecha_encargo(fecha_encargo.getFecha_encargo());
        nuevoEncargo.setNombre_imagen(encargo); //= nombre_imagen
        entityManager.merge(nuevoEncargo);
        //Buscamos al usuario que solicitó el encargo;
        String buscarUsuario = "FROM usuarios WHERE email = :email";
        List<usuarios> listaDeUsuarios = entityManager.createQuery(buscarUsuario).
                                            setParameter("email",email.getEmail()).
                                                getResultList();
        listaDeUsuarios.get(0).setId_encargo(nuevoEncargo.getId_encargo());

    }
//EL PROBLEMA ESTÁ EN QUE DEBO BUSCAR EL ID_ENCARGO DE ATENDIDO EN LUGAR DE USUARIOS COMO ESTOY HACIENDO AHORA
    @Override
    public String checkNotificaciones(usuarios email) {
        String buscarUsuario = "FROM usuarios WHERE email = :email";
        List<usuarios> listaUsuarios = entityManager.createQuery(buscarUsuario).setParameter("email",email.getEmail()).getResultList();
        if(listaUsuarios.isEmpty()){return "";}
        String buscarAtentido = "FROM Atendido WHERE id_usuario = :id_usuario";
        List<Atendido> listaAtendido = entityManager.createQuery(buscarAtentido).setParameter("id_usuario",listaUsuarios.get(0).getId_usuario()).getResultList();
        if (listaAtendido.isEmpty()){return "";}
        String buscar = "SELECT respuesta FROM encargos WHERE id_encargo = :id_encargo";
        Object object = entityManager.createQuery(buscar).setParameter("id_encargo",listaAtendido.get(0).getId_encargo()).getSingleResult();
        if(object != null) {
            return object.toString();
        }else{
            return "";
        }
    }

    @Override
    public String verMensaje(usuarios email) {
        String buscarUsuario = "FROM usuarios WHERE email = :email";
        List<usuarios> listaUsuarios = entityManager.createQuery(buscarUsuario).setParameter("email",email.getEmail()).getResultList();
        if(listaUsuarios.isEmpty()){return "";}
        String buscarAtentido = "FROM Atendido WHERE id_usuario = :id_usuario";
        List<Atendido> listaAtendido = entityManager.createQuery(buscarAtentido).setParameter("id_usuario",listaUsuarios.get(0).getId_usuario()).getResultList();
        if (listaAtendido.isEmpty()){return "";}
        String buscar = "SELECT respuesta FROM encargos WHERE id_encargo = :id_encargo";
        Object object = entityManager.createQuery(buscar).setParameter("id_encargo",listaAtendido.get(0).getId_encargo()).getSingleResult();
        if(object != null) {
            return object.toString();
        }else{
            return "";
        }
    }

    @Override
    public String AdminEnviarMensaje(String respuestaString, encargos respuestaObjeto) {
        String buscarUsuario = "FROM encargos WHERE id_encargo = :id_encargo";
        List<encargos> listaEncargos = entityManager.createQuery(buscarUsuario).
                setParameter("id_encargo",respuestaObjeto.getId_encargo()).
                getResultList();
        if(listaEncargos.isEmpty()){return "";}
        listaEncargos.get(0).setRespuesta(respuestaString);
        return "Mensaje enviado de manera exitosa.";
    }

    @Override
    public void EliminarMensaje(usuarios email) {
        String buscarUsuario = "FROM usuarios WHERE email = :email";
        List<usuarios> listaUsuarios = entityManager.createQuery(buscarUsuario).setParameter("email",email.getEmail()).getResultList();
        if(listaUsuarios.isEmpty()){return;}
        String buscarAtentido = "FROM Atendido WHERE id_usuario = :id_usuario";
        List<Atendido> listaAtendido = entityManager.createQuery(buscarAtentido).setParameter("id_usuario",listaUsuarios.get(0).getId_usuario()).getResultList();
        if (listaAtendido.isEmpty()){return;}
        String buscar = "FROM encargos WHERE id_encargo = :id_encargo";
        List<encargos> listaEncargos = entityManager.createQuery(buscar).setParameter("id_encargo",listaAtendido.get(0).getId_encargo()).getResultList();
        if(listaEncargos.isEmpty()){return;}
        listaEncargos.get(0).setRespuesta("");
    }

    @Override
    public boolean verificarPedido(usuarios email) {
        String buscar = "FROM usuarios WHERE email = :email";
        List<usuarios> listaUsuarios = entityManager.createQuery(buscar).setParameter("email",email.getEmail()).getResultList();
        String buscarAtendido = "FROM Atendido WHERE id_usuario = :id_usuario";
        List<Atendido> listaAtendido = entityManager.createQuery(buscarAtendido).setParameter("id_usuario",listaUsuarios.get(0).getId_usuario()).getResultList();

        Long verUsuario = listaUsuarios.get(0).getId_encargo();
        //Long verAtendido = listaAtendido.get(0).getId_encargo();
        if(verUsuario != null || !listaAtendido.isEmpty()){
        return false;
        }else{
            return true;
        }

    }
}
