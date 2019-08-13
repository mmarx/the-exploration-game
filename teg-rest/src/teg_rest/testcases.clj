(ns teg-rest.testcases
  (:require [org.httpkit.server :refer :all]
            [org.httpkit.client :as http]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.data.json :as json])
  (:gen-class))


;;;; Testing
(defn test-get []
  (let [{:keys [status headers body error] :as resp} @(http/get "http://localhost:4223")]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    body))

(defn test-post []
  (let [{:keys [status headers body error] :as resp}
        @(http/post "http://127.0.0.1:4223/explore"
                    {:body (json/write-str
                            {"properties" ["P1", "P3", "P4", "P10", "P20", "P30"],
                             "counterexamples" {
                                                 "q1234" ["P4", "P3", "P10"],
                                                 "q1339" ["P4", "P30", "P10"]},
                             "implications" [
                                              {"head" ["P10"], "body" ["P3", "P4"]},
                                             {"head" ["P30"], "body" ["P4", "P1"]}],
                             "wdbound" 5
                             })})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP POST success: " status))
    (println body)))

(defn test-post2 []
  (let [{:keys [status headers body error] :as resp}
        @(http/post "http://127.0.0.1:4223/explore"
                    {:body (json/write-str
                            {"properties" ["P1", "P3", "P4", "P10", "P20", "P30"],
                             "counterexamples" {
                                                 "q1234" ["P4", "P3", "P10"],
                                                 "q1339" ["P4", "P30", "P10"]},
                             "implications" [
                                             {"head" ["P10"], "body" ["P3", "P4"]},
                                             {"head" ["P30"], "body" ["P4", "P1"]}
                                             {"head" ["P10", "P4"], "body" []}],
                             "wdbound" 5
                             })})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP POST success: " status))
    (println body)))

(defn test-rw1-s1 [] ;; real world test from previous experiments
   (let [{:keys [status headers body error] :as resp}
        @(http/post "http://127.0.0.1:4223/explore"
                    {:body (json/write-str
                            {"properties" ["P364", "P161", "P495"],
                             "counterexamples" {
                                                "Qartificial" []
                                                },
                             "implications" [],
                             "wdbound" 5
                             })})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP POST success: " status))
    (println body)))

(defn test-rw1-s2 [] ;; real world test from previous experiments
   (let [{:keys [status headers body error] :as resp}
        @(http/post "http://127.0.0.1:4223/explore"
                    {:body (json/write-str
                            {"properties" ["P364", "P161", "P495"],
                             "counterexamples" {
                                                "Qartificial" [],
                                                "Q73706" ["P495"]},
                             "implications" [],
                             "wdbound" 5
                             })})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP POST success: " status))
    (println body)))

(defn test-rw1-s3 [] ;; real world test from previous experiments
   (let [{:keys [status headers body error] :as resp}
        @(http/post "http://127.0.0.1:4223/explore"
                    {:body (json/write-str
                            {"properties" ["P364", "P161", "P495"],
                             "counterexamples" {
                                                "Qartificial" [],
                                                "Q73706" ["P495"],
                                                "Q7156451" ["P161"]},
                             "implications" [],
                             "wdbound" 5
                             })})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP POST success: " status))
    (println body)))

(defn test-rw1-s4 [] ;; real world test from previous experiments
   (let [{:keys [status headers body error] :as resp}
        @(http/post "http://127.0.0.1:4223/explore"
                    {:body (json/write-str
                            {"properties" ["P364", "P161", "P495"],
                             "counterexamples" {
                                                "Qartificial" [],
                                                "Q73706" ["P495"],
                                                "Q7156451" ["P161"],
                                                "Q32445" ["P161" "P495"]
                                                },
                             "implications" [],
                             "wdbound" 5
                             })})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP POST success: " status))
    (println body)))


(defn test-rw1-s5 [] ;; real world test from previous experiments
   (let [{:keys [status headers body error] :as resp}
        @(http/post "http://127.0.0.1:4223/explore"
                    {:body (json/write-str
                            {"properties" ["P364", "P161", "P495"],
                             "counterexamples" {
                                                "Qartificial" [],
                                                "Q73706" ["P495"],
                                                "Q7156451" ["P161"],
                                                "Q32445" ["P161" "P495"],
                                                "Q12347438" ["P364"]
                                                },
                             "implications" [],
                             "wdbound" 5
                             })})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP POST success: " status))
    (println body)))

(defn test-rw1-s6 [] ;; real world test from previous experiments
   (let [{:keys [status headers body error] :as resp}
        @(http/post "http://127.0.0.1:4223/explore"
                    {:body (json/write-str
                            {"properties" ["P364", "P161", "P495"],
                             "counterexamples" {
                                                "Qartificial" [],
                                                "Q73706" ["P495"],
                                                "Q7156451" ["P161"],
                                                "Q32445" ["P161" "P495"],
                                                "Q12347438" ["P364"],
                                                "Q47441" ["P364" "P495"]
                                                },
                             "implications" [],
                             "wdbound" 5
                             })})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP POST success: " status))
    (println body)))


(defn test-rw2-s1 [] ;; real world test from previous experiments
   (let [{:keys [status headers body error] :as resp}
        @(http/post "http://127.0.0.1:4223/explore"
                    {:body (json/write-str
                            {"properties" ["P247", "P375", "P619"],
                             "counterexamples" {
                                                "Qartificial" []
                                                },
                             "implications" [],
                             "wdbound" 5
                             })})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP POST success: " status))
    (println body)))

(defn test-rw2-s2 [] ;; real world test from previous experiments
   (let [{:keys [status headers body error] :as resp}
        @(http/post "http://127.0.0.1:4223/explore"
                    {:body (json/write-str
                            {"properties" ["P247", "P375", "P619"],
                             "counterexamples" {
                                                "Qartificial" [],
                                                "Q166220" ["P619"]
                                                },
                             "implications" [],
                             "wdbound" 5
                             })})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP POST success: " status))
    (println body)))

(defn test-rw2-s3 [] ;; real world test from previous experiments
   (let [{:keys [status headers body error] :as resp}
        @(http/post "http://127.0.0.1:4223/explore"
                    {:body (json/write-str
                            {"properties" ["P247", "P375", "P619"],
                             "counterexamples" {
                                                "Qartificial" [],
                                                "Q166220" ["P619"],
                                                "Q1592295" ["P619" "P375"]
                                                },
                             "implications" [],
                             "wdbound" 5
                             })})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP POST success: " status))
    (println body)))

(defn test-rw2-s4 [] ;; real world test from previous experiments
   (let [{:keys [status headers body error] :as resp}
        @(http/post "http://127.0.0.1:4223/explore"
                    {:body (json/write-str
                            {"properties" ["P247", "P375", "P619"],
                             "counterexamples" {
                                                "Qartificial" [],
                                                "Q166220" ["P619"],
                                                "Q1592295" ["P619" "P375"],
                                                "Q298019" ["P375"]
                                                },
                             "implications" [],
                             "wdbound" 5
                             })})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP POST success: " status))
    (println body)))


(defn test-rw2-s5 [] ;; real world test from previous experiments
   (let [{:keys [status headers body error] :as resp}
        @(http/post "http://127.0.0.1:4223/explore"
                    {:body (json/write-str
                            {"properties" ["P247", "P375", "P619"],
                             "counterexamples" {
                                                "Qartificial" [],
                                                "Q166220" ["P619"],
                                                "Q1592295" ["P619" "P375"],
                                                "Q298019" ["P375"],
                                                "Q113808" ["P619" "P247"]
                                                },
                             "implications" [],
                             "wdbound" 5
                             })})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP POST success: " status))
    (println body)))

(defn test-rw2-s6 [] ;; real world test from previous experiments
   (let [{:keys [status headers body error] :as resp}
        @(http/post "http://127.0.0.1:4223/explore"
                    {:body (json/write-str
                            {"properties" ["P247", "P375", "P619"],
                             "counterexamples" {
                                                "Qartificial" [],
                                                "Q166220" ["P619"],
                                                "Q1592295" ["P619" "P375"],
                                                "Q298019" ["P375"],
                                                "Q113808" ["P619" "P247"],
                                                "Q9390874" ["P247"]
                                                },
                             "implications" [],
                             "wdbound" 5
                             })})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP POST success: " status))
    (println body)))
