/**    
 * 文件名：Tuple.java    
 *    
 * 版本信息：    
 * 日期：2015年8月19日    
 * Copyright 足下 Corporation 2015     
 * 版权所有    
 *    
 */
package com.app.ad.utils;

import java.io.Serializable;

/**    
 *      
 * 类名称：Tuple    
 * 类描述： 元素，用于函数返回多个参数
 * 创建人：wesley    
 * 创建时间：2015年8月19日 下午1:17:42           
 *     
 */
public  class Tuple<A ,B> implements Serializable{

	/**    
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）    
	 *    
	 * @since Ver 1.1    
	 */    
	
	private static final long serialVersionUID = 1L;
	
	private A a;
	private B b;
	
	public Tuple(){
		
	}
	public Tuple(A a,B b){
		this.a = a;
		this.b = b;
	}
	/**    
	 * a    
	 *    
	 * @return  the a    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	*/    
	
	public A getFirst() {
		return a;
	}
	/**    
	 * @param a the a to set    
	 */
	public void setFirst(A a) {
		this.a = a;
	}
	/**    
	 * b    
	 *    
	 * @return  the b    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	*/    
	
	public B getSecond() {
		return b;
	}
	/**    
	 * @param b the b to set    
	 */
	public void setSecond(B b) {
		this.b = b;
	}
	
	

}
