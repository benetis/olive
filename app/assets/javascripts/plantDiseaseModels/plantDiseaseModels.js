define([ "../main" ], function(main) {
    require(["jquery"], function($) {
        $(".create-plant-disease").click(function(e) {
            e.preventDefault();
            $(".modal").modal("show");
        });

        $(".save-plant-disease").click(function (e) {
            e.preventDefault();
            //TODO: Add reverse routing
            $.post('/plantsDiseaseModels',
                $(".plant-disease-form").serialize(),
                function(data, status, xhr){
                    console.log(data);
            });
        });

    });
});
