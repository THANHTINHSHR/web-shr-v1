/*
covert raw number format to currency format
 */
function numberWithCurrencyFormat(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.') + ' đ';
}

/*
covert currency format to number
 */
function covertDotNumberToStringNumber(x) {
    return x.toString().replace('đ', '').replace('.', '');

}

/*
SUP-TOTAL covert on load
 */

// $
function formatCurency() {
    // sup- total
    allE = document.getElementsByClassName("sub-total");
    for (i = 0; i < allE.length; i++) {

        total = allE[i].getAttribute("total");
        pid = allE[i].getAttribute("pid");
        document.getElementById("total" + pid).innerText = numberWithCurrencyFormat(total);
    }
    // price
    allP = document.getElementsByClassName("price");
    for (i = 0; i < allP.length; i++) {

        price = allP[i].getAttribute("price");
        pid = allP[i].getAttribute("pid");
        document.getElementById("price" + pid).innerText = numberWithCurrencyFormat(price);
    }
    // cart - total
    cartTotal = document.getElementById("cart-total").getAttribute("total");
    document.getElementById("cart-total").innerText = numberWithCurrencyFormat(Number(cartTotal));
    // grand - total
    document.getElementById("grand-total").innerText = numberWithCurrencyFormat(Number(cartTotal) + Number(50000));
}

window.onload = formatCurency();
/*
Cart - total and grand total
 */


// QUANTITY CONTROLL
$(document).ready(function () {
    $(".btn-minus").on("click", function (evt) {
        evt.preventDefault();
        productId = $(this).attr("pid");
        qtyInput = $("#quantity" + productId);
        newQty = parseInt(qtyInput.val()) - 1;
        if (newQty > 0 && newQty <= 98) {
            qtyInput.val(newQty);
            path = "/cart/update/" + productId + "/" + newQty;
            updateQuantity(path, productId);

        }
        if (newQty >= 99) {
            newQty = 98;
            qtyInput.val(newQty);
        }

    })
    $(".btn-plus").on("click", function (evt) {
        evt.preventDefault();
        productId = $(this).attr("pid");
        qtyInput = $("#quantity" + productId);
        newQty = parseInt(qtyInput.val()) + 1;
        if (newQty > 0 && newQty <= 98) {
            qtyInput.val(newQty);
            path = "/cart/update/" + productId + "/" + newQty;
            updateQuantity(path, productId);

        }
        if (newQty >= 99) {
            newQty = 98;
            qtyInput.val(newQty);
        }
    })

});

function updateQuantity(path, productId) {

    // ajax
    $.ajax({
        type: "POST",
        url: path,

        success: function (respone) {
            $("#total" + productId).text(numberWithCurrencyFormat(respone));
            updateCartSumary();
        },
        error: function (xhr, textStatus, errorThrow) {
            alert(xhr.status);
            console.log(xhr);
        }

    });
}

function updateCartSumary() {
    // ajax
    $.ajax({
        type: "POST",
        url: "/cart",

        success: function (supTotal) {

            $("#cart-total").text(numberWithCurrencyFormat(Number(supTotal)));

            document.getElementById("grand-total").innerText = numberWithCurrencyFormat(Number(supTotal) + Number(50000));


        },
        error: function (xhr, textStatus, errorThrow) {
            alert(xhr.status);
            console.log(xhr);
        }

    })
}
// DELETE CONTROLL
$(document).ready(function () {
    $(".delete-item").on("click", function (evt) {
        evt.preventDefault();
        productId = $(this).attr("id");
        rowNumber = $(this).attr("rowNumber");
        path = "/cart/delete/" + productId;
        $.ajax({
            type: "POST",
            url: path,
            success: function (res) {
                if (res == false) {

                    $(".cart-content").empty();
                    let appendResult = "<div class =\"ui-state-error-text\">\n" +
                        "        <h2>Your cart is empty</h2>\n" +
                        "\n" +
                        "    </div>";
                    $(".cart-content").append(appendResult);
                } else {
                    $('#' + 'row' + rowNumber).remove();
                    updateCartSumary();
                }
            },
            error: function (xhr, textStatus, errorThrow) {
                alert(xhr.status);
                console.log(xhr);
            }
        })
    })


})






