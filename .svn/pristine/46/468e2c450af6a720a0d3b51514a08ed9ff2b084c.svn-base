package models;

import com.avaje.ebean.Ebean;
import org.junit.Before;
import org.junit.Test;
import play.libs.Yaml;
import play.test.WithApplication;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeGlobal;
import static play.test.Helpers.inMemoryDatabase;

public class CycleTest extends WithApplication {

    private User user;

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
        user = User.find.where().eq("email", "bob@gmail.com").findUnique();
    }

    @Test
    public void createAndRetrieveCycle(){
        Cycle.create("First", "", null, user.id);
        Cycle first = Cycle.find.where().eq("title", "First").findUnique();
        assertNotNull(first);
    }

    @Test
    public void findCycleAuthor() {
        Cycle.create("First", "", null, user.id);
        List<Cycle> results = Cycle.showAuthorCycles(user);
        assertEquals(2, results.size());
        Cycle second = Cycle.find.where().eq("title", "What did you do with foto?").findUnique();
        assertEquals(1, second.id);
    }
}