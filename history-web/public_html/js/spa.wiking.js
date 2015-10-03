
/*jslint         browser : true, continue : true,
  devel  : true, indent  : 2,    maxerr   : 50,
  newcap : true, nomen   : true, plusplus : true,
  regexp : true, sloppy  : true, vars     : false,
  white  : true
*/

/*global $, spa */

spa.wiking = (function () {
  
    var configMap = {
      main_html : String()
        
        + '<div class="spa-wiking" class="center-global cf tab">'
            + '<div class="spa-wiking-svgcontainer">'
                + '<div class="spa-wiking-svgcontrols">'
                        + '<input id="spa-wiking-page" type="text" class="spa-wiking-page shadow"/>'
                        + '<button id="spa-wiking-go" class="shadow">Go</button>'
                + '</div>'
                + '<svg class="shadow"/>'
            + '</div>'

            + '<aside class="spa-wiking-sidebar">'
                + '<div id="data" class="shadow"></div>'
            + '</aside>'
        + '</div>',
      
        settable_map : {}
    },
    stateMap  = { $container : null },
    jqueryMap = {},

    setJqueryMap, configModule, initModule
    ;

  
    setJqueryMap = function () {
        var $container = stateMap.$container;
        jqueryMap = { $container : $container };
    };

    configModule = function ( input_map ) {
        spa.util.setConfigMap({
          input_map    : input_map,
          settable_map : configMap.settable_map,
          config_map   : configMap
        });
        return true;
    };

    initModule = function ( $container ) {
        $container.html( configMap.main_html );
        stateMap.$container = $container;
        setJqueryMap();
        return true;
    };

    return {
        configModule : configModule,
        initModule   : initModule
    };
}());
