;; This code is initally taken from
;; https://github.com/plaxdan/openweathermap-charts-cljs
;; Thanks!

(ns itrains.weather
  (:require [cljs-http.client :as http]))

(defonce api-key "62e36025d1c0a3fab4ff4dba5e34a50f")

(defonce url-daily-forecast
  "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&q=")

(defn- construct-url
  [city & {:keys [units count]
           :or {units "metric" count 5}}
          cfg]
  (str  url-daily-forecast city
    "&units=" units
    "&cnt=" count
    "&appid=" api-key))

(defn weather-channel
  "Fetches daily forecast data from the server and returns
  a core.async channel. Available options:
  - units ('metric')
  - count (5)"
  [city & cfg]
  (let [url (construct-url city cfg)]
    (http/jsonp url)))
