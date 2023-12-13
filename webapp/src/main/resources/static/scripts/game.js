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

document.addEventListener('DOMContentLoaded', function () {
    const polygons = document.querySelectorAll('polygon');

    polygons.forEach(function (polygon) {
        polygon.addEventListener('click', function () {
            const polygonId = polygon.id;
            fetch('/move', {
                method: 'POST',
                headers: {
                    'Content-Type': 'text/plain',
                },
                body: polygonId,
            }).then(response => response.json())
                .then(data => updateBoard(data))
                .catch(error => console.error('Error while sending the request:', error));

            fetch('/allMoves', {
                method: 'POST',
                headers: {
                    'Content-Type': 'text/plain',
                },
                body: polygonId,
            }).then(response => response.json())
                .then(data => displayPossibleMoves(data))
                .catch(error => console.error('Error while sending the request:', error));

            fetch('/currentPlayer', {
                method: 'GET',
                headers: {
                    Accept: "text/plain"
                }
            }).then(reponse => reponse.text())
                .then(data => updateCurrenPlayer(data))
                .catch(error => console.error('Error while sending the request:', error));
        });
    });
});

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
function updateBoard(data) {
    clearBoard();
    console.log('New Board Configuration:', data);

    for (const squareId in data) {

        const value = data[squareId];
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
    //console.log(squareId);
    const square = document.getElementById(squareId);
    //console.log(square);
    const points = square.points;

    let x = (points.getItem(0).x + points.getItem(2).x)/2;
    let y = (points.getItem(0).y + points.getItem(2).y)/2;

    const textElement = getPieceText(x, y, pieceColor, pieceToken );
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
    textElement.setAttribute('font-size', '48');
    textElement.setAttribute('font-weight', 'bold');
    //textElement.setAttribute('font-family', 'FreeSerif');
    textElement.textContent = pieceMap[pieceToken];
    //textElement.classList.add('theme');
    textElement.setAttribute('class', theme);

    return textElement;
}

/**
 * removes all displayed pieces from the chessboard
 */
function clearBoard() {
    const textElements = document.querySelectorAll('text');
    textElements.forEach((textElement) => {
        textElement.remove();
    });
}

const startPosition = {
    "h1":"♖",
    "g1":"♘",
    "f1":"♗",
    "e1":"♔",
    "d1":"♕",
    "a1":"♖",
    "b1":"♘",
    "c1":"♗",
    "h2":"♙",
    "g2":"♙",
    "f2":"♙",
    "e2":"♙",
    "d2":"♙",
    "a2":"♙",
    "b2":"♙",
    "c2":"♙",
}

displayPieces('red', startPosition);
displayPieces('green', startPosition);
displayPieces('blue', startPosition);

function displayPieces(color, position) {
    for(const id in position) {
        const pieceSymbol = startPosition[id];
        const squares = document.getElementsByClassName(id + " "+ color);
        console.log(squares);
        for(let i = 0; i < squares.length; i++) {
            const square = squares[i];
            const punkte = square.points;

            let x = (punkte.getItem(0).x + punkte.getItem(2).x)/2
            let y = (punkte.getItem(0).y + punkte.getItem(2).y)/2

            const textElement = document.createElementNS('http://www.w3.org/2000/svg', 'text');
            textElement.setAttribute('x', x.toString());
            textElement.setAttribute('y', y.toString());
            textElement.setAttribute("text-anchor", "middle");
            textElement.setAttribute("dominant-baseline", "middle");
            textElement.setAttribute('fill', color);
            textElement.setAttribute('font-size', '48');
            textElement.setAttribute('font-weight', 'bold');
            textElement.setAttribute('class', theme);
            textElement.textContent = pieceSymbol;
            square.parentNode.insertBefore(textElement, square.nextSibling);
        }

    }
}