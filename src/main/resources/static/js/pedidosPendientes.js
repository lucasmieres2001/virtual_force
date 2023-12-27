$(document).ready(function(){
    verificarToken()
    listadoNuevos()
})

function verificarToken(){
    const token = localStorage.getItem('token');
    const email = localStorage.getItem('email');
    if(!token || email){
    window.location.href = "inicio.html";
    }
    }


    const listadoNuevos = async()=>{
        const query = await fetch('api/pedidosPendientes',{
    method: 'GET',
    headers: {
        'Accept': 'Application/json',
        'Content-Type': 'Application/json',
        'Authorization': localStorage.token
        }
     });
     const respuesta = await query.json();
    
    
    const tablaFija= "<tr><td>Nombre</td><td>Apellido</td><td>Celular</td><td>Fecha pedido tomado</td><td>Retomar</td></tr>"
    let tablas = "";
        for(i of respuesta){
            let plantilla = "<tr><td>"+i.nombre+"</td><td>"+i.apellido+"</td><td>"+i.telefono+"</td><td>"+i.fecha_encargo+"</td><td><div class='botonVerde' onclick='retomarTrabajo("+JSON.stringify(i)+")'></div></td></tr>";
            tablas += plantilla;
        }
    document.querySelector('.tabla table').innerHTML = tablaFija + tablas;
    
    
    
    
    }
    
async function retomarTrabajo(i){
    
    try{
        const query = await fetch('api/retomarPedido',{
            method: 'POST',
            headers: {
                'Accept': 'Application/json',
                'Content-Type': 'Application/json',
                'Authorization': localStorage.token
            },
            body: JSON.stringify(i)
        })
        window.location.href="entregarTrabajo.html"; 
   }catch(e){console.log(e)}
   
}