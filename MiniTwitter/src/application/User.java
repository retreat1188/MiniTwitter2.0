package application;

// User class meant for Leaf of Tree as Composite design pattern

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import visiting.Visitor;

public class User extends UserInfo implements Profile {
	private static final List<String> Positive = Arrays.asList("good, great, excellent");
	
	private Map<String, Observer> followers;
	private Map<String, Profile> following;
	private List<String> newsFeed;
	
	private String latestMessage;
	private int positiveMessageNum;
	private long creationTime;
	private long lastUpdateTime;
	
	public User(String id) {
		super(id);
		followers = new HashMap<String, Observer>();
		followers.put(this.getID(), this);
		following = new HashMap<String, Profile>();
		newsFeed = new ArrayList<String>();
		this.creationTime = System.currentTimeMillis();
		this.lastUpdateTime = System.currentTimeMillis();
	}
	
	public Map<String, Observer> getFollowers() {
		return followers;
	}
	
	public Map<String, Profile> getFollowing() {
		return following;
	}
	
	public List<String> getNewsFeed() {
		return newsFeed;
	}
	
	public void sendMessage(String message) {
		this.latestMessage = message;
		this.setMessageNum(this.getMessageNum() + 1);
	
	
	if (isPositive(message)) {
		++positiveMessageNum;
	}
	
	notifyObservers();
}
	
	public String getLatestMessage() {
		return this.latestMessage;
	}
	
	public int getPositiveMessageNum() {
		return positiveMessageNum;
	}
	
	
	@Override
	public boolean contains(String id) {
		return this.getID().equals(id);
	}
	
	@Override
	public int getUserGroupNum() {
		return 0;
	}
	
	@Override
	public int getUserNum() {
		return 1;
	}
	
	@Override
	public void update(Profile profile) {
		newsFeed.add(0, ((User) profile).getID() + ": " + ((User) profile).getLatestMessage());
	}
	
	public void attach(Observer observer) {
		addFollower(observer);
	}
	
	@Override
	public void notifyObservers() {
		for (Observer obs : followers.values()) {
			obs.update(this);
		}
	}
	
	@Override
	public void accept(Visitor visiting) {
		visiting.visitUser(this);
	}
	
	@Override
	public long creationTime() {
		return System.currentTimeMillis();
	}
	
	public long lastUpdateTime() {
		return System.currentTimeMillis();
	}
	
	private void addFollower(Observer user) {
		this.getFollowers().put(((UserInfo) user).getID(), user);
		((User) user).addUserToFollow(this);
	}
	
	private void addUserToFollow(Profile toFollow) {
		if (toFollow.getClass() == User.class) {
			getFollowing().put(((UserInfo) toFollow).getID(), toFollow);
		}
	}
	
	private boolean isPositive(String message) {
		boolean positive = false;
		message = message.toLowerCase();
		for (String word : Positive) {
			if (message.contains(word)) {
				positive = true;
			}
		}
		
		return positive;
	}
	
}
