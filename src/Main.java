
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Jarvan
 * @version 1.0
 * @create 2021/6/5 17:20
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String basePath = "D:\\Enviroment\\java\\java_static_server";
        int port = 9000;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("port:"+port);
        while (true) {
            Socket socket = serverSocket.accept();
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            String reqUrl = getReqUrl(inputStream);
            String[] split = reqUrl.split("/");
            //1.header
            writeHeader(outputStream,split[split.length-1]);
            //2.data
            Path path = Paths.get(basePath,reqUrl);
            try {
                Files.copy(path, outputStream);
            } catch (IOException ioException) {
                System.out.println("FileNotFound:"+path);
                continue;
            }
            outputStream.close();
            socket.close();
        }
    }

    private static String getReqUrl(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();
        String[] strs = line.split(" ");
        //GET /index.html HTTP/1.1...
        return strs[1];
    }
    private static void writeHeader(OutputStream outputStream,String contentType) throws IOException {
        StringBuilder respBuf = new StringBuilder();
        respBuf.append("HTTP/1.1 200 OK\n");
        respBuf.append("Content-Type:");
        respBuf.append(contentType);
        respBuf.append(";charset=UTF-8\n\n");
        outputStream.write(respBuf.toString().getBytes());
        outputStream.flush();
    }
}
