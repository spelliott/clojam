(ns clojam.core
	(:require [clojam.redisrepository :as redisrepo])
	)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defonce server1-conn {:pool {} :spec {:host "127.0.0.1" :port 6379}})

(defonce mytest redisrepo/server1-conn)

(defonce myrepo (redisrepo/new-redis-repository server1-conn))

(defn try-parse-long
	[x]
	(try (Long/parseLong x) (catch Exception e 0)))

(defn prep-namespace-needed [snippet]
	(< (try-parse-long (:last-loaded-time snippet)) (try-parse-long (:last-modified-time-namespace snippet))
	)
	)

(defn prep-namespace [snippet repo]
	(if (prep-namespace-needed snippet)
		(do 
			; (create-ns (symbol (:namespace-name snippet)))
			(binding [*ns* *ns*]
				(load-string (str "(ns " (:namespace-name snippet) "  (:require [clojure.math.numeric-tower :as math]) )"))
				)
			(.loaded repo (:name snippet))
			)
		))

(defn run-snippet [snippet repo]
	(do 
		; (println "snippet is" snippet)
		(prep-namespace snippet repo)
	(binding [*ns* (find-ns (symbol (:namespace-name snippet)))]
		(do (println "My namespace is " *ns*)
			(println "math/expt is: ")
			(load-string "math/expt")
			))
	)
	)

	(defn run-codes [object]
		(let [codes (.get-all-codes myrepo)]
			; codes
			; (doall codes ())
			(map run-snippet codes)
		))



(defn set-example-codes [repo]
	(.set-code repo "code1" "ns1" "" "(println \"my namespace is\" *ns*)")
	)



