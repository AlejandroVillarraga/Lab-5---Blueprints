/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
function actualizarNombreAutor() {
                        document.getElementById("author").innerHTML = document.getElementById("nameAuthor").value+"'s Blueprints";
                    }*/
                  
var module = (function () {
    var nombre;
    var nombrePlano;
    var canvas;
    var ctx; 
    var puntosActuales =[];
    
    return{
        setBlueprintsByName: function (authorName){
            nombre = authorName;
        },
        
        actualizarTabla: function (author){
            var canvas = document.getElementById("canvas");
            var ctx = canvas.getContext('2d')
            $("#canvas").empty();
            ctx.clearRect(0,0,canvas.width,canvas.height)
            module.setBlueprintsByName(author);
            $.get("/blueprints/"+author, function(data){
                console.log("Data: " + data[0] + "\nStatus: " + status);
                $("#tabla").empty();
                $("#tabla").append("<tr><th id='Bpname'>Blue Prints Name</th><th id='NumPoint'>Number of Points</th><th id='ButtonOpen'>Open</th></tr>");
                for (i = 0; i < data.length; i++) {
                    $("#tabla").append("<tr>");
                    $("#tabla").append("<tr><td>"+data[i].name+"</td><td>"+data[i].points.length+"</td><td><button id='botonCan' type='button' onclick=module.draw('"+data[i].name+"')>Open</button></td></tr>");
                    }
            });
            $("#divNewBlueprint").empty();
            $('#divNewBlueprint').append("<button id='nuevoPlanoBlueprint' style='background-color:crimson' class='btn btn-primary' type = 'button' onclick='module.createNewBlueprint()'>Create new BLUEPRINT</button>");
            document.getElementById("author").innerHTML = document.getElementById("nameAuthor").value+"'s Blueprints";
            puntosActuales =[];
                          
        },
                
        draw: function(nplano) {
            nombrePlano = nplano;
            canvas = document.getElementById("canvas");
            ctx = canvas.getContext('2d');
            document.getElementById("nombrePlano").innerHTML = "Current Blueprint: "+nplano;
            
            $("#divsaveydelete").empty();
            $("#divsaveydelete").append("<button id='botonSave' class='btn btn-primary' type = 'button' onclick='module.saveBlueprint()'>Save/Update</button><button id='botonDelete' style='background-color:crimson' class='btn btn-primary' type = 'button' onclick='module.deleteBlueprint()'>Delete</button>");
            
            
            $.get("/blueprints/"+nombre+"/"+nplano, function(data){
                console.log("Nuevo Plano Abierto:");
                console.log("Autor: "+nombre);
                console.log("nombre: "+nplano);
                
                
                var primerPuntoX, primerPuntoY;
                var pAntx, pAnty;
                var esPrimerPunto = true;
                canvas.width = canvas.width;
                canvas.height = canvas.height;
                console.log(data);
                console.log(data.points.length);
                for (i = 0; i < data.points.length; i++){
                    if(esPrimerPunto){
                        console.log(data.points[i]);
                        pAntx = data.points[i].x;
                        pAnty = data.points[i].y;
                        primerPuntoX = data.points[i].x;
                        primerPuntoY = data.points[i].y;
                    }
                    else{
                        console.log("entro aqui");
                        module.drawLine(canvas, ctx, pAntx,pAnty,data.points[i].x,data.points[i].y)
                        //Se asigna el punto actual a las coordenadas del punto guardado para dibujar
                        pAntx = data.points[i].x;
                        pAnty = data.points[i].y;
                    }
                    esPrimerPunto = false;
               }
           });
       },
       //Función para crear un nuevo blueprint
        createNewBlueprint : function () {
            $("#divsaveydelete").empty();
            var canvas = document.getElementById("canvas");
            var ctx = canvas.getContext('2d')
            $("#canvas").empty();
            ctx.clearRect(0,0,canvas.width,canvas.height)
            document.getElementById("nombrePlano").innerHTML = "Creando nuevo plano...";
            $("#nameNewBlueprint").empty();
            $("#nameNewBlueprint").append("<label>Nombre:</label><input id='nombreNuevoPlano' type='text' size='50' maxlength='30' value='' name='nombre'>");
            $("#divNewBlueprint").empty();
            $('#nameNewBlueprint').append("<button id='botCrearPlano' style='background-color:crimson' class='btn btn-primary' type = 'button' onclick='module.crearPlano()'>Crear Plano</button>");
            
            
        },
        
        crearPlano: function(){
            document.getElementById("nombrePlano").innerHTML = "Plano Creado :D";
            var nuevoPlanoname = document.getElementById("nombreNuevoPlano").value;
            var nuevo = "{\"author\":"+"\""+nombre+"\","+"\"points\":"+JSON.stringify(puntosActuales)+",\""+"name\":"+"\""+nuevoPlanoname+"\""+"}";
            
            var crear=$.ajax({
                url: "/blueprints",
                type: 'POST',
                data: nuevo,
                contentType: "application/json"
            });
            crear.then(
               function(){
                   getBlueprintsByAuthor();
               },
               function(){
                   alert("No se pudo guardar");
               }
                       
            );
            $("#nameNewBlueprint").empty();
            
            module.actualizarTabla(nombre);
         },
         
         saveBlueprint: function(){
            document.getElementById("nombrePlano").innerHTML = "Plano: "+nombrePlano+" Guardado";
            var nuevo = "{\"author\":"+"\""+nombre+"\","+"\"points\":"+JSON.stringify(puntosActuales)+",\""+"name\":"+"\""+nombrePlano+"\""+"}";
            
             var crear=$.ajax({
                url: "/blueprints",
                type: 'PUT',
                data: nuevo,
                contentType: "application/json"
            });
            crear.then(
               function(){
                   getBlueprintsByAuthor();
               },
               function(){
                   alert("No se pudo Actualizar");
               }
                       
            );
            
            module.actualizarTabla(nombre);
         },
         
         deleteBlueprint: function(){
             document.getElementById("nombrePlano").innerHTML = "Plano: "+nombrePlano+" Eliminado";
            var nuevo = "{\"author\":"+"\""+nombre+"\","+"\"points\":"+JSON.stringify(puntosActuales)+",\""+"name\":"+"\""+nombrePlano+"\""+"}";
            
            var crear=$.ajax({
                url: "/blueprints",
                type: 'DELETE',
                data: nuevo,
                contentType: "application/json"
            });
            crear.then(
               function(){
                   getBlueprintsByAuthor();
               },
               function(){
                   alert("No se pudo Eliminar");
               }
                       
            );
            module.actualizarTabla(nombre);
         },
        
        
        drawLine: function(canvas, ctx, p1X,p1Y,p2X,p2Y){
            
            console.log(p2X+ ',' + p2Y + ' :DrawLine');
            //ctx.clearRect(0, 0, canvas.width, canvas.height);
            ctx.moveTo(p1X, p1Y);
            ctx.lineTo(p2X, p2Y);
            ctx.stroke();

        },
        
        init : function(){
            
            var canvas = document.getElementById("canvas");
            var ctx = canvas.getContext('2d');
            ctx.clearRect(0,0,canvas.width,canvas.height);
            var clientRect = canvas.getBoundingClientRect();
            
            var puntoInicial = true;
            var puntoAntX = 0, puntoAntY = 0;
            
            //init:function(){

                console.info('initialized');

                //if PointerEvent is suppported by the browser:
                if (window.PointerEvent) {
                    
                    canvas.addEventListener("pointerdown", function(event){
                        if(puntoInicial){
                            puntoAntX=event.clientX-clientRect.left;
                            puntoAntY=event.clientY-clientRect.top;
                        }
                        else{
                            console.log((event.clientX-clientRect.left) + ',' + (event.clientY-clientRect.top));
                            module.drawLine(canvas, ctx, puntoAntX, puntoAntY, (event.clientX-clientRect.left),(event.clientY-clientRect.top) );
                            
                            puntoAntX=event.clientX-clientRect.left;
                            puntoAntY=event.clientY-clientRect.top;
                            
                        }
                        puntosActuales = puntosActuales+"{'x':"+(event.clientX-clientRect.left)+",'y':"+(event.clientY-clientRect.top)+"}";
                        console.log("Puntos actuales: "+puntosActuales);
                        puntoInicial = false;
                    });   
                   
                } else {
                    canvas.addEventListener("mousedown", function(event){
                        
                        //alert('mousedown at ' + (event.clientX-clientRect.left) + ',' + (event.clientY-clientRect.top));
                        if(puntoInicial){
                            puntoAntX=event.clientX-clientRect.left;
                            puntoAntY=event.clientY-clientRect.top;
                        }
                        else{
                            console.log((event.clientX-clientRect.left) + ',' + (event.clientY-clientRect.top));
                            module.drawLine(canvas, ctx, puntoAntX, puntoAntY, (event.clientX-clientRect.left),(event.clientY-clientRect.top) );
                            
                            puntoAntX=event.clientX-clientRect.left;
                            puntoAntY=event.clientY-clientRect.top;
                            
                        }
                        
                        puntoInicial = false;
                        var x = {"x":event.clientX-clientRect.left,"y":event.clientY-clientRect.top};
                        puntosActuales.push(x);
                        console.log("Puntos actuales: "+JSON.stringify(puntosActuales));
                    });
            
            } 
        },
        
        
        

   }
 
})();