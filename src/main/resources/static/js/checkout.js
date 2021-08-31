function numberWithCurrencyFormat(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')+' đ';
}
function checkoutSumary(){
   cCartTotal = $("#cart-total").attr("total");
   shippingCost = jQuery("input[name='shippingID']").attr("cost");
    // covert to currency
    $("#cart-total").text(numberWithCurrencyFormat(Number(cCartTotal)));
    $("#shipping-cost").text(numberWithCurrencyFormat(Number(shippingCost)));
    $("#grand-total").text(numberWithCurrencyFormat(Number(shippingCost)+Number(cCartTotal)));

}
window.onload = checkoutSumary();
$(document).ready(function () {

    $('input:radio[name="shippingID"]').change(
        function () {
            if ($(this).is(':checked')) {
                // append goes here
                cCartTotal = $("#cart-total").attr("total");
                shippingCost = $(this).attr("cost");
                // covert to currency
                $("#cart-total").text(numberWithCurrencyFormat(Number(cCartTotal)));
                $("#shipping-cost").text(numberWithCurrencyFormat(Number(shippingCost)));
                $("#grand-total").text(numberWithCurrencyFormat(Number(shippingCost) + Number(cCartTotal)));
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
