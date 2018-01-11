'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;

var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

$('#move').hide();

function connect(event) {
    username = document.querySelector('#name').value.trim();
    if(username == 'a'){
        $('#move').show();
    }
    $('#user-name-id').text(username);
    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
        stompClient.debug = null;
    }


    event.preventDefault();


}
$('#createGame-modal-btn').on('click', function(e){

    var gameName = $('#createGame-input').val();
    stompClient.send("/app/room/"+gameName,
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    );
    stompClient.subscribe('/topic/room/{room}', function(info) {
        $('#createGame-modal').modal('hide');
        $('#menu-after-connected').hide();
        $('#lobby-container').show();
        var message = JSON.parse(info.body);
        var lobbyPlayers = '';
        message.allPlayers.forEach(function(item, i, arr){
            lobbyPlayers+='<li class="fa fa-user list-group-item" style="color: '+ getAvatarColor(item)+'"> '+item+' </li>';
        });
        $('#list-lobby').html(lobbyPlayers);
        console.log(message.allPlayers);

        var messageElement = document.createElement('li');

        if(message.type === 'JOIN') {
            messageElement.classList.add('event-message');
            message.content = message.sender + ' joined!';
        } else if (message.type === 'LEAVE') {
            messageElement.classList.add('event-message');
            message.content = message.sender + ' left!';
        }
        var textElement = document.createElement('p');
        var messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    });
    e.preventDefault();
});

$('#createGame-btn').on('click', function(){
    $('#createGame-modal').modal('show');
});

function onConnected() {

    $('#menu-after-connected').show();

    // Subscribe to the Public Topic
    // stompClient.subscribe('/topic/public', onMessageReceived);
    // stompClient.subscribe('/topic/public/start', startGame);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser", {}, JSON.stringify({sender: username, type: 'JOIN'}));



    // stompClient.send("/app/startGame", {}, {});
    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

// function move(e){
//
//     var move ={
//         player:{
//             name: username
//         },
//         monster:{
//             id: 1000
//         }
//     }
//     stompClient.send("/app/move", {}, JSON.stringify(move));
//     e.preventDefault();
//     $('#move').hide();
// }
//
// $('#move').on('click', move);


function startGame(payload){

        var body  = JSON.parse(payload.body);
        var filterObj = body.players.filter(function(e) {
            return e.name == username;
        });
        console.log(filterObj);
        $('#startGame').hide();

}
$('#startGame').on('click', function(e){
    stompClient.send("/app/startGame", {}, {});
    e.preventDefault();
});



function onMessageReceived(payload) {




    //
  var body  = JSON.parse(payload.body);
  console.log(body);
  var players = body.players;
  //
  if(players!= undefined){
      players.forEach(function(item, i, arr) {
         if(item.name == username && item.moving){
              console.log(item);
              $('#move').show();
          }
      });
  }
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}


usernameForm.addEventListener('submit', connect, true);


