define([ "../main" ], function(main) {
    require(["jquery"], function($) {
        $(".create-plant-disease").click(function(e) {
            e.preventDefault();
            $(".modal").modal("show");
        });

    });
});
