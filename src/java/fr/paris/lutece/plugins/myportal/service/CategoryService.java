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
package fr.paris.lutece.plugins.myportal.service;

import java.util.Collection;
import java.util.List;

import fr.paris.lutece.plugins.myportal.business.Category;
import fr.paris.lutece.plugins.myportal.business.CategoryHome;
import fr.paris.lutece.plugins.myportal.business.Widget;
import fr.paris.lutece.plugins.myportal.business.WidgetFilter;
import fr.paris.lutece.plugins.myportal.business.WidgetHome;
import fr.paris.lutece.util.ReferenceList;


/**
 *
 * CategoryService
 *
 */
public final class CategoryService
{
	public static final String BEAN_NAME = "myportal.categoryService";
	
	private Collection<Category> _categories;
	private boolean _cached;	

    /**
     * Private constructor
     */
    private CategoryService(  )
    {
    }

    /**
     * Load the data of all the category objects and returns them in form of a collection
     * @return the collection which contains the data of all the category objects
     */
    public Collection<Category> getCategoriesList(  )
    {
    	if(!_cached)
    	{
    		_categories = CategoryHome.getCategoriesList(  );
    		_cached = true;
    	}
    	
        return _categories;
    }

    /**
     * Load the data of all the category objects and returns them in form of a ReferenceList
     * @return the ReferenceList which contains the data of all the category objects
     */
    public ReferenceList getCategories(  )
    {
    	 ReferenceList list = new ReferenceList(  );

         for ( Category category : getCategoriesList(  ) )
         {
        	 list.addItem( category.getIdCategory(  ), category.getName(  ) );
         }
    	
        return list;
    }

    /**
     * Returns an instance of a category whose identifier is specified in parameter
     * @param nCategoryId The category primary key
     * @return an instance of Category
     */
    public Category findByPrimaryKey( int nCategoryId )
    {
    	if(_cached)
    	{
    		for (Category category : _categories) {
				if(category.getIdCategory() == nCategoryId)
				{
					return category;
				}
			}
    		_cached = false;
    	}
        return CategoryHome.findByPrimaryKey( nCategoryId );
    }

    /**
     * Create an instance of the category class
     * @param category The instance of the Category which contains the informations to store
     * @return The  instance of category which has been created with its primary key.
     */
    public Category createCategory( Category category )
    {
    	_cached = false;
        return CategoryHome.create( category );
    }

    /**
     * Remove the category whose identifier is specified in parameter.
     * The category is removed if and only if it is not linked to any widget.
     * @param nCategoryId The category Id
     * @return true if the category is linked to a widget, false otherwise
     */
    public boolean removeCategory( int nCategoryId )
    {
        boolean bIsCategoryLinkedToWidget = false;

        // Check if the category is linked to any widget
        WidgetFilter wFilter = new WidgetFilter(  );
        wFilter.setIdCategory( nCategoryId );

        List<Widget> listWidgets = WidgetHome.getWidgetsByFilter( wFilter );

        if ( ( listWidgets != null ) && ( listWidgets.size(  ) > 0 ) )
        {
            bIsCategoryLinkedToWidget = true;
        }
        else
        {
        	_cached = false;
            CategoryHome.remove( nCategoryId );
        }

        return bIsCategoryLinkedToWidget;
    }

    /**
     * Update of the category which is specified in parameter
     * @param category The instance of the Category which contains the data to store
     * @return The instance of the  category which has been updated
     */
    public Category updateCategory( Category category )
    {
    	_cached = false;
        return CategoryHome.update( category );
    }

    /**
     * Find the first category (order by the ID category)
     * @return a {@link Category}
     */
    public Category findFirstCategory(  )
    {
        return CategoryHome.findFirstCategory(  );
    }
}
