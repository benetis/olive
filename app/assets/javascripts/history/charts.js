define([ "../main" ], function(main) {
    require(["jquery"], function($){
        function buildChart(labels, data) {
            var ctx = document.getElementById("myChart").getContext("2d");
            var myChart = new Chart(ctx, {
                data: {
                    labels: labels,
                    datasets: [
                        {
                            type: 'line',
                            fill: false,
                            borderColor: "#FF974F",
                            backgroundColor: "#F77A52",
                            label: 'Temperature °C',
                            yAxisID: "y-axis-1",
                            data: data.temperature
                        },
                        {
                            type: 'line',
                            fill: false,
                            borderColor: "#A49A87",
                            backgroundColor: "#644D52",
                            label: 'Humidity %',
                            yAxisID: "y-axis-2",
                            data: data.humidity
                        },
                        {
                            type: 'line',
                            fill: false,
                            backgroundColor: "#304269",
                            borderColor: "#91BED4",
                            label: 'Wind speed m/s',
                            yAxisID: "y-axis-3",
                            data: data.windSpeed
                        }
                    ]
                },
                options: {
                    responsive: true,
                    hoverMode: 'label',
                    stacked: false,
                    scales: {
                        xAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: 'Hour'
                            }
                        }],
                        yAxes: [{
                            type: "linear",
                            display: true,
                            position: "left",
                            id: "y-axis-1",
                            scaleLabel: {
                                display: true,
                                labelString: 'Temperature °C'
                            }
                        }, {
                            type: "linear",
                            display: true,
                            position: "right",
                            id: "y-axis-2",
                            scaleLabel: {
                                display: true,
                                labelString: 'Humidity %'
                            }
                        }, {
                            type: "linear",
                            display: true,
                            position: "right",
                            id: "y-axis-3",
                            scaleLabel: {
                                display: true,
                                labelString: 'Wind speed m/s'
                            }
                        }]
                    }
                }
            });
        }


        $.when($.getJSON("/samples/2016.05.10/2016.05.10").then(function(data) {
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
                                var date = Date.createFromMysql(group[item]);
                                arrayClocked.push(date.getHours());
                            } else {
                                if(!arrayRest[item]) { arrayRest[item] = []; }
                                arrayRest[item].push(group[item]);
                            }
                        }
                    }
                }
            }
            console.log(arrayRest);
            buildChart(arrayClocked, arrayRest);
        }));

        Date.createFromMysql = function(mysql_string)
        {
            var t, result = null;
            if( typeof mysql_string === 'string' )
            {
                t = mysql_string.split(/[- :]/);
                //when t[3], t[4] and t[5] are missing they defaults to zero
                result = new Date(t[0], t[1] - 1, t[2], t[3] || 0, t[4] || 0, t[5] || 0);
            }
            return result;
        };
    });
});
