import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;

class LogMapTest  
{
    private static final String UTC_FILE = "tests/utc.csv";
    private static final String OFFSET_FILE = "tests/offset.csv";
    private static final String SINGLE_DATE = "2018-12-09";
    private static final String MULTIPLE_DATE = "2018-12-08";
    private static final String NONE_DATE = "2018-12-10";

    public static void main(String[] args)
    {
        testNone();
        testLogMapUtcSingle();
        testLogMapUtcMultiple();
        testLogMapOffsetSingle();
        testLogMapOffsetMultiple();
        System.out.println("no errors");
    }

    public static void testNone()
    {
        HashMap<String, Integer> logMap = new LogMap(UTC_FILE, NONE_DATE).getMap();

        assertTrue(logMap.size() == 0);
    }

    public static void testLogMapUtcSingle()
    {
        LogMap mac = new LogMap(UTC_FILE, SINGLE_DATE);
        HashMap<String, Integer> logMap = mac.getMap();

        assertTrue(logMap.size() == 3);
        assertTrue(logMap.containsKey("AtY0laUfhglK3lC7"));
        assertTrue(logMap.get("AtY0laUfhglK3lC7") == 2);
    }

    public static void testLogMapUtcMultiple()
    {
        HashMap<String, Integer> logMap = new LogMap(UTC_FILE, MULTIPLE_DATE).getMap();

        assertTrue(logMap.size() == 3);
        assertTrue(logMap.containsKey("SAZuXPGUrfbcn5UA"));
        assertTrue(logMap.containsKey("4sMM2LxV07bPJzwf"));
        assertTrue(logMap.containsKey("fbcn5UAVanZf6UtG"));
        assertTrue(logMap.get("SAZuXPGUrfbcn5UA") == 1);
        assertTrue(logMap.get("4sMM2LxV07bPJzwf") == 1);
        assertTrue(logMap.get("fbcn5UAVanZf6UtG") == 1);
    }

    public static void testLogMapOffsetSingle()
    {
        HashMap<String, Integer> logMap = new LogMap(OFFSET_FILE, SINGLE_DATE).getMap();

        assertTrue(logMap.size() == 1);
        assertTrue(logMap.containsKey("5UAVanZf6UtGyKVS"));
        assertTrue(logMap.get("5UAVanZf6UtGyKVS") == 1);
    }

    public static void testLogMapOffsetMultiple()
    {
        HashMap<String, Integer> logMap = new LogMap(OFFSET_FILE, MULTIPLE_DATE).getMap();

        assertTrue(logMap.size() == 4);
        assertTrue(logMap.containsKey("SAZuXPGUrfbcn5UA"));
        assertTrue(logMap.containsKey("4sMM2LxV07bPJzwf"));
        assertTrue(logMap.containsKey("AtY0laUfhglK3lC7"));
        assertTrue(logMap.get("SAZuXPGUrfbcn5UA") == 1);
        assertTrue(logMap.get("4sMM2LxV07bPJzwf") == 1);
        assertTrue(logMap.get("AtY0laUfhglK3lC7") == 1);
    }
}
