package fr.paris.lutece.plugins.myportal.business;

public class WidgetsTag {
	
	  private String _strCssClass;
	  private String _strContent;
	  
	  /**
	   * 
	   * @return
	   */
	  public String getCssClass(  )
	    {
	        return _strCssClass;
	    }

	 /**
	  * 
	  * @param strCssClass
	  */
	    public void setCssClass( String strCssClass )
	    {
	    	_strCssClass = strCssClass;
	    }
	    /**
	     * 
	     * @return
	     */
	    public String getContent(  )
	    {
	        return _strContent;
	    }

	    /**
	     * 
	     * @param strContent
	     */
	    public void setContent( String strContent )
	    {
	    	_strContent = strContent;
	    }

}
