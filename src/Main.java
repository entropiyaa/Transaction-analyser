public class Main {
    public static void main(String[] args) throws Exception {
        // You have to provide your OWN FULL path to csv file or "transactions.csv" for my file
        String path = "C:\\Users\\Таня\\IdeaProjects\\Transaction-analyser\\transactions.csv";
        // Function parameters: String fromDate, String toDate, String merchant, String path
        TransactionAnalyser.parseCSV("20/08/2018 12:00:00", "20/08/2018 13:00:00", "Kwik-E-Mart", path);
    }
}