$(document).ready(function(){
    verificarToken();
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

    
    document.querySelector('#email').textContent = localStorage.email;
    


   

 async function enviarMensaje(){
    //Transportes;
    let formData = new FormData();
    //Recopilar datos;

    let fecha = new Date();
    const dia = fecha.getDate();
    const mes = fecha.getMonth() + 1;
    const año = fecha.getFullYear();
    const fechaEncargo = {fecha_encargo: dia+"/"+mes+"/"+año};

    const email = {email: localStorage.email};
    let encargo = "Página Web";
    let img;
    if(document.querySelector('#categoria').value.length <= 0){alert("Por favor, detallanos tu problema antes de enviarnos tu pedido");return}
    if(document.querySelector('#upload').files.length != 0){ img = document.querySelector('#upload').files[0];}else{
        alert("Debes subir una imágen sobre el problema para que podamos ayudarlo correctamente."); return}
    let comentario = document.querySelector('#categoria').value
    let tipo_imagen =document.querySelector('#upload').files[0].type
    let tipoImagenSoloPNG = document.querySelector('#upload').files[0].type;
    let solo_png = tipoImagenSoloPNG.substring(tipoImagenSoloPNG.lastIndexOf("/") + 1);
    let nombre_imagen =  document.querySelector('#upload').files[0].lastModified.toString() +"."+solo_png

    //Transferir datos;
    formData.append('fecha_encargo',new Blob([JSON.stringify(fechaEncargo)],{type: 'Application/json'}))
    formData.append('email',new Blob([JSON.stringify(email)],{type: 'Application/json'}))
    formData.append('encargo',encargo);
    formData.append('comentario',comentario)
    formData.append('nombre_imagen',nombre_imagen)
    formData.append('tipo_imagen',tipo_imagen)
    formData.append('img',img);
    //Llevar al servidor;
    const query = await fetch('api/subirImagen',{
        method: 'POST',
        headers: {
            'Authorization': localStorage.token
        },
        body: formData
    })
    alert("¡Pedido enviado! En breve nuestros operadores se comunicarán con ustéd.")
        window.location.href ="store.html";
    }

  