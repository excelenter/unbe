package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by:
 * User: mayatskiy
 * Date: 22.04.13
 * Time: 14:55
 */
@Entity
public class Article extends GeneralModel {

    public static String NEWS_TITLE = "Новость";

    @Id
    public long id;
    public String title;

    @Column(columnDefinition = "TEXT")
    public String content;
    public boolean isPublished;
    public boolean isApproved;

    @Formats.DateTime(pattern="dd.MM.yyyy")
    public Date publishDate;
    public int baseRating;
    public int rating;
    @OneToOne
    public S3File listImage;

    @Column(columnDefinition = "TEXT")
    public String tmpContent;

    @ManyToOne
    public Cycle parentCycle;
    @ManyToOne
    public ArticleType articleType;
    @ManyToOne
    public ArticleArea articleArea;

    public static Model.Finder<Long,Article> find = new Model.Finder(Long.class, Article.class);

    public Article(String title, S3File listImage, String tmpContent, Cycle cycle, ArticleType articleType,  ArticleArea articleArea){
        this.title = title;
        this.listImage = listImage;
        this.tmpContent = tmpContent;
        this.parentCycle = cycle;
        this.articleType = articleType;
        this.articleArea = articleArea;
        isPublished = false;
        isApproved = false;
        baseRating = 0;
        rating = 0;
    }

    public Article(String title, Cycle cycle, ArticleType articleType,  ArticleArea articleArea){
        this(title, null, "", cycle, articleType, articleArea);
    }

    public static Article create(String title, Cycle cycle, ArticleType articleType,  ArticleArea articleArea){
        Article article = new Article(title, cycle, articleType, articleArea);
        article.save();
        return article;
    }

    public static List<Article> lastNews(){
        return find.where().eq("articleType.title", NEWS_TITLE).orderBy().desc("publishDate").findList();
    }

    public static List<Article> cycleArticles(Cycle cycle){
        return find.where().eq("parentCycle", cycle).orderBy().desc("publishDate").findList();
    }
}
