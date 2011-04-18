<%@page import="fr.paris.lutece.plugins.myportal.web.MyPortalApp" %>
<%@page import="fr.paris.lutece.portal.service.util.AppPathService" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr" xml:lang="fr">
    <head>

        <title>LUTECE - My Portal</title>

        <base href="<%= AppPathService.getBaseUrl( request ) %>" />

        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
        <meta http-equiv="Content-Language" content="fr" />
        <meta name="author" content="L'Equipe Lutece" />
        <meta name="copyright" content="Ville de Paris" />
        <meta name="keywords" content="lutece, portail , CMS" />

        <meta name="description" content="CMS Lutece" />
        <!-- Dublin Core metadatas -->
        <meta name="DC.Creator" content="L'Equipe Lutece" />
        <meta name="DC.Rights" content="Ville de Paris" />
        <meta name="DC.Subject" content="lutece, portail , CMS" />
        <meta name="DC.Description" content="CMS Lutece" />


        <!-- no RSS feed -->

        <link rel="stylesheet" href="js/jquery/plugins/treeview/jquery.treeview.css" type="text/css" media="screen, projection" />
        <link rel="stylesheet" href="js/jquery/plugins/ui/datepicker/ui.datepicker.css" type="text/css" media="screen, projection" />
        <link rel="stylesheet" href="js/jquery/plugins/autocomplete/jquery.autocomplete.css" type="text/css" media="screen, projection" />

        <link rel="stylesheet"  href="css/plugins/../../js/jscalendar/calendar-lutece.css" type="text/css"  media="screen" />
        <link rel="stylesheet"  href="css/plugins/myportal/jquery-ui-1.8.10.custom.css" type="text/css"  media="screen" />
        <link rel="stylesheet"  href="css/plugins/myportal/ceebox/css/ceebox-min.css" type="text/css"  media="screen" />

        <link rel="stylesheet"  href="css/plugins/myportal/myportal.css" type="text/css"  media="screen" />


        <link rel="stylesheet" href="css/blueprint/screen.css" type="text/css" media="screen, projection" />
        <link rel="stylesheet" href="css/blueprint/print.css" type="text/css" media="print" />
        <!--[if IE]><link rel="stylesheet" href="css/blueprint/ie.css" type="text/css" media="screen, projection" /><![endif]-->
        <link rel="stylesheet" href="css/commons.css" type="text/css" media="screen, projection" />

        <link rel="stylesheet" href="css/page_template_styles.css" type="text/css" media="screen, projection" />

        <link rel="shortcut icon" href="favicon.ico" />



    </head>
    <body>
        <div class="container">
            <div id="banner" class="span-24 last -lutece-border-radius-top">
                <div id="logo" class="span-4 prepend-1 prepend-top">

                    <a href="jsp/site/Portal.jsp"><img class="png" src="images/logo.png" title="Home" alt="Home"/></a>
                </div>
                <div class="span-10 prepend-top" id="header-link"><!-- no myLutece plugin installed --></div>
                <div id="search" class="span-8 prepend-top append-1 last">
                    <form action="jsp/site/Portal.jsp" method="post">
                        <div class="hide"><input type="hidden" name="page" value="search" /></div>
                        <div>
                            <input class="-lutece-input -lutece-border-radius-mini" type="text" name="query" value="" />
                            <input class="-lutece-input -lutece-border-radius-mini" type="submit" value="Rechercher" />

                        </div>
                    </form>
                </div>
            </div>
            <div id="header-menu" class="span-24 last hide">
                <div class="span-23 prepend-1 last"><!-- no myLutece plugin installed --></div>
            </div>
            <div id="page" class="span-24 last">
                <div id="page-path" class="span-22 prepend-1 append-1 append-bottom last">
		Vous êtes ici : <a target="_top" href="jsp/site/Portal.jsp?page_id=1">accueil</a> &gt;My Portal
                </div>

                <script type="text/javascript">
                    document.write( '<a title="#i18n{myportal.myportal.labeAddTab}" href="jsp/site/RunStandaloneApp.jsp?page=myportal&amp;action=add_tab" class="ceebox">' );
                    document.write( '<img src="images/skin/plugins/myportal/add_widget.png" alt="#i18n{myportal.myportal.labeAddTab}" />&nbsp;Ajouter un onglet' );
                    document.write( '</a>' );
                </script>
                <noscript>
                    <a title="#i18n{myportal.myportal.labeAddTab}" href="jsp/site/Portal.jsp?page=myportal&amp;action=add_tab" class="ceebox">
                        <img src="images/skin/plugins/myportal/add_widget.png" alt="#i18n{myportal.myportal.labeAddTab}" />&nbsp;Ajouter un onglet
                    </a>
                </noscript>
                <br />
                <div id="tabs">


                    <%-- //////////////////////////  Begining of dynamic content  /////////////////////// --%>
                    <%
                            MyPortalApp app = new MyPortalApp();
                            out.print( app.getTabs( request ) );
                    %>
                    <%-- ///////////////////////////  End of dynamic content //////////////////////////////--%>


                    
                    <div class="clear"></div>
                </div>

            </div>
            <div id="footer1" class="span-24 last">
                <div id="footer1-subelement" class="-lutece-border-radius" ><div class="footer1-column">
                        <a target="_top" class="first-level" href="jsp/site/Portal.jsp?page_id=5">L'outil</a>
                        <br/>
                        <a target="_top" class="last-level" href="jsp/site/Portal.jsp?page_id=11">Répondez à notre questionnaire</a>
                        <br/>
                    </div>
                    <div class="footer1-column">
                        <a target="_top" class="first-level" href="jsp/site/Portal.jsp?page_id=3">Documentation</a>
                        <br/>
                        <a target="_top" class="last-level" href="jsp/site/Portal.jsp?page_id=6">Guide utilisateur</a>
                        <br/>
                        <a target="_top" class="last-level" href="jsp/site/Portal.jsp?page_id=7">Guide technique</a>
                        <br/>
                    </div>
                    <div class="footer1-column">
                        <a target="_top" class="first-level" href="jsp/site/Portal.jsp?page_id=10">Développeurs</a>
                        <br/>
                        <a target="_top" class="last-level" href="jsp/site/Portal.jsp?page_id=12">Générateur de code</a>

                        <br/>
                    </div>
                </div>
            </div>
            <div id="footer2" class="span-24 last -lutece-border-radius-bottom">
                <div id="footer-logo" class="span-2 prepend-1 prepend-top append-bottom">
                    <a href="http://fr.lutece.paris.fr" title="Site du portail Lutèce">
                        <img src="images/local/skin/poweredby.jpg" width="55" height="22" alt="Réalisé par Lutèce" />
                    </a>
                </div>
                <div id="footer-links" class="span-18">

                    <a href="jsp/site/Portal.jsp?page=map" accesskey="3" >plan du site</a><br />
                    <a href="jsp/site/Portal.jsp?page=contact" accesskey="7" >contacts</a><br />
                    <a href="jsp/site/PopupCredits.jsp" title="[Nouvelle fenêtre] crédits" target="info_comp">crédits</a><br />
                </div>
                <div id="footer-rss" class="span-2 append-1 prepend-top append-bottom last">
                    <a href="http://fr.lutece.paris.fr/fr/plugins/rss/lutece.xml" title="RSS">
                        <img src="images/local/skin/feed.png" alt="RSS" title="RSS" />

                    </a>
                </div>
            </div>

        </div>

        <script src="js/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
        <script src="js/jquery/plugins/treeview/jquery.cookie.js" type="text/javascript"></script>
        <script src="js/jquery/plugins/treeview/jquery.treeview.pack.js" type="text/javascript"></script>
        <script src="js/jquery/plugins/ui/datepicker/ui.datepicker.js" type="text/javascript"></script>
        <script src="js/jquery/plugins/ui/datepicker/ui.datepicker-fr.js" type="text/javascript"></script>
        <script src="js/jquery/plugins/autocomplete/jquery.autocomplete.pack.js" type="text/javascript"></script>
        <script type='text/javascript' src="js/plugins/myportal/jquery-ui-1.8.10.custom.min.js"></script>
        <script type='text/javascript' src='js/plugins/myportal/jquery.swfobject.js' ></script>
        <script type='text/javascript' src='js/plugins/myportal/jquery.metadata.js'></script>
        <script type='text/javascript' src='js/plugins/myportal/jquery.color.js'></script>
        <script type='text/javascript' src="js/plugins/myportal/jquery.ceebox-min.js"></script>
        <script type='text/javascript' src="js/plugins/myportal/myportal.custom.js"></script>
        <script  src="js/plugins/myportal/myportal.js" type="text/javascript" ></script>

    </body>
</html>



