document.addEventListener("DOMContentLoaded", count);

function count() {
    var elementsByClassName = document.getElementsByClassName('priceAll');
    var count = 0;
    // console.log(elementsByClassName);
    for (var i = 0; i < elementsByClassName.length; i++) {
        count += +elementsByClassName[i].textContent.slice(1);
    }
    console.log(count);
    document.getElementById('price').innerText = '$'+count;
}

function changeData() {
    var elementsByClassName = document.getElementsByClassName('aaaa');

    var count = 0;
    var bb=0

    // console.log(elementsByClassName);
    for (var i = 0; i < elementsByClassName.length; i++) {
        var val = elementsByClassName[i].value;
        var ifor=elementsByClassName[i].getAttribute('for').slice(1)
        bb=val*ifor;

        // alert(bb)
        count +=bb;
        bb=0;
    }
    console.log(count);
    document.getElementById('price').innerText = '$'+count;
}



