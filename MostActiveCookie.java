/**
 *  This file contains the main java executable for the Quantcast coding task. 
 *  It contains the class most_active_cookie and a subclass FileNameException
 *  @author John Li jziyangli@yahoo.com
 */

import org.apache.commons.cli.*;
import java.time.format.DateTimeFormatter;
import java.time.*;
import java.util.*;
import java.util.Map.Entry;
import java.io.*;

class MostActiveCookie
{
    /**
     *  Used for splitting each entry into cookie and date/time
     */
    final static String COMMA_DELIMITER = ",";

    /**
     *  Main method
     *  Parses input and if valid, determines the cookie with the most occurances
     *  within the specified day.
     *
     *  @param  args    array of arguments
     */
    public static void main(final String[] args) throws Exception 
    {
        final Options options = new Options();

        /**
         *  Create required option "-d" for date in UTC time
         */
        Option dateOption = Option.builder("d")
            .argName("date")
            .hasArg()
            .required(true)
            .desc("Date in UTC time zone").build();
        options.addOption(dateOption);

        final CommandLineParser parser = new DefaultParser();
        final HelpFormatter helper = new HelpFormatter();

        try 
        {
            final CommandLine cmd = parser.parse(options, args);
            final String fileName;

            //There is no argument besides '-d' so no file specified
            if(cmd.getArgList().size() < 1) throw new FileNameException();
            else fileName = cmd.getArgList().get(0); 
            if(cmd.hasOption("d"))
            {
                /**
                 *  Get and print the list of cookies with most occurances
                 */
                final String inputDate = cmd.getOptionValue("d");
                most_active_cookie mac = new most_active_cookie(fileName, inputDate);
                for(String cookie : mac.mostActive())
                {
                    System.out.println(cookie);
                }
            }
        }
        catch(ParseException ex)
        {
            System.out.println(ex.getMessage());
            helper.printHelp("most_active_cookie <FILE> [OPTIONS]", options);
            System.exit(0);
        }
        catch(FileNameException ex)
        {
            System.out.println(ex.getMessage());
            helper.printHelp("most_active_cookie <FILE> [OPTIONS]", options);
            System.exit(0);
        }
    }

    /**
     *  Given the log file and specified date, finds the max occurance count and returns
     *  a list of all cookies with the sasme occurance count
     *
     *  @param  fileName    Name of .csv file to read.
     *  @param  inputDate   The specified date to search within
     *  @return List of the cookies that occur the most within specified inputDate
     */
    /*public static List<String> mostActive(String fileName, String inputDate)
    {
        HashMap<String, Integer> logMap = logToMap(fileName, inputDate);

        if(logMap.size() == 0) return new ArrayList<String>();

        //get the max count from the map
        int maxCount=(Collections.max(logMap.values()));
        List<String> activeCookies = new ArrayList<>();
        
        //iterate through the map to find all keys where the value == max
        for(Entry<String, Integer> entry : logMap.entrySet())
        {
            if(entry.getValue() == maxCount) activeCookies.add(entry.getKey());
        }

        return activeCookies;
    }*/

    /**
     *  Takes a log file and creates a hash map where the key is the cookie name and the
     *  value is the number of occurances within the specified day
     *
     *  @param  fileName    Name of the .csv file to read
     *  @param  searchDate  specified date to search for
     *  @return Hashmap with cookies and occurances within searchDate
     */
    /*public static HashMap<String, Integer> logToMap(String fileName, String searchDate)
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            String entry;
            //Date format to read
            DateTimeFormatter entryDateFormat = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            //Date format to convert to
            DateTimeFormatter dateOnlyFormat = DateTimeFormatter.ISO_LOCAL_DATE;
            HashMap<String, Integer> logMap = new HashMap<>();
            boolean seenDate = false;
            while((entry = reader.readLine()) != null)
            {
                String[] entrySplit = entry.split(COMMA_DELIMITER);
                //Parse the date string, convert to UTC time, then format to contain only date
                if(entrySplit.length != 2) continue;
                String entryDate = dateOnlyFormat
                    .format(ZonedDateTime
                        .parse(entrySplit[1], entryDateFormat)
                        .withZoneSameInstant(ZoneOffset.UTC));
                if(entryDate.equals(searchDate))
                {
                    //increment the cookie's count in hashmap if it already exists, otherwise set to 1
                    logMap.put(entrySplit[0], logMap.containsKey(entrySplit[0]) ? logMap.get(entrySplit[0]) + 1 : 1);
                }
                else if(seenDate)
                {
                    break;
                }
            }
            return logMap;
        }
        catch (FileNotFoundException ex)
        {
            System.exit(0);
        }
        catch(IOException ex)
        {
            System.exit(0);
        }

        return new HashMap<String, Integer>();
    }*/

    /**
     *  Custom exception class for when no file is specified as an argument while running.
     */
    public static class FileNameException extends Exception
    {
        public final static String NO_FILE = "No file specified";
        public FileNameException()
        {
            super(NO_FILE);
        }
        public FileNameException(String errorMessage)
        {
            super(errorMessage);
        }
    }
}

