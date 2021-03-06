package models;

import play.data.format.Formats;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by:
 * User: mayatskiy
 * Date: 28.03.13
 * Time: 13:56

 */
@Entity
public class User extends GeneralModel {

    public enum Gender {
        UNKNOWN, MALE, FEMALE;
        public static List<String> toStringList(){
            ArrayList<String> result = new ArrayList<String>();
            Gender allValues[] = Gender.values(); // Returns array of enum constants.
            for( Gender value : allValues ){
                result.add(value.toString());
            }
            return result;
        }

    }

    @Id
    public long id;
    public String email;
    public String name;
    public String secondName;
    public String password;

    @Formats.DateTime(pattern="dd.MM.yyyy")
    public Date registerDate;

    public String whereFrom;
    public boolean isActive;
    @OneToOne
    public S3File userAvatar;
    public int statusPoints;
    public Gender gender;

    @ManyToOne
    public UserStatus userStatus;

    public User(String email, String name, String secondName, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.secondName = secondName;
        isActive = true;
        statusPoints = 0;
        userAvatar = null;
        registerDate = new Date (System.currentTimeMillis());
        userStatus = UserStatus.find.ref(1);
        gender = Gender.UNKNOWN;
    }

    public User(String email, String name, String password){
        this(email, name, " ",  password);
    }

    public static User authenticate(String email, String password) {
        return find.where().eq("email", email)
                .eq("password", password).findUnique();
    }

    public static Finder<Long,User> find = new Finder<Long,User>(Long.class, User.class);
}
