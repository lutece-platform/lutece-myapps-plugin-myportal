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
import fr.paris.lutece.plugins.myportal.business.WidgetHome;
import fr.paris.lutece.plugins.myportal.service.handler.WidgetHandler;
import fr.paris.lutece.plugins.myportal.service.handler.WidgetHandlerService;
import fr.paris.lutece.portal.service.cache.AbstractCacheableService;

/**
 *
 * @author pierre
 */
public class WidgetContentService extends AbstractCacheableService
{
    private static WidgetContentService _singleton = new WidgetContentService();

    private static final String SERVICE_NAME = "MyPortal Widget Content Service";

    private WidgetContentService()
    {

    }

    public String getName()
    {
        return SERVICE_NAME;
    }

    public static WidgetContentService instance()
    {
        return _singleton;
    }

    public String getWidgetContent( int id )
    {
        String strWidgetId = "" + id;
        String strWidget = (String) getFromCache( strWidgetId );
        if( strWidget == null )
        {
            Widget widget = WidgetHome.findByPrimaryKey(id, null);
            String strType = widget.getWidgetType();
            WidgetHandler handler = WidgetHandlerService.instance().getHandler( strType );
            strWidget = handler.renderWidget( widget.getConfigData() );
            putInCache(strWidgetId, strWidget);
        }
        return strWidget;
    }


}
