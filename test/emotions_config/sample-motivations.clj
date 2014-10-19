[{:id :phys-anger :name "anger" :layer :physical
  :valence -0.7 :arousal 0.7
  :desire 0.0 :decay-rate -0.02 :max-delta 1.0
  :learning-window 30000
  :description "gets irritated if frustrated in achieving an action"
  :attractors [{:fn "proportional-attractor"
                :valence -0.75
                :arousal 0.75
                :scale 2.0}]}
 {:id :saf-boredom :name "boredom" :layer :safety
  :valence -0.1 :arousal -0.4
  :desire 0.0 :decay-rate 0.01 :max-delta 0.3
  :learning-window (* 5 60 1000)
  :description "proactively look for something if insufficient stimulus"
  :attractors [{:fn "proportional-attractor"
                :valence -0.25
                :arousal -0.75
                :scale 1.0}]}
 {:id :saf-delight :name "delight" :layer :safety
  :valence 0.7 :arousal 0.7
  :desire 0.5 :decay-rate 0.0 :max-delta 0.8
  :learning-window 60000
  :description "try and seek out things (i.e. friends) with positive associations"
  :attractors [{:fn "inverse-attractor"
                :valence 1.0
                :arousal 0.75
                :scale 1.0}
               {:fn "proportional-attractor"
                :valence -0.75
                :arousal -0.75
                :scale 1.0}]}
 {:id :phys-fear :name "fear" :layer :physical
  :valence -0.9 :arousal 0.2
  :desire 0.0 :decay-rate -0.2 :max-delta 1.0
  :learning-window 30000
  :description "try and avoid dangerous situations / obstacles / percepts with negative associations"
  :attractors [{:fn "proportional-attractor"
                :valence -1.0
                :arousal 0.0
                :scale 1.0}]}
 {:id :phys-hunger :name "hunger" :layer :physical
  :valence 0.0 :arousal 0.5
  :desire 0.0 :decay-rate 0.0 :max-delta 1.0
  :learning-window (* 2 60 60 1000)
  :description "monitor battery level"
  :attractors []}
 {:id :soc-sociable :name "sociable" :layer :social
  :valence -0.6 :arousal -0.6
  :desire 0.0 :decay-rate 0.005 :max-delta 0.3
  :learning-window (* 60 60 1000)
  :description "try to find people to interact with"
  :attractors [{:fn "proportional-attractor"
                :valence -0.75
                :arousal -0.75
                :scale 0.5}]}
 ]
