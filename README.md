# Transaction-analyser
Simple Project to demonstrate financial transaction analysis system.
### How to run
* Download the repository
* Open the Main.java class
* Specify the FULL path to the csv file in a path variable.  
Example: String path = "..your path should be here";
* Specify parameters of parseCSV function: the first parameter is fromDate, second - toDate, third - merchant, the fourth parameter is the variable path  
Example: TransactionAnalyser.parseCSV("20/08/2018 12:00:00", "20/08/2018 13:00:00", "Kwik-E-Mart", path);
* See the output of the program in the console
### Task
The goal of the system is to display statistic information about processed financial transactions.
A transaction record will contain the following fields:
* ID - A string representing the transaction id
* Date - The date and time when the transaction took place (format "DD/MM/YYYY hh:mm:ss")
* Amount - The value of the transaction (dollars and cents)
* Merchant - The name of the merchant this transaction belongs to
* Type - The type of the transaction, which could be either PAYMENT or REVERSAL
* Related Transaction - (Optional) - In the case a REVERSAL transaction, this field will contain the ID of the transaction it is reversing.
#### The Problem
The system will be Initialised with an input file in CSV format containing a list of transaction records.
Once initialised, the system should report the total number of transactions and the average transaction value for a specific merchant in a specific date range.
An additional requirement is that, if a transaction record has a REVERSAL transaction, then it should not be included in the computed statistics, even if the reversing transaction is outside of the requested date range.
#### Assumptions
For the sake of simplicity, you can assume that Transaction records are listed in correct time order.  
The input file is well formed and is not missing data.
