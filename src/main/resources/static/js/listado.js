$(document).ready(function(){
    verificarToken();
    listadoNuevos();
})

function verificarToken(){
    const token = localStorage.getItem('token');
    const email = localStorage.getItem('email');
    if(!token || email){
    window.location.href = "inicio.html";
    }
    }

const listadoNuevos = async()=>{
    const query = await fetch('api/pedidos',{
method: 'GET',
headers: {
    'Accept': 'Application/json',
    'Content-Type': 'Application/json',
    'Authorization': localStorage.token
    }
 });
 const respuesta = await query.json();


const tablaFija= "<tr><td>Nombre</td><td>Apellido</td><td>Celular</td><td>Atender</td></tr>"
let tablas = "";
    for(i of respuesta){
        let plantilla = "<tr><td>"+i.nombre+"</td><td>"+i.apellido+"</td><td>"+i.telefono+"</td><td><div class='botonVerde' onclick='atendido("+JSON.stringify(i)+")'></div></td></tr>";
        tablas += plantilla;
    }
document.querySelector('.tabla table tbody').outerHTML = tablaFija + tablas;




}

async function atendido(i){
    try{
    const query = await fetch('api/datos',{
    method: 'POST',
    headers: {
        'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.token
    },
    body: JSON.stringify(i)
    
    })
        }catch(e){console.log(e)}
   window.location.href ='EstacionDeTrabajo.html';
        }
       