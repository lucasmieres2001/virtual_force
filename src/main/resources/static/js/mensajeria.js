$(document).ready(function(){
    verificarToken();
    verMensaje();
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


const verMensaje = async () =>{
    let email = {email: localStorage.email}
    const query = await fetch('api/verMensaje',{
        method: 'POST',
        headers: {
            'Accept': 'Application/json',
            'Content-Type': 'Application/json',
            'Authorization': localStorage.token
        },
        body: JSON.stringify(email)
    })
    const respuesta = await query.text();
    if(respuesta != "" && respuesta != null){
        document.querySelector('.formContainer textarea').textContent =respuesta;
    }else{
        document.querySelector('.formContainer textarea').outerHTML ="<p style = 'color: red'>Ningun mensaje, todo en orden :)</p>";
    }
    
}

async function eliminarBandejaDeEntrada(){
    let email = {email: localStorage.email}
    const query = await fetch('api/eliminarMensaje',{
        method: 'POST',
        headers: {
            'Accept': 'Application/json',
            'Content-Type': 'Application/json',
            'Authorization': localStorage.token
        },
        body: JSON.stringify(email)
    })
    window.location.href = "store.html";
    
}
