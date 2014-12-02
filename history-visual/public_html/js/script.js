function doTheThing(){
    alert('This is the thing!');
}

var elem = document.getElementById("about");
elem.addEventListener('click', doTheThing, false);

var elem2 = document.getElementById("copy");
elem2.addEventListener('click', doTheThing, false);

var elem3 = document.getElementById("chart");
elem3.addEventListener('click', show, false);