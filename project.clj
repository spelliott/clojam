(defproject clojam "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main clojam.core
  :dependencies [
  				; DEPENDENCIES REQUIRED BY THE REALTIME-SQUARED FRAMEWORK
  				; (NO NEED TO UPDATE UNLESS YOU'RE MODIFYING THE CORE OF REALTIME SQUARED)
  				;
  				 [com.taoensso/carmine "2.3.1"] 
                 [org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.namespace "0.2.4"]

                 ; THE FOLLOWING DEPENDENCIES ARE NOT REQUIRED BY THE PROCESSING FRAMEWORK
                 ; (ADD ALL POSSIBLE DEPENDENCIES NEEDED BY CODE SNIPPETS HERE)
                 ; 
                 [org.clojure/math.numeric-tower "0.0.2"]

                 ; END DEPENDENCY LIST
                 ])

; to use refresh in the repl: (use '[clojure.tools.namespace.repl :only (refresh)])
