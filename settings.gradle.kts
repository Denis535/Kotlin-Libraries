val isJitpack = System.getenv("JITPACK") == "true"
val jitpackTag = System.getenv("JITPACK_TAG")

rootProject.name = "Kotlin-Libraries"

if (!isJitpack || jitpackTag?.startsWith("state-machine-pro/") == true) {
    include("state-machine-pro")
}
if (!isJitpack || jitpackTag?.startsWith("tree-machine-pro/") == true) {
    include("tree-machine-pro")
}
if (!isJitpack || jitpackTag?.startsWith("game-framework-pro/") == true) {
    include("game-framework-pro")
}
if (!isJitpack || jitpackTag?.startsWith("game-framework-pro-extensions/") == true) {
    include("game-framework-pro-extensions")
}
