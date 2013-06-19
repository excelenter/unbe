package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by:
 * User: mayatskiy
 * Date: 22.04.13
 * Time: 16:25
 */
@Entity
public class ArticleType extends GeneralModel {

    @Id
    public int id;
    public String title;

    public static Model.Finder<Integer,ArticleType> find = new Model.Finder(Integer.class, ArticleType.class);

    public ArticleType(String title){
        this.title = title;
    }
}
