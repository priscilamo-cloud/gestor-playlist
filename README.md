# Gestor de Lista de Reproducción

Lista doblemente enlazada circular implementada en Java para administrar canciones.

## Integrantes
- Martin Ortega Priscila Patricia 
- Mena Brito Ana Noemi
- Arroyo Miguel Luis Jesús
- Cruces Rosales Raúl Leonardo

## Versión de Java
Java 21 (compatible con Java 17 o superior).

## Estructura del proyecto
- `Cancion.java`: datos de la canción.
- `Nodo.java`: nodo con enlaces anterior y siguiente.
- `ListaReproduccion.java`: lista doblemente enlazada circular.
- `Menu.java`: menú de consola.
- `Main.java`: punto de entrada (si creaste esta clase).
- `PruebasLista.java`: pruebas reproducibles.

## Compilación
```
mkdir out
javac -encoding UTF-8 -d out src/*.java
```

## Ejecución
Programa:
```
java -Dstdout.encoding=UTF-8 -cp out Main
```
Pruebas:
```
java -Dstdout.encoding=UTF-8 -cp out PruebasLista
```