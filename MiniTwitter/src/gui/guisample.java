package gui;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import org.junit.Assert;

public class guisample {

	@Test
	public void getInstanceTest() {
        AdminControlPanel window1 = AdminControlPanel.getInstance();
        AdminControlPanel window2 = AdminControlPanel.getInstance();

        Assert.assertEquals(true, window1 == window2);
    }

}
