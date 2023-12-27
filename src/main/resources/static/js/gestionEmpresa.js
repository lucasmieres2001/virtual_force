$(document).ready(function(){
   verificarToken()
   gananciasTotales()
})

function verificarToken(){
    const token = localStorage.getItem('token');
    const email = localStorage.getItem('email');
    if(!token || email){
    window.location.href = "inicio.html";
    }
    }


const crearReunion = () =>{
window.open("https://meet.google.com/");
}

async function gananciasTotales(){
    const query = await fetch('api/gananciasTotales',{
        method: 'GET',
        headers: {
            'Accept': 'Application/json',
            'Content-Type': 'Application/json',
            'Authorization': localStorage.token
        }
    })
    const gananciasTotales = await query.text();
    document.querySelector('#gananciasTotales span').textContent = "$" + gananciasTotales;
    document.querySelector('#gananciasPersonales span').textContent = "$" + Math.round(gananciasTotales/3);

}

//Si quieres avisar quien organizó una reunion;
//<h4 style="color: rgb(196, 196, 0);">Lucas_Dev a convocado una reunion el día 12/11/2023</h4>