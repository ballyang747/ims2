/**
 * 
 */
package org.kingson.commrs.pager;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**alt+shift+j
 * @author kingson
 * 2018年8月9日
   org.kingson.commrs.pager
   Imsn2
   @version 1.0

  @email 1606656167@qq.com
  @tel 15768607477
   
 */

public class PageOpetrs  extends TagSupport{
	   
	    private String value;
	public String getValue() {
		return value;
	   }

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int doStartTag() throws JspException {
	 System.out.println("value:"+value);
	 
         List<String> opertrs=(List<String>) this.pageContext.getSession().getAttribute("session_option");
          
         System.out.println(opertrs);
         if(opertrs.indexOf(value) !=-1) {
        	  
        	  return EVAL_PAGE;
          }else {
        	  return SKIP_BODY;
          }
          
		
	}

	
	 
}
