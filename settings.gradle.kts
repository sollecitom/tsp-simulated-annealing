rootProject.name = "tsp-simulated-annealing"

val modules = setOf("tsp-domain", "coordinates-domain")
modules::forEach { include(it) }