define([ "../main"], function(main) {
    require(["jquery"], function($) {
        var conditions = [];
        var lastCondition = {};

        $.fn.serializeObject = function()
        {
            var o = {};
            var a = this.serializeArray();
            $.each(a, function() {
                if (o[this.name] !== undefined) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            return o;
        };

        $(".save-plant-disease").click(function(e) {
            var modelForm = $(".plant-disease-form");
            if (!modelForm[0].checkValidity()) {
                modelForm.find('input[type="submit"]').click();
                return false;
            }
            var diseaseModel = modelForm.serializeObject();
            diseaseModel.conditions = conditions;
            $.ajax({
                url: '/plantsDiseaseModels',
                data: JSON.stringify(diseaseModel),
                type: "post",
                contentType: "application/json",
                dataType: "json",
                success: function (data) {
                    console.log(data);
                }
            });
        });

        $(".create-plant-disease-condition").click(function(e) {
            e.preventDefault();
            $(".modal").modal("show");
        });

        $(".save-plant-disease-condition").click(function (e) {
            var conditionForm = $(".plant-disease-condition-form");
            if (!conditionForm[0].checkValidity()) {
                conditionForm.find('input[type="submit"]').click();
                return false;
            }
            lastCondition = conditionForm.serializeArray();
            conditions.push(conditionForm.serializeObject());
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
            var param = params[condition[0].value];
            var condParam = condParams[condition[1].value];
            var cond = condition[2].value;
            var duration = condition[3].value;
            //TODO: refactor into something proper, at least this has good performance :D
            var description = "<li>" + param + " " + condParam + " " + cond + " for at least " + duration + " seconds" + "</li>";
            conditionList.append(description);
        });

    });
});
