$(document).ready(function(){
    verificarToken();
})

function verificarToken(){
    const token = localStorage.getItem('token');
    if(token){
    window.location.href = "store.html";
    }
    }