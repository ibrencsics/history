/*
 * spa.shell.js
 * Shell module for SPA
*/

/*jslint         browser : true, continue : true,
  devel  : true, indent  : 2,    maxerr   : 50,
  newcap : true, nomen   : true, plusplus : true,
  regexp : true, sloppy  : true, vars     : false,
  white  : true
*/
/*global $, spa */

spa.shell = (function () {
  //---------------- BEGIN MODULE SCOPE VARIABLES --------------
  var
    configMap = {
      main_html : String()
//        + '<div class="center-global cf">'
//            + '<header id="header" role="banner">'
//                + '<img src="img/l3po_logo.png"/>'
//                + '<nav id="nav" role="navigation">'
//                    + '<ul>'
//                        + '<li><a href="#" id="W1">Wiking</a></li>'
//                        + '<li><a href="#" id="W2">About</a></li>'
//                        + '<li><a href="#" id="W3">Cypher</a></li>'
//                    + '</ul>'
//                + '</nav>'
//            + '</header>'
//        + '</div>'
        + '<div class="spa-shell-header"></div>'
        + '<div class="spa-shell-warning"></div>'
        + '<div class="spa-shell-workspace"></div>'
        + '<div class="spa-shell-footer"></div>'
    },
    stateMap  = { $container : null },
    jqueryMap = {},

    setJqueryMap, initModule;
  //----------------- END MODULE SCOPE VARIABLES ---------------

  //-------------------- BEGIN UTILITY METHODS -----------------
  //--------------------- END UTILITY METHODS ------------------

  //--------------------- BEGIN DOM METHODS --------------------
  // Begin DOM method /setJqueryMap/
  setJqueryMap = function () {
    var $container = stateMap.$container;
    jqueryMap = { $container : $container };
  };
  // End DOM method /setJqueryMap/
  //--------------------- END DOM METHODS ----------------------

  //------------------- BEGIN EVENT HANDLERS -------------------
  //-------------------- END EVENT HANDLERS --------------------

  //------------------- BEGIN PUBLIC METHODS -------------------
  // Begin Public method /initModule/
  initModule = function ( $container ) {
    stateMap.$container = $container;
    $container.html( configMap.main_html );
    setJqueryMap();
  };
  // End PUBLIC method /initModule/

  return { initModule : initModule };
  //------------------- END PUBLIC METHODS ---------------------
}());
