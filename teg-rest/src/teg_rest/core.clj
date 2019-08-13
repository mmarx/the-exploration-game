(ns teg-rest.core
  (:require [org.httpkit.server :refer :all]
            [org.httpkit.client :as http]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.data.json :as json]
            [conexp.base :refer [conexp-version difference next-closed-set]]
            [conexp.fca.contexts :as contexts]
            [conexp.fca.implications :refer
             [make-implication close-under-implications clop-by-implications
              premise conclusion]]
            [conexp.fca.applications.wikidata :as wd])
  (:gen-class))


(def banner
  (str "https://tools.wmflabsorg/teg/ using conexp-clj/"
       (conexp-version)))

; Simple Body Page
(defn simple-body-page [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body "This is backend for 'The Exploration game'. See <a
   href='https://tools.wmflabs.org/teg/ for more
   details'>https://tools.wmflabs.org/teg/</a>."})

(defn version [req]
   {:status  200
   :headers {"Content-Type" "text/html"}
   :body (str "This is The Exploration Game" banner)})

(defn hello-name [req] ;(3)
     {:status  200
      :headers {"Content-Type" "text/html"}
      :body    (->
                (pp/pprint req)
                (str "Hello " (:name (:params req))))})

(defn exploration-step
  "Conduct one exploration step using counterexamples and background knowledge about implications"
  [ctx input-implications]
  (loop [implications input-implications
         last         (close-under-implications implications #{}),
         ctx          ctx]
    (if (not last)
      {:implications (difference (set implications) (set input-implications)) :context ctx} 
      (let [conclusion-from-last (contexts/context-attribute-closure ctx last)]
        (if (= last conclusion-from-last)
          (recur implications
                 (next-closed-set (contexts/attributes ctx)
                                  (clop-by-implications implications)
                                  last)
                 ctx)
           (let [newimp (make-implication last conclusion-from-last)]
             (recur (conj implications newimp) nil ctx))  ;; new candidate implication
           )))))

(defn explore [jsonthing]
  (let [atts (set (jsonthing "properties")) ;; Attribute set input
        E (fn [x] ((jsonthing "counterexamples") (str x))) ;; object to attributes map
        obs (map (fn [x] (first x)) (jsonthing "counterexamples"))
        L (set
           (map (fn [x] (make-implication (x "body") (x "head")))
                (jsonthing "implications"))) ;;conv to imps.
        ctx (contexts/make-context
               obs
               atts
               (fn [x y] (contains? (set (E x)) y)))
        wdbound (jsonthing "wdbound")]
    (println  ctx)
    (println  L)
    (println wdbound)
    ;; (println "*****")
    (let [newimp (first (:implications (exploration-step ctx L)))
          thepremise (set  (map (fn [x] (str "(" x ")")) (premise newimp)))
          theconclusion  (set (map (fn [x] (str "(" x ")")) (conclusion newimp)))]
      ;; (println thepremise)
      ;; (println theconclusion)
      (if (not (empty? thepremise))
        (let [wd_counterexamples (wd/counterexample
                                  (make-implication thepremise theconclusion) wdbound)]
          (println wd_counterexamples)
          [newimp wd_counterexamples])
        [newimp #{}]
        )
      )))

(defn exploration-handler [req]
        {:status  200
         :headers {"Content-Type" "text/json"}
         :body    (str (json/write-str {"nothing" "nothing"}))})


(defn exploration-post [req]
  (let [rdr (clojure.java.io/reader (:body req))
        body (slurp rdr)
        thereq (json/read-str body)
        reply (explore thereq)]
    ;; (println (premise (first reply)))
    ;; (println (conclusion (first reply)))
    {:status  200
     :headers {"Content-Type" "text/json"}
     :body    (json/write-str
               {
                "counterexamples" (second reply)
                "new-implication" {
                                   "head" (conclusion (first reply)),
                                   "body" (premise (first reply))}
                })}))


;;; The webapp and stuff

(defroutes app-routes
  (GET "/" [] simple-body-page)
  (GET "/version" [] version)
;;  (GET "/request" [] request-example)
  (GET "/explore" [] exploration-handler)
  (POST "/explore" [] exploration-post)
  (route/not-found "Error, page not found!"))

(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    ;; graceful shutdown: wait 100ms for existing requests to be finished
    ;; :timeout is optional, when no timeout, stop immediately
    (@server :timeout 100)
    (reset! server nil)))

(defn -main
  [& args]
  (reset! server (run-server (wrap-defaults #'app-routes (assoc-in
  site-defaults [:security :anti-forgery] false)) {:port 4223})))



