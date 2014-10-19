(ns emotions-config.core
  (:require [clojurefx.core :refer :all]
            [clojure.string :refer [trim]]
            [clojure.pprint :refer [pprint]]
            [clojure.string :as s]
            [clj-time.core :as t]
            [clj-time.format :as tf]
            [clojure.tools.logging :as log]
            [reagi.core :as r]
            [emotions.core :refer :all]
            [emotions.util :refer [float= seconds-diff]]
            [emotions-config.bus :refer [bus]]
            [emotions-config.util :refer :all])
  (:import [javafx.fxml FXMLLoader]
           [javafx.stage StageBuilder]
           [javafx.scene Scene]))

(def template-id-desire-slider "motivation_x_desire")
(def template-id-add-attractor-button "motivation_x_add_attractor")
(def template-id-motivation-attractors "motivation_x_attractors")
(def template-id-motivation-attractor "motivation_x_attractor")

(def id-pad-chart "pad_chart")
(def id-attractors-chart "attractors_chart")
(def id-motivations-container "motivations")
(def id-prefix-motivation "motivation")
(def id-prefix-motivation-add-attractor "motivation_add")
(def id-prefix-motivation-desire "motivation_desire")
(def id-prefix-motivation-attractors "motivation_attractors")
(def id-prefix-attractor "attractor")

(def motivations (atom []))

(def attractor-id (atom 0))

(def ui-root (atom nil))

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

(defn mid->ui-id [mid] (str id-prefix-motivation mid))

(defn mid->desire-id [mid] (str id-prefix-motivation-desire mid))

(defn mid->add-attractor-id [mid] (str id-prefix-motivation-add-attractor mid))

(defn mid->attractors-id [mid] (str id-prefix-motivation-attractors mid))

(defn aid->ui-id [mid] (str id-prefix-attractor mid))

(defn get-motivations-container
  []
  (->
   (.getScene @ui-root)
   (.lookup (str "#" id-motivations-container))))

(defn load-motivation-ui
  "Add the UI for the given motivation to the container. Must be invoked in javaFX thread."
  [container motivation]
  (let [root
        (load-motivation-ui-template)
        add-button
        (get-child-by-id template-id-add-attractor-button)
        desire-slider
        (get-child-by-id template-id-desire-slider)
        attractors-container
        (get-child-by-id template-id-motivation-attractors)
        attractor-template
        (get-child-by-id template-id-motivation-attractor)
        attractor-parent
        (.getParent attractor-template)
        mid (:id motivation)
        attractors
        (:attractors motivation)]
    (log/debug "Building UI for " (pr-str motivation))
    (do
      (.setId root (mid->ui-id mid))
      (.setText root (:name motivation))
      (.setId add-button (mid->add-attractor-id mid))
      (.setId desire-slider (mid->desire-id mid))
      (.setId attractors-container (mid->attractors-id mid))
      (.remove (.getChildren attractor-parent) attractor-template)
      (.add (.getPanes container) root)
      )))

(def stage
  (run-now
   (doto
     (.build (StageBuilder/create))
     (.setTitle "Motivations editor")
     (.setScene (Scene. (reset! ui-root (load-ui)))))))

(def reload-stream
  (r/filter #(= "reload_button" (.getId (.getSource %))) bus))

(defn reload-motivations
  [event]
  (let [scene (.getScene (.getSource event))
        t (.lookup scene "#motivations_text")
        mstr (.getText t)
        container (.lookup scene "#motivations")]
    (log/info "Loading motivations: " mstr)
    (run-now (do
               (log/info "Clearing motivations container")
               (.clear (.getPanes container))
               (log/info "Ready to load motivations")
               (doseq [m (load-motivations mstr)]
                 (do
                   (log/info "Loading: " (pr-str m))
                   (load-motivation-ui container m)))))))

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
    (log/info "Starting...")
    (run-now (.show stage))
    (setup-events)))

(defn -main
  [& args]
  (start))
