Gradle

```groovy
repositories {
	mavenCentral()
	maven { url 'https://jitpack.io' }
}

dependencies {
	implementation 'com.github.max1mde:CinematicAPI:Tag'
}
```

Maven

```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

	<dependency>
	    <groupId>com.github.max1mde</groupId>
	    <artifactId>CinematicAPI</artifactId>
	    <version>Tag</version>
	</dependency>
```
