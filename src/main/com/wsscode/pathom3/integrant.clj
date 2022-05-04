(ns com.wsscode.pathom3.integrant
  (:require
    [clojure.java.io :as io]
    [clojure.spec.alpha :as s]
    [com.wsscode.pathom3.connect.indexes :as pci]
    [com.wsscode.pathom3.connect.operation :as pco]
    [com.wsscode.pathom3.interface.eql :as p.eql]
    [integrant.core :as ig]
    [some.org.sample-resolvers]))

(s/def ::registry ::pci/operations)
(s/def ::env map?)
(s/def ::pathom-viz-enabled? boolean?)
(s/def ::pathom-viz-name string?)

(defn re-find-operations [re]
  (into []
        (comp
          (filter #(re-find re (str %)))
          (map ns-publics)
          (mapcat vals)
          (map var-get)
          (filter pco/operation?))
        (all-ns)))

(defn find-operations [x]
  (cond
    (qualified-symbol? x)
    (var-get (requiring-resolve x))

    (string? x)
    (re-find-operations (re-pattern x))))

(def readers
  {'pathom3/ops find-operations})

(defmethod ig/init-key ::pathom3
  [_ {::keys [env registry pathom-viz-enabled? pathom-viz-name]}]
  (let [plan-cache (atom {})
        env'       (-> {:com.wsscode.pathom3.connect.planner/plan-cache* plan-cache}
                       (merge env)
                       (pci/register registry)
                       (cond-> pathom-viz-enabled?
                         ((requiring-resolve 'com.wsscode.pathom.viz.ws-connector.pathom3/connect-env)
                          (or pathom-viz-name "integrant"))))]
    (p.eql/boundary-interface env')))

(comment
  (var-get (requiring-resolve 'some.org.sample-resolvers/registry))

  (ig/read-string
    {:readers readers}
    (slurp (io/resource "sample_app/config.edn")))

  (let [publics (ns-publics ns)])
  (->> (find-ns 'some.org.sample-resolvers)
       (ns-publics)
       vals
       (mapv #(var-get %)))
  (find-operations #"^some\.org\..+-resolvers$"))
