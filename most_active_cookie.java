import java.util.*;
import java.util.Map.*;
import java.io.*;
import java.time.*;
import java.time.format.*;

/**
 *  This class contains the most_active_cookie class which implements the logic
 *  behind the MostActiveCookie class.
 */
public class most_active_cookie
{
    private final String COMMA_DELIMITER = ",";
    private HashMap<String, Integer> logMap;
    private String fileName;
    private String searchDate;

    /**
     *  Constructor:
     *  Given the log file and specified date, finds the max occurance count and returns
     *  a list of all cookies with the sasme occurance count
     *
     *  @param  _fileName    Name of .csv file to read.
     *  @param  _inputDate   The specified date to search within
     *  @return List of the cookies that occur the most within specified inputDate
     */
    most_active_cookie(String _fileName, String _searchDate)
    {
        fileName = _fileName;
        searchDate = _searchDate;

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            String entry;
            //Date format to read
            DateTimeFormatter entryDateFormat = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            //Date format to convert to
            DateTimeFormatter dateOnlyFormat = DateTimeFormatter.ISO_LOCAL_DATE;
            logMap = new HashMap<>();
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
        }
        catch (FileNotFoundException ex)
        {
            System.exit(0);
        }
        catch(IOException ex)
        {
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
    public List<String> mostActive()
    {
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
    }

    public HashMap<String, Integer> getMap()
    {
        return logMap;
    }
}
