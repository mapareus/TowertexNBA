# TowertexNBA
experimental architecture demo
features:
- separated concerns
    - NBAApi module to handle network
    - NBAModel module to handle business logic
- NBAApi module
    - test coverage via JUnit
    - Retrofit
    - custom Call to handle non-standard responses
    - services separated via delegation
- NBAModel module
    - test coverage via AndroidInstrumentation
    - persistency via Room
    - SigleSourceOfTruth architecture
    - repositories separated via delegation
- app module
    - test coverage via MockK
    - dependency injection via Koin
    - ResourceRepository pattern
    - MVVM
    - Compose
    - navigation via NavHost
    - Jetpack paging library