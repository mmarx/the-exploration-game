(defproject teg-rest "0.0.1-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [http-kit "2.3.0"]
                 [ring/ring-defaults "0.3.2"]
                 [org.clojure/data.json "0.2.6"]
                 [conexp-clj "2.0.1-SNAPSHOT"]]
  :main ^:skip-aot teg-rest.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
