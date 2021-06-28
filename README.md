# Reactor Interruptible
[![Build Status](https://travis-ci.com/pine/reactor-interruptible.svg?branch=main)](https://travis-ci.com/pine/reactor-interruptible)
[![codecov](https://codecov.io/gh/pine/reactor-interruptible/branch/main/graph/badge.svg?token=BdEE5ZTyZm)](https://codecov.io/gh/pine/reactor-interruptible)

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
### Upload Maven Central

```
$ ./gradlew clean publish
```

## License
MIT &copy; Pine Mizune
