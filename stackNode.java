/**
 * @author: SHIV SHAH
 * FILENAME: stackNode.java
 */

public class stackNode<TemplateType> {
	private TemplateType data; //data being stored
	private stackNode<TemplateType> next; //pointer to the next node in the chain
	
	//constructor
	public stackNode() {
		data = null;
		next = null;
	}
	
	//overloaded constructor
	public stackNode(TemplateType data) { 
		this.data = data;
		next = null;
	}
	
	/**
	 * gets the data that will be used for grabbing info from the stack
	 * @return this.data
	 */
	public TemplateType getData() { 
		return this.data;
	}
	
	/**
	 * gets the next node from the stack (down from the top element in the stack)
	 * @return this.next
	 */
	public stackNode<TemplateType> getNext() { 
		return this.next;
	}
	
	/**
	 * sets this.data to what the variable data is
	 * @param data
	 */
	public void setData(TemplateType data) { 
		this.data = data;
	}
	
	/**
	 * sets this.next to what the variable next is
	 * @param next
	 */
	public void setNext(stackNode<TemplateType> next) {
		this.next = next;
	}
}
