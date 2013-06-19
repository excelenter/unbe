import com.avaje.ebean.Ebean;
import models.User;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

import java.util.List;
import java.util.Map;

/**
 * Created by:
 * User: mayatskiy
 * Date: 11.04.13
 * Time: 19:39
 */

public class Global extends GlobalSettings {
    @Override
    public void onStart(Application app) {
        System.setProperty("java.io.tmpdir", System.getProperty("user.dir"));
        // Check if the database is empty
        if (User.find.findRowCount() == 0) {
            Map<String,List<Object>> all = (Map<String,List<Object>>) Yaml.load("initial-data.yml");
            // Insert users first
            Ebean.save(all.get("usersStatuses"));
            // Insert projects
            Ebean.save(all.get("users"));
            // Insert tasks
            Ebean.save(all.get("cycles"));
            // Insert articleAreas
            Ebean.save(all.get("articleAreas"));
            // Insert articleTypes
            Ebean.save(all.get("articleTypes"));
            // Insert articles
            Ebean.save(all.get("articles"));
        }
    }
}
