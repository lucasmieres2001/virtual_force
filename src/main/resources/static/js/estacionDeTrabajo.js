$(document).ready(function(){
    verificarToken()
    mainArranque();
})

function verificarToken(){
    const token = localStorage.getItem('token');
    const email = localStorage.getItem('email');
    if(!token || email){
    window.location.href = "inicio.html";
    }
    }

const mainArranque = async ()=>{
    const query = await fetch('api/datosrecibidos',{
        method: 'GET',
        headers:{
        'Accept': 'Application/json',
        'Content-Type': 'Application/json'
        },
    }) 
    const respuesta = await query.json();

    let comentarioCliente = "";
    //texto_original contiene el comentario del cliente;
   const texto_original = '<p>'+respuesta.encargos.comentario+'</p>';
    //Con la lógica del resto siempre se va a crear un <br> (o lo que vos quieras) cada cuantas letras quieras;
   for(let i = 0; i<texto_original.length;i++){
        comentarioCliente += texto_original[i]
        if(i > 0 && (i+1) % 130 === 0){
            comentarioCliente += '-<br>';
        }
   }
   let notNull = (respuesta.encargos.pago === null) ? "0" : respuesta.encargos.pago;
    document.querySelector('.verImg').outerHTML = `<div onclick="verImg('${respuesta.encargos.nombre_imagen}')" class="verImg"><p>Imágenes</p></div>`;
    document.querySelector('.enviar').outerHTML = "<div onclick='responder("+respuesta.encargos.id_encargo+")' class='enviar'><p>Tomár Pedido</p></div>";
    document.querySelector('.info').innerHTML = "<h2>Datos del cliente</h2><p><b>Nombre:</b>"+respuesta.usuarios.nombre+"</p><p><b>Apellido:</b>"+respuesta.usuarios.apellido+"</p><p><b>Encargo:</b>"+respuesta.encargos.encargo+"</p><p><b>Fecha de encargo:</b>"+respuesta.encargos.fecha_encargo+"</p><p><b></b>Pago:</b>$"+notNull+"</p>";
        
    document.querySelector('.comentarios p').outerHTML = comentarioCliente;
}

async function responder(id){
    if(confirm('¿Quieres iniciar con este trabajo?.')){
    let fecha = new Date();
    const dia = fecha.getDate();
    const mes = fecha.getMonth() + 1;
    const año = fecha.getFullYear();
    const fechaEncargo = dia+"/"+mes+"/"+año;
    let datos = {}
datos.fecha_encargo = fechaEncargo;
datos.respuesta = document.querySelector('.responder').value;
const query = await fetch('api/enviarRespuesta/'+ id,{
    method: 'POST',
    headers: {
        'Accept': 'Application/json',
        'Content-Type': 'Application/json'
    },
    body: JSON.stringify(datos)
})
window.location.href = "pedidosPendientes.html";
    }
}

async function verImg(img){
    const query = await fetch('api/verImg',{
        method: 'POST',
        headers: {
            'Accept': 'Application/json',
            'Content-Type': 'Application/json',
            'Authorization': localStorage.token
        },
        body: JSON.stringify(img)
    })
    window.location.href = "verImagen.html";
}

