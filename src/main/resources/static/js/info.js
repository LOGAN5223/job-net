let imgArr = ['1office.png', '2office.png', '4office.png'];
let imgDiv = document.querySelector(".img-info");



imgDiv.style.background = 'url("../images/' + imgArr[Math.floor(Math.random() * 3)] + '")';
imgDiv.style.backgroundSize = "100% 100%";

