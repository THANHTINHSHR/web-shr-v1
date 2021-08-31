$(document).ready(function () {

    $("#form-edit-product").on("submit", function (e){
        e.preventDefault();
        let pid = $(this).attr("pid");
        let data = new FormData(this);
        data.append("pid", pid);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/admin/product/edit",
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
            error : function (res){
                alert("Update Fail");
                location.reload();
            }
        })

    })
})