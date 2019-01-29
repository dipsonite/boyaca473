$(document).ready(function () {

    $('.resetUser').click(function () {

        var $row = $(this).closest("tr");
        $('#resetForm').attr("action", "/admin/"+$row.find("#user-userName").text()+"/reset");
        $('#userToReset').text($row.find("#user-userName").text());
        $('#userToResetPiso').text($row.find("#user-piso").text());
        $('#userToResetDepto').text($row.find("#user-depto").text());

    });

});


function desloguear() {
    document.logoutForm.submit();
}