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

    var configMap = {
        
        anchor_schema_map : {
            page  : { wiking : true, about : true }
        },
      
        main_html: String()
                + '<div class="spa-shell-header"></div>'
                + '<div class="spa-shell-warning"></div>'
                + '<div class="spa-shell-workspace"></div>'
                + '<div class="spa-shell-footer"></div>'
    },
    
    stateMap = { anchor_map : {} },
    
    jqueryMap = {},
            
    copyAnchorMap, setJqueryMap, initModule;

    copyAnchorMap = function () {
        return $.extend(true, {}, stateMap.anchor_map);
    };

    setJqueryMap = function () {
        var $container = stateMap.$container;
        jqueryMap = {
            $container: $container,
            $workspace: $container.find('.spa-shell-workspace'),
            $header: $container.find('.spa-shell-header')
        };
    };

    changeAnchorPart = function (arg_map) {
        var
            anchor_map_revise = copyAnchorMap(),
            bool_return = true,
            key_name, key_name_dep;

        // Begin merge changes into anchor map
        KEYVAL:
        for (key_name in arg_map) {
            if (arg_map.hasOwnProperty(key_name)) {

                // skip dependent keys during iteration
                if (key_name.indexOf('_') === 0) {
                    continue KEYVAL;
                }

                // update independent key value
                anchor_map_revise[key_name] = arg_map[key_name];

                // update matching dependent key
                key_name_dep = '_' + key_name;
                if (arg_map[key_name_dep]) {
                    anchor_map_revise[key_name_dep] = arg_map[key_name_dep];
                }
                else {
                    delete anchor_map_revise[key_name_dep];
                    delete anchor_map_revise['_s' + key_name_dep];
                }
            }
        }
        // End merge changes into anchor map

        // Begin attempt to update URI; revert if not successful
        try {
            $.uriAnchor.setAnchor(anchor_map_revise);
        }
        catch (error) {
            // replace URI with existing state
            $.uriAnchor.setAnchor(stateMap.anchor_map, null, true);
            bool_return = false;
        }
        // End attempt to update URI...

        return bool_return;
    };
    
    onHashchange = function (event) {
        var
            _s_page_previous, _s_page_proposed, s_page_proposed,
            anchor_map_proposed,
            is_ok = true,
            anchor_map_previous = copyAnchorMap();

        // attempt to parse anchor
        try {
            anchor_map_proposed = $.uriAnchor.makeAnchorMap();
        }
        catch (error) {
            $.uriAnchor.setAnchor(anchor_map_previous, null, true);
            return false;
        }
        stateMap.anchor_map = anchor_map_proposed;

        // convenience vars
        _s_page_previous = anchor_map_previous._s_page;
        _s_page_proposed = anchor_map_proposed._s_page;
        
        // Begin adjust page component if changed
        if (!anchor_map_previous || _s_page_previous !== _s_page_proposed) {
            s_page_proposed = anchor_map_proposed.page;
            switch (s_page_proposed) {
                case 'wiking' :
                    spa.wiking.configModule( {} );
                    spa.wiking.initModule( jqueryMap.$workspace );
                    
//                    is_ok = spa.page.setSliderPosition('opened');
                    break;
                case 'about' :
                    spa.about.configModule( {} );
                    spa.about.initModule( jqueryMap.$workspace );
                    
//                    is_ok = spa.page.setSliderPosition('closed');
                    break;
                default :
//                    spa.page.setSliderPosition('closed');
                    delete anchor_map_proposed.page;
                    $.uriAnchor.setAnchor(anchor_map_proposed, null, true);
            }
        }
        // End adjust page component if changed

        // Begin revert anchor if slider change denied
        if (!is_ok) {
            if (anchor_map_previous) {
                $.uriAnchor.setAnchor(anchor_map_previous, null, true);
                stateMap.anchor_map = anchor_map_previous;
            }
            else {
                delete anchor_map_proposed.page;
                $.uriAnchor.setAnchor(anchor_map_proposed, null, true);
            }
        }
        // End revert anchor if slider change denied

        return false;
    };
    
    setPageAnchor = function ( position_type ){
        return changeAnchorPart({ page : position_type });
    };

    initModule = function ($container) {
        stateMap.$container = $container;
        $container.html(configMap.main_html);
        setJqueryMap();

        $.uriAnchor.configModule({
            schema_map : configMap.anchor_schema_map
        });    
        
//        spa.wiking.configModule( {} );
//        spa.wiking.initModule( jqueryMap.$wiking );

        spa.header.configModule({
            set_page_anchor : setPageAnchor,
//            page_model      : spa.model.page
        });
        spa.header.initModule(jqueryMap.$header);

//        spa.about.initModule(jqueryMap.$wiking);
        
        $(window)
            .bind( 'hashchange', onHashchange )
            .trigger( 'hashchange' );
    };

    return {initModule: initModule};
}());
