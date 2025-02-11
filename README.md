# Gestor d'estacions de tren
Aquesta aplicació permet gestionar les estacions de tren, incloent la creació, lectura, actualització i eliminació (CRUD) de les dades relacionades amb les estacions. Els usuaris poden afegir noves estacions, veure la informació detallada de totes les clases existents, modificar les dades i eliminar les que ja no són necessàries.
## CRUD
Cada class té el seu CRUD a continuació explicaré cada prova realitzada i la ruta general de cadascuna:

```
==================
CONSULTA BD Renfe
==================
OPCIONS
1. CONSULTAR TOTES LES DADES
2. INSERIR NOU REGISTRE
3. MODIFICAR UN REGISTRE
4. ESBORRAR UN REGISTRE
5. MOSTRAR QUANTS TRAJECTES OFEREIX CADA COMPANYIA
9. SORTIR
Introdueix l'opcio tot seguit >> 
```
Es mostra un menu de totes les possibilitats que es poden realitzar.

### CREATE

```
Introdueix l'opcio tot seguit >> 
2
A quina taula vols afegir un registre?
1. ESTACIO
2. HORARI
3. TRAJECTE
4. COMPANYIA
5. SORTIR
3
1 Sants
2 Valencia
Tria un número d'estació d'origen per aquest trajecte
1
Tria un número d'estació de destí per aquest trajecte
2
Afegir Companyies a aquest trajecte
1 AVE
Tria un número de companyia per afegir al Trajecte
1
Vols afegir més Companyies al Trajecte?(s Si, n No)
n
Vols afegir horaris a aquest trajecte?(s Si, n No)
s
1 Hora de partida: 12:00  Hora d'arrivada: 13:00 Trajecte: 1 Estació d'origen: 1 Sants Estació de destí: 2 Valencia
Tria un número de horari per afegir al Trajecte
1
Vols afegir més Horaris al Trajecte?(s Si, n No)
n
Trajecte afegida a la base de dades: 3 Estació d'origen: 1 Sants Estació de destí: 2 Valencia
Vols afegir més trajectes?(s Si, n No)
n
```

El métode crear fent servir instruccions descriptivas guia a l'usuari per realizar la creació d'una clase, té bastantes restriccions fent que les classes que contenen foreign keys han de requerir cert ordre de creació en aquest cas el ideal és Companyia, Estacio(al menys 2), Trajecte, Horari, així garantint la correcta funcionalitat de l'app.  
### READ

```
Introdueix l'opcio tot seguit >> 
1
De quina taula vols mostrar les dades?
1. ESTACIO
2. HORARI
3. TRAJECTE
4. COMPANYIA
5. SORTIR
1
1 Sants
2 Valencia
De quina taula vols mostrar les dades?
1. ESTACIO
2. HORARI
3. TRAJECTE
4. COMPANYIA
5. SORTIR
2
1 Hora de partida: 12:00  Hora d'arrivada: 13:00 Trajecte: 1 Estació d'origen: 1 Sants Estació de destí: 2 Valencia
```
La funció read mostra la informació de cada taula i també amb la restricció de verificar que els registres existeixen abans de mostrar.

### UPDATE

```
Introdueix l'opcio tot seguit >> 
3
De quina taula vols canviar el registre?
1. ESTACIO
2. HORARI
3. TRAJECTE
4. COMPANYIA
5. SORTIR
3
1 Estació d'origen: 1 Sants Estació de destí: 2 Valencia
3 Estació d'origen: 1 Sants Estació de destí: 2 Valencia
Tria un número de trajecte per modificar
1
Que vols canviar d'aquest Trajecte? (eo EstOrigen, ed EstDesti, c Companyies, h Horaris)
ed
1 Sants
2 Valencia
Tria un número d'estació de destí per aquest trajecte
2
Trajecte actualitzada a la base de dades: 1 Estació d'origen: 1 Sants Estació de destí: 2 Valencia
1 Estació d'origen: 1 Sants Estació de destí: 2 Valencia
3 Estació d'origen: 1 Sants Estació de destí: 2 Valencia
```
Per actualitzar els registres permet a l'usuari triar l'atribut que desitja canviar i segregar el registre per Id i fer els canvis necessaris per després fer l'update.

### DELETE

```
Introdueix l'opcio tot seguit >> 
4
De quina taula vols eliminar un registre?
1. ESTACIO
2. HORARI
3. TRAJECTE
4. COMPANYIA
5. SORTIR
1
1 Sants
2 Valencia
4 Atocha
5 Bilbao
Tria un número d'estació per eliminar
5
Estacio esborrada la base de dades
```

Per realitzar el delete de la clase en específic ja sigui que contingui foreign keys o no, es possible eliminar-ho degut a la implementació utilitzada fent servir PreRemove.

### Consultar amb Group By

```
==================
CONSULTA BD Renfe
==================
OPCIONS
1. CONSULTAR TOTES LES DADES
2. INSERIR NOU REGISTRE
3. MODIFICAR UN REGISTRE
4. ESBORRAR UN REGISTRE
5. MOSTRAR QUANTS TRAJECTES OFEREIX CADA COMPANYIA
9. SORTIR
Introdueix l'opcio tot seguit >> 
5
Estació: AVE Numero de Trajectes: 2
```

En aquest cas he decidit fer servir un grou by per veure quants trajectes son de quina companyia, aixó serveix per veure que tots els trajectes estan assignats per debugging i fer modificacions necessaries ja que una companyia potser creada sense trajectes i després assignar un trajecte creat a una companyia o companyies