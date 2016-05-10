var ctx = document.getElementById("myChart");
var myChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
        datasets: [{
            fill: false,
            backgroundColor: "rgba(75,192,192,0.4)",
            borderColor: "rgba(75,192,192,1)",
            label: '# of Votes',
            data: [12, 19, 3, 5, 2, 3]
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:true
                }
            }]
        }
    }
});

$.getJSON("/samples/2016.05.10/2016.05.10", function(data) {
    var jsonData = data;
    arrayClocked = [];
    arrayRest = [];
    for(var indexGroup in jsonData){
        if (!jsonData.hasOwnProperty(indexGroup)) {
        } else {
            // loop in the group in the json data
            for (var item in jsonData[indexGroup]) {
                var group = jsonData[indexGroup];
                if (group.hasOwnProperty(item)) {
                    if (item == "clocked") {
                        arrayClocked.push(group[item]);
                    } else {
                        arrayRest.push(group[item]);
                    }
                }
            }
        }
    }
    console.log(arrayClocked, arrayRest)
});