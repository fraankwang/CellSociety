## Lab discussion
  * Jeremy Schreck jes85
  * Joe Lilien jrl48

### Code Smell Refactoring
  * In PredatorPreyGrid, we subclassed the getNeighbors() method to accommodate the toroidal structure of PredatorPrey. However, this resulted in duplicated code, since much of the getNeighbors() method was still the same. To fix this, I refactored the code. Because we now are supposed to be able to make any simulation finite, toroidal, or infinite, this code can go in the superclass, Grid. In getNeighbors, it calls the method getNeighborToroidal, getNeighborFinite, or getNeighborInfinite. I haven't implemented infinite yet, but toroidal and finite implementation can be combined using mods. Later, I will add functionality so that the Grid determines which method to call depending on a value in a resource file.

 ### Checklist refactoring
  * Our code was mostly good, except for a few printStackTraces and a few methods that were too long. Many of these methods are difficult to make shorter though. I will look more into this later

  