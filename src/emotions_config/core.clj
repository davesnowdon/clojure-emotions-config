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

(def motivations (atom []))

(defn load-motivations
  "Read motivations from string"
  [mstr]
  (reset! motivations (load-string mstr)))

(defn load-ui
  "Get Main UI"
  []
  (FXMLLoader/load (clojure.java.io/resource "editor.fxml")))

(defn load-motivation-ui-template
  "Get UI template for a motivation"
  []
  (FXMLLoader/load (clojure.java.io/resource "motivation.fxml")))

(defn load-motivation-ui
  "Add the UI for the given motivation to the container"
  [container motivation])

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
  (let [scene (.getScene (.getSource event))
        t (.lookup scene "#motivations_text")
        mstr (.getText t)
        container (.lookup scene "#motivations")]
    (run-now (do
               (.clear (.getPanes container))
               (doseq [m (load-motivations mstr)]
                 (load-motivation-ui container m))))))

(defn setup-events
  ""
  []
  (r/map (fn [event] (reload-motivations event)) reload-stream)
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
