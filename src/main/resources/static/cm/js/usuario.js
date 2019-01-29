$(document).ready(function () {

    $("#submit").validate({
        rules: {
            password: {
                required: true,
                minlength: 6,
                maxlength: 10,

            },

            password2: {
                equalTo: "#password",
                minlength: 6,
                maxlength: 10
            }


        },
        messages: {
            password: {
                required: "La contrase&ntilde;a es requerida."

            }
        }

    });

});