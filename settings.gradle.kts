rootProject.name = "Kotlin-Libraries"

val isJitpack = System.getenv("JITPACK") == "true"
val version = System.getenv("VERSION")

println("IsJitpack: $isJitpack")
println("Version: $version")

//if (!isJitpack || version?.startsWith("state-machine-pro~") == true) {
//    include("state-machine-pro")
//}
//if (!isJitpack || version?.startsWith("tree-machine-pro~") == true) {
//    include("tree-machine-pro")
//}
//if (!isJitpack || version?.startsWith("game-framework-pro~") == true) {
//    include("game-framework-pro")
//}
//if (!isJitpack || version?.startsWith("game-framework-pro-extensions~") == true) {
//    include("game-framework-pro-extensions")
//}

include("state-machine-pro")
include("tree-machine-pro")
include("game-framework-pro")
include("game-framework-pro-extensions")
