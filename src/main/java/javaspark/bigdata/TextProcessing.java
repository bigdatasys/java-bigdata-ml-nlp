package javaspark.bigdata;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class TextProcessing {
    public static void main(String[] args) {

        System.setProperty("hadoop.home.dir", "c:/hadoop");
        Logger.getLogger("org.apache").setLevel(Level.WARN);

        SparkConf conf = new SparkConf().setAppName("TextProcessingApp").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> inputRDD = sc.textFile("src/main/resources/text-files-set1/text-to-process.txt");

        JavaRDD<String> toLettersRDD = inputRDD.map( sentence -> sentence.replaceAll("[^a-zA-Z\\s]", "").toLowerCase() );

        JavaRDD<String> withoutEmptyLinesRDD = toLettersRDD.filter( sentence -> sentence.trim().length() > 0 );

        JavaRDD<String> toWordsRDD = withoutEmptyLinesRDD.flatMap(sentence -> Arrays.asList(sentence.split(" ")).iterator());

        JavaRDD<String> withoutBlankWordsRDD = toWordsRDD.filter(element -> element.trim().length() > 0);

        JavaRDD<String> toWordsToKeepRDD = withoutBlankWordsRDD.filter(element -> Tool.keepElement(element));

        JavaPairRDD<String, Long> mapToPairRDD = toWordsToKeepRDD.mapToPair(element -> new Tuple2<String, Long>(element, 1L));

        JavaPairRDD<String, Long> totalsRDD = mapToPairRDD.reduceByKey((value1, value2) -> value1 + value2);

        JavaPairRDD<Long, String> switchedElementsRDD = totalsRDD.mapToPair(tuple -> new Tuple2<Long, String> (tuple._2, tuple._1 ));

        JavaPairRDD<Long, String> sortedElementsRDD = switchedElementsRDD.sortByKey(false);

        List<Tuple2<Long,String>> processingResults = sortedElementsRDD.take(20);

        processingResults.forEach(System.out::println);

        sc.close();
    }
}
