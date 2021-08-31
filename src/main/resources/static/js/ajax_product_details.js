// add to cart (product details page)
$(document).ready(function () {
    $("#btnAddToCart").on("click", function (e) {
        e.preventDefault();
        productId = $(this).attr("pid");
        qtyInput = $("#quantity" + productId).val();
        path = "/cart/add/" + productId + "/" + qtyInput;
        addToCart(path);
    })

});

function addToCart(path) {

    // ajax
    $.ajax({
        type: "POST",
        url: path,

        success: function (respone) {
            alert(" item(s) was added in your cart")
        },
        error: function (xhr, textStatus, errorThrow) {
            alert(xhr.status);
        }

    });
}

// QUANTITY CONTROLL
$(document).ready(function () {
    $(".btn-minus").on("click", function (evt) {
        evt.preventDefault();
        productId = $(this).attr("pid");
        qtyInput = $("#quantity" + productId);
        newQty = parseInt(qtyInput.val()) - 1;
        if (newQty > 0) qtyInput.val(newQty);

    })
    $(".btn-plus").on("click", function (evt) {
        evt.preventDefault();
        productId = $(this).attr("pid");
        qtyInput = $("#quantity" + productId);
        newQty = parseInt(qtyInput.val()) + 1;
        if (newQty > 0) qtyInput.val(newQty);

    })

});
// description
$(document).ready(function () {

    let pDescription = $("#p-p-d").attr("pden");
    $("#p-p-d").append(pDescription);
})
////////////////// REVIEW///////////
$(document).on("click", "ul.pagination li a", function (e) {

    e.preventDefault();
    let activeValue = parseInt($("ul.pagination li.active").text()); // current page
    let val = $(this).text();
    let totalPages = $("ul.pagination").attr("totalPages");
    let pid = $("ul.pagination").attr("pid");


    // rewrite
    fetchReviews(val, pid);
    let currentActive = $("li.active");
    // remove .active class for the old li tag
    currentActive.removeClass("active");
    // add .active to next-pagination li
    $(this).parent("li").addClass("active");


})

// function ajax review
function fetchReviews(page, pid) {
    // $.ajax({
    $.getJSON({
        type: "POST",
        url: "/product/reviews/page",
        data: {
            page: page,
            pid: pid,
        },
        success: function (respone) {

            $(".reviews").empty();
            // $(".reviews").append("<p >"+ 'HA HA HA HA HA' +"</p> ");

            $.each(respone, (i, review) => {
                // let reviewRow = "<p >"+ 'HA HA HA HA HA' +"</p> ";
                let reviewRow = "     <div class=\"reviews-submitted\">\n" +
                    "                                                    <div class=\"row\">\n" +
                    "                                                        <div class=\"col-md-3\">\n" +
                    "                                                            <div class=\"reviewer\">\n" +
                    "<p class=\"reviewer-name\">" + review.userName + "</p>" +
                    "<p class=\"create-time\">" + 'Create time : ' + review.createTime + "</p>" +
                    "<p class=\"buy-time\">" + 'Buy Time : ' + review.buyTime + "</p>" +
                    "                                                            </div>\n" +
                    "\n" +
                    "                                                        </div>\n" +
                    "                                                        <div class=\"col-md-9\">\n" +
                    "                                                            <div class=\"ratting\">\n" +
                    "                                                                <i class=\"" + ((review.rating >= 1) ? 'fa fa-star light' : 'fa fa-star') + "\"></i>\n" +
                    "                                                           <i class=\"" + ((review.rating >= 2) ? 'fa fa-star light' : 'fa fa-star') + "\"></i>\n" +
                    "                                                                <i class=\"" + ((review.rating >= 3) ? 'fa fa-star light' : 'fa fa-star') + "\"></i>\n" +
                    "                                                              <i class=\"" + ((review.rating >= 4) ? 'fa fa-star light' : 'fa fa-star') + "\"></i>\n" +
                    "                              <i class=\"" + ((review.rating >= 5) ? 'fa fa-star light' : 'fa fa-star') + "\"></i>\n" +
                    "                                                            </div>\n" +
                    "                                                            <p >" + review.review +
                    "                                                            </p>\n" +
                    ((review.image != null) ?
                        "                                                                <img class=\"review-image\"\n" +
                        "                                                                    src='/img/Review_Images/product_" + review.pid + "/" + review.image + "'\">\n" : "") +
                    "                                                            </block>\n" +
                    "                                                        </div>\n" +
                    "                                                    </div>\n" +
                    "                                                </div>";
                $(".reviews").append(reviewRow);
            })

        },
        error: function (e) {
            alert("Some ERROR");
            console.log(e);
        }

    })

}


$(document).ready(function () {
    let currencyClass = $(".currency");
    currencyClass.each(function (index) {
        let cClassValue = $(this).text();
        $(this).text(numberWithCurrencyFormat(Number(cClassValue)));
    })
});
