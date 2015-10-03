spa.about = (function () {
    
    configModule = function ( input_map ) {
        return true;
    }
    
    initModule = function ( $container ) {
        $container.load( "html/spa.about.html" );
        return true;
    }
    
    return {
        configModule : configModule,
        initModule   : initModule
    };
}());