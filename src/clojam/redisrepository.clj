(ns clojam.redisrepository (:require [taoensso.carmine :as car :refer (wcar)] 
											[clojam.repository :as repo]))

(def server1-conn {:pool {} :spec {:host "127.0.0.1" :port 6379}})

(defmacro wcar* [& body] `(car/wcar server1-conn ~@body))

(deftype redis-repository [conn]
  repo/code-repository
  (set-code [x key namespace-name namespace-bootstrap body]

    (car/wcar conn [(car/hset "body" key body)
     (car/hset "namespace-name" key namespace-name) (car/hset "namespace-bootstrap" key namespace-bootstrap)
     (car/hset "last-modified-time-code" key (System/currentTimeMillis)) (car/hset "last-modified-time-namespace" key (System/currentTimeMillis))
    ]
    )

    )
  (get-all-codes [x]
    (let
    [bodies (car/wcar conn (car/hgetall* "body"))
    namespace-names (car/wcar conn (car/hgetall* "namespace-name"))
    namespace-bootstraps (car/wcar conn (car/hgetall* "namespace-bootstrap"))
    last-modified-times-code (car/wcar conn (car/hgetall* "last-modified-time-code"))
    last-modified-times-namespace (car/wcar conn (car/hgetall* "last-modified-time-namespace"))
    last-loaded-times (car/wcar conn (car/hgetall* "last-loaded-time"))
    ]
      (map (fn [x] (merge (repo/new-snippet) {:name x :namespace-name (namespace-names x) :namespace-bootstrap (namespace-bootstraps x) :code (bodies x) :last-modified-time-code (last-modified-times-code x) :last-modified-time-namespace (last-modified-times-namespace x) :last-loaded-time (last-loaded-times x)})) (keys bodies))
    )
    )
  (ping [x]
    (do 

    "PONG!"
    )
    )

  (loaded [x key]
        (car/wcar conn [
     (car/hset "last-loaded-time" key (System/currentTimeMillis))
    ]
    )
    )

  (reset-load-times [x]
            (car/wcar conn [
     (car/del "last-loaded-time")
    ]
    )
    )
  )
  


(defn new-redis-repository [conn]
  (redis-repository. conn))