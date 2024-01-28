function checkInputs() {
    const pl1 = document.getElementById('pl1').value;
    const pl2 = document.getElementById('pl2').value;
    const pl3 = document.getElementById('pl3').value;
    const newGameButton = document.getElementById('newgamebtn');

    if (pl1 !== '' && pl2 !== '' && pl3 !== '') {
        newGameButton.removeAttribute('disabled');
    } else {
        newGameButton.setAttribute('disabled', 'true');
    }
}

function newGame(){
    const request = new XMLHttpRequest();
    request.open("GET", "/newGame", false);
    //request.setRequestHeader('Content-Type', 'application/json');
    const pl1 = document.getElementById('pl1').value;
    const pl2 = document.getElementById('pl2').value;
    const pl3 = document.getElementById('pl3').value;

    localStorage.setItem('Blue', pl1);
    localStorage.setItem('Green', pl2);
    localStorage.setItem('Red', pl3);

    request.send(null);

    if (request.status === 200) {
        window.location.href = '/game.html';
    }
}