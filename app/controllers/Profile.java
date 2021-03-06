package controllers;

import models.Cycle;
import models.S3File;
import models.User;
import org.imgscalr.Scalr;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import views.html.mainparts.profileparts.pcyclesinfo;
import views.html.mainparts.profileparts.pgeneralinfo;
import views.html.mainparts.profileparts.pprojectsinfo;
import views.html.mainparts.profileparts.pusersettings;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static play.data.Form.form;

/**
 * Created by:
 * User: mayatskiy
 * Date: 17.04.13
 * Time: 19:11
 */
public class Profile extends GeneralController {

    public static List<String> genderList = User.Gender.toStringList();

    public static class Settings {

        public String email;
        public String password;
        public String name;
        public String secondName;
        public String whereFrom;
        public String userAvatar;
        public User.Gender gender;

    }


    public static Result userCycles(long id) {
        List<models.Cycle> cycles = models.Cycle.showAuthorCycles(User.find.byId(id));
        return ok(pcyclesinfo.render(User.find.byId(id), cycles));
    }


    public static Result profile(long id){
        return ok(pgeneralinfo.render(User.find.byId(id)));
    }

    public static Result userProjects(long id){
        List<Project> projects = new ArrayList();
        return ok(pprojectsinfo.render(User.find.byId(id), projects));
    }

    @Security.Authenticated(Secured.class)
    public static Result userSettings(long id){
        if(id!=Long.parseLong(session().get("id")))
            return redirect(
                routes.Profile.profile(id)
            );
        return ok(pusersettings.render(User.find.byId(id), form(Settings.class), genderList));
    }

    @Security.Authenticated(Secured.class)
    public static Result saveSettings(long id) throws IOException {
        User user = User.find.byId(id);
        Form<Settings> settingsForm = form(Settings.class).bindFromRequest();
        if (settingsForm.hasErrors()) {
            return badRequest(pusersettings.render(user,settingsForm,genderList));
        } else {
            user.name = settingsForm.get().name;
            user.email = settingsForm.get().email;
            if(!settingsForm.get().password.equals(""))
                user.password = settingsForm.get().password;
            user.secondName = settingsForm.get().secondName;
            user.whereFrom = settingsForm.get().whereFrom;
            user.gender = settingsForm.get().gender;

            Http.MultipartFormData body = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart picture = body.getFile("userAvatar");

            if(!picture.getFilename().equals("")){
                user.userAvatar = imageLoader(picture);
            }
            user.update();
            flash("success", "Данные успешно обновлены");
            return ok(pusersettings.render(user, form(Settings.class),genderList));
        }
    }
}
