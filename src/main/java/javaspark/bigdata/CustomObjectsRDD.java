package javaspark.bigdata;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.Serializable;
import java.util.List;
import org.apache.spark.api.java.JavaRDD;
import com.google.common.collect.ImmutableList;

public class CustomObjectsRDD {
    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("SparkProcessing").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Operator> operatorList = ImmutableList.of(
                new Operator("TestOperator1", 173),
                new Operator("TestOperator2", 154));

        // parallelize the list using SparkContext
        JavaRDD<Operator> perJavaRDD = sc.parallelize(operatorList);

        for(Operator operator : perJavaRDD.collect()){
            System.out.println(operator.name);
        }

        sc.close();
    }
}

class Operator implements Serializable{
    String name;
    int tasks;
    public Operator(String name, int tasks){
        this.name = name;
        this.tasks = tasks;
    }
}