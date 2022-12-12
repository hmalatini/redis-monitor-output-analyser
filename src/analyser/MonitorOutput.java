package analyser;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

record MonitorOutput(Instant timestamp, int dbNumber, String host, String operationType, String key) {

    MonitorOutput(String outputLine) {
        this(outputLine.split(" "));
    }

    MonitorOutput(String[] outputs) {
        this(getInstantFromTimestamp(outputs), getDbNumberFromOutput(outputs), getHostFromOutput(outputs),
            getOperationTypeFromOutput(outputs),
            getKeyFromOutput(outputs));
    }

    private static String getKeyFromOutput(String[] outputs) {
        if (outputs.length < 5) {
            return "";
        }

        StringBuilder key = new StringBuilder();
        for (int i = 4; i < outputs.length; i++) {
            key.append(outputs[i]);
            if (i + 1 < outputs.length) {
                key.append(" ");
            }
        }
        return key.toString();
    }

    private static String getOperationTypeFromOutput(String[] outputs) {
        if (outputs.length < 5) {
            return "";
        }
        return outputs[3];
    }

    private static String getHostFromOutput(String[] outputs) {
        if (outputs.length < 5) {
            return "";
        }
        return outputs[2].substring(0, outputs[2].length() - 1);
    }

    private static int getDbNumberFromOutput(String[] outputs) {
        if (outputs.length < 5) {
            return 0;
        }
        return Integer.parseInt(outputs[1].substring(1));
    }

    private static Instant getInstantFromTimestamp(String[] outputs) {
        if (outputs.length < 5) {
            return null;
        }
        List<Long> timestampDefinition = Stream.of(outputs[0].split("\\."))
                                               .map(Long::valueOf)
                                               .toList();
        return Instant.ofEpochSecond(timestampDefinition.get(0), timestampDefinition.get(1));
    }

    boolean isGetOperation() {
        return this.operationType.equals("\"get\"");
    }
}
