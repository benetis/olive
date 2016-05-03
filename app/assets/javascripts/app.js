define(["jquery", "bootstrap"], function($, bootstrap) {
    var fillSignInForm;
    fillSignInForm = function($tr) {
        $('#identifier, #email').val($tr.find('.email').text());
        return $('#password').val($tr.find('.pwd ').text());
    };
    return $(function() {
        return $('#signin-helper tr').click(function(e) {
            return fillSignInForm($(this));
        });
    });
});