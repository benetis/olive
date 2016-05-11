define([ "../main" ], function(main) {
    require(["jquery"], function($){
        function buildChart(labels, data) {
            var ctx = document.getElementById("myChart").getContext("2d");
            var myChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [
                        {
                            fill: false,
                            backgroundColor: "rgba(75,192,192,0.4)",
                            borderColor: "rgba(75,192,192,1)",
                            label: 'Temperature °C',
                            yAxisID: "y-axis-1",
                            data: data
                        },
                        {
                            fill: false,
                            backgroundColor: "rgba(75,192,192,0.4)",
                            borderColor: "rgba(75,192,192,1)",
                            label: 'Second axis',
                            yAxisID: "y-axis-2",
                            data: data
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
                            gridLines: {
                                offsetGridLines: false
                            }
                        }],
                        yAxes: [{
                            type: "linear",
                            display: true,
                            position: "left",
                            id: "y-axis-1",
                        }, {
                            type: "linear",
                            display: true,
                            position: "right",
                            id: "y-axis-2"
                        }],
                    }
                }
            });
        }


        $.when($.getJSON("/samples/2016.05.10/2016.05.10").then(function(data) {
            console.log(data);
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
                                arrayRest.push(group[item]);
                            }
                        }
                    }
                }
            }
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
