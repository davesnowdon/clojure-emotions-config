(ns emotions-config.util-test
  (:require [emotions-config.core :refer :all]
            [emotions-config.util :refer :all]
            [expectations :refer :all]
            [clojure.test :refer [function?]]
            [clojure.pprint :refer [pprint]])
  (:import [javafx.scene.control Slider]
           [javafx.scene.control Button]
           [javafx.scene.control Accordion]
           [javafx.scene.layout FlowPane]
           [javafx.scene.chart BubbleChart]
           [javafx.scene.control ScrollPane]
           [javafx.scene.control TextArea]))

(def motivation-template (load-ui))

(def editor-template (load-motivation-ui-template))

;; test that correct elements are returned from template
(expect (instance? Slider
                   (get-child-by-id motivation-template
                                    "motivation_x_desire")))

(expect (instance? Button
                   (get-child-by-id motivation-template
                                    "motivation_x_add_attractor")))

(expect (instance? FlowPane
                   (get-child-by-id motivation-template
                                    "motivation_x_attractors")))

(expect (instance? Button
                   (get-child-by-id motivation-template
                                    "motivation_x_attractor")))

(expect (instance? BubbleChart
                   (get-child-by-id editor-template
                                    "attractors_chart")))

(expect (instance? BubbleChart
                   (get-child-by-id editor-template
                                    "pad_chart")))

(expect (instance? ScrollPane
                   (get-child-by-id editor-template
                                    "motivations-scroller")))

(expect (instance? TextArea
                   (get-child-by-id editor-template
                                    "motivations_text")))

(expect (instance? Button
                   (get-child-by-id editor-template
                                    "reload_button")))
