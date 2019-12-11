```scala
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer, VectorIndexer, OneHotEncoder,IndexToString}
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer, VectorIndexer, OneHotEncoder}
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer, VectorIndexer, OneHotEncoder}
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.SparkSession


// Minimiza los erorres mostrados
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

// Se inicia una sesion en spark
val spark = SparkSession.builder().getOrCreate()
// Se cargan los datos en la variable "data" en el formato "libsvm"
val data = spark.read.option("header","true").option("inferSchema", "true").option("delimiter",";").format("csv").load("bank-full.csv")
// Se imprime el schema del dataFrame
data.printSchema()

//Convertimos los valores de la columna y a valores numericos
val conv = data.withColumn("y",when(col("y").equalTo("yes"),1).otherwise(col("y")))
val conv2 = conv.withColumn("y",when(col("y").equalTo("no"),0).otherwise(col("y")))
val newDF = conv2.withColumn("y",'y.cast("Int"))


// Se Genera un nuevo vector que contiene las caracteristicas que seran evaluadas
val vectorFeatures = (new VectorAssembler().setInputCols(Array("balance","day","duration","pdays","previous")).setOutputCol("features"))
// Se transforman los features usando el dataframe
val features = vectorFeatures.transform(newDF)
//Se renombre la columna y por label
val featuresLabel = features.withColumnRenamed("y", "label")
//Se seleccionan las columnas features y label como datos indexados
val dataIndexed = featuresLabel.select("label","features")
// Se indexan las columnas de input
val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(dataIndexed)
val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(dataIndexed) // features with > 4 distinct values are treated as continuous.
// Se declararan 2 arreglos, uno tendra los datos de entrenamiento y el otro tendra
// los datos de pruebas.
val Array(trainingData, testData) = dataIndexed.randomSplit(Array(0.7, 0.3))
// Se declara el Clasificador de árbol de decisión y se le agrega la columna que sera las etiquetas (indices) y
// los valores que cada respectivo indice (caracteristicas)
val dt = new DecisionTreeClassifier().setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures")
//Se agrega la etiqueta de prediccion
val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(labelIndexer.labels)
val pipeline = new Pipeline().setStages(Array(labelIndexer, featureIndexer, dt, labelConverter))

// Se entrena el modelo con los datos de entrenamiento
val model = pipeline.fit(trainingData)
// Se hacen las predicciones con los datos de pruebas

val predictions = model.transform(testData)

// Se manda a imprimir las etiquetas y las predicciones
predictions.select("predictedLabel", "label", "features").show(5)

// Evalua la precision de las predicciones
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")
// Se calcula la precision en base a las predicciones
val accuracy = evaluator.evaluate(predictions)
// Se manda a imprimir el resultado de error
println(s"Test Error Decision Tree = ${(1.0 - accuracy)}")
// Se genera el modelo de arbol
val treeModel = model.stages(2).asInstanceOf[DecisionTreeClassificationModel]

println(s"Learned classification tree model:\n ${treeModel.toDebugString}")





// Se separan los datos de entrenamiento de los de pruebas
val Array(training, test) = dataIndexed.randomSplit(Array(0.7, 0.3), seed = 12345)
// Se inicia un modelo LogisticRegression
val logisticAlgorithm = new LogisticRegression().setMaxIter(10).setRegParam(0.3).setElasticNetParam(0.8).setFamily("multinomial")
// Fit del modelo
val logisticModel = logisticAlgorithm.fit(training)
// Se procesa el modelo con los datos de pruebas
val results = logisticModel.transform(test)
// Se importa una libreria para probar el modelo
import org.apache.spark.mllib.evaluation.MulticlassMetrics
// Se seleccionan las predicciones de los resultados dados por el modelo
val predictionAndLabels = results.select($"prediction",$"label").as[(Double, Double)].rdd
val metrics = new MulticlassMetrics(predictionAndLabels)

// Calcula el porcentaje de presicion del modelo
println("Test accuracy Logistic Regression")
metrics.accuracy



val splits = dataIndexed.randomSplit(Array(0.6, 0.4), seed = 1234L)
val train = splits(0)
val test = splits(1)

// Se establece la configuracion de las capas para el modelo 
val layers = Array[Int](5,4,2,2)

// Se configura el entrenador del algoritmo Multilayer
val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)

// Se entrena el modelo usando los datos de entrenamiento
val model = trainer.fit(train)

// Se  ejecuta el modelo con los datos de pruebas
val result = model.transform(test)

// Se selecciona la prediccion y la etiqueta
val predictionAndLabels = result.select("prediction", "label")

// Se ejecuta la estimacion de la precision del modelo
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")

// Se imprime el resultado de la presicion
println(s"Test set accuracy Multilayer Perceptron = ${evaluator.evaluate(predictionAndLabels)}")

```
