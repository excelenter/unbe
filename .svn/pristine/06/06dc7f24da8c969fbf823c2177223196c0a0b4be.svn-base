package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.article.articles;
import views.html.article.news;

import java.util.List;

/**
 * Created by:
 * User: mayatskiy
 * Date: 16.04.13
 * Time: 15:28
 */

public class Article extends Controller {
    public static Result lastNews() {
        List<models.Article> LastNews = models.Article.lastNews();

        return ok(news.render(LastNews));
    }

    public static Result articles(){
        return ok(articles.render());
    }
}
