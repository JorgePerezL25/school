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
![test](https://user-images.githubusercontent.com/54339562/70497561-6d6f1800-1ac8-11ea-8296-1fce432da1ea.png)

