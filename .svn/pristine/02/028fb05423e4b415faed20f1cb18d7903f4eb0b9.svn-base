package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 * User: Arugin
 * Date: 09.04.13
 * Time: 1:09
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class UserStatus extends GeneralModel {
    @Id
    public int id;
    public String title;

    public static Model.Finder<Integer,UserStatus> find = new Model.Finder(Integer.class, UserStatus.class);

    public UserStatus(String title){
        this.title = title;
    }
}
