# Practicas Unidad 3

# Contenido
<!--ts-->
   * [Practicas](#practicas-unidad-3)
      * [Practica 1 ](#practica-1)
      * [Practica 2 ](#practica-2)
      * [Practica 3 ](#practica-3)
      * [Practica 4 ](#practica-4)
      * [Practica 5 ](#practica-5)
   * [Contenido](#Contenido)
<!--te-->

--------------------------------------------------------------------------------------------------------------------------------
## Practica 1

##### Desarrollar un algoritmo en scala que calcule el radio de un circulo
##### Desarrollar un algoritmo en scala que me diga si un numero es primo
##### Dada la variable bird = "tweet", utiliza interpolacion de string para
##### imprimir "Estoy ecribiendo un tweet"
##### Dada la variable mensaje = "Hola Luke yo soy tu padre!" utiliza slilce para extraer la secuencia "Luke"
##### Cual es la diferencia en value y una variable en scala?
##### Dada la tupla ((2,4,5),(1,2,3),(3.1416,23))) regresa el numero 3.1416

```scala 
//1. Desarrollar un algoritmo en scala que calcule el radio de un circulo
val cir = 15
val pi = 2*3.1416
val rad = cir / pi

rad

//2. Desarrollar un algoritmo en scala que me diga si un numero es primo

def Esprimo(i :Int) : Boolean = {

if (i <= 1)
    false
else if (i==2)
    true
else
!(2 to (i-1)).exists(x=> i % x==0)

//3. Dada la variable bird = "tweet", utiliza interpolacion de string para
//   imprimir "Estoy ecribiendo un tweet"

val bird = "tweet"
val interpolar = "Estoy escribiendo un "+ bird

interpolar

//4. Dada la variable mensaje = "Hola Luke yo soy tu padre!" utiliza slilce para extraer la
//   secuencia "Luke"

val mensaje = List("Hola","Luke","yo","soy","tu","padre")

mensaje.slice(1,2)



//5. Cual es la diferencia en value y una variable en scala?

//value(val) se le asigna un valor definido y no
//puede ser cambiado, y variable(var) si se puede modificar en un metodo


//6. Dada la tupla ((2,4,5),(1,2,3),(3.1416,23))) regresa el numero 3.1416

var x = (2,4,5,1,2,3,3.1416,23)
println(x,7)
```

--------------------------------------------------------------------------------------------------------------------------------
## Practica 2 

```scala
// 1. Crea una lista llamada "lista" con los elementos "rojo", "blanco", "negro"

import scala.collection.mutable.ListBuffer
var lista = collection.mutable.ListBuffer("rojo","blanco","negro")

// 2. Añadir 5 elementos mas a "lista" "verde" ,"amarillo", "azul", "naranja", "perla" 
lista+=("verde","amarillo","azul","naranja","perla")

// 3. Traer los elementos de "lista" "verde", "amarillo", "azul". La función (slice) nos permite tomar los datos con el uso de sus coordenadas que se encuentran en la lista

lista slice (3,6)

// 4. Crea un arreglo de número en rango del 1-1000 en pasos de 5 en 5
// La función (range) nos permite darle el rango que queremos que tenga nuestro arreglo

var arrayrang = Array(1,1000,5)



// 5. Cuales son los elementos únicos de la lista Lista(1,3,3,4,6,7,3,7) utilice conversión a conjuntos // La función (toSet) nos muestra datos no repetidos o
//duplicados
var lista = List(1,3,3,4,6,7,3,7)
var unique = lista.toSet
println(unique)


//6. Crea una mapa mutable llamado nombres que contenga los siguiente "Jose", 20, "Luis", 24, "Ana", 23, "Susana", "27" 

var lista = collection.mutable.Map(("Jose",2),("Luis",24),("Ana",23),("Susana"
,27))

//  6 a . Imprime todas la llaves del mapa */

println(lista)

// 7 b . Agrega el siguiente valor al mapa("Miguel", 23)

lista += ("Miguel" -> 23)

```

--------------------------------------------------------------------------------------------------------------------------------
## Practica 3


```scala
//Fiboniacci Recursivo
/*val n = 10
def fibonacci1(n:Int) : Int ={
if (n<2){
return n
}
else{
    return fibonacci1(n-1) + fibonacci1(n-2)
}
}  
println(fibonacci1(n))
*/

/*
//Algoritmo 2 Versión con fórmula explícita (6) (Complejidad O  
val n = 10
var phi=((1+math.sqrt(5))/2)
var j=((math.pow(phi,n)-math.pow((1-phi),n))/(math.sqrt(5)))
def fibonacci2(n:Double) : Double ={
if (n<2){
return n
}
else {
    return j
}
}
println(fibonacci2(n))
*/


//Algoritmo 3 Versión iterativa
//(Complejidad O ( n)
/*
def fibonacci3(n:Int):Int={
var n : Int = 6
var a = 0
var b = 1
var c = 0
var k = 0 
    for(k <- 1 to n) {
        
        c = b + a
        a = b
        b = c 
    }
     return a
}
println(fibonacci3(n))
*/

//Algoritmo 4 Versión iterativa 2 variables (Complejidad (O(n))

/*
def fibonacci4(n:Int):Int={
var n : Int = 10
var a = 0
var b = 1
var k = 0 
    for(k <- 1 to n) {
        b = b + a
        a = b - a        
    
        }
     return a
}
println(fibonacci4(a))
*/



//Algoritmo 5 Versión iterativa vector (Complejidad O(n))
/*
var n = 10
def fibonacci4(n:Int):Int={
    var arreglo = Array (n+2)
    var i : Int
    arreglo (0,0)
    arreglo (1,1)
    for (i <- 1 to 2 )
}
println(fibonacci4(a))
*/

/*
  def fib(n: Int): Int = {
  	val n = 10
    val f: Array[Int] = Array.ofDim[Int](n + 2)
    
    f(0) = 0
    f(1) = 1
    for (i <- 2 to n) {
      
      f(i) = f(i - 1) + f(i - 2) //{ i += 1; i - 1 }
    }
    f(n)
  }
  println(fib(8))
*/

/*
//ALGORITMO 6
def fib6 (n : Int) : Double =
{
    if (n <= 0)
    {
        return 0
    }
    var i = n-1
    var auxOne = 0.0
    var auxTwo = 1.0
    var ab = Array(auxTwo,auxOne)
    var cd = Array(auxOne,auxTwo)
    while (i>0)
    {
        if (i % 2 != 0)
        {
            auxOne = cd(1) * ab(1) + cd(0) * ab(0)
            auxTwo = cd(1) * (ab(1)+ab(0)) + cd(0)*ab(1)
            ab(0) = auxOne
            ab(1) = auxTwo 
        } 
        auxOne = (math.pow(cd(0),2)) + (math.pow(cd(1),2))
        auxTwo = cd(1)* (2*cd(0) + cd(1))
        cd(0) = auxOne
        cd(1) = auxTwo
        i = i/2
    }
    return (ab(0) + ab(1))
}
*/

```
--------------------------------------------------------------------------------------------------------------------------------
## Practica 4

```scala

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
```
--------------------------------------------------------------------------------------------------------------------------------
## Practica 5

```scala 
import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header", "true").option("inferSchema","true")csv("Sales.csv")

//1.sumDistinct
df.select(sumDistinct("Sales")).show()

//2.last
df.select(last("Company")).show()

//3.first
df.select(first("Person")).show()

//4.var_pop
df.select(var_pop("Sales")).show()

//5.avg
df.select(avg("Sales")).show()

//6.collect_list
df.select(collect_list("Sales")).show()

//7.var_samp
df.select(var_samp("Sales")).show()

//8.sum
df.select(sum("Sales")).show()

//9.stddev_pop
df.select(stddev_pop("Sales")).show()

//10.skewness
df.select(skewness("Sales")).show()

//11.min
df.select(min("Sales")).show()

//12.kurtosis
df.select(kurtosis("Sales")).show()

//13.collect_set
df.select(collect_set("Sales")).show()

//14.approx_count_distinct
df.select(approx_count_distinct("Company")).show()

//15.mean
df.select(mean("Sales")).show()

```

