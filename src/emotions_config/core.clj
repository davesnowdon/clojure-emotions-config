(ns emotions-config.core
  (:require [clojurefx.core :refer :all]
            [clojure.string :refer [trim]]
            [clojure.pprint :refer [pprint]]
            [clojure.string :as s]
            [clj-time.core :as t]
            [clj-time.format :as tf]
            [reagi.core :as r]
            [emotions.core :refer :all]
            [emotions.util :refer [float= seconds-diff]]
            [emotions-config.bus :refer [bus]])
  (:import [javafx.fxml FXMLLoader]
           [javafx.stage StageBuilder]
           [javafx.scene Scene]))

(defn load-ui
  "Get Main UI"
  []
  (FXMLLoader/load (clojure.java.io/resource "editor.fxml")))

(defn load-motivation-ui
  "Get UI template for a motivation"
  []
  (FXMLLoader/load (clojure.java.io/resource "motivation.fxml")))

(def stage
  (run-now
   (doto
     (.build (StageBuilder/create))
     (.setTitle "Motivations editor")
     (.setScene (Scene. (load-ui))))))

(def reload-stream
  (r/filter #(= "reload_button" (.getId (.getSource %))) bus))

(defn reload-motivations
  [event]
  (let [b (.getSource event)
        s (.getScene b)
        t (.lookup s "#motivations_text")]
    (run-now (.setText t (s/reverse (.getText t))))))

(defn setup-events
  ""
  []
  (r/map (fn [event]
           (reload-motivations event))
         reload-stream)
  ;; (r/map (fn [event]
  ;;          (-> (.getSource event)
  ;;              (.getScene)
  ;;              (.lookup "#motivationsReload")
  ;;              (reload-motivations)))
  ;;        reload-stream)
  )

(defn start []
  (do

    (run-now (.show stage))
    (setup-events)))

(defn -main
  [& args]
  (start))
