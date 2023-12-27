$(document).ready(function(){
    verificarToken()
    consultarPedidosPendientes()
})

function verificarToken(){
    const token = localStorage.getItem('token');
    const email = localStorage.getItem('email');
    if(!token || email){
    window.location.href = "inicio.html";
    }
    }

function cerrarSesion(){
    localStorage.removeItem("token");
    localStorage.removeItem("usuario");
    window.location.href = "AdminPass.html";
}

const nombreUsuario = localStorage.usuario;
document.querySelector('.opciones h1').innerHTML = "Bienvenido "+ nombreUsuario;

const consultarPedidosPendientes = async()=>{
    const query = await fetch('api/pantallaPendientes',{
        method: 'GET',
        headers: {
            'Accept': 'Application/json',
            'Content-Type': 'Application/json',
            'Authorization': localStorage.token
        }
    });
    const respuesta = await query.text();
    document.querySelector('.headerLeft h1').textContent = respuesta;
    if(respuesta != 1){
        document.querySelector('.headerLeft h2').textContent = "Encargos Pendientes";
    }else{
        document.querySelector('.headerLeft h2').textContent = "Encargo pendiente";
    }
}