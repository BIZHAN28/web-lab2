var components = {
    x: document.querySelectorAll('input[class="x"]'),
    y: document.getElementById("y-value"),
    r: document.querySelectorAll('input[class="r"]'),
    submit: document.getElementById("submit-button")
};

var choosen = {
    x: null,
    y: null,
    r: []
};

// массив попавших точек для каждого радиуса
var pointsContainer = [];
var resultsTable = document.getElementById("results-content");


$(document).ready(function () {
    console.log("get table by session")
    getTableBySession();

    initialize_table(pointsContainer);
    redrawGraph(choosen.r[choosen.r.length-1]);
});

components.x.forEach(cbx => cbx.addEventListener("change", checkEnteredX));
function checkEnteredX(){
    choosen.x = null;
    for (let cbx of components.x){
        if(cbx.checked) {
            choosen.x = cbx.value;
        }
    }
    updateSubmitLock();
    redrawGraph(choosen.r[choosen.r.length-1]);
}

components.y.onblur = function checkEnteredY(){
    const yMin = -5.0;
    const yMax = 5.0;
    let y = components.y.value;
    let parsedY;
    choosen.y = null;
    console.log("CHECK Y", y)

    if(isNaN(y.trim()) || !y.match('[\-\+]?([0-5]?.[0-9]*)')){
        updateSubmitLock();
        return;
    }
    parsedY = parseFloat(y);
    if (isNaN(parsedY) || yMin > parsedY || parsedY > yMax) {
        updateSubmitLock();
        return;
    }
    choosen.y = y;
    updateSubmitLock();
}

components.r.forEach(rd => rd.addEventListener("change", checkEnteredR));
function checkEnteredR(){
    choosen.r = [];
    for (let rd of components.r) {
        if (rd.checked){
            choosen.r.push(rd.value);
        }
    }
    console.log("r=", choosen.r);
    updateSubmitLock();
    redrawGraph(choosen.r[choosen.r.length-1]);
}

$('#submit-button').click(function() {
    choosen.r.forEach(_r =>{
        let [x, y, r] = validateAndParse(choosen.x, choosen.y, _r)
        let result = validate_values(x, y, r);
        if (result) {
            sendForm(x, y, r);
        }
    });
});

$('#clear-button').click(function (event) {
    console.log("send request to clear");
    clean_table();
    getTableBySession();
    redrawGraph(choosen.r[choosen.r.length-1]);
});


document.addEventListener('click', (ev)=>this.handleClick(ev));
function handleClick(event) {
    // Получаем координаты точки, куда нажал пользователь
    let x = event.clientX;
    let y = event.clientY;
    let one = 30;

    if (x > canvasRect.left && x < canvasRect.right &&
        y < canvasRect.bottom && y > canvasRect.top) {

        //пересчитываем значения в локальные координаты
        x = (x - canvasRect.left - (canvasRect.width/2))/one;
        y = ((canvasRect.height/2) - y + canvasRect.top)/one;

        //console.log("click point = ", x, y);
        choosen.r.forEach(_r => {
            sendForm(x.toFixed(3).toString(), y.toFixed(3).toString(), _r);
        });

    }
}

function updateSubmitLock(){
    components.submit.disabled = choosen.x == null || choosen.y == null || choosen.r.length == 0;
}