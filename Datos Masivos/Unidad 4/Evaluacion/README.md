# Readme of Final proyect
import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer, VectorIndexer, OneHotEncoder}
import org.apache.spark.ml.feature.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.classification.LogisticRegression

import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.clustering.KMeans

//Cross Validation k=10 Folding
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.ml.tuning.{ParamGridBuilder, TrainValidationSplit}

//Desicion Tree
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.regression.DecisionTreeRegressionModel
import org.apache.spark.ml.regression.DecisionTreeRegressor

import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

//Carga de datos y limpieza
//Los datos seran convertidos de tipo int o double
val spark = SparkSession.builder().getOrCreate()
val data = spark.read.option("header","true").option("inferSchema","true").option("delimiter",";").format("csv").load("bank-full.csv")

val c1 = data.withColumn("y",when(col("y").equalTo("yes"),1).otherwise(col("y")))
val c2 = c1.withColumn("y",when(col("y").equalTo("no"),2).otherwise(col("y")))
val c3 = c2.withColumn("y",'y.cast("Int"))
val featureCols = Array("age","balance")
val assembler = new VectorAssembler().setInputCols(featureCols).setOutputCol("features")
val df = assembler.transform(c3)
df.show(5)

//Cross Validation k=10 Folding

val lr = new LinearRegression().setMaxIter(10)
val paramGrid = new ParamGridBuilder().addGrid(lr.regParam, Array(0.1, 0.01)).addGrid(lr.fitIntercept).addGrid(lr.elasticNetParam,
  Array(0.0, 0.5, 1.0)).build()
val trainValidationSplit = new TrainValidationSplit().setEstimator(lr).setEvaluator(new RegressionEvaluator).setEstimatorParamMaps(
  paramGrid).setTrainRatio(0.8)
val assembler = (new VectorAssembler().setInputCols(Array("balance","day","duration","pdays","previous")).setOutputCol("features"))
val assembler1 = (new VectorAssembler().setInputCols(Array("y")).setOutputCol("label"))
val df = assembler.transform(c3)
df.show(5)
val df1 = assembler1.transform(df)
df1.show(5)
val  ct= df1.withColumn("label",'y.cast("Double"))
val Array(trainingData, testData) = ct.randomSplit(Array(0.7,0.3))
val modelcv = trainValidationSplit.fit(trainingData)
modelcv.transform(testData).select("features", "label").show()


val kmeans = new KMeans().setK(7).setSeed(1L).setPredictionCol("prediction")
val model = kmeans.fit(df)
val WSSE = model.computeCost(df)
val categories = model.transform(testData)
val displaydoresult = categories.select($"age",$"balance",$"prediction").groupBy("age","balance","prediction").agg(count("prediction")).orderBy("age","balance","prediction")
displaydoresult.show(5)
categories.show(5)

//Multilayer Perceptron



val kmeans = new KMeans().setK(7).setSeed(1L).setPredictionCol("prediction")
val model = kmeans.fit(df)
val WSSE = model.computeCost(df)
println("Cluster Centers: ")
model.clusterCenters.foreach(println)
val categories = model.transform(testData)
val displaydoresult = categories.select($"age",$"balance",$"prediction").groupBy("age","balance","prediction").agg(count("prediction")).orderBy("age","balance","prediction")
displaydoresult.show(5)
categories.show(5)

val splits = categories.randomSplit(Array(0.6, 0.4), seed = 1234L)
val train = splits(0)
val test = splits(1)
val layers = Array[Int](2, 1, 3, 3)
val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)
val ct4= categories.withColumn("prediction",'prediction.cast("Double"))
val sl= ct4.withColumn("label",'prediction.cast("Double"))
val predictionAndLabels = sl .select("prediction", "label")
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
println(s"Test set accuracy = ${evaluator.evaluate(predictionAndLabels)}")



//Desicion Tree

val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(ct)
val Array(trainingData, testData) = ct.randomSplit(Array(0.7, 0.3))
val dt = new DecisionTreeRegressor().setLabelCol("label").setFeaturesCol("indexedFeatures")
val pipeline = new Pipeline().setStages(Array(featureIndexer, dt))
//val model43 = pipeline.fit(trainingData)
val predictions = model.transform(testData)
predictions.select("prediction", "label", "features").show(5)










```scala
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer, VectorIndexer, OneHotEncoder}
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.SparkSession
```

    
