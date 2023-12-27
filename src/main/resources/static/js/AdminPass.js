$(document).ready(function(){
    verificarToken()
})

function verificarToken(){
    const token = localStorage.getItem('token');
    if(token){
    window.location.href = "index.html";
    }
    }

    function play(event) {
        if (event.key === 'Enter') {   
            comprobar();
        }
    }

const comprobar = async(event) =>{
    let datos = {}
    datos.usuario = document.querySelector('#usuario').value;
    datos.contrasenia = document.querySelector('#pass').value;

    if(datos.usuario.trim().length === 0 || datos.contrasenia.trim().length === 0){
        alert("Por favor, llene los campos requeridos.");
        document.querySelector('#usuario').value = "";
        document.querySelector('#pass').value = "" ;
        return
        }
    const query = await fetch('api/logVip',{
        method: 'POST',
        headers: {
            'Accept': 'Application/json',
            'Content-Type':'Application/json'
        },
        body: JSON.stringify(datos)
    })
    const respuesta = await query.text();

    if(respuesta != "Error"){
        localStorage.setItem("token", respuesta);
        localStorage.setItem("usuario", datos.usuario);
        window.location.href = "index.html";
    }else{
        alert("Acceso denegado");
    }

}