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
package fr.paris.lutece.plugins.myportal.business;

import org.apache.commons.lang.StringUtils;


/**
 *
 * WidgetFilter
 *
 */
public class WidgetFilter
{
    public static final int FILTER_TRUE = 1;
    private static final int ALL_INT = -1;
    private boolean _bIsWideSearch;
    private String _strName;
    private String _strDescription;
    private int _nIdCategory;
    private int _nIdStyle;
    private String _strWidgetType;
    private int _nStatus;
    private int _nIsEssential;
    private int _nIsNew;

    /**
     * Constructor
     */
    public WidgetFilter(  )
    {
        _bIsWideSearch = true;
        _strName = StringUtils.EMPTY;
        _strDescription = StringUtils.EMPTY;
        _nIdCategory = ALL_INT;
        _nIdStyle = ALL_INT;
        _strWidgetType = StringUtils.EMPTY;
        _nStatus = ALL_INT;
        _nIsEssential = ALL_INT;
        _nIsNew = ALL_INT;
    }

    /**
     * Set true if the filter is applied to a wide search.
     * <br/>
     * In other words, the SQL query will use
     * <ul>
     * <li>SQL <b>OR</b> if it is applied to a wide search</li>
     * <li>SQL <b>AND</b> if it is not applied to a wide search</li>
     * </ul>
     * @param bIsWideSearch true if it a wide search, false otherwise
     */
    public void setIsWideSearch( boolean bIsWideSearch )
    {
        _bIsWideSearch = bIsWideSearch;
    }

    /**
     * Check if the filter is applied to a wide search or not.
     * <br/>
     * In other words, the SQL query will use
     * <ul>
     * <li>SQL <b>OR</b> if it is applied to a wide search</li>
     * <li>SQL <b>AND</b> if it is not applied to a wide search</li>
     * </ul>
     * @return true if it is applied to a wide search
     */
    public boolean getIsWideSearch(  )
    {
        return _bIsWideSearch;
    }

    /**
     * Returns the Name
     * @return The Name
     */
    public String getName(  )
    {
        return _strName;
    }

    /**
     * Sets the Name
     * @param strName The Name
     */
    public void setName( String strName )
    {
        _strName = strName;
    }

    /**
     * Check if the filter contains the attribute Name
     * @return true if it containts, false otherwise
     */
    public boolean containsName(  )
    {
        return StringUtils.isNotBlank( _strName );
    }

    /**
     * Returns the Description
     * @return The Description
     */
    public String getDescription(  )
    {
        return _strDescription;
    }

    /**
     * Sets the Description
     * @param strDescription The Description
     */
    public void setDescription( String strDescription )
    {
        _strDescription = strDescription;
    }

    /**
     * Check if the filter contains the attribute Description
     * @return true if it containts, false otherwise
     */
    public boolean containsDescription(  )
    {
        return StringUtils.isNotBlank( _strDescription );
    }

    /**
     * Returns the IdCategory
     * @return The IdCategory
     */
    public int getIdCategory(  )
    {
        return _nIdCategory;
    }

    /**
     * Sets the IdCategory
     * @param nIdCategory The IdCategory
     */
    public void setIdCategory( int nIdCategory )
    {
        _nIdCategory = nIdCategory;
    }

    /**
     * Check if the filter contains the attribute ID category
     * @return true if it containts, false otherwise
     */
    public boolean containsIdCategory(  )
    {
        return _nIdCategory != ALL_INT;
    }

    /**
     * Returns the IdStyle
     * @return The IdStyle
     */
    public int getIdStyle(  )
    {
        return _nIdStyle;
    }

    /**
     * Sets the IdStyle
     * @param nIdStyle The IdStyle
     */
    public void setIdStyle( int nIdStyle )
    {
        _nIdStyle = nIdStyle;
    }

    /**
     * Check if the filter contains the attribute ID style
     * @return true if it containts, false otherwise
     */
    public boolean containsIdStyle(  )
    {
        return _nIdStyle != ALL_INT;
    }

    /**
     * Returns the WidgetType
     * @return The WidgetType
     */
    public String getWidgetType(  )
    {
        return _strWidgetType;
    }

    /**
     * Sets the WidgetType
     * @param strWidgetType The WidgetType
     */
    public void setWidgetType( String strWidgetType )
    {
        _strWidgetType = strWidgetType;
    }

    /**
     * Check if the filter contains the attribute Widget Type
     * @return true if it containts, false otherwise
     */
    public boolean containsWidgetType(  )
    {
        return StringUtils.isNotBlank( _strWidgetType );
    }

    /**
     * Set the status of the widget
     * @param nStatus the status
     */
    public void setStatus( int nStatus )
    {
        _nStatus = nStatus;
    }

    /**
     * Get the status of the widget
     * @return the status of the widget
     */
    public int getStatus(  )
    {
        return _nStatus;
    }

    /**
    * Check if the filter contains the attribute Name
    * @return true if it containts, false otherwise
    */
    public boolean containsStatus(  )
    {
        return _nStatus != ALL_INT;
    }

    /**
     * Set the widget as essential
     * @param nIsEssential true if it is essential, false otherwise
     */
    public void setIsEssential( int nIsEssential )
    {
        _nIsEssential = nIsEssential;
    }

    /**
     * Check if the widget is essential
     * @return true if it is essential, false otherwise
     */
    public int getIsEssential(  )
    {
        return _nIsEssential;
    }

    /**
     * Check if the filter contains the attribute essential
     * @return true if it contains, false otherwise
     */
    public boolean containsIsEssential(  )
    {
        return _nIsEssential != ALL_INT;
    }

    /**
     * Set the widget as new
     * @param nIsNew true if it is new, false otherwise
     */
    public void setIsNew( int nIsNew )
    {
        _nIsNew = nIsNew;
    }

    /**
     * Check if the widget is new
     * @return true if it is new, false otherwise
     */
    public int getIsNew(  )
    {
        return _nIsNew;
    }

    /**
     * Check if the filter contains the attribute new
     * @return true if it contains, false otherwise
     */
    public boolean containsIsNew(  )
    {
        return _nIsNew != ALL_INT;
    }
}
