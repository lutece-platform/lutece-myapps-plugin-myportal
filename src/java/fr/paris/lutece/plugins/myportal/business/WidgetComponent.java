/*
 * Copyright (c) 2002-2014, Mairie de Paris
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

/**
 *
 * WidgetConfig
 *
 */
public class WidgetComponent implements Comparable<WidgetComponent>
{
    private int _nIdWidgetComponent;
    private int _nIdWidget;
    private String _strWidgetName;
    private String _strStyleName;
    private int _nWidgetState;
    private int _nColumn;
    private int _nOrder;

    /**
     * Set WidgetConfigId
     * 
     * @param nIdWidgetComponent
     *            WidgetConfigId
     */
    public void setIdWidgetComponent( int nIdWidgetComponent )
    {
        _nIdWidgetComponent = nIdWidgetComponent;
    }

    /**
     * Get WidgetConfigId
     * 
     * @return WidgetConfigId
     */
    public int getIdWidgetComponent( )
    {
        return _nIdWidgetComponent;
    }

    /**
     * Returns the WidgetId
     * 
     * @return The WidgetId
     */
    public int getIdWidget( )
    {
        return _nIdWidget;
    }

    /**
     * Sets the WidgetId
     * 
     * @param nIdWidget
     *            The WidgetId
     */
    public void setIdWidget( int nIdWidget )
    {
        _nIdWidget = nIdWidget;
    }

    /**
     * Returns the WidgetState
     * 
     * @return The WidgetState
     */
    public int getWidgetState( )
    {
        return _nWidgetState;
    }

    /**
     * Sets the WidgetState
     * 
     * @param nWidgetState
     *            The WidgetState
     */
    public void setWidgetState( int nWidgetState )
    {
        _nWidgetState = nWidgetState;
    }

    /**
     * Returns the Column
     * 
     * @return The Column
     */
    public int getColumn( )
    {
        return _nColumn;
    }

    /**
     * Sets the Column
     * 
     * @param nColumn
     *            The Column
     */
    public void setColumn( int nColumn )
    {
        _nColumn = nColumn;
    }

    /**
     * Set the Order
     * 
     * @param nOrder
     *            The Order
     */
    public void setOrder( int nOrder )
    {
        _nOrder = nOrder;
    }

    /**
     * Get the Order
     * 
     * @return The Order
     */
    public int getOrder( )
    {
        return _nOrder;
    }

    /**
     * Compare to a widget component by its order
     * 
     * @param o
     *            a {@link WidgetComponent}
     * @return the result of the comparaison
     */
    public int compareTo( WidgetComponent o )
    {
        return getOrder( ) - o.getOrder( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof WidgetComponent )
        {
            WidgetComponent other = (WidgetComponent) obj;

            return other.getIdWidgetComponent( ) == _nIdWidgetComponent;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode( )
    {
        return _nIdWidgetComponent;
    }

    /**
     * Set widgetname
     * 
     * @param strWidgetName
     *            The widget name
     */
    public void setWidgetName( String strWidgetName )
    {
        _strWidgetName = strWidgetName;
    }

    /**
     * Get widget name
     * 
     * @return The widget name
     */
    public String getWidgetName( )
    {
        return _strWidgetName;
    }

    /**
     * Set widget style
     * 
     * @param strStyleName
     *            the style
     */
    public void setStyleName( String strStyleName )
    {
        _strStyleName = strStyleName;
    }

    /**
     * Get widget style
     * 
     * @return the style
     */
    public String getStyleName( )
    {
        return _strStyleName;
    }
}
