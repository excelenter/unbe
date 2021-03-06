package controllers;

import models.S3File;
import play.data.Form;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import views.html.cycle.cnewcycle;

import java.io.IOException;

import static play.data.Form.form;

/**
 * Created with IntelliJ IDEA.
 * User: mayatskiy
 * Date: 24.05.13
 * Time: 18:57
 * To change this template use File | Settings | File Templates.
 */
public class Cycle extends GeneralController {
    public static class NewCycle {

        public String title;
        public S3File logo;
        public String description;

    }

    @Security.Authenticated(Secured.class)
    public static Result newCycle() {
        return ok(cnewcycle.render(form(NewCycle.class)));
    }

    @Security.Authenticated(Secured.class)
    public static Result saveCycle() throws IOException {
        Long userId = Long.parseLong(session("id"));
        Form<NewCycle> newCycleForm = form(NewCycle.class).bindFromRequest();
        if (newCycleForm.hasErrors()) {
            return badRequest(cnewcycle.render(newCycleForm));
        }
        else{
            S3File s3File = null;
            Http.MultipartFormData body = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart picture = body.getFile("logo");

            if(!picture.getFilename().equals("")){
                s3File = imageLoader(picture);
            }

            models.Cycle cycle = new models.Cycle(newCycleForm.get().title,
                    newCycleForm.get().description,s3File,Long.parseLong(session("id")));

            cycle.save();
            return redirect(
                routes.Profile.userCycles(userId)
            );
        }
    }
}
