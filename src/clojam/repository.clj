(ns clojam.repository) 
(defprotocol code-repository
(get-all-codes [x]
  )

;(get-codes-shard [shardnum numComponents]
;  )

(set-code [x key namespace-name namespace-bootstrap body]
  )
(ping [x]
      )

(loaded [x key])
(reset-load-times [x])
)

(defrecord Snippet [name namespace-name namespace-bootstrap code last-modified-time-code last-modified-time-namespace last-loaded-time])

(defn new-snippet []
  (Snippet. nil nil nil nil nil nil nil))



