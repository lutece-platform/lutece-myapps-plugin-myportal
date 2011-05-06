/*
 * Copyright (c) 2002-2010, Mairie de Paris
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


import java.io.Serializable;


/**
 *
 * This is the business class for the object Widget
 *
 */
public class Widget implements Serializable
{
    private static final long serialVersionUID = 6779038411191982156L;

    // Variables declarations 
    private int _nIdWidget;
    private String _strName;
    private String _strDescription;
    private String _strCategory;
    private int _nIdCategory;
    private String _strStyle;
    private int _nIdStyle;
    private String _strWidgetType;
    private int _nIdIcon;
    private String _strConfigData;
    private String _strCssClass;
    private int _nStatus;
    private boolean _bIsEssential;
    private boolean _bIsNew;

    /**
     * Returns the IdWidget
     * @return The IdWidget
     */
    public int getIdWidget(  )
    {
        return _nIdWidget;
    }

    /**
     * Sets the IdWidget
     * @param nIdWidget The IdWidget
     */
    public void setIdWidget( int nIdWidget )
    {
        _nIdWidget = nIdWidget;
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
     * Returns the Category
     * @return The Category
     */
    public String getCategory(  )
    {
        return _strCategory;
    }

    /**
     * Sets the Category
     * @param strCategory The Category
     */
    public void setCategory( String strCategory )
    {
        _strCategory = strCategory;
    }

    /**
     * Returns the IdIcon
     * @return The IdIcon
     */
    public int getIdIcon(  )
    {
        return _nIdIcon;
    }

    /**
     * Sets the IdIcon
     * @param nIdIcon The IdIcon
     */
    public void setIdIcon( int nIdIcon )
    {
        _nIdIcon = nIdIcon;
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
     * Returns the Style
     * @return The Style
     */
    public String getStyle(  )
    {
        return _strStyle;
    }

    /**
     * Sets the Style
     * @param strStyle The Style
     */
    public void setStyle( String strStyle )
    {
        _strStyle = strStyle;
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
     * Returns the ConfigData
     * @return The ConfigData
     */
    public String getConfigData(  )
    {
        return _strConfigData;
    }

    /**
     * Sets the ConfigData
     * @param strConfigData The ConfigData
     */
    public void setConfigData( String strConfigData )
    {
        _strConfigData = strConfigData;
    }

    /**
     * Returns the CssClass
     * @return The CssClass
     */
    public String getCssClass(  )
    {
        return _strCssClass;
    }

    /**
     * Sets the CssClass
     * @param strCssClass The CssClass
     */
    public void setCssClass( String strCssClass )
    {
        _strCssClass = strCssClass;
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
     * Set the widget as essential
     * @param bIsEssential true if it is essential, false otherwise
     */
    public void setIsEssential( boolean bIsEssential )
    {
        _bIsEssential = bIsEssential;
    }

    /**
     * Check if the widget is essential
     * @return true if it is essential, false otherwise
     */
    public boolean getIsEssential(  )
    {
        return _bIsEssential;
    }

    /**
     * Set the widget as new
     * @param bIsNew true if it is new, false otherwise
     */
    public void setIsNew( boolean bIsNew )
    {
        _bIsNew = bIsNew;
    }

    /**
     * Check if the widget is new
     * @return true if it is new, false otherwise
     */
    public boolean getIsNew(  )
    {
        return _bIsNew;
    }
}
