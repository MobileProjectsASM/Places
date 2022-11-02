# Places

Places es una aplicación móvil nativa, desarrollada para el sistema operativo **Android** en el lenguaje de programación **Java**.

Esta aplicación está desarrollada siguiendo las pautas de **Clean Architecture** dividiendo la App en 3 módulos diferentes (app, domain, data), representando las 3 capas (Presentación, dominio, datos) de **Clean Architecture**.

## Estructura

### Capa de datos

La capa de datos esta estructurada bajo el **patrón Repository**, con el fin de recuperar datos de distintas fuentes de una forma limpia, tanto locales (Database, SharedPreferences), remotas(Servicio Rest, Graphql) y de componentes de hardware del dispositivo(GPS).


### Capa de dominio

En esta capa se encuentran el modelo de datos que la aplicación sigue y los casos de uso, esta capa sirve principalmente para separar la lógica de la aplicación que se ejecutara en segundo plano y la que se ejecutara en el hilo principal.

### Capa de presentación

Esta capa está modelada siguiente el patrón **MVVM**, en esta se realiza toda la lógica de vista, ocupando los distintos componentes que nos proporciona el framework de android, como Activities, Fragments, Navigation Component, etc. La parte gráfica está diseñada siguiendo las pautas de **Material design**, usando diversos componentes como cajas de texto, listas, menús contextuales, chip groups, mapas, imágenes, etc.


## Librerías y herramientas utilizadas para el desarrollo

* Room
* Shared Preferences
* Gson
* Retrofit
* Apollo
* Google Service Location
* RxJava
* Dagger
* LiveData
* ViewModel
* Navigation Component
* Google maps
* Material design
* View binding
* Permisos del dispositivo
* Picasso
* Git
* Expresiones regulares para validación de campos
* Yelp Api

## Ejemplo

![places image 1](preview/places_1.gif)