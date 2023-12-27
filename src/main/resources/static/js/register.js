$(document).ready(function(){
    verificarToken();
})

const verificarToken = () =>{
    const token = localStorage.token;
    if(token){window.location.href = "store.html"}
}


async function registrarse(){
    let datos = {}
    datos.nombre = document.querySelector('#nombre').value;
    datos.apellido = document.querySelector('#apellido').value;
    datos.telefono = document.querySelector('#celular').value;
    datos.email =document.querySelector('#email').value;
    datos.contrasenia = document.querySelector('#pass').value;
    const confirmar = document.querySelector('#confirmar').value;

    if(datos.nombre.trim().length == 0){alert("por favor, llene todos los campos para registrarse");return}
    if(datos.apellido.trim().length == 0){alert("por favor, llene todos los campos para registrarse");return}
    if(datos.telefono.trim().length < 10){alert("Por favor, escriba un número válido."); return}
    if(datos.email.indexOf('.com') === -1||datos.email.indexOf('@') === -1||datos.email.trim().length < 11){alert("Debe escribir un correo electrónico válido."); return}
    if(datos.contrasenia.trim().length < 10){alert("Su contraseña debe tener un minimo 10 carácteres."); return}
    if(datos.contrasenia !== confirmar){alert("Las contraseñas no coinciden, por favor, vuelve a intentarlo");return}

    const ruta = 'api/normalRegister'
    const query = await fetch(ruta,{
        method: 'POST',
        headers: {
            'Accept': 'Application/json',
            'Content-Type': 'Application/json'
        },
        body: JSON.stringify(datos)
    })
    window.location.href = "login.html"
}