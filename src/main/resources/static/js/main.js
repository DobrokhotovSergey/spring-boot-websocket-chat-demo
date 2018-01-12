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
    $('#user-name-id').text(username);
    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({name: username}, onConnected, onError);
        stompClient.debug = null;
    }


    event.preventDefault();


}

function connectToRoom(info){

    $('#lobby-container').show();

    var message = JSON.parse(info.body);
    $('#lobby-id').text(message.name);

    stompClient.subscribe('/topic/public/'+$('#lobby-id').text(), function(i) {
        var t = JSON.parse(i.body);
        var leave = '#lobby-player-' + t.sender;
        $(leave).remove();
    });

    var lobbyPlayers = '';
    var messageElement = document.createElement('li');
    message.allPlayers.forEach(function(item, i, arr){
        lobbyPlayers+='<li class="fa fa-user list-group-item" id=lobby-player-'+item.sender+' style="color: '+ getAvatarColor(item.sender)+'"> '+item.sender+' </li>';
    });
    $('#list-lobby').html(lobbyPlayers);

    $('#messageArea').empty();

    message.allPlayers.forEach(function(item, i, arr){
        console.log(item);
        if(item.type === 'JOIN') {
            messageElement.classList.add('event-message');
            item.content = item.sender + ' присоединился!';
        } else if (item.type === 'LEAVE') {
            messageElement.classList.add('event-message');
            item.content = item.sender + ' вышел!';
        }
        var textElement = document.createElement('p');
        var messageText = document.createTextNode(item.content);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    });


}
var findGameTable = $('#findGame-table').DataTable({
    destroy: true,
    autoWidth: false,
    columnDefs: [{},{},{}],
});
$('#findGame-table tbody').on('click', 'tr td .join-btn', function(e){
    var data = findGameTable.row( $(this).parents('tr') ).data();
    console.log(data);
    stompClient.send("/app/room/"+data[1], {}, JSON.stringify({sender: username, type: 'JOIN'}));

    e.preventDefault();
});

$('#findGame-btn').on('click', function(e){
    stompClient.send("/app/getrooms/"+username, {}, {});
    e.preventDefault();
});

$('#createGame-modal-btn').on('click', function(e){
    var gameName = $('#createGame-input').val();
    stompClient.send("/app/create/"+gameName, {}, JSON.stringify({sender: username, type: 'JOIN'}));
    e.preventDefault();
});

$('#createGame-btn').on('click', function(){
    $('#createGame-modal').modal('show');
});

function onConnected() {

    $('#menu-after-connected').show();

    stompClient.subscribe('/user/topic/create/{room}', function(info) {
        $('#createGame-modal').modal('hide');
        $('#menu-after-connected').hide();
        connectToRoom(info);
    });

    stompClient.subscribe('/topic/room/{room}', function(info) {
        console.log(JSON.stringify(info.body));
        $('#findGame-modal').modal('hide');
        $('#menu-after-connected').hide();
        connectToRoom(info);
    });

    stompClient.subscribe('/topic/room/{room}', function(info) {
        console.log(JSON.stringify(info.body));
        $('#findGame-modal').modal('hide');
        $('#menu-after-connected').hide();
        connectToRoom(info);
    });

    stompClient.subscribe('/user/topic/getrooms/{user}', function(info) {
        $('#findGame-modal').modal('show');
        var data = JSON.parse(info.body);
        findGameTable.clear().draw();
        var rows = [];
        data.forEach(function(item, i, arr) {
            rows[i] = [item.name, item.owner.sender, '<button type="button" class="join-btn" class="btn btn-default">клик</button>'];
        });
        findGameTable.rows.add(rows).draw();
    });




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


