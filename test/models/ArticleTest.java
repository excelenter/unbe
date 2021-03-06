package models;

import com.avaje.ebean.Ebean;
import org.junit.Before;
import org.junit.Test;
import play.libs.Yaml;
import play.test.WithApplication;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

/**
 * Created by:
 * User: mayatskiy
 * Date: 23.04.13
 * Time: 16:19
 */
public class ArticleTest extends WithApplication {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
        Map<String,List<Object>> all = (Map<String,List<Object>>) Yaml.load("test-data.yml");
        Ebean.save(all.get("usersStatuses"));
        Ebean.save(all.get("users"));
        Ebean.save(all.get("cycles"));
        Ebean.save(all.get("articleAreas"));
        Ebean.save(all.get("articleTypes"));
        Ebean.save(all.get("articles"));
    }

    @Test
    public void testLastNews(){
        List<Article> news = Article.lastNews();
        assertEquals("There are " + news.size() + " news. This is wrong", 4, news.size());
    }
}
