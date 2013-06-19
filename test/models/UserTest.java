package models;
import com.avaje.ebean.Ebean;
import org.junit.Before;
import org.junit.Test;
import play.libs.Yaml;
import play.test.WithApplication;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeGlobal;
import static play.test.Helpers.inMemoryDatabase;

/**
 * Created with IntelliJ IDEA.
 * User: Arugin
 * Date: 12.04.13
 * Time: 0:55
 * To change this template use File | Settings | File Templates.
 */
public class UserTest extends WithApplication {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
        Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("test-data.yml");
        // Insert users first
        Ebean.save(all.get("usersStatuses"));
        // Insert projects
        Ebean.save(all.get("users"));
        // Insert tasks
        Ebean.save(all.get("cycles"));
    }

    @Test
    public void createAndRetrieveUser() {
        User bob = User.find.where().eq("email", "bob@gmail.com").findUnique();
        assertNotNull(bob);
        assertEquals("Bob", bob.name);
        assertEquals(2, bob.userStatus.id);
        User valery = User.find.where().eq("email", "valera-sochi@bk.ru").findUnique();
        assertEquals("Валерий", valery.name);
    }

    @Test
    public void tryAuthenticateUser() {
        assertNotNull(User.authenticate("bob@gmail.com", "secret"));
        assertNull(User.authenticate("bob@gmail.com", "badpassword"));
        assertNull(User.authenticate("tom@gmail.com", "secret"));
    }
}
