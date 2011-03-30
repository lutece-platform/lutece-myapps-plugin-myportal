$(function() {
    $( "#tabs" ).tabs().find(">.ui-tabs-nav").sortable({ axis: "x", items: "li:gt(0)" });

    $( ".myportal-column" ).sortable({
    	connectWith: ".myportal-column",
    	items: "div.myportal-portlet"
    });

    $( ".myportal-portlet" ).addClass( "ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" )
    .find( ".myportal-portlet-header" )
    .addClass( "ui-widget-header ui-corner-all" )
    .append( "<span class='ui-icon ui-icon-circle-triangle-n icon-collapse'></span>")
    .end()
    .find( ".myportal-portlet-content" );

    $( ".myportal-portlet-fixed" ).addClass( "ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" )
    .find( ".myportal-portlet-header" )
    .addClass( "ui-widget-header ui-corner-all" )
    .end()
    .find( ".myportal-portlet-content" );

    $( ".myportal-portlet-header .icon-collapse" ).click(function() {
        $( this ).toggleClass( "ui-icon-circle-triangle-n" ).toggleClass( "ui-icon-circle-triangle-s" );
        $( this ).parents( ".myportal-portlet:first" ).find( ".myportal-portlet-content" ).toggle();
    });

    $( ".myportal-portlet-header .icon-close" ).click(function() {
         var id = $( this ).parents( ".myportal-portlet:first" ).attr('id');
         $.ajax({ type: "POST", url: "jsp/site/plugins/myportal/DoRemoveWidget.jsp", data: "widget=" + id });
         $( this ).parents( ".myportal-portlet:first" ).hide();
    });

    $( ".myportal-column" ).disableSelection();

    // Ceebox init
    jQuery(document).ready(function(){
            $(".ceebox").ceebox({titles:false, borderColor:'#dcdcdc',boxColor:"#fff"});
    });

});

/**
 * Save myportal state on every drap&drop
 */
function saveCurrentConf(  ) {
	var strJson = '{"page":{"name":"Ma page","tabs":[';
	$( "#tabs>ul>li" ).each( function( tabIndex ) {
		var tabId = $( this ).children("a").attr( "href" );
		var tabName = $( this ).children( "a" ).html(  );
		strJson += '{"name": "' + tabName + '","widgets":[';
    	$( tabId ).children( ".myportal-column, .myportal-column-fixed" ).each( function( columnPos ) {
    		columnPos++;
	    	$( this ).children( ".myportal-portlet, .myportal-portlet-fixed").each( function( portletPos ) {
	    		strJson += '{"id":' + $(this).attr("id").substring(7) + ', "state":0,"column":' + columnPos + '},';
	    	});
    	});
		strJson += ']},';
	});
	strJson += ']}}';
    $.ajax({ type: "POST", url: "jsp/site/plugins/myportal/DoSavePortalState.jsp", data: "portalState=" + strJson });
}

$( "#tabs" ).bind( "sortstop", saveCurrentConf );
