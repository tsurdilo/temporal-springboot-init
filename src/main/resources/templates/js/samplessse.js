$( document ).ready(function() {

    var sse = $.SSE('/workflow-messages', {
        onMessage: function(e){
            console.log(e);
            $('#workflow-messages tr:last').after('<tr><td>'+e.data+'</td></tr>');
        },
        onError: function(e){
            sse.stop();
            console.log("Could not connect..Stopping SSE");
        },
        onEnd: function(e){
            console.log("End");
        }
    });
    sse.start();

});