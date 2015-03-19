(function(i){var e=/iPhone/i,n=/iPod/i,o=/iPad/i,t=/(?=.*\bAndroid\b)(?=.*\bMobile\b)/i,r=/Android/i,d=/BlackBerry/i,s=/Opera Mini/i,a=/IEMobile/i,b=/(?=.*\bFirefox\b)(?=.*\bMobile\b)/i,h=RegExp("(?:Nexus 7|BNTV250|Kindle Fire|Silk|GT-P1000)","i"),c=function(i,e){return i.test(e)},l=function(i){var l=i||navigator.userAgent;this.apple={phone:c(e,l),ipod:c(n,l),tablet:c(o,l),device:c(e,l)||c(n,l)||c(o,l)},this.android={phone:c(t,l),tablet:!c(t,l)&&c(r,l),device:c(t,l)||c(r,l)},this.other={blackberry:c(d,l),opera:c(s,l),windows:c(a,l),firefox:c(b,l),device:c(d,l)||c(s,l)||c(a,l)||c(b,l)},this.seven_inch=c(h,l),this.any=this.apple.device||this.android.device||this.other.device||this.seven_inch},v=i.isMobile=new l;v.Class=l})(window);

window.onload = function() {
    selectW1();
};

var selectW1 = function() {
    hideAll();
    document.getElementById('wikingDiv').style.display = 'block';
    wiki.show();
};

var selectW2 = function() {
    hideAll();
    document.getElementById('about').style.display = 'block';
};

var selectW3 = function() {
    hideAll();
    document.getElementById('admin').style.display = 'block';
}

var hideAll = function() {
    var input = document.getElementsByClassName("tab");
    var inputList = Array.prototype.slice.call(input);
    inputList.forEach(function(tab) {tab.style.display = 'none'});
}



var w1 = document.getElementById("W1");
w1.addEventListener('click', selectW1, false);

var w2 = document.getElementById("W2");
w2.addEventListener('click', selectW2, false);

var w3 = document.getElementById("W3");
w3.addEventListener('click', selectW3, false);