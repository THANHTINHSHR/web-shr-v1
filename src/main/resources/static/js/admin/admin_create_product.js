$(document).ready(function () {
// product
    $("#form-create-product").on("submit", function (e){
        e.preventDefault();
        let data = new FormData(this);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/admin/product/create",
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
                alert("Create Product Fail");
                location.reload();

            }
        })

    })
    // Brand
    $("#form-create-brand").on("submit", function (e){
        e.preventDefault();
        let data = new FormData(this);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/admin/brand/create",
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
                alert("Create Brand Fail");
                location.reload();
            }
        })

    })

    // category
    $("#form-create-category").on("submit", function (e){
        e.preventDefault();
        let data = new FormData(this);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/admin/category/create",
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
                alert("Create Category Fail");
                location.reload();
            }
        })

    })
})