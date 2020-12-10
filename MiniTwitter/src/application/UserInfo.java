package application;

// UserInfo class used to implement Composite design pattern

import javax.swing.tree.DefaultMutableTreeNode;
import visiting.Visitor;

public abstract class UserInfo extends DefaultMutableTreeNode implements Observer {
	private String id;
	private int messageNum;
	
	public abstract boolean contains(String id);
	public abstract int getUserNum();
	public abstract int getUserGroupNum();
	
	public UserInfo(String id) {
		super(id);
		this.id = id;
		this.setMessageNum(0);
	}
	
	public String getID() {
		return id;
	}
	
	public int getMessageNum() {
		return messageNum;
	}
	
	public void setMessageNum(int messageNum) {
		this.messageNum = messageNum;
	}
	
	public abstract void accept(Visitor visitor);
}
