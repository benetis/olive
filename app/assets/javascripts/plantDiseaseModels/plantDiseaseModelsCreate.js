define([ "../main"], function(main) {
    require(["jquery"], function($) {
        var conditions = [];
        var lastCondition = {};

        $(".create-plant-disease-condition").click(function(e) {
            e.preventDefault();
            $(".modal").modal("show");
        });

        $(".save-plant-disease-condition").click(function (e) {
            var form = $(".plant-disease-condition-form");
            if (!form[0].checkValidity()) {
                form.find('input[type="submit"]').click();
                return false;
            }
            lastCondition = form.serializeArray();
            conditions.push(form.serializeArray());
            $(".modal").modal("hide");
            var conditionList = $('.condition-list');

            var condition = lastCondition;
                var params = {
                    1 : "Temperature",
                    2 : "Humidity"
                };
                var condParams = {
                    1 : ">",
                    2 : "="
                };
                var param = params[condition[1].value];
                var condParam = condParams[condition[2].value];
                var cond = condition[3].value;
                var duration = condition[4].value;
                //TODO: refactor into something proper, at least this has good performance :D
                var description = "<li>" + param + " " + condParam + " " + cond + " for at least " + duration + " seconds" + "</li>";
                conditionList.append(description);
                conditionList.attr("data-cond"+conditions.length, JSON.stringify(condition));
        });

    });
});
