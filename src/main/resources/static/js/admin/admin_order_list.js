$(document).ready(function () {
    $(".update-status").on("click", function (e) {
        e.preventDefault();
        // alert("save clicked");
        let oID = $(this).attr("oid");
        let formData = $("#formData-" + oID)[0];
        let data = new FormData(formData);
        data.append("oID", oID);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/admin/order/update-status",
            data: data,

            // prevent jQuery from automatically transforming the data into a query string
            processData: false,
            contentType: false,
            cache: false,
            timeout: 1000000,
            success: function (res) {
                alert(res);
                location.reload();
            },
            error: function (res) {
                alert("Update Status Fail");
                location.reload();
            }

        });

    })


});