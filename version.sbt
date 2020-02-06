isSnapshot := true
val baseVersion = "0.0.1"

version in ThisBuild := {
  if (isSnapshot.value) {
    s"$baseVersion-SNAPSHOT"
  } else {
    baseVersion
  }
}
