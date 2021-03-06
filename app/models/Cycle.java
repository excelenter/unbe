package models;

import play.db.ebean.*;
import javax.persistence.*;
import java.util.List;

/**
 * Created by:
 * User: mayatskiy
 * Date: 30.03.13
 * Time: 13:41
 */
@Entity
public class Cycle extends GeneralModel {

    @Id
    public long id;
    public String title;
    @OneToOne
    public S3File logo;
    public String description;

    @ManyToOne
    public User creator;

    public static Model.Finder<Long,Cycle> find = new Model.Finder(Long.class, Cycle.class);

    public Cycle(String title, String description, S3File logo, Long creatorId){
        this.title = title;
        this.logo = logo;
        this.description = description;
        this.creator = User.find.ref(creatorId);
    }

    public static Cycle create(String title, String description, S3File logo, Long creatorId){
        Cycle cycle = new Cycle(title, description, logo, creatorId);
        cycle.save();
        return cycle;
    }

    public static List<Cycle> showAuthorCycles(User user){
        return find.where().eq("creator.email", user.email).findList();
    }
}
