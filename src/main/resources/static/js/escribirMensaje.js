$(document).ready(function(){
   // verificarToken();
})

function verificarToken(){
    const token = localStorage.getItem('token');
    const email = localStorage.getItem('email');
    if(!token || email){
    window.location.href = "inicio.html";
    }
    }


function play(event) {
    if (event.key === 'Enter') {   
        mensajeEnviado();
    }
}
//EL ERROR ES QUE QUEREMOS USAR EMAIL COMO IDENTIFICADOR (QUE ESTÁ BIEN PENSADO) SIN EMBARGO, ESTAMOS COMO ADMINS (LOCALSTORAGE NO TIENE EMAIL)
const mensajeEnviado = async()=>{
   const respuesta = document.querySelector('#texto').value
    const query = await fetch('api/adminMensajeEnviado',{
        method: 'POST',
        headers: {
            'Accept': 'Application/json',
            'Content-Type': 'Application/json',
            'Authorization': localStorage.token
        },
        body: JSON.stringify(respuesta)
    })
    const respuestaFetch = await query.text();
    if(respuestaFetch == "Mensaje enviado de manera exitosa."){
        alert(respuestaFetch);
        window.location.href = "entregarTrabajo.html"
    }else{
        alert("Error al intentar enviar el mensaje, por favor, intentelo de nuevo")
        return
    }
}