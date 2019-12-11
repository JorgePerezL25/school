# Introduction
Machine learning is an application of artificial intelligence (AI) that provides systems the ability to automatically learn and improve from experience without being explicitly programmed. Machine learning focuses on the development of computer programs that can access data and use it learn for themselves.

The process of learning begins with observations or data, such as examples, direct experience, or instruction, in order to look for patterns in data and make better decisions in the future based on the examples that we provide. The primary aim is to allow the computers learn automatically without human intervention or assistance and adjust actions accordingly.

# Algorithms 

### Decision Tree 
###### A decision tree is a map of the possible outcomes of a series of related choices. It allows an individual or organization to weigh possible actions against one another based on their costs, probabilities, and benefits. They can can be used either to drive informal discussion or to map out an algorithm that predicts the best choice mathematically. A decision tree typically starts with a single node, which branches into possible outcomes. Each of those outcomes leads to additional nodes, which branch off into other possibilities. This gives it a treelike shape. There are three different types of nodes: chance nodes, decision nodes, and end nodes. A chance node, represented by a circle, shows the probabilities of certain results. A decision node, represented by a square, shows a decision to be made, and an end node shows the final outcome of a decision path.

![Capture](https://user-images.githubusercontent.com/54339562/70585324-bf6f7680-1b78-11ea-910e-3aabefb35562.PNG)


### Logistic Regression

###### Is the appropriate regression analysis to conduct when the dependent variable is dichotomous (binary).  Like all regression analyses, the logistic regression is a predictive analysis.  Logistic regression is used to describe data and to explain the relationship between one dependent binary variable and one or more nominal, ordinal, interval or ratio-level independent variables.Logistic Regression is used to solve the classification problems, so it’s called as Classification Algorithm that models the probability of output class.

  - ###### It is a classification problem where your target element is categorical
  
  - ###### Unlike in Linear Regression, in Logistic regression the output required is represented in discrete values like binary 0 and 1
  - ###### It estimates relationship between a dependent variable (target) and one or more independent variable (predictors) where dependent variable is categorical/nominal.

![123123](https://user-images.githubusercontent.com/54339562/70585462-30169300-1b79-11ea-819b-4c857064009d.PNG)


### MultiLayer Perceptron

###### A multilayer perceptron (MLP) is a feedforward artificial neural network that generates a set of outputs from a set of inputs. An MLP is characterized by several layers of input nodes connected as a directed graph between the input and output layers. MLP uses backpropogation for training the network. MLP is a deep learning method.A multilayer perceptron is a neural network connecting multiple layers in a directed graph, which means that the signal path through the nodes only goes one way. Each node, apart from the input nodes, has a nonlinear activation function. An MLP uses backpropagation as a supervised learning technique. Since there are multiple layers of neurons, MLP is a deep learning technique. MLP is widely used for solving problems that require supervised learning as well as research into computational neuroscience and parallel distributed processing. Applications include speech recognition, image recognition and machine translation.

# Code to Evalutate Dataset

#### Librarys needed to Evaluate dataset
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
import org.apache.spark.mllib.evaluation.MulticlassMetrics
```

#### Loading the datasets and transforming the columns data

###### Minimiza los erorres mostrados
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

###### Se inicia una sesion en spark
```scala 
val spark = SparkSession.builder().getOrCreate()
```
###### Se cargan los datos y Se imprime el schema del dataFrame
```scala
val data = spark.read.option("header","true").option("inferSchema", "true").option("delimiter",";").format("csv").load("bank-full.csv")
data.printSchema()
```

##### Convertimos los valores de la columna y a valores numericos
```scala val conv = data.withColumn("y",when(col("y").equalTo("yes"),1).otherwise(col("y")))
val conv2 = conv.withColumn("y",when(col("y").equalTo("no"),0).otherwise(col("y")))
val newDF = conv2.withColumn("y",'y.cast("Int"))
```

##### Se Genera un nuevo vector que contiene las caracteristicas que seran evaluadas
```scala val vectorFeatures = (new VectorAssembler().setInputCols(Array("balance","day","duration","pdays","previous")).setOutputCol("features"))
```
##### Se transforman las caracteristicas usando el dataframe
```scala
val features = vectorFeatures.transform(newDF)
val featuresLabel = features.withColumnRenamed("y", "label")
````
##### Se seleccionan las columnas features y label como datos indexados
```scala
val dataIndexed = featuresLabel.select("label","features")
```
##### Se indexan las columnas de input
```scala 
val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(dataIndexed)
val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(dataIndexed) // features with > 4 distinct values are treated as continuous.
```
##### Se declararan 2 arreglos, uno tendra los datos de entrenamiento y el otro tendra los datos de pruebas.
```scala
val Array(trainingData, testData) = dataIndexed.randomSplit(Array(0.7, 0.3))
```



### Decesion Tree Code

##### Se declara el Clasificador de árbol de decisión y se le agrega la columna que sera las etiquetas (indices) y los valores que cada respectivo indice (caracteristicas)
```scala 
val dt = new DecisionTreeClassifier().setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures")
```
##### Se agrega la etiqueta de prediccion
```scala
val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(labelIndexer.labels)
val pipeline = new Pipeline().setStages(Array(labelIndexer, featureIndexer, dt, labelConverter))
```
##### Se entrena el modelo con los datos de entrenamiento y Se hacen las predicciones con los datos de pruebas
```scala
val model = pipeline.fit(trainingData)
val predictions = model.transform(testData)
```
##### Evalua la precision de las predicciones, Se calcula la precision en base a las predicciones
```scala
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")
val accuracy = evaluator.evaluate(predictions)
```
##### Se imprime el resultado de error y Se genera el modelo de arbol
```scala
println(s"Test Error Decision Tree = ${(1.0 - accuracy)}")
val treeModel = model.stages(2).asInstanceOf[DecisionTreeClassificationModel]
println(s"Learned classification tree model:\n ${treeModel.toDebugString}")
```

### Logistic Regression Code


##### Se separan los datos de entrenamiento de los de pruebas y Se inicia un modelo LogisticRegression
```scala
val Array(training, test) = dataIndexed.randomSplit(Array(0.7, 0.3), seed = 12345)
val logisticAlgorithm = new LogisticRegression().setMaxIter(10).setRegParam(0.3).setElasticNetParam(0.8).setFamily("multinomial")
```
##### Fit del modelo y Se procesa el modelo con los datos de pruebas
```scala
val logisticModel = logisticAlgorithm.fit(training)
val results = logisticModel.transform(test)
```
##### Se importa una libreria para probar el modelo y Se seleccionan las predicciones de los resultados dados por el modelo
```scala
val predictionAndLabels = results.select($"prediction",$"label").as[(Double, Double)].rdd
val metrics = new MulticlassMetrics(predictionAndLabels)
```
##### Resultados del modelo
println("Test accuracy Logistic Regression")
metrics.accuracy


##### Se separan los datos de entrenamiento de los de pruebas y Se inicia un modelo LogisticRegression
```scala
val splits = dataIndexed.randomSplit(Array(0.6, 0.4), seed = 1234L)
val train = splits(0)
val test = splits(1)
```
##### Se establece la configuracion de las capas para el modelo y Se configura el entrenador del algoritmo Multilayer
```scala
val layers = Array[Int](5,4,2,2)
val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)
```
##### Se entrena el modelo usando los datos de entrenamiento y se  ejecuta el modelo con los datos de pruebas
```scala 
val model = trainer.fit(train)
val result = model.transform(test)
```
##### Se selecciona la prediccion y la etiqueta para ejecutar la estimacion de la precision del modelo
```scala
val predictionAndLabels = result.select("prediction", "label")
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
```

##### Resultados del modelo
```scala
println(s"Test set accuracy Multilayer Perceptron = ${evaluator.evaluate(predictionAndLabels)}")

```
