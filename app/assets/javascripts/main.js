(function(requirejs) {
    "use strict";
    requirejs.config({
        baseUrl : "/assets/javascripts",
        shim : {
            "jquery" : {
                exports : "$"
            },
            "jsRoutes" : {
                exports : "jsRoutes"
            }
        },
        paths : {
            // Map the dependencies to CDNs or WebJars directly
            "jsRoutes" : "//localhost:9000/jsRoutes.js",
            "_" : "//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.1/underscore-min",
            "jquery" : "//cdnjs.cloudflare.com/ajax/libs/jquery/2.2.3/jquery.min.js",
            "bootstrap" : "//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/js/bootstrap.min.js",
            // A WebJars URL would look like
            // //server:port/webjars/angularjs/1.0.7/angular.min
        }
    });

    requirejs.onError = function(err) {
        console.log(err);
    };
})(requirejs);