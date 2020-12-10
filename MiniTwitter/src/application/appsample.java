package application;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class appsample {

	private UserInfo User1;
    private UserInfo User2;
    private UserInfo User3;
    private UserInfo groupUser1;
    private UserInfo groupUser2;
    private UserInfo groupUser3;

    @Before
    public void setup() {
        User1 = new User("000000000");
        User2 = new User("111111111");
        User3 = new User("222222222");
        groupUser1 = new UserGroup("333333333");
        groupUser2 = new UserGroup("444444444");
        groupUser3 = new UserGroup("555555555");
    }

    @Test
    public void addUserTest_addUser() {
        ((UserGroup) groupUser1).addUserInGroup(User1);

        Map<String,UserInfo> group = ((UserGroup) groupUser1).getUserGroups();

        Assert.assertEquals(1, group.size());
    }

    @Test
    public void addUserTest_addGroupUser() {
        ((UserGroup) groupUser1).addUserInGroup(groupUser2);

        Map<String,UserInfo> group = ((UserGroup) groupUser1).getUserGroups();

        Assert.assertEquals(1, group.size());
    }

    @Test
    public void getUserTest_addUser() {
        ((UserGroup) groupUser1).addUserInGroup(User1);

        Map<String,UserInfo> group = ((UserGroup) groupUser1).getUserGroups();

        Assert.assertEquals(true, User1 == group.get(User1.getID()));
    }

    @Test
    public void getUserTest_addGroupUser() {
        ((UserGroup) groupUser1).addUserInGroup(groupUser2);

        Map<String,UserInfo> group = ((UserGroup) groupUser1).getUserGroups();

        Assert.assertEquals(true, groupUser2 == group.get(groupUser2.getID()));
    }

    @Test
    public void containsTest_SingleUsers_shouldPass() {
        ((UserGroup) groupUser1).addUserInGroup(User1);
        ((UserGroup) groupUser1).addUserInGroup(User2);
        ((UserGroup) groupUser1).addUserInGroup(User3);

        ((UserGroup) groupUser1).addUserInGroup(groupUser2);

        Assert.assertEquals(true, groupUser1.contains(User3.getID()));
    }

    @Test
    public void containsTest_SingleUsersInGroup_shouldPass() {
        ((UserGroup) groupUser2).addUserInGroup(User1);
        ((UserGroup) groupUser2).addUserInGroup(User2);
        ((UserGroup) groupUser2).addUserInGroup(User3);

        ((UserGroup) groupUser1).addUserInGroup(groupUser2);

        Assert.assertEquals(true, groupUser1.contains(User3.getID()));
    }

    @Test
    public void getSingleUserCountTest_singleUsers() {
        ((UserGroup) groupUser1).addUserInGroup(User1);
        ((UserGroup) groupUser1).addUserInGroup(User2);
        ((UserGroup) groupUser1).addUserInGroup(User3);

        Assert.assertEquals(3, ((UserGroup) groupUser1).getUserNum());
    }

    @Test
    public void getSingleUserCountTest_singleUsersInGroup() {
        ((UserGroup) groupUser1).addUserInGroup(User1);
        ((UserGroup) groupUser1).addUserInGroup(User2);
        ((UserGroup) groupUser2).addUserInGroup(User3);

        ((UserGroup) groupUser2).addUserInGroup(groupUser1);

        Assert.assertEquals(3, ((UserGroup) groupUser2).getUserNum());
    }

    @Test
    public void getGroupUserCountTest_singleUsers() {
        ((UserGroup) groupUser1).addUserInGroup(User1);
        ((UserGroup) groupUser1).addUserInGroup(User2);
        ((UserGroup) groupUser1).addUserInGroup(User3);

        Assert.assertEquals(0, ((UserGroup) groupUser1).getUserGroupNum());
    }

    @Test
    public void getGroupUserCountTest_singleUsersInGroup() {
        ((UserGroup) groupUser3).addUserInGroup(User1);
        ((UserGroup) groupUser3).addUserInGroup(User2);
        ((UserGroup) groupUser3).addUserInGroup(User3);
        ((UserGroup) groupUser2).addUserInGroup(groupUser3);

        ((UserGroup) groupUser1).addUserInGroup(groupUser2);

        Assert.assertEquals(2, ((UserGroup) groupUser1).getUserGroupNum());
    }

    @Test
    public void getMessageCountTest_singleUsers() {
        ((User) User1).sendMessage("message 1, user 1");
        ((User) User1).sendMessage("message 2, user 1");
        ((User) User2).sendMessage("message 1, user 2");
        ((User) User3).sendMessage("message 1, user 3");

        ((UserGroup) groupUser1).addUserInGroup(User1);
        ((UserGroup) groupUser1).addUserInGroup(User2);
        ((UserGroup) groupUser1).addUserInGroup(User3);

        Assert.assertEquals(4, ((UserGroup) groupUser1).getMessageNum());
    }

    @Test
    public void getMessageCountTest_singleUsersInGroup() {
        ((User) User1).sendMessage("message 1, user 1");
        ((User) User1).sendMessage("message 2, user 1");
        ((User) User2).sendMessage("message 1, user 2");
        ((User) User3).sendMessage("message 1, user 3");

        ((UserGroup) groupUser1).addUserInGroup(groupUser2);
        ((UserGroup) groupUser1).addUserInGroup(User1);
        ((UserGroup) groupUser2).addUserInGroup(User2);
        ((UserGroup) groupUser2).addUserInGroup(User3);

        Assert.assertEquals(4, ((UserGroup) groupUser1).getMessageNum());
    }
}
