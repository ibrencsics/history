function doTheThing(){
    alert('This is the thing!');
}

var elem = document.getElementById("about");
elem.addEventListener('click', doTheThing, false);

var elem2 = document.getElementById("copy");
elem2.addEventListener('click', doTheThing, false);

var elem3 = document.getElementById("chart1");
elem3.addEventListener('click', case0410.show, false);

var elem4 = document.getElementById("chart2");
elem4.addEventListener('click', case0420.show, false);

var elem5 = document.getElementById("family-tree");
elem5.addEventListener('click', familyTree.show, false)