(ns emotions-config.core
  (:require [clojurefx.core :refer :all]
            [clojure.string :refer [trim]]
            [clojure.pprint :refer [pprint]]
            [clj-time.core :as t]
            [clj-time.format :as tf]
            [emotions.core :refer :all]
            [emotions.util :refer [float= seconds-diff]])
  (:import [javafx.fxml FXMLLoader]
           [javafx.stage StageBuilder]
           [javafx.scene Scene]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(def stage (run-now
            (doto
                (.build (StageBuilder/create))
              (.setScene (Scene. (FXMLLoader/load (clojure.java.io/resource "editor.fxml")))))))

(defn start []
  (run-now (.show stage)))

(defn -main
  [& args]
  (start))
