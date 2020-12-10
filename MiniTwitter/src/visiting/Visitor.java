package visiting;

// Interface for Visitor design pattern

import application.UserInfo;

public interface Visitor {
	public int readUserInfo(UserInfo user);
	public int visitUser(UserInfo user);
	public int visitUserGroup(UserInfo user);

}
