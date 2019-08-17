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

(defn test-get []
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                    {:body (json/write-str
                            {"sessionId" 123456789,
                             "properties" ["P1", "P3", "P4", "P10", "P20", "P30"],
                             "counterexamples" {
                                                 "q1234" ["P4", "P3", "P10"],
                                                 "q1339" ["P4", "P30", "P10"]},
                             "implications" [
                                              {"head" ["P10"], "body" ["P3", "P4"]},
                                             {"head" ["P30"], "body" ["P4", "P1"]}],
                             "maxCounterexamples" 5
                             })})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

(def iii ())

(defn test-get2 []
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                   {:query-params 
                    {"sessionId" "123456789",
                     "properties" ["P1" "P3" "P4" "P10" "P20" "P30"],
                     :counterexamples  (json/write-str {
                                                        "q1234" ["P4", "P3", "P10"],
                                                        "q1339" ["P4", "P30", "P10"]
                                                        }),
                     :implications [ 
                                    (json/write-str {"head" ["P10"] "body" ["P3" "P4"]})
                                    (json/write-str {"head" ["P30"] "body" ["P4" "P1"]})
                                    (json/write-str {"head" ["P10" "P4"] "body" []})
                                    ]
                     ,
                     "maxCounterexamples" "5"
                     }
                    })]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

(defn test-rw1-s1 [] ;; real world test from previous experiments
   (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                    {:query-params 
                            {"sessionId" 123456789,
                             "properties" ["P364" "P161" "P495"],
                             :counterexamples  (json/write-str {
                                                                "Qartificial" []
                                                                }),
                             :implications []
                             ,
                             "maxCounterexamples" "5"
                             }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

(defn test-rw1-s2 [] ;; real world test from previous experiments
   (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                    {:query-params 
                            {"sessionId" 123456789,
                             "properties" ["P364", "P161", "P495"],
                             :counterexamples (json/write-str {
                                                               "Qartificial" [],
                                                               "Q73706" ["P495"]
                                                               }),
                             :implications [],
                             "maxCounterexamples" "5"
                             }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

(defn test-rw1-s3 [] ;; real world test from previous experiments
   (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                    {:query-params 
                            {"sessionId" 123456789,
                             "properties" ["P364", "P161", "P495"],
                             :counterexamples (json/write-str {
                                                               "Qartificial" [],
                                                               "Q73706" ["P495"],
                                                               "Q7156451" ["P161"]
                                                               }),
                             :implications [],
                             "maxCounterexamples" "5"
                             }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

(defn test-rw1-s4 [] ;; real world test from previous experiments
   (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                    {:query-params 
                            {"sessionId" 123456789,
                             "properties" ["P364", "P161", "P495"],
                             :counterexamples (json/write-str {
                                                               "Qartificial" [],
                                                               "Q73706" ["P495"],
                                                               "Q7156451" ["P161"]
                                                               "Q32445" ["P161" "P495"]
                                                               }),
                             :implications [],
                             "maxCounterexamples" "5"
                             }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

(defn test-rw1-s5 [] ;; real world test from previous experiments
   (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                    {:query-params 
                            {"sessionId" 123456789,
                             "properties" ["P364", "P161", "P495"],
                             :counterexamples (json/write-str {
                                                               "Qartificial" [],
                                                               "Q73706" ["P495"],
                                                               "Q7156451" ["P161"]
                                                               "Q32445" ["P161" "P495"]
                                                               "Q12347438" ["P364"]
                                                               }),
                             :implications [],
                             "maxCounterexamples" "5"
                             }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

(defn test-rw1-s6 [] ;; real world test from previous experiments
   (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                    {:query-params 
                            {"sessionId" 123456789,
                             "properties" ["P364", "P161", "P495"],
                             :counterexamples (json/write-str {
                                                               "Qartificial" [],
                                                               "Q73706" ["P495"],
                                                               "Q7156451" ["P161"]
                                                               "Q32445" ["P161" "P495"]
                                                               "Q12347438" ["P364"]
                                                               "Q47441" ["P364" "P495"]
                                                               }),
                             :implications [],
                             "maxCounterexamples" "5"
                             }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))


(defn test-rw2-s1 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                   {:query-params
                    {"sessionId" 987654321
                     "properties" ["P247" "P375" "P619"],
                     :counterexamples (json/write-str {
                                                       "Qartificial" []
                                                       }),
                     :implications [],
                     "maxCounterexamples" "5"
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

(defn test-rw2-s2 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                   {:query-params
                    {"sessionId" 987654321
                     "properties" ["P247" "P375" "P619"],
                     :counterexamples (json/write-str {
                                                       "Qartificial" [],
                                                       "Q166220" ["P619"]
                                                       }),
                     :implications [],
                     "maxCounterexamples" "5"
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

(defn test-rw2-s3 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                   {:query-params
                    {"sessionId" 987654321
                     "properties" ["P247" "P375" "P619"],
                     :counterexamples (json/write-str {
                                                       "Qartificial" [],
                                                       "Q166220" ["P619"]
                                                       "Q1592295" ["P619" "P375"]
                                                       }),
                     :implications [],
                     "maxCounterexamples" "5"
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

(defn test-rw2-s4 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                   {:query-params
                    {"sessionId" 987654321
                     "properties" ["P247" "P375" "P619"],
                     :counterexamples (json/write-str {
                                                       "Qartificial" [],
                                                       "Q166220" ["P619"]
                                                       "Q1592295" ["P619" "P375"]
                                                       "Q298019" ["P375"]
                                                       }),
                     :implications [],
                     "maxCounterexamples" "5"
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

(defn test-rw2-s5 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                   {:query-params
                    {"sessionId" 987654321
                     "properties" ["P247" "P375" "P619"],
                     :counterexamples (json/write-str {
                                                       "Qartificial" [],
                                                       "Q166220" ["P619"]
                                                       "Q1592295" ["P619" "P375"]
                                                       "Q298019" ["P375"]
                                                       "Q113808" ["P619" "P247"]
                                                       }),
                     :implications [],
                     "maxCounterexamples" "5"
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

(defn test-rw2-s6 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                   {:query-params
                    {"sessionId" 987654321
                     "properties" ["P247" "P375" "P619"],
                     :counterexamples (json/write-str {
                                                       "Qartificial" [],
                                                       "Q166220" ["P619"]
                                                       "Q1592295" ["P619" "P375"]
                                                       "Q298019" ["P375"]
                                                       "Q113808" ["P619" "P247"]
                                                       "Q9390874" ["P247"]
                                                       }),
                     :implications [],
                     "maxCounterexamples" "5"
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

(defn test-rw2-s7 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                   {:query-params
                    {"sessionId" 987654321
                     "properties" ["P247" "P375" "P619"],
                     :counterexamples (json/write-str {
                                                       "Qartificial" [],
                                                       "Q166220" ["P619"]
                                                       "Q1592295" ["P619" "P375"]
                                                       "Q298019" ["P375"]
                                                       "Q113808" ["P619" "P247"]
                                                       "Q9390874" ["P247"]
                                                       }),
                     :implications [
                                    (json/write-str {"head" ["P619"] "body" ["P247" "P375"]})
                                    (json/write-str {})
                                    ],
                     "maxCounterexamples" "5"
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))


(defn test-rw3-s1 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                   {:query-params
                    {"sessionId" 987654321
                     "properties" ["P272" "P17" "P136"],
                     :counterexamples (json/write-str {
                                                       "Qartificial" []
                                                       }),
                     :implications [
                                    ],
                     "maxCounterexamples" "5"
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))


;; [20/15007 (#{"country (P17)" "shares border with (P47)" "named after (P138)"}
;; ⟶ #{"located in the administrative territorial entity (P131)"})]
(defn test-rw4-s1 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                   {:query-params
                    {"sessionId" 987654321
                     "properties" ["P47" "P17" "P138" "P131"],
                     :counterexamples (json/write-str {
                                                       "Qartificial" []
                                                       }),
                     :implications [
                                    ],
                     "maxCounterexamples" "5"
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

;; [18/15007 (#{"capital (P36)" "named after (P138)"
;;              "located in the administrative territorial entity (P131)"}
;; ⟶ #{"country (P17)"})]
(defn test-rw5-s1 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                   {:query-params
                    {"sessionId" 987654321
                     "properties" ["P36" "P138" "P131" "P17"],
                     :counterexamples (json/write-str {
                                                       "Qartificial" []
                                                       }),
                     :implications [
                                    ],
                     "maxCounterexamples" "5"
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

;; [32/45021 (#{"country of citizenship (P27)" "member of (P463)"
;;              "field of work (P101)" "instance of (P31)"}
;;            ⟶ #{"occupation (P106)"})]
(defn test-rw6-s1 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                   {:query-params
                    {"sessionId" 987654321
                     "properties" ["P27" "P463" "P101" "P31" "P106"],
                     :counterexamples (json/write-str {
                                                       "Qartificial" []
                                                       }),
                     :implications [
                                    ],
                     "maxCounterexamples" "5"
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))


;; [29/45021 (#{"series (P179)" "publisher (P123)"} ⟶ #{"genre (P136)"})]
(defn test-rw7-s1 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                   {:query-params
                    {"sessionId" 987654321
                     "properties" ["P179" "P123" "P136"],
                     :counterexamples (json/write-str {
                                                       "Qartificial" []
                                                       }),
                     :implications [
                                    ],
                     "maxCounterexamples" "5"
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

(defn test-rw7-s2 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                   {:query-params
                    {"sessionId" 987654321
                     "properties" ["P179" "P123" "P136"],
                     :counterexamples (json/write-str {
                                                       "Qartificial" []
                                                       "Q2110870" ["P123"]
                                                       }),
                     :implications [
                                    ],
                     "maxCounterexamples" "5"
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

(defn test-rw7-s3 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                   {:query-params
                    {"sessionId" 987654321
                     "properties" ["P179" "P123" "P136"],
                     :counterexamples (json/write-str {
                                                       "Qartificial" []
                                                       "Q2110870" ["P123"]
                                                       "Q333563" ["P136"]
                                                       }),
                     :implications [
                                    ],
                     "maxCounterexamples" "5"
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))


(defn test-rw7-s4 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                   {:query-params
                    {"sessionId" 987654321
                     "properties" ["P179" "P123" "P136"],
                     :counterexamples (json/write-str {
                                                       "Qartificial" []
                                                       "Q2110870" ["P123"]
                                                       "Q333563" ["P136"]
                                                       "Q9349" ["P136" "P123"]
                                                       }),
                     :implications [
                                    ],
                     "maxCounterexamples" "5"
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

(defn test-rw7-s5 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                   {:query-params
                    {"sessionId" 987654321
                     "properties" ["P179" "P123" "P136"],
                     :counterexamples (json/write-str {
                                                       "Qartificial" []
                                                       "Q2110870" ["P123"]
                                                       "Q333563" ["P136"]
                                                       "Q9349" ["P136" "P123"]
                                                       "Q207542" ["P179"]
                                                       }),
                     :implications [
                                    ],
                     "maxCounterexamples" "10"
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

(defn test-rw7-s6 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                   {:query-params
                    {"sessionId" 987654321
                     "properties" ["P179" "P123" "P136"],
                     :counterexamples (json/write-str {
                                                       "Qartificial" []
                                                       "Q2110870" ["P123"]
                                                       "Q333563" ["P136"]
                                                       "Q9349" ["P136" "P123"]
                                                       "Q207542" ["P179"]
                                                       "Q18091411" ["P179" "P123"]
                                                       }),
                     :implications [
                                    ],
                     "maxCounterexamples" "5"
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

(defn test-rw7-s7 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/explore"
                   {:query-params
                    {"sessionId" 987654321
                     "properties" ["P179" "P123" "P136"],
                     :counterexamples (json/write-str {
                                                       "Qartificial" []
                                                       "Q2110870" ["P123"]
                                                       "Q333563" ["P136"]
                                                       "Q9349" ["P136" "P123"]
                                                       "Q207542" ["P179"]
                                                       "Q18091411" ["P179" "P123"]
                                                       "Q1110652" ["P179" "P136"]
                                                       }),
                     :implications [
                                    ],
                     "maxCounterexamples" "5"
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))

;; examples for /api/howmany
(defn test-hm1 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/teg/api/howmany"
                   {:query-params 
                    {:implications [(json/write-str {"head" ["P619"] "body" ["P247" "P375"]})]
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))


(defn test-hm2 [] ;; real world test from previous experiments
  (let [{:keys [status headers body error] :as resp}
        @(http/get "http://127.0.0.1:4223/teg/api/howmany"
                   {:query-params 
                    {:implications [(json/write-str {"head" ["P619"] "body" ["P247"]})]
                     }})]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))
    (println body)))
