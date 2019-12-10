# Kmeans 
//importar una simple sesion spark
```scala
import org.apache.spark.sql.SparkSession
```

//utilice las lineas de codigo para minizar errores

```scala
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)
```
//cree una instancia de la session spark

```scala
val spark = SparkSession.builder().getOrCreate()
```
//importar la libreria de kmeans para el algoritmo de agrupamiento

```scala
import org.apache.spark.ml.clustering.KMeans
```
//carga el dataset de wholesale customers data
```scala
val df = spark.read.option("header","true").option("inferSchema","true").format("csv").load("Wholesalecustomersdata.csv")
```
//seleccione las siguientes columnas: Fresh,Milk,Grocery, Froczn,Detergents_Paper,Delicassen y llamar a este conjunto feature_data

```scala
val feature_data = df.select($"Fresh", $"Milk", $"Grocery", $"Frozen", $"Detergents_Paper", $"Delicassen")
```
/Importar vector assembler y vector

```scala
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors
```
//Crea un objeto vector assemble para las columnas de caracteristicas como un conjunto de entrada, recordando que no hay etiquetas
```scala
val assembler = new VectorAssembler().setInputCols(Array("Fresh", "Milk", "Grocery", "Frozen", "Detergents_Paper", "Delicassen")).setOutputCol("features")
```

//utilice el objeto assembler para transformar feature_data
```scala
val training_data = assembler.transform(feature_data).select("features")
```
//crear un modelo kmeans con k=3

```scala
val kmeans = new KMeans().setK(3).setSeed(1L)
val model = kmeans.fit(training_data)
```

//Evalue los grupos utilizando????


//cuales son los nombres de las columnas?
```scala
feature_data.printSchema()
```


```sh
Results
Cluster Centers: 
[7993.574780058651,4196.803519061584,5837.4926686217,2546.624633431085,2016.2873900293255,1151.4193548387098]
[9928.18918918919,21513.081081081084,30993.486486486487,2960.4324324324325,13996.594594594595,3772.3243243243246]
[35273.854838709674,5213.919354838709,5826.096774193548,6027.6612903225805,1006.9193548387096,2237.6290322580644]
warning: there was one deprecation warning; re-run with -deprecation for details
WSSE: Double = 8.095172370767671E10
Within set sum of Squared Errors = 8.095172370767671E10
root
 |-- Fresh: integer (nullable = true)
 |-- Milk: integer (nullable = true)
 |-- Grocery: integer (nullable = true)
 |-- Frozen: integer (nullable = true)
 |-- Detergents_Paper: integer (nullable = true)
 |-- Delicassen: integer (nullable = true)
 
```
