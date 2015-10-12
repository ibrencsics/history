
/*jslint         browser : true, continue : true,
  devel  : true, indent  : 2,    maxerr   : 50,
  newcap : true, nomen   : true, plusplus : true,
  regexp : true, sloppy  : true, vars     : false,
  white  : true
*/

/*global $, spa */

spa.header = (function () {

    var configMap = {
      main_html : String()
        + '<div class="center-global cf">'
            + '<header id="header" role="banner">'
                + '<img src="img/l3po_logo.png"/>'
                + '<nav id="nav" role="navigation">'
                    + '<ul>'
                        + '<li><a href="#" id="W1">Wiking</a></li>'
                        + '<li><a href="#" id="W2">About</a></li>'
                        + '<li><a href="#" id="W3">Cypher</a></li>'
                    + '</ul>'
                + '</nav>'
            + '</header>'
        + '</div>',
      settable_map : {
          set_page_anchor : true
      },
      set_page_anchor : null
    },
    stateMap  = { 
        position_type    : 'wiking' 
    },
    jqueryMap = {},

    setJqueryMap, configModule, initModule
    ;

    setJqueryMap = function () {
        var $container = stateMap.$container;
        jqueryMap = { $container : $container };
    };
    
    onSelect = function (event) {
        var set_page_anchor = configMap.set_page_anchor;
        
        switch(event.target.id) {
            case 'W1' :
                set_page_anchor('wiking');
                break;
            case 'W2' :
                set_page_anchor('about');
                break;
            case 'W3' :
                break;
            default :
                break;
        }
        return false;
    }

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
        
        $("nav ul li a").click(onSelect);
        
        return true;
    };

    return {
        configModule : configModule,
        initModule   : initModule
    };
}());
