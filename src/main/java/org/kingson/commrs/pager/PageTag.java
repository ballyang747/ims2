/**
 * 
 */
package org.kingson.commrs.pager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.stereotype.Component;



/**alt+shift+j
 * @author kingson
 * 2018年7月28日
   org.kingson.tag
   pageAppTEST
   @version 1.0
 */
@Component
public class PageTag  extends TagSupport{
    //当前页面totalNumpageSizepageIndex
	private int pageIndex;
	private int pageSize;
	private int totalNum;
	private String submitUrl;
	private String pageStyle="black";
	

	public int getPageIndex() {
		return pageIndex;
	}


	public void setPageIndex(int pageIndex) {
		
		if(pageIndex ==0 ) {
			pageIndex=1;
		}
		this.pageIndex = pageIndex;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getTotalNum() {
		return totalNum;
	}


	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}


	public String getSubmitUrl() {
		return submitUrl;
	}


	public void setSubmitUrl(String submitUrl) {
		this.submitUrl = submitUrl;
	}


	public String getPageStyle() {
		return pageStyle;
	}


	public void setPageStyle(String pageStyle) {
		this.pageStyle = pageStyle;
	}


	@Override
	public int doStartTag() throws JspException {
	   try {
		         JspWriter jspWriter=  this.pageContext.getOut();
		         //创建StringBuffer用于拼装页码相关信息
		      StringBuffer str =   new StringBuffer();
		      
		      //计算总页数
		      int totalPageNum =totalNum%pageSize ==0?totalNum/pageSize:(totalNum/pageSize)+1;
		      //定义跳转地址
		      String jumpUrl="";
		      //new 一个StringBuffer 用来拼装页码信息
		      StringBuffer pager = new StringBuffer();
		      //当前页码是第一页
		      if(pageIndex ==1) {
		    	  
		    	  pager.append("<span class='disabled'>上一页</span>");
		    	  //处理中间页码
		    	  calcMiddlePager(pager,totalPageNum);
		    	  if(totalPageNum ==1) {
		    		  pager.append("<span class='disabled'>下一页</span>");
		    		  
		    	  }else {
					jumpUrl=this.submitUrl.replace("{0}", String.valueOf(this.pageIndex+1));
					pager.append("<a href='"+jumpUrl+"'>下一页</a>");
					
				}
		      }else if(pageIndex == totalPageNum) {
		    	  //当前页码是尾页
		    	jumpUrl=  this.submitUrl.replace("{0}", String.valueOf(this.pageIndex-1));
		    	  pager.append("<a href='"+jumpUrl+"'>上一页</a>");
		    	  calcMiddlePager(pager, totalPageNum);
		    	  pager.append("<span class='disabled'>下一页</span>");
				
			}else {
				//当前页码在中间
				jumpUrl=this.submitUrl.replace("{0}", String.valueOf(this.pageIndex-1));
				pager.append("<a href='"+jumpUrl+"'>上一页</a>");
				
				calcMiddlePager(pager, totalPageNum);
				
				jumpUrl=this.submitUrl.replace("{0}", String.valueOf(this.pageIndex+1));
				pager.append("<a href='"+jumpUrl+"'>下一页</a>");
			}
		      
		      
		      //计算每页开始及结束行号
		      int startSize = (this.pageIndex-1)*this.pageSize+1;
		      
		      int endSize= this.pageIndex==totalPageNum ?this.totalNum:this.pageSize*this.pageIndex;
		   str.append("<table class='"+this.pageStyle+"' style='align:center' width='100%'><tr><td>"+pager.toString()+" 跳转到<input type='text' size='4' id='jumpNum'/><input type='button' value='跳转' id='jumpBut'/></td></tr>");   
		      str.append("<tr><td>总共<font color='red'>"+this.totalNum+"</font>条记录,当前显示"+startSize+"-"+endSize+"记录</td></tr></table>");
		      str.append("<script type='text/javascript'>");
				
				str.append("document.getElementById('jumpBut').onclick = function(){");
				str.append("var value = document.getElementById('jumpNum').value;");
				str.append("if(!/^[1-9]\\d*$/.test(value)||value > "+this.totalNum+"){");
				str.append("alert('请输入[1-"+totalPageNum+"]之间的页码值！');");
				str.append("}else{");
				// index.action?pageIndex = {0}
				str.append("var submiturl = '"+this.submitUrl+"';");
				str.append("submiturl = submiturl.replace('{0}',value);");
				str.append("window.location = submiturl;");
				
				str.append("}");
				
				str.append("}");
				str.append("</script>");
				jspWriter.write(str.toString());
		      
	} catch (Exception e) {
		
	  throw	new  RuntimeException("写出出错",e);
	  
	  
	}
	   
	   return super.doStartTag();
	}


	/**
	 * @param pager
	 * @param totalPageNum
	 */
	private void calcMiddlePager(StringBuffer str, int totalPageNum) {
		String jumpUrl="";
		if(totalPageNum <=10) {
			
			for (int i = 1; i < totalPageNum; i++) {
				if (i==this.pageIndex) {
					str.append("<span class='current'>"+i+"</span>");
					
				}else {
					jumpUrl=this.submitUrl.replace("{0}", String.valueOf(i));
					str.append("<a href='"+jumpUrl+"'>"+i+"</a>");
					
				}
			}
		}else if (this.pageIndex <=8) {
			
			for (int i = 1; i <=9; i++) {
				
				if (i==this.pageIndex) {
					str.append("<span class='current'>"+i+"</span>");
				}else {
					jumpUrl=this.submitUrl.replace("{0}", String.valueOf(i));
					str.append("<a href='"+jumpUrl+"'>"+i+"</a>");
				}
				
			}
			str.append("...");
			jumpUrl=this.submitUrl.replace("{0}", String.valueOf(totalPageNum));
			
			str.append("<a href='"+jumpUrl+"'>"+totalPageNum+"</a>");
			
		}else if (this.pageIndex +8 >= totalPageNum) {
			jumpUrl=this.submitUrl.replace("{0}", String.valueOf(1));
			str.append("<a href='"+jumpUrl+"'"+1+"</a>");
			str.append("...");
			for (int j = totalPageNum -9; j <=totalPageNum; j++) {
				if (j==this.pageIndex) {
					str.append("<span class='current'>"+j+"</span>");
					
					
				}else {
					jumpUrl=this.submitUrl.replace("{0}", String.valueOf(j));
					str.append("<a href='"+jumpUrl+"'>"+j+"</a>");
					
				}
			}
		}else {
			jumpUrl=this.submitUrl.replace("{0}", String.valueOf(1));
			str.append("<a href='"+jumpUrl+"'>"+1+"</a>");
			str.append("...");
			for (int j =this.pageIndex-4; j <=this.pageIndex+4; j++) {
				if (j==this.pageIndex) {
					str.append("<span class='current'>"+j+"</span>");
					
				}else {
					jumpUrl=this.submitUrl.replace("{0}", String.valueOf(j));
					str.append("<a href='"+jumpUrl+"'>"+j+"</a>");
					
				}
			}
			
			str.append("...");
			jumpUrl=this.submitUrl.replace("{0}", String.valueOf(totalPageNum));
			str.append("<a href='"+jumpUrl+"'>"+totalPageNum+"</a>");
		}
		
	}
    

	
}
