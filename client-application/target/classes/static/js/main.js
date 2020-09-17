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

function connect(event) {
    console.log("connect");
    username = document.querySelector('#name').value.trim();

    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws/chat');
        stompClient = Stomp.over(socket);

        var headers = {
            'Authorization': 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI5IiwiaWF0IjoxNjAwMTUxMDk0LCJleHAiOjE2MDA3NTU4OTR9.vkQ6ppYVbJLRc2HFEkL-kpY3clyFd7bt9jKew2_9DGirAwavxRj9T6Zn1sAmC7gPe3h49zUtcsUfnoQXMFAkzA'
        };
        stompClient.connect(headers, onConnected, onError);
        //stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic
    console.log("Onconnected");
    stompClient.subscribe('/sub/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/pub/chat/addUser",
        {},
        JSON.stringify({userId: username, type: 'NOTI'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    console.log("send message");
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        var chatMessage = {
            userId: username,
            content: messageInput.value,
            type: 'TEXT'
        };

        stompClient.send("/pub/chat/message", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    console.log("message received");
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'NOTI') {
        messageElement.classList.add('event-message');
        message.content = message.userId + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.userId+ ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.userId[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.userId);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.userId);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = messageSender;
    console.log("messageSender = " + messageSender);

/*
    for (var i = 0; i < messageSender; i++) {
        hash = 31 * hash;
    }
*/

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)
