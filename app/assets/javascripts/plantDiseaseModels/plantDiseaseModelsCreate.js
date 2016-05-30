define([ "../main", "../i18n!nls/translations"], function(main, translations) {
    require(["jquery"], function($) {
        var conditions = [];
        var lastCondition = {};

        $.fn.serializeObject = function()
        {
            function isNumeric(num){
                return !isNaN(num);
            }
            var o = {};
            var a = this.serializeArray();
            $.each(a, function() {
                var value = this.value;
                if(isNumeric(this.value)) {
                    value = parseFloat(this.value);
                }
                if (o[this.name] !== undefined) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(value || null);
                } else {
                    o[this.name] = value || null;
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
                dataType: "json"
            }).done(function(response) {
                window.location.href="/plantsDiseaseModels";
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
            lastCondition = conditionForm.serializeObject();
            conditions.push(conditionForm.serializeObject());
            $(".modal").modal("hide");
            var conditionList = $('.condition-list');

            var condition = lastCondition;
            var params = {
                1 : "Tempratūra",
                2 : "Santykinė drėgmė",
                3 : "Vėjo kryptis",
                4 : "Vėjo greitis",
                5 : "Kritulių kiekis"
            };
            var condParams = {
                1 : ">",
                2 : "=",
                3 : "<"
            };
            var param = params[condition.paramId];
            var condParam = condParams[condition.conditionParam];
            var cond = condition.condition;
            var duration = condition.duration;
            //TODO: refactor into something proper, at least this has good performance :D
            var description = "<li>" + param + " " + condParam + " " + cond + " bent " + duration + " valandas" + "</li>";
            conditionList.append(description);
        });

    });
});
