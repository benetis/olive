define([ "../main",
         "../../../../public/libs/jquery.json2html-master/json2html",
         "../../../../public/libs/jquery.json2html-master/jquery.json2html"], function(main) {
    require(["jquery"], function($) {
        var conditions = [];

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
            conditions.push(form.serializeArray());
            $(".modal").modal("hide");

            console.log(conditions);
            var transform =
            {"<>":"li","html":[
                {"<>":"i","class":"", "html":"${conditionParam}"}
            ]};

            $('.condition-list').json2html(conditions,transform);
        });

    });
});
