package javaspark.bigdata;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Tool {
    private static Set<String> elements = new HashSet<String>();

    static {
        InputStream is = Tool.class.getResourceAsStream("/text-files-set1/elements-to-exclude.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        br.lines().forEach(elements::add);
    }

    public static boolean excludeElement(String element)
    {
        return elements.contains(element);
    }

    public static boolean keepElement(String element)
    {
        return !excludeElement(element);
    }
}
