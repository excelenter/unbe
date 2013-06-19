package controllers;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

import play.mvc.*;
import play.libs.*;
import play.test.*;
import static play.test.Helpers.*;
import com.avaje.ebean.Ebean;
import com.google.common.collect.ImmutableMap;

public class LoginTest extends WithApplication {
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
    public void authenticateSuccess() {
        Result result = callAction(
                controllers.routes.ref.Application.authenticate(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                        "email", "bob@gmail.com",
                        "password", "secret"))
        );
        status(result);
        assertEquals("bob@gmail.com", session(result).get("email"));
    }

    @Test
    public void authenticateFailure() {
        Result result = callAction(
                controllers.routes.ref.Application.authenticate(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                        "email", "bob@example.com",
                        "password", "badpassword"))
        );
        assertEquals(400, status(result));
        assertNull(session(result).get("email"));
    }
}