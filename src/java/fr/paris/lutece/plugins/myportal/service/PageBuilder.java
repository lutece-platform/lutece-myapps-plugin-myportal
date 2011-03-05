/*
 * Copyright (c) 2002-2011, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.myportal.service;

import fr.paris.lutece.plugins.myportal.business.Widget;
import fr.paris.lutece.plugins.myportal.business.page.PageConfig;
import fr.paris.lutece.plugins.myportal.business.page.TabConfig;
import fr.paris.lutece.plugins.myportal.business.page.WidgetConfig;
import fr.paris.lutece.portal.service.security.LuteceUser;

import java.util.List;


/**
 * This is an implentation of a page builder. Other implementation can be injected using Spring IoC
 */
public class PageBuilder implements IPageBuilder
{
    private static final String BEGIN_DIV_COLUMN = "\n<div class=\"myportal-column\">\n";
    private static final String BEGIN_DIV_COLUMN_FIXED = "\n<div class=\"myportal-column-fixed\">\n";
    private static final String BEGIN_DIV_HEADER = "\n<div class=\"myportal-portlet-header\">\n";
    private static final String BEGIN_DIV_CONTENT = "\n<div class=\"myportal-portlet-content\">\n";
    private static final String END_DIV = "\n</div>\n";

    public String buildPage( PageConfig pageConfig, LuteceUser user )
    {
        StringBuilder sb = new StringBuilder(  );

        List<TabConfig> listTabs = pageConfig.getTabList(  );

        int nTab = 1;
        sb.append( "<ul>" );

        for ( TabConfig tab : listTabs )
        {
            sb.append( "<li><a href=\"#tabs-" );
            sb.append( nTab );
            sb.append( "\">" );
            sb.append( tab.getName(  ) );
            sb.append( "</a></li>" );
            nTab++;
        }

        sb.append( "</ul>" );

        nTab = 1;

        for ( TabConfig tab : listTabs )
        {
            StringBuilder sbCol1 = new StringBuilder( BEGIN_DIV_COLUMN );
            StringBuilder sbCol2 = new StringBuilder( BEGIN_DIV_COLUMN );
            StringBuilder sbCol3 = new StringBuilder( BEGIN_DIV_COLUMN_FIXED );
            sb.append( "\n<div id=\"tabs-" );
            sb.append( nTab );
            sb.append( "\" >\n" );

            for ( WidgetConfig widgetConfig : tab.getWidgetList(  ) )
            {
                StringBuilder sbWidget = sbCol1;
                Widget widget = WidgetService.instance(  ).getWidget( widgetConfig.getWidgetId(  ) );

                switch ( widgetConfig.getColumn(  ) )
                {
                    case 2:
                        sbWidget = sbCol2;

                        break;

                    case 3:
                        sbWidget = sbCol3;

                        break;
                }

                sbWidget.append( "\n<div class=\"" );
                sbWidget.append( widget.getCssClass(  ) );
                sbWidget.append( "\" id=\"widget-" );
                sbWidget.append( widget.getIdWidget(  ) );
                sbWidget.append( "\" >\n" );
                sbWidget.append( BEGIN_DIV_HEADER );
                sbWidget.append( widget.getName(  ) );
                sbWidget.append( END_DIV );
                sbWidget.append( BEGIN_DIV_CONTENT );
                sbWidget.append( WidgetContentService.instance(  ).getWidgetContent( widgetConfig.getWidgetId(  ), user ) );
                sbWidget.append( END_DIV );
                sbWidget.append( END_DIV );
            }

            sbCol1.append( END_DIV );
            sbCol2.append( END_DIV );
            sbCol3.append( END_DIV );
            sb.append( sbCol1 );
            sb.append( sbCol2 );
            sb.append( sbCol3 );
            sb.append( END_DIV );
            nTab++;
        }

        return sb.toString(  );
    }
}
