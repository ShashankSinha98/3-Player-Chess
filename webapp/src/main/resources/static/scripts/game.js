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
    'P': '♙'
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
    console.log("Current player: " + color);
    const p = document.getElementById('playerdisplay');
    p.textContent = colorMap[color]
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
 * highlights a specific set of squares
 * @param data set of squares as JSON array
 */
function displayPossibleMoves(data){
    console.log('Highlighted Squares: ' + data);
    removeHighlighting();
    data.forEach(function (squareId) {
        const polygon = document.getElementById(squareId);
        if(polygon != null){
            polygon.classList.add('highlight')
        }

    });
}

/**
 * removes the highlighting of all squares
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
    let highlightedSquares = response['highlightedSquares']

    updatePieces(board);
    displayPossibleMoves(highlightedSquares);
}

function updatePieces(board) {
    for (const squareId in board) {
        const value = board[squareId];
        const pieceColor = value[0];
        const pieceToken = value[1];

        displayPiece(squareId, pieceToken, pieceColor);

    }
}

/**
 * displays the piece as a textElement inside svg
 * @param squareId Id of the square, e.g. Ba1, Ba2, ...
 * @param pieceToken token of the piece, e.g. R, N, B, K, Q, P
 * @param pieceColor color as single character, e.g. R, G, B
 */
function displayPiece(squareId, pieceToken, pieceColor) {
    const square = document.getElementById(squareId);
    const existingText = square.nextElementSibling; // Assuming the existing text is the immediate next sibling

    const points = square.points;
    let x = (points.getItem(0).x + points.getItem(2).x) / 2;
    let y = (points.getItem(0).y + points.getItem(2).y) / 2;

    const textElement = getPieceText(x, y, pieceColor, pieceToken);

    // Check if there is existing text, and insert the new text after it
    if (existingText) {
        square.parentNode.insertBefore(textElement, existingText.nextSibling);
    }
    else {
        // If there's no existing text, just insert the new text after the polygon
        square.parentNode.insertBefore(textElement, square.nextSibling);
    }
}
function insertLabels(squareId) {
        const square = document.getElementById(squareId);
        const points = square.points;
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
        textElement.textContent = squareId.toUpperCase();
        square.parentNode.insertBefore(textElement, square.nextSibling);
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
            sendSquareClicked(polygonId);
            requestCurrentPlayer();
        });
    });
}

/**
 * post the id of the clicked Square to the server on /onClick endpoint
 * @param polygonId id of the clicked square, e.g. Ra1, Gb3, ...
 */
function sendSquareClicked(polygonId){
    fetch('/onClick', {
        method: 'POST',
        headers: {
            'Content-Type': 'text/plain',
        },
        body: polygonId,
    }).then(response => response.json())
        .then(data => updateBoard(data))
        .catch(error => console.error('Error while sending the request:', error));
}

/**
 * requests all possible Moves of a piece and highlights them on the board
 * @param polygonId id of the square on which the piece is located
 */
function requestHighlightedSquares(polygonId){
    fetch('/allMoves', {
        method: 'POST',
        headers: {
            'Content-Type': 'text/plain',
        },
        body: polygonId,
    }).then(response => response.json())
        .then(data => displayPossibleMoves(data))
        .catch(error => console.error('Error while sending the request:', error));
}

/**
 * requests the new board state and displays it
 */
function requestUpdatedBoard(){
    fetch('/board', {
        method: 'GET',
    }).then(response => response.json())
        .then(data => updatePieces(data))
        .catch(error => console.error('Error while sending the request:', error));
}

/**
 * requests the current player and displays it
 */
function requestCurrentPlayer(){
    fetch('/currentPlayer', {
        method: 'GET',
        headers: {
            Accept: "text/plain"
        }
    }).then(reponse => reponse.text())
        .then(data => updateCurrenPlayer(data))
        .catch(error => console.error('Error while sending the request:', error));
}