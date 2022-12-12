package analyser;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

class ResultCalculator {

    private final Map<String, KeyResult> resultsByKey;

    ResultCalculator() {
        this.resultsByKey = new HashMap<>();
    }

    void addResult(String key) {
        KeyResult previousResult = resultsByKey.getOrDefault(key, new KeyResult(key, 0));
        resultsByKey.put(key, new KeyResult(key, previousResult.counter() + 1));
    }

    List<Result> getResults() {
        System.out.println("Amount of keys accessed in this cache: " + resultsByKey.size());

        int operationsLimit = 500;
        AtomicInteger operationCounter = new AtomicInteger();

        List<KeyResult> results = resultsByKey.values()
                                              .stream()
                                              .peek(keyResult -> operationCounter.addAndGet(keyResult.counter()))
                                              .sorted(
                                                  Comparator.comparingInt(KeyResult::counter).reversed())
                                              //.limit(20)
                                              .filter(r -> r.counter() > operationsLimit)
                                              .toList();

        int operationNumber = operationCounter.get();
        System.out.println("Amount of operations in this cache: " + operationNumber);
        List<Result> resultList = results.stream()
                      .map(r -> new Result(r.key(), r.counter(), (r.counter() * 100 / operationNumber)))
                      .toList();

        System.out.println("Amount of keys with more than " + operationsLimit + " operations in this cache: " + resultList.size());
        System.out.println("List:");
        return resultList;
    }
}
