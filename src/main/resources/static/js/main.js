'use strict';

let messageForm = document.querySelector('#messageForm');
let messageInput = document.querySelector('#message');
let messageArea = document.querySelector('#messageArea');
let connectingElement = document.querySelector('.connecting');
let stompClient = null;
let usersIcons  = new Map();
let roomId = (new URL(window.location.href)).pathname.split('/')[5];
let colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

usersIcons.set(document.querySelector(".main-container").getAttribute("data-cProfileLogin"), document.querySelector(".main-container").getAttribute("data-cProfilePicture"));
usersIcons.set(document.querySelector(".main-container").getAttribute("data-tProfileLogin"), document.querySelector(".main-container").getAttribute("data-tProfilePicture"));

document.querySelector(".main-container").removeAttribute("data-cProfileLogin");
document.querySelector(".main-container").removeAttribute("data-tProfileLogin");
document.querySelector(".main-container").removeAttribute("data-cProfilePicture");
document.querySelector(".main-container").removeAttribute("data-tProfilePicture");

connect();

function connect() {
    let socket = new SockJS('/ws');

    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);

}

function onConnected() {
    const joinDate = new Date(Date.now());
    // Subscribe to the Public Topic
    stompClient.subscribe('/queue/chat/' + roomId, onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/" + roomId + "/chat.addUser",
        {},
        JSON.stringify({
            sentAt: joinDate.toISOString(),
            type: 'JOIN'
        })
    )

}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    const currentDate = new Date(Date.now());
    let messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        let chatMessage = {
            content: messageInput.value,
            sentAt: currentDate.toISOString(),
            type: 'CHAT'
        };
        stompClient.send("/app/" + roomId + "/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);


    if (message.type === 'CHAT'){
        let messageElement = document.createElement('li');

        messageElement.classList.add('chat-message');

        let avatarElement = document.createElement('img');
        avatarElement.src = usersIcons.get(message.sender);

        messageElement.appendChild(avatarElement);

        let usernameElement = document.createElement('span');
        let usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);

        let textElement = document.createElement('p');
        let messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    }


}

messageForm.addEventListener('submit', sendMessage, true)
