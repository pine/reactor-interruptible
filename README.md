# Reactor Interruptible
[![Build Status](https://travis-ci.com/pine/reactor-interruptible.svg?branch=main)](https://travis-ci.com/pine/reactor-interruptible)
[![codecov](https://codecov.io/gh/pine/reactor-interruptible/branch/main/graph/badge.svg?token=BdEE5ZTyZm)](https://codecov.io/gh/pine/reactor-interruptible)
[![Maven Central](https://img.shields.io/maven-central/v/moe.pine/reactor-interruptible)](https://search.maven.org/artifact/moe.pine/reactor-interruptible)

:hourglass: Reactor InterruptedException utilities
<br>
<br>

## Requirements

- Java 8 or later
- Reactor

## Getting started
The library is published to Maven Central.

```gradle
repositories {
    mavenCentral()
}

depepdencies {
    implementation "moe.pine:reactor-interruptible:$latestVersion"
}
```

## Development
### Test

```
$ ./gradlew clean check
```

### Upload Maven Central

```
$ ./gradlew clean publish
```

## License
MIT &copy; Pine Mizune
