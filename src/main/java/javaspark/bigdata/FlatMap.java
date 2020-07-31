package javaspark.bigdata;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlatMap {
    public static void main(String[] args) {
        List<String> inputStateData = new ArrayList<>();

        inputStateData.add("STATE: Condition-leading-to-increased-quality: Machine#1 Process#6 Task#73");
        inputStateData.add("STATE: Condition-leading-to-reduced-quality: Machine#1 Process#8 Task#41");
        inputStateData.add("STATE: State-keeping-current-parameters: Machine#2 Process#4 Task#10");
        inputStateData.add("STATE: Condition-leading-to-increased-quality: Machine#3 Process#1 Task#6");
        inputStateData.add("STATE: State-keeping-current-parameters: Machine#2 Process#4 Task#16");
        inputStateData.add("STATE: State-keeping-current-parameters: Machine#1 Process#17 Task#26");

        Logger.getLogger("org.apache").setLevel(Level.WARN);
        SparkConf conf = new SparkConf().setAppName("FlatMapApp").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.parallelize(inputStateData)
                .flatMap(value -> Arrays.asList(value.split(" ")).iterator())
                .filter(word -> word.length() > 1)
                .collect().forEach(System.out::println);
        sc.close();
    }
}
