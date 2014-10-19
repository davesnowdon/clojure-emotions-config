(ns emotions-config.util
  (:require [clojurefx.core :refer :all])
  (:import [javafx.fxml FXMLLoader]
           [javafx.stage StageBuilder]
           [javafx.scene Scene]
           [javafx.scene Parent]
           [javafx.scene.control TitledPane]
           [javafx.scene.control SplitPane]
           [javafx.scene.control Accordion]))

(defn node->id
  "Return the ID of a JavaFX node"
  [node]
  (.get (.idProperty node)))

; converted to clojure from https://gist.github.com/andytill/3024651
(defmulti get-child-by-id
  "Find a node within a parent by its ID"
  (fn [parent id] (.getClass parent)))

(defn- generic-get-child-by-id
  [parent id]
  (let [children (.getChildrenUnmodifiable parent)]
    (loop [node (first children) tail (rest children)]
      (if (= id (node->id node))
        node
        (if-let [result (get-child-by-id node id)]
          result
          (recur (first tail) (rest tail)))))))

(defmethod get-child-by-id TitledPane
  [parent id]
  (let [content (.getContent parent)
        node-id (node->id content)]
    (cond
     (= id node-id) content
     (instance? Parent content)
     (if-let [child (get-child-by-id content id)]
       child
       (generic-get-child-by-id parent id)))))

(defmethod get-child-by-id SplitPane
  [parent id]
  )

(defmethod get-child-by-id Accordion
  [parent id])

(defmethod get-child-by-id Parent
  [parent id]
  (generic-get-child-by-id parent id))

; class we don't know how to handle
(defmethod get-child-by-id :default
  [parent id]
  (if (= ( node->id parent) id) parent nil))
