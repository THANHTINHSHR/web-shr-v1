function numberWithCurrencyFormat(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')+' đ';
}

$(document).ready(function () {
    let currencyClass = $(".currency");
    currencyClass.each(function (index) {
        let cClassValue = $(this).text();
        $(this).text(numberWithCurrencyFormat(Number(cClassValue)));
    })
});
