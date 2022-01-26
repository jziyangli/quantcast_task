# quantcast_task
I chose to use java.time. I believe without this, it would be extremely difficult to implement the intended functionality within the given time constraints. This is due to the complexity of comparing dates and times, especially across different time zones, considering edge cases such as months having 28,30 or 31 days, leap years, and so on and so forth. 
I used apache commons cli for parsing the command line arguments and java.io for reading the log file.
Java.util contains basic data structures such as HashMaps and Lists which are necessary to store frequency of cookie occurances, cookie names, etc.

# Algorithm
I had initially intended to to a binary search to find the specified date in the file (given that the file is sorted). However, performing a binary search would require first reading all of the lines into some data structure. Because of this, I decided on comparing the dates as I read the lines.
For each line, the entry is split into cookie name and date/time. The time is converted to UTC date, and compared to the specified date, if they are the same, I increment the count for that cookie in a hashmap (inserting it with value 1 if the key doesn't already exist)
Then find the max value/count in the hashmap and find all keys(cookies) with matching value.
The list of these keys is then printed.
# Format
This application has 5 main files:
* MostActiveCookie.java
* * This is the front-facing file which is used to parse command line arguments and print the output
* LogMap.java
* * This file implements the logic and acts as a 'backend' to MostActiveCookie.java. It constructs a hashmap and finds the number of occurances of a cookie within a specified date.
* most_active_cookie
* * This is a bash script which first checks for dependencies (apache commons cli) and sets the CLASSPATH environment variable prior to running MostActiveCookie
* LogMapTest.java
* * This file tests the functionality of the LogMap class. Ideally, this would have been implemented using jUnit, but it was not possible due to time constraints and issues with using correct libraries.
* most_active_cookie_test
* * This is a bash script which first checks for dependencies (apache commons cli AND JUnit), installing either if necessary, and sets the CLASSPATH variable prior to running LogMapTest
# How to Run
* clone the repository (git clone https://github.com/jziyangli/quantcast_task.git)
* cd to repository (cd quantcast_task)
* make sure java and javac are installed (which java, which javac)
* If all dependencies are already installed correctly, you can run: java MostActiveCookie test/utc.csv -d 2018-12-09
* However using the provided script is recommended:
* add executable permissions to most_active_cookie (chmod +x most_active_cookie)
* run most_active_cookie as specified in the instructions (./most_active_cookie tests/utc.csv -d 2018-12-09)
* * log files are located in the tests/ directory. I have provided 2 log files: utc.csv and offset.csv
* * utc.csv contains all dates/times in UTC time
* * offset.csv contains the same dates/times as utc.csv, but some are in different time zones, thus potentially changing the UTC date
# How to Test
* clone the repository
* make sure that java and javac are installed
* add executable permissions to most_active_cookie_test
* run most_active_cookie_test (./most_active_cookie_test)
* * This will download JUnit libraries on first execution
* * Will print "no errors" upon successful run
* * Otherwise will reach an error at the appropriate assert statement
