function numberWithCurrencyFormat(x) {
    // document.write(x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')+' vnđ');
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')+' vnđ';
}

function codeAddress(dd){
    // document.getElementById("test").innerHTML=Date();

    // rowMoney = document.getElementById("test").getAttribute("pid");
    // rowMoney = numberWithCurrencyFormat(rowMoney);
    // document.getElementById("test").innerText = rowMoney;
    var x= document.getElementsByClassName("class-test");
    for (i =0; i < x.length; i++){
        valueX = x[i].getAttribute("pid");
        document.getElementById("test"+valueX).innerText = numberWithCurrencyFormat(valueX);
    }
}
window.onload = codeAddress();
