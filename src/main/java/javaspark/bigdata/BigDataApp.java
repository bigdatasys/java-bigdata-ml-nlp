package javaspark.bigdata;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class BigDataApp {

    private static final String CSV_FILE_PATH
            = "./generated-test-file.csv";
    public static void main(String[] args) {
        System.out.println("BigData App...");

        Logger.getLogger("org.apache").setLevel(Level.WARN);
        SparkConf conf = new SparkConf().setAppName("SparkProcessing").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> data = sc.textFile(CSV_FILE_PATH);
        data.collect().forEach(System.out::println);

        List<Double> doubleList = new ArrayList<>();
        doubleList.add(41.32);
        doubleList.add(72.84);
        doubleList.add(16.97);
        doubleList.add(153.826);
        doubleList.add(623.283);
        JavaRDD<Double> javaRDD = sc.parallelize(doubleList);
        //map
        JavaRDD<Integer> mappedRDD = javaRDD.map(val -> (int)Math.round(val));
        mappedRDD.collect().forEach(x->System.out.println(x));
        //reduce
        int reducedResult = mappedRDD.reduce(Integer::sum);
        System.out.println(reducedResult);

        sc.close();
    }
}
