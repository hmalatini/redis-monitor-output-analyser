package analyser;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class RedisMonitorOutputAnalyser {

    public static void analyzeFile(String path) throws IOException {
        ResultCalculator resultCalculator = new ResultCalculator();
        calculateResultsForOutputFile(path, resultCalculator);

        printResult(resultCalculator.getResults());
    }

    private static void printResult(List<Result> results) {
        for (Result r : results) {
            System.out.println(r);
        }
    }

    private static void calculateResultsForOutputFile(String path, ResultCalculator resultCalculator) throws IOException {
        ConnectionHandler connectionHandler = null;
        try {
            connectionHandler = new ConnectionHandler(path);
            processFile(connectionHandler, resultCalculator);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            assert connectionHandler != null;
            connectionHandler.close();
        }
    }

    private static void processFile(ConnectionHandler connectionHandler, ResultCalculator resultCalculator)
        throws IOException {
        Optional<String> outputLine = connectionHandler.read();
        while (outputLine.isPresent()) {
            MonitorOutput output = new MonitorOutput(outputLine.get());
            if (output.isGetOperation()) {
                resultCalculator.addResult(output.key());
            }

            outputLine = connectionHandler.read();
        }
    }
}
