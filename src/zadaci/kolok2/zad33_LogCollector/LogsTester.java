package kolok2.zad33_LogCollector;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

enum TypeOfLog {
    INFO, WARN, ERROR
}

class Log {
    //service_name microservice_name type message timestamp, каде што type може да биде INFO, WARN и ERROR
    String service_name;
    String microservice_name;
    TypeOfLog type;
    String message;
    long timestamp;

    public Log(String service_name, String microservice_name, String type, String message, long timestamp) {
        this.service_name = service_name;
        this.microservice_name = microservice_name;
        this.type = TypeOfLog.valueOf(type);
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getService_name() {
        return service_name;
    }

    public String getMicroservice_name() {
        return microservice_name;
    }

    public TypeOfLog getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getSeverity() {
        int severity = 0;
        if (type == TypeOfLog.ERROR) {
            severity = 3;
            if (message.contains("fatal") && message.contains("exception")) {
                severity += 5;
            } else if (message.contains("exception")) {
                severity += 3;
            } else if (message.contains("fatal")) {
                severity += 2;
            }
        } else if (type == TypeOfLog.WARN) {
            severity = 1;
            if (message.contains("might cause error")) {
                severity += 1;
            }
        }
        // TypeOfLog.INFO si vrakja 0 sekako
        return severity;
    }

    @Override
    public String toString() {
        //service2|microservice2 [INFO] Log message 9. 953 T:953↩
        return String.format("%s|%s [%s] %s %d T:%d",
                service_name,
                microservice_name,
                type,
                message,
                timestamp,
                timestamp);
    }
}

class Service implements Comparable<Service> {
    String service_name;
    Map<String, Integer> logsPerMicroServiceSize;
    Map<String, List<Log>> logsPerMicroServiceLogs;
    List<Log> logs;

    public Service(String service_name) {
        this.service_name = service_name;
        this.logsPerMicroServiceSize = new HashMap<>();
        this.logsPerMicroServiceLogs = new HashMap<>();
        this.logs = new ArrayList<>();
    }

    public double averageSeverityForLogs() {
        return logs.stream()
                .mapToInt(Log::getSeverity)
                .average()
                .orElse(0.0);
    }

    public double averageNumberOfLogsPerMicroService() {

        for (String microservice_name : logsPerMicroServiceLogs.keySet()) {
            List<Log> logsPerMicro = logs.stream()
                    .filter(log -> log.getMicroservice_name().equals(microservice_name))
                    .collect(Collectors.toList());
            logsPerMicroServiceSize.put(microservice_name, logsPerMicro.size());
        }

        return logsPerMicroServiceSize.values().stream().mapToDouble(i -> i).average().orElse(0.0);
    }

    public Map<Integer, Long> getSeverityPerMicroService(String microservice) {
        return logsPerMicroServiceLogs.get(microservice).stream().collect(Collectors.groupingBy(
                Log::getSeverity,
                Collectors.counting()));
    }


    @Override
    public String toString() {
        //Service name: service2 Count of microservices: 3 Total logs in service: 5 Average severity for all logs: 3.40 Average number of logs per microservice: 1.67
        return String.format("Service name: %s" +
                        " Count of microservices: %d" +
                        " Total logs in service: %d" +
                        " Average severity for all logs: %.2f" +
                        " Average number of logs per microservice: %.2f",
                service_name,
                logsPerMicroServiceLogs.keySet().size(),
                logs.size(),
                averageSeverityForLogs(),
                averageNumberOfLogsPerMicroService()
        );
    }

    @Override
    public int compareTo(Service o) {
        return Double.compare(this.averageSeverityForLogs(), o.averageSeverityForLogs());
    }
}

class LogCollector {

    Map<String, Service> logsPerService;

    public LogCollector() {
        this.logsPerService = new TreeMap<>();
    }

    public void addLog(String addLog) {

        String[] parts = addLog.split("\\s+");
        String serviceName = parts[0];
        String microserviceName = parts[1];
        String type = parts[2];

        String message = IntStream.range(3, parts.length - 1).mapToObj(line -> parts[line]).collect(Collectors.joining(" "));
        long timestamp = Long.parseLong(parts[parts.length - 1]);

        Log newLog = new Log(serviceName, microserviceName, type, message, timestamp);

        if (logsPerService.containsKey(serviceName)) {
            //se pojavil takov service dodaj log i micro

            if (logsPerService.get(serviceName).logsPerMicroServiceLogs.containsKey(microserviceName)) {
                logsPerService.get(serviceName).logsPerMicroServiceLogs.get(microserviceName).add(newLog);
            } else {
                logsPerService.get(serviceName).logsPerMicroServiceLogs.put(microserviceName, new ArrayList<>());
                logsPerService.get(serviceName).logsPerMicroServiceLogs.get(microserviceName).add(newLog);
            }
        } else {
            //ne se pojavil uste takov service, keriraj prazen
            Service newService = new Service(serviceName);
            newService.logsPerMicroServiceLogs.put(microserviceName,new ArrayList<>());
            newService.logsPerMicroServiceLogs.get(microserviceName).add(newLog);
            logsPerService.put(serviceName, newService);
        }
        logsPerService.get(serviceName).logs.add(newLog);

    }

    public void printServicesBySeverity() {
        logsPerService.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Service>comparingByValue().reversed())
                .forEach(entry -> System.out.println(entry.getValue()));
    }

    public Map<Integer, Long> getSeverityDistribution(String service, String microservice) {

        if (microservice == null) {
            return logsPerService.get(service).logs.stream().collect(Collectors.groupingBy(
                    Log::getSeverity,
                    Collectors.counting()));
        } else {

            return logsPerService.get(service).logsPerMicroServiceLogs.get(microservice).stream()
                    .collect(Collectors.groupingBy(
                            Log::getSeverity,
                            Collectors.counting()));
        }
    }

    public void displayLogs(String service, String microservice, String order) {


        Comparator<Log> sortNewestFirst = Comparator.comparingLong(Log::getTimestamp).reversed();
        Comparator<Log> sortOldestFirst = Comparator.comparingLong(Log::getTimestamp);
        Comparator<Log> sortMostSevereFirst = Comparator.comparingInt(Log::getSeverity).thenComparing(Log::getTimestamp).reversed();
        Comparator<Log> sortLeastSevereFirst = Comparator.comparingInt(Log::getSeverity);
        Comparator<Log> comparator = null;

        if (order.equalsIgnoreCase("NEWEST_FIRST")) {
            comparator = sortNewestFirst;
        } else if (order.equalsIgnoreCase("OLDEST_FIRST")) {
            comparator = sortOldestFirst;
        } else if (order.equalsIgnoreCase("MOST_SEVERE_FIRST")) {
            comparator = sortMostSevereFirst;
        } else if (order.equalsIgnoreCase("LEAST_SEVERE_FIRST")) {
            comparator = sortLeastSevereFirst;
        }

        if (microservice == null) {
            logsPerService.get(service).logs.stream().sorted(comparator)
                    .forEach(System.out::println);
        } else {
            logsPerService.get(service).logsPerMicroServiceLogs.get(microservice)
                    .stream()
                    .sorted(comparator)
                    .forEach(System.out::println);
        }

    }
}


public class LogsTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LogCollector collector = new LogCollector();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.startsWith("addLog")) {
                collector.addLog(line.replace("addLog ", ""));
            } else if (line.startsWith("printServicesBySeverity")) {
                collector.printServicesBySeverity();
            } else if (line.startsWith("getSeverityDistribution")) {
                String[] parts = line.split("\\s+");
                String service = parts[1];
                String microservice = null;
                if (parts.length == 3) {
                    microservice = parts[2];
                }
                collector.getSeverityDistribution(service, microservice).forEach((k, v) -> System.out.printf("%d -> %d%n", k, v));
            } else if (line.startsWith("displayLogs")) {
                String[] parts = line.split("\\s+");
                String service = parts[1];
                String microservice = null;
                String order = null;
                if (parts.length == 4) {
                    microservice = parts[2];
                    order = parts[3];
                } else {
                    order = parts[2];
                }
                System.out.println(line);

                collector.displayLogs(service, microservice, order);
            }
        }
    }
}
