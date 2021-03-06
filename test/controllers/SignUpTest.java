package controllers;

import com.avaje.ebean.Ebean;
import com.google.common.collect.ImmutableMap;
import models.User;
import org.junit.Before;
import org.junit.Test;
import play.libs.Yaml;
import play.mvc.Result;
import play.mvc.Controller;
import play.test.WithApplication;
import play.Routes.*;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static play.test.Helpers.*;
import static play.test.Helpers.fakeRequest;

/**
 * Created by:
 * User: mayatskiy
 * Date: 16.04.13
 * Time: 17:29
 */
public class SignUpTest extends WithApplication {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
        Map<String,List<Object>> all = (Map<String,List<Object>>) Yaml.load("test-data.yml");
        // Insert users first
        Ebean.save(all.get("usersStatuses"));
        // Insert projects
        Ebean.save(all.get("users"));
        // Insert tasks
        Ebean.save(all.get("cycles"));
    }

    @Test
    public void registerSuccess(){
        Result result = callAction(
                controllers.routes.ref.Application.register(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                        "name", "Аро",
                        "email", "arod@aro.com",
                        "password", "123456",
                        "passwordRepeat", "123456"))
        );
        status(result);
        User aro = User.find.where().eq("email", "arod@aro.com").findUnique();
        assertNotNull("Register failed", aro);
    }

   @Test
    public void registerEmailDuplicate(){
        Result result = callAction(
                controllers.routes.ref.Application.register(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                        "email", "valera-sochi@bk.ru",
                        "password", "123456",
                        "passwordRepeat","123456",
                        "name","Аро"))
        );
        assertEquals("Registration with duplicated email go on",400, status(result));
        User aro = User.find.where().eq("email", "valera-sochi@bk.ru").findUnique();
        assertEquals("User 'Valery' was broken with registration with dup. email","Валерий", aro.name);
    }

    @Test
    public void registerPasswordsNotEquals(){
        Result result = callAction(
                controllers.routes.ref.Application.register(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                        "email", "aro@aro.ru",
                        "password", "123456",
                        "passwordRepeat","123347",
                        "name","Аро"))
        );
        assertEquals("user created with not eq passwords",400, status(result));
        User aro = User.find.where().eq("email", "aro@aro.ru").findUnique();
        assertNull("user created with not eq passwords", aro);
    }

    @Test
    public void registerWithEmptyValues(){
        Result result = callAction(
                controllers.routes.ref.Application.register(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                        "email", "",
                        "password", "",
                        "passwordRepeat","",
                        "name",""))
        );
        assertEquals("Registration with duplicated email go on",400, status(result));
        User aro = User.find.where().eq("email", "").findUnique();
        assertNull("user created with empty values", aro);
    }

    @Test
    public void registerWithShortPassword(){
        Result result = callAction(
                controllers.routes.ref.Application.register(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                        "email", "naaru@aro.com",
                        "password", "123",
                        "passwordRepeat","123",
                        "name","Аро"))
        );
        assertEquals("Registration with short password go on",400, status(result));
        User aro = User.find.where().eq("email", "naaru@aro.com").findUnique();
        assertNull("user created with short password", aro);
    }
}
