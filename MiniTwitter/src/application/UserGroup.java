package application;

// GroupUser class is part of Composite design pattern

import java.util.Map;
import java.util.HashMap;

import visiting.Visitor;

public class UserGroup extends User {
	private Map<String, UserInfo> userGroups;
	private long creationTime;
	
	public UserGroup(String id) {
		super(id);
		userGroups = new HashMap<String, UserInfo>();
		this.creationTime = System.currentTimeMillis();
	}
	
	public Map<String, UserInfo> getUserGroups() {
		return userGroups;
	}
	
	public User addUserInGroup(UserInfo user) {
		if (!this.contains(user.getID())) {
			this.userGroups.put(user.getID(), user);
		}
		
		return this;
	}
	
	@Override 
	public boolean contains(String id) {
		boolean contains = false;
		for (UserInfo user : userGroups.values()) {
			if (user.contains(id)) {
				contains = true;
			}
		}
		
		return contains;
	}
	
	@Override
	public int getUserNum() {
		int num = 0;
		for (UserInfo user : userGroups.values()) {
			num += user.getUserNum();
		}
		
		return num;
	}
	
	@Override
	public int getUserGroupNum() {
		int num = 0;
		for (UserInfo user : userGroups.values()) {
			if (user.getClass() == UserGroup.class) {
				++num;
				num += user.getUserGroupNum();
			}
		}
		
		return num;
	}
	
	@Override
	public int getMessageNum() {
		int msgNum = 0;
		for (UserInfo user : userGroups.values()) {
			msgNum += user.getMessageNum();
		}
		
		return msgNum;
	}
	
	@Override
	public void update(Profile profile) {
		for (UserInfo user : userGroups.values()) {
			((Observer) user).update(profile);
		}
	}
	
	@Override
	public void accept(Visitor visiting) {
		for (UserInfo user : userGroups.values()) {
			user.accept(visiting);
		}
		
		visiting.visitUserGroup(this);
	}
	
	@Override
	public long creationTime() {
		return creationTime;
	}
	
	private boolean holdsUserGroup() {
		boolean holdsUsers = false;
		for (UserInfo user : userGroups.values()) {
			if (user.getClass() == UserGroup.class) {
				holdsUsers = true;
			}
		}
		
		return holdsUsers;
	}
}
