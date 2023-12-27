package com.virtualforce.virtual_force.Controlador;


import com.virtualforce.virtual_force.ClasesCompartidas.FullDates;
import com.virtualforce.virtual_force.ClasesCompartidas.FullServerDates;
import com.virtualforce.virtual_force.Dao.DaoCliente;
import com.virtualforce.virtual_force.Modelo.Atendido;
import com.virtualforce.virtual_force.Modelo.admins;
import com.virtualforce.virtual_force.Modelo.encargos;
import com.virtualforce.virtual_force.Modelo.usuarios;
import com.virtualforce.virtual_force.Utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ControladorClientes {
    @Autowired
    private JWTUtil jwtUtil;

    private boolean validarToken (String token){
        String usuarioId = jwtUtil.getKey(token);
        return usuarioId != null;
    }

    private usuarios transferirDatos;
    private Atendido transferirDatosAtendidos;
    private String transferirString;
    private encargos transferirEncargos;
    @Autowired
    private DaoCliente daoCliente;
    @RequestMapping(value = "api/regVip", method = RequestMethod.POST)
    public boolean regVip(@RequestBody admins registro){
        try {
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            String hash = argon2.hash(1,1024,1,registro.getContrasenia());
            registro.setContrasenia(hash);
            daoCliente.regVip(registro);
            return true;
        }catch (Error e){
            return false;
        }
    }
    @RequestMapping(value = "api/logVip",method = RequestMethod.POST)
    public String logVip(@RequestBody admins login){
        try {
        admins TrueLogin = daoCliente.logVip(login);
        if(TrueLogin != null){
            String token = jwtUtil.create(String.valueOf(TrueLogin.getId_admins()),TrueLogin.getUsuario());
            return token;
        }
        }catch (Error e){
            return String.valueOf(e);
        }
        return "Error";
    }
    @RequestMapping(value = "api/pedidos",method = RequestMethod.GET)
    public List<usuarios> getNewUsers(@RequestHeader(value="Authorization") String token){
        if(!validarToken(token)){return null;}
        return daoCliente.getNewUsers();
    }

    @RequestMapping(value = "api/datos", method = RequestMethod.POST)
    public void usuarioDatos(@RequestBody usuarios usuarioDatos, @RequestHeader(value = "Authorization") String token){
        if(!validarToken(token)){return;}
        transferirDatos = usuarioDatos;
    }

    @RequestMapping(value = "api/datosrecibidos",method = RequestMethod.GET)
    public FullDates datosRecibidos(){
        encargos Encargos = new encargos();
        return daoCliente.getUsuarioDatos(transferirDatos,Encargos);

    }

    @RequestMapping(value = "api/enviarRespuesta/{id}", method = RequestMethod.POST)
    public void tomarPedido(@PathVariable Long id,@RequestBody encargos respuesta){
        Atendido atendido = new Atendido();
        daoCliente.tomarPedido(id,respuesta,atendido);
    }

    @RequestMapping(value = "api/pedidosPendientes",method = RequestMethod.GET)
    public List<Atendido> pedidosTomados(@RequestHeader(value = "Authorization")String token){
        if(!validarToken(token)){return null;}
        return daoCliente.getPedidosTomados();
    }

    @RequestMapping(value = "api/retomarPedido",method = RequestMethod.POST)
    public void enviarDatosRetomados(@RequestBody Atendido datosRetomados,@RequestHeader(value = "Authorization") String token){
        if(!validarToken(token)){return;}
        transferirDatosAtendidos = datosRetomados;
    }

    @RequestMapping(value = "api/pedidoRetomado",method = RequestMethod.GET)
    public FullServerDates pedidoRetomado(){
        encargos Encargos = new encargos();
        return daoCliente.getPedidoRetomado(transferirDatosAtendidos,Encargos);

    }

    @RequestMapping(value = "api/trabajoTerminado/{id}",method = RequestMethod.DELETE)
    public void trabajoTerminado(@PathVariable Long id){
        daoCliente.setTerminarTrabajo(id);
    }

    @RequestMapping(value = "api/pantallaPendientes",method = RequestMethod.GET)
    public String pantallaPanel(@RequestHeader(value = "Authorization" )String token){
        if(!validarToken(token)){return "";}
        return daoCliente.getPantallaPanel();

    }

    @RequestMapping(value = "api/gananciasTotales",method = RequestMethod.GET)
    public String gananciasTotales(@RequestHeader(value = "Authorization") String token){
        if(!validarToken(token)){return "";}
        if(daoCliente.gananciasTotales() != null && daoCliente.gananciasTotales() != ""){return daoCliente.gananciasTotales();}
        return "0";

    }

    @RequestMapping(value = "api/verImg",method = RequestMethod.POST)
    public void enviarImg(@RequestHeader(value = "Authorization") String token,@RequestBody String img ){
    if(!validarToken(token)){return;}
    transferirString = img;
    }

    @RequestMapping(value = "api/imgRecibida",method = RequestMethod.GET)
    public String imgRecibida(){
        return transferirString;
    }
    @RequestMapping(value = "api/normalRegister",method = RequestMethod.POST)
    public void normalRegister(@RequestBody usuarios registro){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,1024,1,registro.getContrasenia());
        registro.setContrasenia(hash);
        daoCliente.setNormalRegister(registro);
    }

    @RequestMapping(value = "api/normalLogin",method = RequestMethod.POST)
    public String normalLogin(@RequestBody usuarios login){
    usuarios loginCliente = daoCliente.getNormalLogin(login);
    if(loginCliente == null){return "";}
    return jwtUtil.create(loginCliente.getId_usuario().toString(),loginCliente.getEmail());

    }

    @RequestMapping(value = "api/subirImagen",method = RequestMethod.POST)
    public void subirImagen(@RequestHeader(value = "Authorization") String token,@RequestPart("img")MultipartFile img,@RequestPart("email") usuarios email,
                            @RequestPart("encargo") String encargo,@RequestPart("comentario") String comentario,
                            @RequestPart("nombre_imagen") String nombre_imagen,@RequestPart("tipo_imagen") String tipo_imagen,
                            @RequestPart("fecha_encargo") encargos fecha_encargo){
    if(!validarToken(token)){return;}

    try {
        String rutaCompleta = "D:/1Intellij_Proyects/virtual_force/src/main/resources/static/img/" + nombre_imagen;
        //Asignamos la ruta que queremos guardar nuestras imagenes
        Path ruta = Paths.get(rutaCompleta);
        //Copiamos el archivo Byte[][] que recibimos y lo direccionamos a la ruta anteriormente pactada
        Files.copy(img.getInputStream(),ruta, StandardCopyOption.REPLACE_EXISTING);
        //Enviamos los datos a la entidad encargo
        daoCliente.enviarEncargo(email,encargo,comentario,tipo_imagen,nombre_imagen,fecha_encargo);
    }catch (IOException e){return;}
    }
    @RequestMapping(value = "api/notificacion",method = RequestMethod.POST)
    public String checkNotificaciones(@RequestHeader(value = "Authorization") String token,@RequestBody usuarios Email){
        if(!validarToken(token)){return "";}
        return daoCliente.checkNotificaciones(Email);
    }

    @RequestMapping(value = "api/verMensaje",method = RequestMethod.POST)
    public String verMensaje(@RequestHeader(value = "Authorization")String token,@RequestBody usuarios email){
        if(!validarToken(token)){return "";}
        String respuesta = daoCliente.verMensaje(email);
        if(respuesta != "" && respuesta != null){
        return respuesta;
        }else{
            return "";
        }
    }
    @RequestMapping(value = "api/enviarMensajeAdmin",method = RequestMethod.POST)
    public void enviarMensaje(@RequestHeader(value = "Authorization") String token,@RequestBody encargos id_encargo){
    if(!validarToken(token)){return;}
    transferirEncargos = id_encargo;
    }

    @RequestMapping(value = "api/adminMensajeEnviado",method = RequestMethod.POST)
    public String AdminMensajeEnviado(@RequestHeader(value = "Authorization") String token,@RequestBody String respuesta){
    if(!validarToken(token)){return "";}
    String respuestaDao = daoCliente.AdminEnviarMensaje(respuesta,transferirEncargos);
    if(respuestaDao == "Mensaje enviado de manera exitosa."){return respuestaDao;}else {return "";}
    }
    @RequestMapping(value = "api/eliminarMensaje",method = RequestMethod.POST)
    public void eliminarMensaje(@RequestHeader(value = "Authorization") String token,@RequestBody usuarios email){
        if(!validarToken(token)){return;}
        daoCliente.EliminarMensaje(email);
    }
    @RequestMapping(value = "api/verificarPedido",method = RequestMethod.POST)
    public boolean verificarPedido(@RequestHeader(value = "Authorization") String token,@RequestBody usuarios email){
        if(!validarToken(token)){return false;}
        return daoCliente.verificarPedido(email);
    }
}






