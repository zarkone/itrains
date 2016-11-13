(ns itrains.app
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require
   [reagent.core :as r]
   [cljs.core.async :refer [<!]]
   [itrains.weather :as weather]))

(defn console-log [& args]
  (apply (.-log js/console) args))

(defn console-dir [cljs-object]
  (.dir js/console (clj->js cljs-object)))

(def current-city (r/atom "Barnaul"))
(def current-weather (r/atom {}))


(let [w (weather/weather-channel @current-city :count 1)]
  (console-log "fetch weatcher...")
  (go
    (swap! current-weather assoc @current-city (<! w))))

;; (console-dir @current-weather)

(get @current-weather @current-city)
(defn get-snow [weather-map]
  (-> weather-map
    :body
    :list
    (get 0)
    :snow))

(defn weather-component [current-city current-weather]
  (when-let [w (get @current-weather @current-city)]
    (let [snow (get-snow w)]
      (console-dir w)
      [:div
       [:h2 @current-city]
       [:h3 "Снег: " snow]])))

(defn init []
  [weather-component current-city current-weather])

(defn ^:export run []
  (r/render [init]
            (js/document.getElementById "app")))

(run)
