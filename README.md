# Pathom 3 Integrant

This library provides tools to facilitate the usage of [Pathom 3](https://pathom3.wsscode.com/) via [Integrant](https://github.com/weavejester/integrant).

IN DEVELOPMENT, NOT READY FOR GENERAL USAGE

## Setup

Example on what settings you can use:

```clojure
{:com.wsscode.pathom3.integrant/pathom3
 {:com.wsscode.pathom3.integrant/registry
  [#pathom3/ops some.org.sample-resolvers/registry
   #pathom3/ops "some\\.org\\..+"]

  :com.wsscode.pathom3.integrant/env
  {}

  :com.wsscode.pathom3.integrant/pathom-viz-enabled?
  false

  :com.wsscode.pathom3.integrant/pathom-viz-name
  "demo"}}
```

The `:com.wsscode.pathom3.integrant/pathom3` key will be a [boundary interface](https://pathom3.wsscode.com/docs/eql/#boundary-interface).

To use the Pathom Viz helpers you also need to add the [Pathom Viz Connector](https://github.com/wilkerlucio/pathom-viz-connector/)
dependency (I recommend adding that as a developer dependency):

```
com.wsscode/pathom-viz-connector {:mvn/version "2022.02.14"}
```
