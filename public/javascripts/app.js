
$( document ).ready(function() {

    var wsUrl = jsRoutes.controllers.Console.ws().webSocketURL();

    var connection = new WebSocket(wsUrl);

    // When the connection is open, send some data to the server
    connection.onopen = function () {
        connection.send(JSON.stringify({
            command: "open"
        }));
    };

    // Log errors
    connection.onerror = function (error) {
        console.log('WebSocket Error ' + error);
    };

    // Log messages from the server
    connection.onmessage = function (e) {

        var obj = JSON.parse(e.data);

        $("#console-display ul").append("<li>"+obj.prompt+" # "+obj.output+"</li>");

        console.log('Server: ', obj);
    };

    $("#console-send").click(function(e){

        var text = $("#console-input").val();

        connection.send(JSON.stringify({
            command: "execute",
            data: text
        }));

        e.preventDefault();
        return true;
    });
});
