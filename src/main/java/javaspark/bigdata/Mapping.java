package javaspark.bigdata;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.List;

public class Mapping {
    public static void main(String[] args) {

        List<Integer> inputParamData = new ArrayList<>();
        inputParamData.add(2674858); inputParamData.add(6201152); inputParamData.add(2074658); inputParamData.add(3504862);

        Logger.getLogger("org.apache").setLevel(Level.WARN);

        SparkConf conf = new SparkConf().setAppName("MappingApp").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<Integer> myRdd = sc.parallelize(inputParamData);

        Integer result = myRdd.reduce((value1, value2 ) -> value1 + value2 );

        JavaRDD<Double> sqrtRdd = myRdd.map( value -> Math.sqrt(value) );

        sqrtRdd.collect().forEach( System.out::println );

        System.out.println("Sum of the values in the array: " + result);

        JavaRDD<Long> singleIntegerRdd = sqrtRdd.map( value -> 1L);
        Long count = singleIntegerRdd.reduce((value1, value2) -> value1 + value2);
        System.out.println("Elements in the array: "+count);

        sc.close();
    }
}
