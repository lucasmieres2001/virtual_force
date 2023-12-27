$(document).ready(function(){
    verificarToken();
    refrescarNotificacion();
   imp()
   web()
   soft()
})

function verificarToken(){
    const token = localStorage.getItem('token');
    if(!token){
    window.location.href = "login.html";
    }
    } 

    const cerrarSesion = ()=>{
        localStorage.removeItem("token");
        localStorage.removeItem("email");
        window.location.href = "login.html";
    }

document.querySelector('#sesion').textContent = localStorage.email;
let email = {email: localStorage.email}

async function refrescarNotificacion(){
const query = await fetch('api/notificacion',{
    method: 'POST',
    headers: {
        'Accept': 'Application/json',
        'Content-Type': 'Application/json',
        'Authorization': localStorage.token
    },
    body: JSON.stringify(email)
})
const respuesta = await query.text();
if(respuesta != ""){
    document.querySelector('#not').innerHTML = "<img alt ='Tienes una notificación' onclick='notificacion()' src='css/public/not_active.png'/>"
}else{
    document.querySelector('#not').innerHTML = "<img alt='Bandeja vacía' onclick='notificacion()' src='css/public/not.png'/>"
}
}




function notificacion(){
window.location.href = "mensajeria.html";
}



const imp = async() =>{
    const query = await fetch('api/verificarPedido',{
        method: 'POST',
        headers: {
            'Accept': 'Application/json',
            'Content-Type': 'Application/json',
            'Authorization': localStorage.token
        },
        body: JSON.stringify(email)
    })
     const respuesta = await query.text();
    if(respuesta != true){
        document.querySelector('#uno').style.cursor = 'not-allowed';
        document.querySelector('#dos').style.cursor = 'not-allowed';
        document.querySelector('#tres').style.cursor = 'not-allowed';
        document.querySelector('#uno').style.backgroundColor = 'rgba(92, 0, 0, 0.5)';
        document.querySelector('#dos').style.backgroundColor = 'rgba(92, 0, 0, 0.5)';
        document.querySelector('#tres').style.backgroundColor = 'rgba(92, 0, 0, 0.5)';

    } 
    return respuesta;
}

const web = async() =>{
    const query = await fetch('api/verificarPedido',{
        method: 'POST',
        headers: {
            'Accept': 'Application/json',
            'Content-Type': 'Application/json',
            'Authorization': localStorage.token
        },
        body: JSON.stringify(email)
    })
    const respuesta = await query.text();
    return respuesta;
}

const soft = async() =>{
    const query = await fetch('api/verificarPedido',{
        method: 'POST',
        headers: {
            'Accept': 'Application/json',
            'Content-Type': 'Application/json',
            'Authorization': localStorage.token
        },
        body: JSON.stringify(email)
    })
    const respuesta = await query.text();  
    return respuesta;
}

const confirmImp = async () =>{
    const pass = await imp();
    if(pass == "true"){
    window.location.href="chatRemoteImp.html"
    }else{
        return alert("Por favor, espere a que su pedido actual sea completado antes de realizar uno nuevo.");
    }
}
const confirmWeb = async () =>{
    const pass = await web();
    if(pass == "true"){
    window.location.href="chatRemoteWeb.html"
    }else{
        return alert("Por favor, espere a que su pedido actual sea completado antes de realizar uno nuevo.");
    }
}
const confirmSoft = async () =>{
    const pass = await soft();
    if(pass == "true"){
    window.location.href="chatRemoteSoftware.html"
    }else{
        return alert("Por favor, espere a que su pedido actual sea completado antes de realizar uno nuevo.");
    }
}



