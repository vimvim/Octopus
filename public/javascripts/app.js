
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
        console.log('Server: ', obj);
    };





});