package controllers;

import com.avaje.ebean.Ebean;
import com.google.common.collect.ImmutableMap;
import models.User;
import org.junit.Before;
import org.junit.Test;
import play.libs.Yaml;
import play.mvc.Result;
import play.test.WithApplication;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.*;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.status;

/**
 * Created with IntelliJ IDEA.
 * User: mayatskiy
 * Date: 20.05.13
 * Time: 18:23
 * To change this template use File | Settings | File Templates.
 */
public class ProfileTest extends WithApplication {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
        Map<String,List<Object>> all = (Map<String,List<Object>>) Yaml.load("initial-data.yml");
        // Insert users first
        Ebean.save(all.get("usersStatuses"));
        // Insert projects
        Ebean.save(all.get("users"));
        // Insert tasks
        Ebean.save(all.get("cycles"));
    }
}
