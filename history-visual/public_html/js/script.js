window.onload = function() {
    selectW1();
};

var selectW1 = function() {
    document.getElementById('wikiBrowser').style.display = 'inline';
    document.getElementById('about').style.display = 'none';
    wiki.show();
};

var selectW2 = function() {
    document.getElementById('wikiBrowser').style.display = 'none';
    document.getElementById('about').style.display = 'inline';
};

var w1 = document.getElementById("W1");
w1.addEventListener('click', selectW1, false);

var w2 = document.getElementById("W2");
w2.addEventListener('click', selectW2, false);