/*
 * Copyright (c) 2002-2008, Mairie de Paris
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

import fr.paris.lutece.plugins.myportal.business.page.PageConfig;
import fr.paris.lutece.plugins.myportal.business.page.TabConfig;
import fr.paris.lutece.plugins.myportal.business.page.WidgetConfig;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

/**
 * PageConfigJsonUtil
 */
public class PageConfigJsonUtil
{

    public static PageConfig parseJson(String jsonflow)
    {
        PageConfig pageConfig = new PageConfig();
        try
        {
            JSONTokener tokener = new JSONTokener(jsonflow);
            JSONObject json = (JSONObject) tokener.nextValue();
            JSONObject jsonPage = (JSONObject) json.get("page");
            pageConfig.setName(jsonPage.getString("name"));
            JSONArray jsonTabs = jsonPage.getJSONArray("tabs");
            List<TabConfig> listTabs = new ArrayList<TabConfig>();
            for (int i = 0; i < jsonTabs.size(); i++)
            {
                JSONObject jsonTab = jsonTabs.getJSONObject(i);
                TabConfig tab = new TabConfig();
                tab.setName(jsonTab.getString("name"));
                JSONArray jsonWidgets = jsonTab.getJSONArray("widgets");
                List<WidgetConfig> listWidgets = new ArrayList<WidgetConfig>();
                for (int j = 0; j < jsonWidgets.size(); j++)
                {
                    JSONObject jsonWidget = jsonWidgets.getJSONObject(j);
                    WidgetConfig widget = new WidgetConfig();
                    widget.setWidgetId(jsonWidget.getInt("id"));
                    widget.setWidgetState(jsonWidget.getInt("state"));
                    listWidgets.add(widget);
                }
                tab.setWidgetList(listWidgets);
                listTabs.add(tab);
            }
            pageConfig.setTabList(listTabs);
        } catch (JSONException e)
        {
            throw new RuntimeException("JSON Parsing Error : " + e.getMessage(), e);
        }


        return pageConfig;

    }
}
