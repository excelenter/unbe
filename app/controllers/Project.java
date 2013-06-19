package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.project.projects;

/**
 * Created by:
 * User: mayatskiy
 * Date: 16.04.13
 * Time: 16:47
 */
public class Project extends Controller {
    public static Result projects() {
        return ok(projects.render());
    }
}
