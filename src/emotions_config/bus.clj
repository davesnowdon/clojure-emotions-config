(ns emotions-config.bus
  (:require [reagi.core :as r]))

(def bus (r/events))

(defn push [event]
  (r/push! bus event))
