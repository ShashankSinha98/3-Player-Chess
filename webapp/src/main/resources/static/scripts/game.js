/**
 * maps the piece token to the corresponding unicode character
 * @type {{P: string, Q: string, R: string, B: string, K: string, N: string}}
 */
const pieceMap = {
    'R': '♖',
    'N': '♘',
    'B': '♗',
    'Q': '♕',
    'K': '♔',
    'P': '♙',
    'J': '⎈',
    'W': '▩'
};

/**
 * maps the letters R,G,B to the corresponding color string
 * @type {{R: string, B: string, G: string}}
 */
const colorMap = {'R':'Red', 'G':'Green', 'B': 'Blue'};

/**
 * global variable storing the current theme
 * @type {string}
 */
let theme = 'arialTheme'

/**
 * displays the current player inside p element with the id "playerdisplay"
 * @param color color of the current player as single character (R, G, B)
 */
function updateCurrenPlayer(color){
    const colourName = colorMap[color];
    const playerName = localStorage.getItem(colourName);

    const p_name = document.getElementById('pl-name');
    p_name.textContent = playerName;

    const p_colour = document.getElementById('pl-colour');
    p_colour.style.color = colourName;
}

/**
 * updates the current theme
 * @param name name of the theme (arialTheme, freeSerifTheme, dejaVuSansTheme)
 */
function updateTheme(name){
    console.log('New Font: ' + name);
    theme = name;
    const textElements = document.querySelectorAll('text');
    textElements.forEach(function(element) {
        element.setAttribute('class', name);
    });
}

/**
 * highlights a specific set of polygons
 * @param data set of polygons as JSON array
 */
function displayPossibleMoves(data){
    console.log('Highlighted Polygons: ' + data);
    removeHighlighting();
    data.forEach(function (polygonId) {
        const polygon = document.getElementById(polygonId);
        if(polygon != null){
            polygon.classList.add('highlight')
        }

    });
}

/**
 * removes the highlighting of all polygons
 */
function removeHighlighting(){
    const polygons = document.querySelectorAll('polygon');
    polygons.forEach( polygon => polygon.classList.remove('highlight'));
}

/**
 * updates the chessboard with the new state
 * @param data new board state with the updated piece positions, e.g. {Ba1: "BR", Ba2: "BP", ...}
 */
function updateBoard(response) {
    clearBoard();
    console.log('New Board Configuration:', response);
    let board = response['board'];
    let highlightedPolygons = response['highlightedPolygons'];
    let winner = response['winner'];
    if(response['gameOver']){
        showGameOverPopup(response['winner']);
    }

    updatePieces(board);
    displayPossibleMoves(highlightedPolygons);
}

function updatePieces(board) {
    for (const polygonId in board) {
        const value = board[polygonId];
        const pieceColor = value[0];
        const pieceToken = value[1];

        displayPiece(polygonId, pieceToken, pieceColor);

    }
}

/**
 * displays the piece as a textElement inside svg
 * @param polygonId Id of the polygon, e.g. Ba1, Ba2, ...
 * @param pieceToken token of the piece, e.g. R, N, B, K, Q, P
 * @param pieceColor color as single character, e.g. R, G, B
 */
function displayPiece(polygonId, pieceToken, pieceColor) {
    const polygon = document.getElementById(polygonId);
    const existingText = polygon.nextElementSibling; // Assuming the existing text is the immediate next sibling

    const points = polygon.points;
    let x = (points.getItem(0).x + points.getItem(2).x) / 2;
    let y = (points.getItem(0).y + points.getItem(2).y) / 2;

    const textElement = getPieceText(x, y, pieceColor, pieceToken);

    // Check if there is existing text, and insert the new text after it
    if (existingText) {
        polygon.parentNode.insertBefore(textElement, existingText.nextSibling);
    }
    else {
        // If there's no existing text, just insert the new text after the polygon
        polygon.parentNode.insertBefore(textElement, polygon.nextSibling);
    }
}

/**
 * creates a new svg text element displaying the polygon name
 * @param polygonId the id of the polygon for which label to be added
 */
function insertLabels(polygonId) {
        const polygon = document.getElementById(polygonId);
        const points = polygon.points;
        let x = (points.getItem(0).x + points.getItem(2).x) / 2;
        let y = (points.getItem(0).y + points.getItem(2).y) / 2;
        const textElement = document.createElementNS('http://www.w3.org/2000/svg', 'text');
        textElement.setAttribute('x', x);
        textElement.setAttribute('y', y);
        textElement.setAttribute("text-anchor", "middle");
        textElement.setAttribute("dominant-baseline", "middle");
        textElement.setAttribute('fill', 'rgba(255,255,255,0.8');
        textElement.setAttribute('font-size', '14');
        textElement.setAttribute('font-weight', 'bold');
        textElement.textContent = polygonId.toUpperCase();
        polygon.parentNode.insertBefore(textElement, polygon.nextSibling);
}

/**
 * creates a new svg text element displaying a piece
 * @param x coordinate of the text element
 * @param y coordinate of the text element
 * @param color color of the displayed piece, e.g. R, G, B
 * @param pieceToken token of the displayed piece, e.g. R, N, B, K, Q, P
 * @returns {SVGTextElement}
 */
function getPieceText(x, y, color, pieceToken) {
    const textElement = document.createElementNS('http://www.w3.org/2000/svg', 'text');
    textElement.setAttribute('x', x);
    textElement.setAttribute('y', y);
    textElement.setAttribute("text-anchor", "middle");
    textElement.setAttribute("dominant-baseline", "middle");
    textElement.setAttribute('fill', colorMap[color]);
    textElement.setAttribute('font-size', '50');
    textElement.setAttribute('font-weight', 'bold');
    textElement.textContent = pieceMap[pieceToken];
    textElement.setAttribute('class', theme);

    return textElement;
}

/**
 * removes all displayed pieces from the chessboard
 */
function clearBoard() {
    const textElements = document.querySelectorAll('text');
    textElements.forEach((textElement) => {
        if (textElement.classList.contains(theme)) {
            textElement.remove();
        }
    });
}

/**
 * called when the html document finished loading
 */
function bodyLoaded(){
    console.log("Body loaded");
    requestUpdatedBoard();
    requestCurrentPlayer()

    const polygons = document.querySelectorAll('polygon');

    polygons.forEach(function (polygon) {
        insertLabels(polygon.id)
        polygon.addEventListener('click', function () {
            const polygonId = polygon.id;
            sendPolygonClicked(polygonId);
            requestCurrentPlayer();
        });
    });
}

/**
 * post the id of the clicked Polygon to the server on /onClick endpoint
 * @param polygonId id of the clicked polygon, e.g. Ra1, Gb3, ...
 */
function sendPolygonClicked(polygonId){
    const request = new XMLHttpRequest();
    request.open("POST", "/onClick", false);
    request.send(polygonId);

    if (request.status === 200) {
        const data = JSON.parse(request.response);
        updateBoard(data);
    }
}

/**
 * requests all possible Moves of a piece and highlights them on the board
 * @param polygonId id of the polygon on which the piece is located
 */
function requestHighlightedPolygons(polygonId){
    const request = new XMLHttpRequest();
    request.open("GET", "/allMoves", false);
    request.send(polygonId);

    if (request.status === 200) {
        const data = JSON.parse(request.response);
        displayPossibleMoves(data);
    }
}

/**
 * requests the new board state and displays it
 */
function requestUpdatedBoard(){
    console.log("Request Current Board");
    const request = new XMLHttpRequest();
    request.open("GET", "/board", false);
    request.send(null);

    if (request.status === 200) {
        const data = JSON.parse(request.response);
        updatePieces(data);
    }
}

/**
 * requests the current player and displays it
 */
function requestCurrentPlayer(){
    const request = new XMLHttpRequest();
    request.open("GET", "/currentPlayer", false);
    request.send(null);

    if (request.status === 200) {
        const player = request.response;
        updateCurrenPlayer(player);
    }
}

function showGameOverPopup(winner) {
    const colourName = colorMap[winner];
    const playerName = localStorage.getItem(colourName);
    document.getElementById('popup').style.display = 'block';
    const winnerText = playerName + " (" + colourName + ") has won the Game!"
    document.getElementById('winner').innerText = winnerText;
}

function closePopup() {
    document.getElementById('popup').style.display = 'none';
}