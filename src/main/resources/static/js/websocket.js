var stompClient = null;

console.log("Hello...");
$("#connect").prop("disabled", false);
$("#alert").hide();
initNotify();

function initNotify(){
	if(window.Notification){
		Notification.requestPermission(function (status) {
			if(status == 'granted'){
				console.log("Notification granted");
			}
			else{
				console.log("Notification" + status);
			}
		});
	}
	else{
		alert('Votre navigateur est trop ancien (ou est Internet Explorer) pour supporter cette fonctionnalité !');
	}
}

function notify(title, body){
	var n = new Notification(title, {
		body: body,
		tag : 'chat'
	});
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (!connected) {
        $("#greetings").html("");
        cleanUserDash();
    }
    else {
        $("#alert").hide();
    }

}

function connect() {
    console.log("connect...");
    var socket = new SockJS('/chat-endpoint');
    stompClient = Stomp.over(socket);

    var headers = {
      login: $("#name").val(),
      passcode: $("#name").val(),
    };

    stompClient.connect(headers,
        function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/chat', function (message) {
                showMessage(JSON.parse(message.body).user, JSON.parse(message.body).type, JSON.parse(message.body).message);
            });
            stompClient.subscribe('/topic/events', function (message) {
                showEvent(JSON.parse(message.body).user, JSON.parse(message.body).type, JSON.parse(message.body).message);
            });
            stompClient.subscribe('/topic/dashboard', function (message) {
                cleanUserDash();
                buildUserDash(JSON.parse(message.body));
            });
            sendEvent("joined the chat", "info");
        },
        function(error) {
           // display the error's message header:
           $("#alert").show();
           $("#alert").text(error.headers.message);
           disconnect();
         });
}

function cleanUserDash() {
    $("#usersonline").text("");
    $("#nbusers").text("");
}

function buildUserDash(userDash) {
    $("#nbusers").text(userDash.nbUsers);
    for ( var key in userDash.users) {
        $("#usersonline").append(userDash.users[key].login  + ' - ' + userDash.users[key].ip + '<br/>');
    }
}

function disconnect() {
    if (stompClient != null) {
        sendEvent("left the chat", "warning");
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendEvent(message, type) {
    stompClient.send("/app/events", {}, JSON.stringify({'user': $("#name").val(), 'message': message, 'type': type}));
}

function sendMessage() {
    stompClient.send("/app/chat", {}, $("#message").val());
    $("#message").prop("value", "");
}

function showMessage(user, type, message) {
    $("#greetings").append('<span class="label label-' + type + '">' + user + '</span> ' + message + '<br/>');
      // Auto scroll down
     var chatConsole = document.getElementById('greetings');
     chatConsole.scrollTop = chatConsole.scrollHeight;

     notify(user, message);
}

function showEvent(user, type, message) {
    $("#greetings").append(user + ' <span class="label label-' + type + '">' + message + '</span><br/>');
    notify("", user + " " + message);
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
});