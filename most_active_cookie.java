import org.apache.commons.cli.*;
import java.time.format.DateTimeFormatter;
import java.time.*;
import java.util.*;
import java.util.Map.Entry;
import java.io.*;

class most_active_cookie
{
    final static String COMMA_DELIMITER = ",";
    public static void main(final String[] args) throws Exception 
    {
        final Options options = new Options();

        //Create required option "-d" for date in UTC time
        Option dateOption = Option.builder("d")
            .argName("date")
            .hasArg()
            .required(true)
            .desc("Date in UTC time zone").build();
        options.addOption(dateOption);

        Option filePath = Option.builder("")
            .argName("filePath")
            .hasArg()
            .required(true)
            .desc("Path to cookie log file").build();

        final CommandLineParser parser = new DefaultParser();
        final HelpFormatter helper = new HelpFormatter();

        Date testDate;

        try 
        {
            final CommandLine cmd = parser.parse(options, args);
            final String fileName;
            if(cmd.getArgList().size() < 1) throw new FileNameException();
            else fileName = cmd.getArgList().get(0); 
            if(cmd.hasOption("d"))
            {
                //SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
                //inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                //Date inputDate = inputFormat.parse(cmd.getOptionValue("d"));
                final String inputDate = cmd.getOptionValue("d");
                for(String cookie : mostActive(fileName, inputDate))
                {
                    System.out.println(cookie);
                }
                //System.out.println(inputDate);
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

    public static List<String> mostActive(String fileName, String inputDate)
    {
        //DateTimeFormatter inputFormat = DateTimeFormatter.ISO_LOCAL_DATE;
        //LocalDate searchDate = LocalDate.parse(inputDate, inputFormat);

        HashMap<String, Integer> logMap = logToMap(fileName, inputDate);

        if(logMap.size() == 0) return new ArrayList<String>();
        int maxCount=(Collections.max(logMap.values()));
        List<String> activeCookies = new ArrayList<>();
        for(Entry<String, Integer> entry : logMap.entrySet())
        {
            if(entry.getValue() == maxCount) activeCookies.add(entry.getKey());
        }
        return activeCookies;
    }

    public static HashMap<String, Integer> logToMap(String fileName, String searchDate)
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            String entry;
            DateTimeFormatter entryDateFormat = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            DateTimeFormatter dateOnlyFormat = DateTimeFormatter.ISO_LOCAL_DATE;
            HashMap<String, Integer> logMap = new HashMap<>();
            while((entry = reader.readLine()) != null)
            {
                String[] entrySplit = entry.split(COMMA_DELIMITER);
                String entryDate = dateOnlyFormat
                    .format(ZonedDateTime
                        .parse(entrySplit[1], entryDateFormat)
                        .withZoneSameInstant(ZoneOffset.UTC));
                //String entryDate = dateOnlyFormat.format(LocalDate.parse(entrySplit[1], entryDateFormat)
                //        .atStartOfDay(ZoneId.of("UTC-12")));
                //System.out.println(entryDate + " " + searchDate);
                if(entryDate.equals(searchDate))
                {
                    //System.out.println("adding to map");
                    logMap.put(entrySplit[0], logMap.containsKey(entrySplit[0]) ? logMap.get(entrySplit[0]) + 1 : 1);
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
    }

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

