(ns some.org.sample-resolvers
  (:require [com.wsscode.pathom3.connect.operation :as pco]))

(pco/defresolver sample [{::keys []}]
  {:foo "bar"})

(def registry
  [sample])
