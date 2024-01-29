function checkInputs() {
    const pl1 = document.getElementById('pl1').value;
    const pl2 = document.getElementById('pl2').value;
    const pl3 = document.getElementById('pl3').value;
    const newGameButton = document.getElementById('submit');

    if (pl1 !== '' && pl2 !== '' && pl3 !== '') {
        newGameButton.removeAttribute('disabled');
    } else {
        newGameButton.setAttribute('disabled', 'true');
    }
}

function newGame(){
    const request = new XMLHttpRequest();
    request.open("POST", "/newGame", false);

    const pl1 = document.getElementById('pl1').value;
    const pl2 = document.getElementById('pl2').value;
    const pl3 = document.getElementById('pl3').value;

    const form = document.getElementById('playerForm');
    let data = new FormData(form);

    request.send(data);

    if (request.status === 200) {
        window.location.href = '/game.html';
    }
}