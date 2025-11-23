val isJitpack = System.getenv("JITPACK") == "true"
val jitpackTag = System.getenv("JITPACK_TAG")

rootProject.name = "Kotlin-Libraries"

if (!isJitpack || jitpackTag?.startsWith("state-machine-pro/")) {
    include("state-machine-pro")
}
if (!isJitpack || jitpackTag?.startsWith("tree-machine-pro/")) {
    include("tree-machine-pro")
}
if (!isJitpack || jitpackTag?.startsWith("game-framework-pro/")) {
    include("game-framework-pro")
}
if (!isJitpack || jitpackTag?.startsWith("game-framework-pro-extensions/")) {
    include("game-framework-pro-extensions")
}
