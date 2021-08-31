$ (document).ready(function (){
    $(logout).on("click",function (e){
        e.preventDefault();
        document.logoutForm.submit()
    })

});

/*
covert raw number format to currency format
 */
function numberWithCurrencyFormat(x) {
    document.write(x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')+' đ');
}
// CART - TOTAL
$('.cart-total ')
// QUANTITY CONTROLL
$(document).ready(function (){
    $(".btn-minus").on("click", function (evt){
        evt.preventDefault();
        productId = $(this).attr("pid");
        qtyInput = $("#quantity" + productId);
        newQty = parseInt(qtyInput.val()) -1;
        if(newQty > 0) qtyInput.val(newQty);
        path = "/cart/add/"+productId+"/"+1;
        addToCart(path);
    })
    $(".btn-plus").on("click", function (evt){
        evt.preventDefault();
        productId = $(this).attr("pid");
        qtyInput = $("#quantity" + productId);
        newQty = parseInt(qtyInput.val()) +1;
        if(newQty > 0) qtyInput.val(newQty);
        path = "/cart/add/"+productId+"/"+1;
        addToCart(path);
    })

});

function addToCart(path){

    // ajax
    $.ajax({
        type : "POST",
        url : path,

        success : function (respone){
            alert("success" + respone);
            // location.reload();



            // $(".result").empty().text(respone);

        },
        error: function(xhr, textStatus, errorThrow){
            alert( xhr.status);
            console.log(xhr);
        }

    });
}