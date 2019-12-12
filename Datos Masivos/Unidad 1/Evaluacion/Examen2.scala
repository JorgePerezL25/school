//1.-Comienza una simple session de Spark


//2.-Cargue el archivo NEtflix Stock CSV, haga que Spark infiera los tipos de datos.
import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header", "true").option("inferSchema","true")csv("Netflix_2011_2016.csv")

//3.-Cuales son los nombres de las columnas?
df.columns

//4.-Como es el esquema?
df.printSchema()

//5.-Imprime las primeras 5 filas
df.show(5)

//6.-Usa descrbie () para aprender sobre el dataframe
df.describe().show()

//7.-Crear un nuevo dataframe con una columna nueva llamada "HV Ratio" que es la relacion entre el precio de la columma 
//"High" frente a la columna "volumen" de acciones negociadas por un dia
val df2 = df.withColumn("hv Ratio",df("High")/df("Volume"))
df2.select("hv Ratio").show()

//8.-que dia tuvo el pico mas alto en la columna "Price"?
//NO HAY NO EXISTE


//9.-cual es el significado de la columna cerrar "close"?
//el como cerro el precio del dia 

//10.-Cual es el maximo y minimo de la columna "volumen"
df.select(max(df("Volume"))).show()
df.select(min(df("Volume"))).show()

//11 con sintaxis scala/spark $ constete los siguiente:
//a. cuantos dias fue la columna "close" unferior a $600
//df.orderBy($"Close"< 600).show()
//df.select($"Close"< 600).show()
//df.filter($"Close"< 600).show()
df.filter($"Close"< 600).count()

//b.-que porcentaje del tiempo fue la columna "high" mayor $500
val num = df.filter($"High">500).count()
val por = (num * 1.0 / df.count() * 100)
//por.show()

//c.-cual es la correlacion de pearson entre columna "High" y la columna "volumen"?
val corre = df.select(corr($"Volume",$"High"))
corre.show()

//d.cual es el maximo de la columna "high" por anio
val yearm = df.withColumn("Year",year(df("Date")))
val yearmax = yearm.select($"Year", $"High").groupBy("Year").max()
val res = yearmax.select($"Year", $"max(High)")
res.show()

//e.cual es el prmedio de la columa "close" para cada mes del calendario
val monthp = df.withColumn("Month",month(df("Date")))
val monthprom = monthp.select($"Month",$"Close").groupBy("Month").mean()
monthprom.select($"Month",$"avg(Close)").show()



