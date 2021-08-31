$(document).ready(function () {
    let currencyClass = $(".currency");
    currencyClass.each(function (index) {
        let cClassValue = $(this).text();
        $(this).text(numberWithCurrencyFormat(Number(cClassValue)));
    })
});
