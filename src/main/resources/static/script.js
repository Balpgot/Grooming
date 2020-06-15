function f() {
    let trace1 = {
        x: [1, 2, 3, 4],
        y: [10, 15, 13, 17],
        type: 'scatter'
    };

    let trace2 = {
        x: [1, 2, 3, 4],
        y: [16, 5, 11, 9],
        type: 'scatter'
    };

    let data = [trace1, trace2];

    Plotly.newPlot('myDiv', data);
}

function showPlot(data){
    let dots = JSON.parse(data);
    Plotly.newPlot('plotPlace', dots);
}

function sho() {

}

function showClientsCustom() {

}