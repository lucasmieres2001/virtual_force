$(document).ready(function(){
    verificarToken()
    mainArranque()
})

function verificarToken(){
    const token = localStorage.getItem('token');
    const email = localStorage.getItem('email');
    if(!token || email){
    window.location.href = "inicio.html";
    }
    }

    const mainArranque = async ()=>{
        const query = await fetch('api/pedidoRetomado',{
            method: 'GET',
            headers:{
            'Accept': 'Application/json',
            'Content-Type': 'Application/json'
            },
        }) 
        const respuesta = await query.json();
    
        let comentarioCliente = "";
        //texto_original contiene el comentario del cliente;
       const texto_original = '<p>'+respuesta.encargos.comentario+'</p>';
        //Con la lógica del resto siempre se va a crear un <br> (o lo que vos quieras) cada cuantas letras quieras;
       for(let i = 0; i<texto_original.length;i++){
            comentarioCliente += texto_original[i]
            if(i > 0 && (i+1) % 130 === 0){
                comentarioCliente += '-<br>';
            }
       }
       let vistoRespuesta = respuesta.encargos.respuesta;
       let visto = "";
       if(vistoRespuesta != ""){visto = "No visto"}else{visto = "Visto"}
       let notNull = (respuesta.encargos.pago === null) ? "0" : respuesta.encargos.pago;
       const objetoMensaje = {id_encargo: respuesta.encargos.id_encargo};
        document.querySelector('.postergar').outerHTML = "<div onclick='enviarMensaje("+JSON.stringify(objetoMensaje)+")' class='postergar'><p>Enviar mensaje</p></div>"
        document.querySelector('.entregar').outerHTML = "<div onclick='entregarPedido("+respuesta.atendido.id_atendido+")' class='entregar'><p>Entregar</p></div>";
        //Aunque la clase empiece con mayuscula ("Atendido.class" en este caso) la iteracion siempre se debe hacer en minuscula (respuesta.atendido)
        document.querySelector('.info').innerHTML = "<h2>Datos del cliente</h2><p><b>Nombre:</b>"+respuesta.atendido.nombre+"</p><p><b>Apellido:</b>"+respuesta.atendido.apellido+"</p><p><b>email: </b>"+respuesta.atendido.email+"</p><p><b>Trabajo iniciado el:</b>"+respuesta.atendido.fecha_encargo+"</p><p><b>Encargo:</b>"+respuesta.encargos.encargo+"</p><p><b>Mensaje:</b>"+visto+"</p><p><b></b>Pago:</b>$"+notNull+"</p>";
        document.querySelector('.comentarios p').outerHTML = comentarioCliente;
    }
    
    async function entregarPedido(id){
        if(confirm('Si aceptas, el trabajo se dará por terminado y el cliente será notificado. Además este proyecto se eliminará de la lista del panel de control. ¿Estas seguro de finalizar este proyecto?')){
    const query = await fetch('api/trabajoTerminado/'+ id,{
        method: 'DELETE',
        headers: {
            'Accept': 'Application/json',
            'Content-Type': 'Application/json'
        },
    })
    alert("¡Trabajo finalizado!")
    window.location.href = "pedidosPendientes.html";
        }
    }

const enviarMensaje = async (mensaje) =>{
const query = await fetch('api/enviarMensajeAdmin',{
    method: 'POST',
    headers: {
        'Accept': 'Application/json',
        'Content-Type': 'Application/json',
        'Authorization': localStorage.token
    },
    body: JSON.stringify(mensaje)
})
window.location.href = "escribirMensaje.html";
}