# quantcast_task
I chose to use java.time. I believe without this, it would be extremely difficult to implement the intended functionality within the given time constraints. This is due to the complexity of comparing dates and times, especially across different time zones, considering edge cases such as months having 28,30 or 31 days, leap years, and so on and so forth. 
I used apache commons cli for parsing the command line arguments and java.io for reading the log file.
Java.util contains basic data structures such as HashMaps and Lists which are necessary to store frequency of cookie occurances, cookie names, etc.
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
* clone the repository
* make sure java and javac are installed (which java, which javac)
* add executable permissions to most_active_cookie (chmod +x most_active_cookie)
* run most_active_cookie as specified in the instructions (./most_active_cookie cookie_log.csv -d 2018-12-09)
# How to Test
* clone the repository
* make sure that java and javac are installed
* add executable permissions to most_active_cookie_test
* run most_active_cookie_test (./most_active_cookie_test)
* * This will download JUnit libraries on first execution
* * Will print "no errors" upon successful run
* * Otherwise will reach an error at the appropriate assert statement
