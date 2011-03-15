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


/**
 *
 * WidgetComponentFilter
 *
 */
public class WidgetComponentFilter
{
    private Integer _nFilterColumn;
    private Integer _nFilterOrder;

    /**
     * Filter column
     * @return Filter column
     */
    public Integer getFilterColumn(  )
    {
        return _nFilterColumn;
    }

    /**
     * Filter column
     * @param nFilterColumn the Filter column
     */
    public void setFilterColumn( Integer nFilterColumn )
    {
        _nFilterColumn = nFilterColumn;
    }

    /**
     * Filter order
     * @return Filter order
     */
    public Integer getFilterOrder(  )
    {
        return _nFilterOrder;
    }

    /**
     * Filter order
     * @param nFilterOrder the Filter order
     */
    public void setFilterOrder( Integer nFilterOrder )
    {
        _nFilterOrder = nFilterOrder;
    }

    /**
     * true if {@link #getFilterOrder()} != null
     * @return <code>true</code> if {@link #getFilterOrder()} != null, <code>false</code> otherwise.
     */
    public boolean containsFilterOrder(  )
    {
        return _nFilterOrder != null;
    }

    /**
     * true if {@link #getFilterColumn()} != null
     * @return <code>true</code> if {@link #getFilterColumn()} != null, <code>false</code> otherwise
     */
    public boolean containsFilterColumn(  )
    {
        return _nFilterColumn != null;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString(  )
    {
        return this.getClass(  ).getName(  ) + "[column=" + this.getFilterColumn(  ) + ", order=" +
        this.getFilterOrder(  ) + "]";
    }
}
