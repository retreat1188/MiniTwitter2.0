package visiting;

import application.UserInfo;
import application.User;
import application.UserGroup;

public class PositiveMessage implements Visitor {
	@Override
    public int readUserInfo(UserInfo user) {
        int count = 0;

        if (user.getClass() == User.class) {
            count += visitUser(user);
        } else if (user.getClass() == UserGroup.class) {
            count += visitUserGroup(user);
        }

        return count;
    }

    @Override
    public int visitUser(UserInfo user) {
        return ((User) user).getPositiveMessageNum();
    }

    @Override
    public int visitUserGroup(UserInfo user) {
        int count = 0;

        for (UserInfo u : ((UserGroup) user).getUserGroups().values()) {
            count += visitUser(u);
        }

        return count;
    }
}
