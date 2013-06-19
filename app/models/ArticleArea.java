package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by:
 * User: mayatskiy
 * Date: 22.04.13
 * Time: 16:31
 */
@Entity
public class ArticleArea extends GeneralModel {
    @Id
    public int id;
    public String title;

    public static Model.Finder<Integer,ArticleArea> find = new Model.Finder(Integer.class, ArticleArea.class);

    public ArticleArea(String title){
        this.title = title;
    }
}
