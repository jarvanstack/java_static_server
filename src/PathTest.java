import java.net.URL;

/**
 * @author Jarvan
 * @version 1.0
 * @create 2021/6/7 20:23
 */
public class PathTest {
    public static void main(String[] args) {
        URL url = Main.class.getClassLoader().getResource("/index");
        System.out.println(url.getPath());
    }
}
