(set-env!
 :source-paths    #{"src/cljs"}
 :resource-paths  #{"resources"}
 :dependencies '[[reagent "0.6.0"]
                 [adzerk/boot-cljs          "1.7.228-2"]
                 [adzerk/boot-cljs-repl     "0.3.3"]
                 [adzerk/boot-reload        "0.4.13"]
                 [pandeiro/boot-http        "0.7.6"]
                 [com.cemerick/piggieback   "0.2.1"]
                 [org.clojure/tools.nrepl   "0.2.12"]
                 [weasel                    "0.7.0"]
                 [cljs-http                 "0.1.42"]
                 [org.clojure/core.async    "0.2.395"]
                 [org.clojure/clojurescript "1.9.293"]])

(require
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload    :refer [reload]]
 '[pandeiro.boot-http    :refer [serve]])

(deftask build []
  (comp (cljs)))

(deftask run []
  (comp (serve)
        (watch)
        (cljs-repl)
        (reload)
        (build)))

(deftask production []
  (task-options! cljs {:optimizations :advanced})
  identity)

(deftask development []
  (task-options! cljs {:optimizations :none :source-map true}
                 reload {:on-jsload 'itrains.app/init})
  identity)

(deftask dev
  "Simple alias to run application in development mode"
  []
  (comp (development)
        (run)))
