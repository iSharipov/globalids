# Global Identifiers

![Java CI with Maven](https://github.com/iSharipov/gson-adapters/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)
<a href="https://codecov.io/gh/iSharipov/globalids">
<img src="https://codecov.io/gh/iSharipov/globalids/branch/master/graph/badge.svg" />
</a>
<a href="https://lgtm.com/projects/g/iSharipov/globalids/alerts/">
<img alt="Total alerts" src="https://img.shields.io/lgtm/alerts/g/iSharipov/globalids.svg?logo=lgtm&logoWidth=18"/>
</a>
<a href="https://lgtm.com/projects/g/iSharipov/globalids/context:java">
<img alt="Language grade: Java" src="https://img.shields.io/lgtm/grade/java/g/iSharipov/globalids.svg?logo=lgtm&logoWidth=18"/>
</a>
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.isharipov/globalids/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.isharipov/globalids)
<br />
[![LinkedIn][linkedin-shield]][linkedin-url]

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=flat-square&logo=linkedin&colorB=555

[linkedin-url]: https://linkedin.com/in/iSharipov

---

* United States
    * Social Security Number - since 0.0.1
        * Regex - `^(?!666|000|9\d{2})\d{3}-(?!00)\d{2}-(?!0{4})\d{4}$`
        * Example - `856-45-6789`
    * ZIP Code - since 0.0.1
        * Regex - `^\d{5}(?:[-\s]\d{4})?$`
        * Example - `99750, 99750-0077, 99750 0077`
* United Kingdom
    * National Insurance number - since 0.0.1
        * Regex - `^[A-CEGHJ-PR-TW-Z]{1}[A-CEGHJ-NPR-TW-Z]{1}[0-9]{6}[A-DFM]{0,1}$`
        * Example - `JG103759A, AP019283D, ZX047829C`

---

### Gradle

```groovy
dependencies {
    implementation("io.github.isharipov:globalids-us:0.0.1")
    implementation("io.github.isharipov:globalids-uk:0.0.1")
}
```

### Maven

```xml

<dependencies>
    <dependency>
        <groupId>io.github.isharipov</groupId>
        <artifactId>globalids-us</artifactId>
        <version>0.0.1</version>
    </dependency>
    <dependency>
        <groupId>io.github.isharipov</groupId>
        <artifactId>globalids-uk</artifactId>
        <version>0.0.1</version>
    </dependency>
</dependencies>
```

---

## Requirements

Global Identifiers requires Java 1.8 or later.