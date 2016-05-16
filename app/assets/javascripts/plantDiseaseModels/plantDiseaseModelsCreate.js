define([ "../main"], function(main) {
    require(["jquery"], function($) {
        $(".create-plant-disease-condition").click(function(e) {
            e.preventDefault();
            $(".modal").modal("show");
        });

        $(".save-plant-disease-condition").click(function (e) {
            var condition = $(".plant-disease-condition-form").serialize();
            console.log(condition);
            // TODO: Add reverse routing
            // $.post('/plantsDiseaseModels',
            //     form.serialize(),
            //     function (data, status, xhr) {
            //
            //     });
        });
    });
});
