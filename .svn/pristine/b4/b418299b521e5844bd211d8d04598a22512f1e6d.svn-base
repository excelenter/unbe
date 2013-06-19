package controllers;

import models.S3File;
import org.imgscalr.Scalr;
import play.mvc.Controller;
import play.mvc.Http;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: mayatskiy
 * Date: 24.05.13
 * Time: 20:36
 * To change this template use File | Settings | File Templates.
 */
public class GeneralController extends Controller {
    public static S3File imageLoader(Http.MultipartFormData.FilePart picture) throws IOException {
        File file = picture.getFile();
        BufferedImage img = ImageIO.read(file);
        BufferedImage scaledImg = Scalr.resize(img, Scalr.Mode.AUTOMATIC, 100, 100);
        ImageIO.write(scaledImg, "jpg", file);
        S3File s3File = new S3File();
        s3File.name = picture.getFilename();
        System.out.println("!!!" + s3File.name);
        s3File.file = file;
        s3File.save();
        return s3File;
    }
}
