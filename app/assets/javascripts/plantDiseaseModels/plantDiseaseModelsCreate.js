define([ "../main"], function(main) {
    require(["jquery"], function($) {
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
            // TODO: Add reverse routing
            // $.post('/plantsDiseaseModels',
            //     form.serialize(),
            //     function (data, status, xhr) {
            //
            //     });
        });
    });
});
