In Minecraft, features is defined as "any type of build". \
For example, trees, structures, ores are "features" (these will be frequently used as examples for easier understanding and reference).

<h2>Configured Feature</h2>
* Configured Features (CFs) is defined as "how a feature should look like and how it is built".
* In other words, it defines the feature itself.
* For a CF, you give it values - how big is the feature? what blocks should be inside? where should the block be inside? etc...
* The said "values" can be given with Java code, or with structure files (NBT files) 
  * A reference can be seen in the RuleMaster mod, where the "Cradle of Life" structure uses a NBT file for the Java class to read.

<h2>Placed Feature</h2>
* Placed Features (PFs) is defined as "how many should a feature be and some of where it should be"
* It is given by the CF, and defines "where and how should the feature be placed".
* For a PF, you also give it values relative to the world - how frequent should a tree spawn? how much ore should an ore vein have? which Y-level should a structure generate at?

<h2>Biome Modifier</h2>
* Biome Modifiers (BMs) is defined as "where a feature should be"
* It is given by the PF, and NeoForge Classes would place the PFs into specific biomes