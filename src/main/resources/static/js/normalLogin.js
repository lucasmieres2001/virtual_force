$(document).ready(function(){
    verificarToken();
})
const verificarToken = async()=>{
    const token = localStorage.getItem("token");
    if(token){
        window.location.href = "store.html"
    }
}

function play(event) {
    if (event.key === 'Enter') {   
        login();
    }
}

async function login(){
    datos = {}
    datos.email =  document.querySelector('#email').value;
    datos.contrasenia =  document.querySelector('#pass').value;

    
    if(datos.email.indexOf('.com') === -1||datos.email.indexOf('@') === -1||datos.email.trim().length < 11){alert("Tu contraseña o correo electrónico no son correctos, por favor, intentalo de nuevo."); return}
    if(datos.contrasenia.trim().length < 10){alert("Tu contraseña o correo electrónico no son correctos, por favor, intentalo de nuevo."); return}

const ruta = 'api/normalLogin'
    const query = await fetch(ruta,{
        method: 'POST',
        headers: {
            'Accept': 'Application/json',
            'Content-Type': 'Application/json'
        },
        body: JSON.stringify(datos)
    })
    const respuesta = await query.text();
    if(respuesta != ""){
        localStorage.setItem("token",respuesta);
        localStorage.setItem("email",datos.email);
        window.location.href = "store.html";
    }else{
        alert("Tu contraseña o correo electrónico no son correctos, por favor, intentalo de nuevo.");
    }
}