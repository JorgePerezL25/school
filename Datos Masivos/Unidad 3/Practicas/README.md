# Practicas Unidad 3

# Contenido
<!--ts-->
   * [Practicas](#practicas-unidad-3)
      * [Practica 1 K-Means](#practica-1-k-means)
      * [Practica 2 Regresion Logistica](#practica-2-regresion-logistica)
   * [Contenido](#Contenido)
<!--te-->

--------------------------------------------------------------------------------------------------------------------------------
## Practica 1 K-means 

#### Start a Spark Session
```scala 
import org.apache.spark.sql.SparkSession
```

#### Optional: Use the following code below to set the Error reporting
```scala
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)
```
#### Spark Session
```scala
val spark = SparkSession.builder().getOrCreate()
```
#### Import clustering Algorithm
```scala 
import org.apache.spark.ml.clustering.KMeans
```
#### Loads data.
```scala
val dataset = spark.read.format("libsvm").load("sample_kmeans_data.txt")
val dataset = spark.read.option("header","true").option("inferSchema","true").csv("sample_kmeans_data.txt")
```
#### Trains a k-means model.
```scala
val kmeans = new KMeans().setK(2).setSeed(1L)
val model = kmeans.fit(dataset)
```
#### Evaluate clustering by computing Within Set Sum of Squared Errors.
```scala
val WSSSE = model.computeCost(dataset)
println(s"Within Set Sum of Squared Errors = $WSSSE")
```
### Shows the result.
```scala
println("Cluster Centers: ")
model.clusterCenters.foreach(println)

```

--------------------------------------------------------------------------------------------------------------------------------
## Practica 2 Regresion Logistica


#### In this project we will be working with a fake advertising data set, indicating whether or not a particular internet user clicked on an Advertisement. We will try to create a model that will predict whether or not they will click on an ad based off the features of that user. This data set contains the following features:
``` sh 
'Daily Time Spent on Site': consumer time on site in minutes
'Age': cutomer age in years
'Area Income': Avg. Income of geographical area of consumer
'Daily Internet Usage': Avg. minutes a day consumer is on the internet
'Ad Topic Line': Headline of the advertisement
'City': City of consumer
'Male': Whether or not consumer was male
'Country': Country of consumer
'Timestamp': Time at which consumer clicked on Ad or closed window
'Clicked on Ad': 0 or 1 indicated clicking on Ad
```


+ #### Complete las siguientes tareas que estan comentas

  - ##### Tome los datos Importe una  SparkSession con la libreria Logistic Regression *Optional: Utilizar el codigo de  Error reporting

  - ##### Cree un sesion Spark 

  - ##### Utilice Spark para leer el archivo csv Advertising.

  - ##### Imprima el Schema del DataFrame

  - ##### importar librerias de spark y metodos de clasificacionn de logistic regression
```scala 
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession
```
##### Declaramos funcion para reportar errores
```scala
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)
```
##### Iniciamos sesion de spark
```scala 
val spark = SparkSession.builder().getOrCreate()
```

##### Utilizamos dataframes para leer el archivo

```scala
val data  = spark.read.option("header","true").option("inferSchema", "true").format("csv").load("advertising.csv")
```

##### imprimimos el esquema del dataframe 
```scala 
data.printSchema()
```
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
+ ### Despliegue los datos
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

###### Imprima un renglon de ejemplo 

##### Imprime el head del dataframe
```scala
data.head(1)
```
##### La variable colnames contendra  un arreglo de string la informacion de la primera columna.
```scala
val colnames = data.columns
```
##### variable firstrow contendra la primera columna de datos
```scala 
val firstrow = data.head(1)(0)
println("\n")
println("Example data row")
for(ind <- Range(1, colnames.length)){
    println(colnames(ind))
    println(firstrow(ind))
    println("\n")
}
```


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
+ ### Preparar el DataFrame para Machine Learning
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

##### Hacer lo siguiente:
  -###### Renombre la columna "Clicked on Ad" a "label"
  -###### Tome la siguientes columnas como features "Daily Time Spent on Site","Age","Area Income","Daily Internet Usage","Timestamp","Male"


##### Cree una nueva clolumna llamada "Hour" del Timestamp conteniendo la  "Hour of the click"
```scala
val timedata = data.withColumn("Hour",hour(data("Timestamp")))
```
##### Renombre la columna "Clicked on Ad" a "label"
```scala
val logregdata = timedata.select(data("Clicked on Ad").as("label"), $"Daily Time Spent on Site", $"Age", $"Area Income", $"Daily Internet Usage", $"Hour", $"Male")
```
##### Importe VectorAssembler y Vectors
##### Cree un nuevo objecto VectorAssembler llamado assembler para los feature
```scala
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors
```

##### Creamos un objeto vector aseembler para que features tenga las caracteristicas que le indiquemos de las columnas indicadas
```scala
val assembler = (new VectorAssembler()
                  .setInputCols(Array("Daily Time Spent on Site", "Age","Area Income","Daily Internet Usage","Hour","Male"))
                  .setOutputCol("features"))
```

##### Utilice randomSplit para crear datos de train y test divididos en 70/30 en 5 semillas
```scala 
val Array(training, test) = logregdata.randomSplit(Array(0.7, 0.3), seed = 12345)
```

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
+ ### Configure un Pipeline 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

##### Importe  Pipeline Tome los Resultados en el conjuto Test con transform
```scala 
import org.apache.spark.ml.Pipeline
```

##### Cree un nuevo objeto de  LogisticRegression llamado lr
```scala
val lr = new LogisticRegression()
```

##### Cree un nuevo  pipeline con los elementos: assembler, lr
```scala
val pipeline = new Pipeline().setStages(Array(assembler, lr))
```

##### Ajuste (fit) el pipeline para el conjunto de training.
```scala
val model = pipeline.fit(training)
val results = model.transform(test)
```

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
+ ### Evaluacion del modelo 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

##### Para Metrics y Evaluation importe MulticlassMetrics. Inicialice un objeto MulticlassMetrics Imprima la  Confusion matrix importar libreria de multiclassmetrics

```scala
import org.apache.spark.mllib.evaluation.MulticlassMetrics
```

##### Se connviertes los resutalos de prueba (test) en RDD utilizando .as y .rdd
```scala
val predictionAndLabels = results.select($"prediction",$"label").as[(Double, Double)].rdd
```
##### Se declara el objeto metrics para utilizar el parametro predictionandlabels de multiclassmetrics
```scala
val metrics = new MulticlassMetrics(predictionAndLabels)
```
##### Se imprimen matriz
```scala
println("Confusion matrix:")
println(metrics.confusionMatrix)
```
##### Imprir la prediccion 
```scala
metrics.accuracy
```
