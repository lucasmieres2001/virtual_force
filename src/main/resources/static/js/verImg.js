$(document).ready(function(){
    verificarToken();
    imgRecibida();
})

function verificarToken(){
    const token = localStorage.getItem('token');
    if(!token){
    window.location.href = "inicio.html";
    }
    }

const imgRecibida = async ()=>{
    const query = await fetch('api/imgRecibida',{
        method: 'GET',
        headers: {
            'Accept': 'Application/json',
            'Content-Type': 'Application/json'
        }
    })
    
    const imagenCodificada = await query.text();
    const img = decodeURIComponent(imagenCodificada.replace(/"/g, ''));
    const volver = 'window.location.href="EstacionDeTrabajo.html"';
    document.querySelector('.imagen').innerHTML = "<img src='img/"+img+"'/>";
    document.querySelector('a').outerHTML = " <a href='img/"+img+"' download='VirtualForce - "+img+"'>Descargar</a> <a onclick='"+volver+"''>Volver</a>"
    document.body.style.backdropFilter = "blur(0.4vh)";
    document.body.style.backgroundImage = "url(../img/"+img+")";
}



