$(document).ready(function(){
verificarToken()
})

function verificarToken(){
    const token = localStorage.getItem('token');
    if(token){
    window.location.href = "index.html";
    }
    }


const comprobar = async () =>{
    const usuario = document.querySelector('#usuario').value;
    const contrasenia = document.querySelector('#pass').value;
    const usuarioDos = document.querySelector('#usuariodos').value;
    const contraseniaDos = document.querySelector('#passdos').value;
    const restricted = document.querySelector('#restricted').value;
    const verificate = "HDR_1J1_733_LMR";
    if(usuario !== usuarioDos || contrasenia !== contraseniaDos || restricted !== verificate){
        alert("los datos no coinciden")
        return;
    }else{
    let datos = {};
    datos.usuario = usuario;
    datos.contrasenia = contrasenia;

    const query = await fetch('api/regVip',{
        method: 'POST',
        headers:{
            'Accept': 'Application/json',
            'Content-Type': 'Application/json'
        },
        body:JSON.stringify(datos)
    })
    const respuesta = await query.json();

    if(respuesta){
        alert("¡Se ah registrado correctamente!");
        window.location.href="AdminPass.html";
    }else{
        console.log("Error al registrarse");
    }
}
}