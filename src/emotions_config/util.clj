(ns emotions-config.util
  (:require [clojurefx.core :refer :all])
  (:import [javafx.fxml FXMLLoader]
           [javafx.stage StageBuilder]
           [javafx.scene Scene]
           [javafx.scene Parent]
           [javafx.scene.layout BorderPane]
           [javafx.scene.control TitledPane]
           [javafx.scene.control ScrollPane]
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

(defn- get-child-from-collection
  [nodes id]
  (loop [node (first nodes) tail (rest nodes)]
      (if node
        (if (= id (node->id node))
          node
          (if-let [result (get-child-by-id node id)]
            result
            (recur (first tail) (rest tail)))))))

(defn- generic-get-child-by-id
  [parent id]
  (get-child-from-collection (.getChildrenUnmodifiable parent) id))

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

(defmethod get-child-by-id BorderPane
  [parent id]
  (let [children [(.getBottom parent)
                  (.getCenter parent)
                  (.getLeft parent)
                  (.getRight parent)
                  (.getTop parent)]]
    (get-child-from-collection children id)))

(defmethod get-child-by-id ScrollPane
  [parent id]
  (get-child-by-id (.getContent parent) id))

(defmethod get-child-by-id SplitPane
  [parent id]
  (get-child-from-collection (.getItems parent) id))

(defmethod get-child-by-id Accordion
  [parent id]
  (get-child-from-collection (.getPanes parent) id))

(defmethod get-child-by-id Parent
  [parent id]
  (generic-get-child-by-id parent id))

; class we don't know how to handle
(defmethod get-child-by-id :default
  [parent id]
  (if (= (node->id parent) id) parent nil))
