import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class TransactionAnalyser {

    public static void parseCSV(String fromDate, String toDate, String merchant, String path) throws Exception {

        if (checkForNull(fromDate, toDate, merchant, path)) {
            throw new Exception("Incorrect parameters");
        }

        if (!validateFile(path)) {
            throw new Exception("Incorrect file");
        }

        String pattern = "dd/MM/u HH:mm:ss";
        LocalDateTime startDate = LocalDateTime.parse(fromDate, DateTimeFormatter.ofPattern(pattern));
        LocalDateTime endDate = LocalDateTime.parse(toDate, DateTimeFormatter.ofPattern(pattern));
        HashMap<String, String> transactionsMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String transactionLine = reader.readLine();
            String[] transactionInfo;
            while ((transactionLine = reader.readLine()) != null) {
                transactionInfo = transactionLine.replaceAll("\"", "").split(" *, *");
                LocalDateTime date = LocalDateTime.parse(transactionInfo[1], DateTimeFormatter.ofPattern(pattern));
                if (!date.isBefore(startDate)) {
                    if (checkTransaction(transactionInfo, date, endDate, merchant)) {
                        transactionsMap.put(transactionInfo[0], transactionInfo[2]);
                    } else if (checkRelatedTransaction(transactionInfo[4])) {
                        removeTransaction(transactionsMap, transactionInfo[5]);
                    }
                }
            }
            report(transactionsMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void report(Map<String, String> transactionsMap) {
        double averageAmount = 0;
        int countTransactions = transactionsMap.size();
        if (countTransactions > 0) {
            for (String key: transactionsMap.keySet()) {
                averageAmount += Double.parseDouble(transactionsMap.get(key));
            }
            averageAmount /= countTransactions;
        }
        System.out.println("Number of transactions = " + countTransactions);
        System.out.println("Average Transaction Value = " + averageAmount);
    }

    private static boolean checkForNull(String fromDate, String toDate, String merchant, String path) {
        return fromDate == null || toDate == null || merchant == null || path == null
                || "".equals(fromDate) || "".equals(toDate) || "".equals(merchant) || "".equals(path);
    }

    private static boolean validateFile(String path) {
        return path.endsWith(".csv");
    }

    private static boolean checkTransaction(String[] transactionInfo, LocalDateTime date, LocalDateTime endDate, String merchant) {
        return date.isBefore(endDate)
                && transactionInfo[3].equals(merchant)
                && "PAYMENT".equals(transactionInfo[4].toUpperCase());
    }

    private static boolean checkRelatedTransaction(String status) {
        return "REVERSAL".equals(status.toUpperCase());
    }

    private static void removeTransaction(Map<String, String> transactionsMap, String relatedId) {
        for (String key: transactionsMap.keySet()) {
            if (key.equals(relatedId)) {
                transactionsMap.remove(key);
                break;
            }
        }
    }
}