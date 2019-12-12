import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header", "true").option("inferSchema","true")csv("Netflix_2011_2016.csv")

df.printSchema()

df.show()

//1
df.describe ("High").show //Describe los valores estadisticos de la columna seleccionada
//2 
df.select ("High","Close").show // Despliega los valores relacionados de las columnas consultadas.
//3 
df.select ("Open","Low").filter("Close < 480").show // Despliega la colummnas relacionadas y seleccionadas y pone un filtro para solo desplegar las que sean menor a 480
//4 
df.groupBy ("Open").show
//5
df.first //   retorna la primera columna del dataframe
//6 
df.columns // Retorna las columnas de dataframe
//7 
val df2 = df.withColumn("HV Ratio", df("High")+df("Volume")) // Agrega una columna que deriva de la columna high y Volume
//8 
df.select(min("Volume")).show() // Optiene el min de la columna volume 
//9 
df.select(max("Volume")).show() // Optiene el max de la columna volume
//10
val df2 = df.withColumn("Year", year(df("Date"))) // Crea la columa año apartir de la columna date
// 11 
val df3 = df.withColumn("Month", month(df("Date"))) // Crea la columna mes apartir de la columna date
// 12 
val df3 = df.withColumn("Day", dayofmonth(df("Date"))) // crea la columna dia apartir de la columna mes y date
// 13
al df3 = df.withColumn("Day", dayofyear(df("Date"))) // Crea la columna dia apartir de la columna año
// 14 
df.select(corr($"High", $"Volume")).show() // retorna la correlacion entre la columna High y Volume
// 15 
df.select($"High").take(1) // Toma 1 columna de de la columna
// 16 
df.select("High").repartition().show() //Reparticia la columna seleccionada
// 17 
df.sort($"High".asc).show() // Sortea la columa High
// 18 
df.select(avg("High")).show() // Muestra el promedio de la columna high 
// 19 
df.filter($"Close" < 480 && $"High" < 480).collectAsList() //crea una lista apartir de una coleccion. 

//20 

df.select(last_day(df("Date"))).show() // retorna el ultimo dia de la columna date 
