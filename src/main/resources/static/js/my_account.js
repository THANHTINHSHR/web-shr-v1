$(document).ready(function () {
    // fetchUserOrders(1,true,false,false,null);
    function fetchUserOrders(page, shortByCreateDate, shortByGrandTotal, desc, searchText) {
        // default values
        let pageNumber = page;
        let pageSize = 5;
        let sBCD = shortByCreateDate;
        let sBGT = shortByGrandTotal;
        let sDESC = desc;
        let searchT = (searchText == undefined || searchText == "") ? "" : searchText;
        // let searchS = (typeof searchValue !== 'undefined' || searchValue !== null) ? searchValue: null;

        /*
        fetching data
         */
        $.ajax({
            type: "POST",
            url: "/my-account/orders",
            data: {
                // like parameter
                currentPage: pageNumber,

                sByCreateDate: sBCD,
                sByGrandTotal: sBGT,
                isDESC: sDESC,
                pageSize: pageSize,
                searchText: searchT,
            },
            success: function (response) {
                if (response.isEmpty == "true") {
                    // alert("empty order");
                    $(".table-responsive").empty();
                    // $("ul.pagination").empty();
                    $("#orders-empty").text("No result found!");

                } else {
                    $("#orders-empty").empty();

                    // clear tbody
                    $("#user_orders tbody").empty();
                    // feach tbody
                    //  $('user-order-body').empty()
                    $.each(response.orders, (i, order) => {
                        let orderRow = "<tr>" + " <td width='20%'>" + order.orderId + "</td>" +
                            "<td width='30%'>" + order.createDate + "</td>" +
                            "<td width='20%'>" + numberWithCurrencyFormat(Number(order.grandTotal)) + "</td>" +
                            "<td width='20%'>" + order.status + "</td>" +
                            "<td width='10%'> <a href=\"/my-account/orders/view/" + order.orderId + "\"> <button class=\"btn\" th:id=\"view-order-btn\">View</button></a>\n</td>" +
                            "</tr>";
                        $("#user_orders tbody").append(orderRow);
                    });
                    if ($('ul.pagination li').length - 2 != response.totalPages) {
                        // build pagination list at the first time loading
                        $('ul.pagination').empty();
                        buildPagination(response.totalPages);
                    }
                }
            },
            error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }


        })
    }

// });
    /**
     *
     * Build the pagination Bar from totalPages
     */
    function buildPagination(totalPages) {
        // Build paging navigation
        let pageIndex = '<li class="page-item"><a class="page-link">Previous</a></li>';
        $("ul.pagination").append(pageIndex);

        // create pagination
        for (let i = 1; i <= totalPages; i++) {
            // adding .active class on the first pageIndex for the loading time
            if (i == 1) {
                pageIndex = "<li class='page-item active'><a class='page-link'>"
                    + i + "</a></li>"
            } else {
                pageIndex = "<li class='page-item'><a class='page-link'>"
                    + i + "</a></li>"
            }
            $("ul.pagination").append(pageIndex);
        }

        pageIndex = '<li class="page-item"><a class="page-link">Next</a></li>';
        $("ul.pagination").append(pageIndex);
        // first time, current page =1
        // add attribute current page = 1
        // $(".pagination").attr("currentPage", 1);

    }


//
    $(document).on("click", "ul.pagination li a", function () {
        let searchText = $("#order-search-input").attr("search-text");
        let activeValue = parseInt($("ul.pagination li.active").text()); // current page
        let val = $(this).text()
// get value form select (sort by)
        let sortByCreateDate = true;
        let sortByGrandTotal = false;
        let isDESC = false;
        let sortBy = $("#sort-by").children("option:selected").val();
        if (sortBy == "CD-OTN") {
            sortByCreateDate = true;
            sortByGrandTotal = false;
            isDESC = false;
        }
        if (sortBy == "CD-NTO") {
            sortByCreateDate = true;
            sortByGrandTotal = false;
            isDESC = true;
        }
        if (sortBy == "GT_MiTMa") {
            sortByCreateDate = false;
            sortByGrandTotal = true;
            isDESC = false;
        }
        if (sortBy == "GT_MaTMi") {
            sortByCreateDate = false;
            sortByGrandTotal = true;
            isDESC = true;
        }
        // click on the NEXT tag
        if (val.toUpperCase() === "NEXT") {
            // let activeValue = parseInt($("ul.pagination li.active").text());
            let totalPages = $("ul.pagination li").length - 2; // -2 beacause 1 for Previous and 1 for Next
            if (activeValue < totalPages) {
                let currentActive = $("li.active");
                fetchUserOrders(activeValue, sortByCreateDate, sortByGrandTotal, isDESCn, searchText); // get next page value
                // remove .active class for the old li tag
                $("li.active").removeClass("active");
                // add .active to next-pagination li
                currentActive.next().addClass("active");
            }
        } else if (val.toUpperCase() === "PREVIOUS") {
            // let activeValue = parseInt($("ul.pagination li.active").text());
            if (activeValue > 1) {
                // get the previous page
                fetchUserOrders(activeValue - 2, sortByCreateDate, sortByGrandTotal, isDESC, searchText);
                let currentActive = $("li.active");
                currentActive.removeClass("active");
                // add .active to previous-pagination li
                currentActive.prev().addClass("active");
            }
        } else {
            fetchUserOrders(parseInt(val) - 1, sortByCreateDate, sortByGrandTotal, isDESC, searchText);
            // add focus to the li tag
            $("li.active").removeClass("active");
            $(this).parent().addClass("active");
        }
    });
    /////////////////////////////////////////////////
    ////////////////// User Order search/////////////
    $(document).on("click", "#order-search-btn", function () {
        let inputText = $("#order-search-input").val();
        if (typeof inputText != undefined && inputText != null || inputText != "") {
            // default search with sort by create date desc
            let sortByCreateDate = true;
            let sortByGrandTotal = false;
            let isDESC = true;

            // call method ajax fetch
            fetchUserOrders(0, sortByCreateDate, sortByGrandTotal, isDESC, inputText);

            // save attribute for search. (save at search input with attribute)
            // only save attribute when search btn clicked
            $("#order-search-input").attr("search-text", inputText);
            $("#sort-by").val("CD-OTN").change();
            $("li.active").removeClass("active");
            $("ul.pagination li:nth-child(2)").addClass("active");

        }

    });
    /*
    event when select sort-by
     */
    $(document).on("change", "select.custom-select-sm", function () {
// alert($(this).val());
        let searchText = $("#order-search-input").attr("search-text");
        // alert(searchText);
        let sortByCreateDate = true;
        let sortByGrandTotal = false;
        let isDESC = true;
        let sortBy = $("#sort-by").children("option:selected").val();
        if (sortBy == "CD-OTN") {
            sortByCreateDate = true;
            sortByGrandTotal = false;
            isDESC = false;
        }
        if (sortBy == "CD-NTO") {
            sortByCreateDate = true;
            sortByGrandTotal = false;
            isDESC = true;
        }
        if (sortBy == "GT_MiTMa") {
            sortByCreateDate = false;
            sortByGrandTotal = true;
            isDESC = false;
        }
        if (sortBy == "GT_MaTMi") {
            sortByCreateDate = false;
            sortByGrandTotal = true;
            isDESC = true;
        }
        // alert(sortByCreateDate +"|" + sortByGrandTotal +"|" + isDESC);
        fetchUserOrders(0, sortByCreateDate, sortByGrandTotal, isDESC, searchText);
        $("li.active").removeClass("active");
        $("ul.pagination li:nth-child(2)").addClass("active");
    });


    $("input[name$='re-new-pass']").keyup(function () {
        let rePass = $('input[name$="re-new-pass"]').val();
        let newPass = $('input[name$="new-pass"]').val();
        if (rePass != newPass) {
            $("#updatePassMess").text("INCORRECT RETYPE NEW PASSWORD").css("color", "blue");
        } else {
            $("#updatePassMess").empty();

        }
    })

});
$(document).ready(function () {
    let currencyClass = $(".currency");
    currencyClass.each(function (index) {
        let cClassValue = $(this).text();
        $(this).text(numberWithCurrencyFormat(Number(cClassValue)));
    })
});

$('.addImage').on('change', function () {
    let pid = $(this).attr("pid")
    // output raw value of file input
    $('#filename-' + pid).html($(this).val().replace(/.*(\/|\\)/, ''));

});

$("textarea[name$='reviewText']").keyup(function () {
    let reviewText = $(this).val();
    if (reviewText != undefined && reviewText.length >= 4) {
        $(".review-require").empty();
        $('.modal-footer button.btn-primary').prop('disabled', false);


    } else {
        $(".review-require").text("Review must be more than 4 characters").css("color", "blue");
        $('.modal-footer button.btn-primary').prop('disabled', true);

    }
})

// try with fromData
$(document).ready(function () {
    // bind form submit event
    $(".modal-footer button.btn-primary").on("click", function (event) {

        // Stop default form Submit.
        event.preventDefault();

// get pid and form
        let pid = $(this).attr("pid");
        let ocd = $(this).attr("ocd");
        let formData = $("#form-" + pid)[0];
        let data = new FormData(formData);
        data.append("pid", pid);
        data.append("ocd", ocd);
        // cancel the default behavior

        // use $.ajax() to upload file
        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/review/new2",
            data: data,

            // prevent jQuery from automatically transforming the data into a query string
            processData: false,
            contentType: false,
            cache: false,
            timeout: 1000000,
            success: function (res) {


                // $("#reviewModal"+pid).modal.hide();
            $("p.header-result").text("Review success");
            $("p.body-result").text("Your review saved, thanks for your review!").css({"text-alight" : "center", "color" : "blue"});


            $("#review-result").modal("show");
            setTimeout(function(){
                $("#review-result").modal("hide");
            }, 2000);
                console.log(res);
                $('#filename-' + pid).empty();
                $('#review-text-' + pid).val('');
                $("#addImage-"+pid).val('');
                $("#submit-"+ pid).prop('disabled', true);
            },
            error: function (err) {

                // $("#result").html(err.responseText);
                // console.log("ERROR : ", err.responseText);
                // $("#submitButton").prop("disabled", false);

                // $("#reviewModal"+pid).modal.hide();
            // alert("review add fail")
            $("p.header-result").text("Review fail");
            $("p.body-result").text("An error occurred, please try again next time!").css({"text-alight" : "center", "color" : "yellow"});
            $("#review-result").modal("show");
            setTimeout(function(){
                $("#review-result").modal("hide");
            }, 2000);
                console.error(err);
                $('#filename-' + pid).empty();
                $('#review-text-' + pid).val('');
                $("#addImage-"+pid).val('');
                $("#submit-"+ pid).prop('disabled', true);
            }
        });
    });
});