import analyser.RedisMonitorOutputAnalyser;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        RedisMonitorOutputAnalyser.analyzeFile("filePath");
    }
}
