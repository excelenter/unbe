package controllers;


import models.Cycle;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.index;
import views.html.login;
import views.html.signup;

import static play.data.Form.form;


public class Application extends Controller {

    public static class Login {

        public String email;
        public String password;

        public String validate() {
            if (User.authenticate(email, password) == null) {
                return "Неверный адрес электронной почты или пароль";
            }
            return null;
        }

    }

    public static class Registration {

        public String name;
        public String email;
        public String password;
        public String passwordRepeat;

        public String validate() {
            if (!password.equals(passwordRepeat)) {
                return "Пароль и подтверждение пароля не совпадают";
            } else if (User.find.where().eq("email", email).findUnique()!=null){
                return "Пользователь с таким email уже зарегистрирован";
            } else if ((name == "")||(email == "") || (password == "")||(passwordRepeat == "")){
                return "Все поля должны быть заполнены. Их не так много";
            } else if (password.length() < 6)
                return "Длина пароля должна превышать 6 символов. Это для вашей же безопасности";
            return null;
        }

    }

    public static Result login() {
        return ok(
            login.render(form(Login.class))
        );
    }

    public static Result signUp(){
        return ok(
           signup.render(form(Registration.class))
        );
    }

    public static Result register(){
        Form<Registration> registrationForm = form(Registration.class).bindFromRequest();
        if (registrationForm.hasErrors()) {
            return badRequest(signup.render(registrationForm));
        } else {
            String name = registrationForm.get().name;
            String email = registrationForm.get().email;
            String password = registrationForm.get().password;
            User newUser = new User(email, name, password);
            newUser.save();
            flash("success", "Регистрация прошла успешно. Добро пожаловать!");
            return redirect(
                    routes.Application.login()
            );
        }
    }

    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            String email = loginForm.get().email;
            User user = User.find.where().eq("email", email).findUnique();
            session("name", user.name);
            session("email", email);
            session("id", String.valueOf(user.id));
            return redirect(
                    routes.Article.lastNews()
            );
        }
    }

    public static Result logout() {
        session().clear();
        flash("success", "Вы покинули сайт");
        return redirect(
                routes.Application.login()
        );
    }


}
