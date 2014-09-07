(ns minesweeper-j.core
  (:require [play-clj.core :refer :all]
            [play-clj.ui :refer :all]
            [play-clj.g2d :refer :all]
            [minesweeper-j.mine :refer [make-board]]))

(def text-to-texture-map
;;   {:0 (list "cloneskin.png" :set-region 0 0 15 15)
;;    :1 (list "cloneskin.png" :set-region 15 0 15 15)
;;    :2 (list "cloneskin.png" :set-region 31 0 15 15)
;;    :3 (list "cloneskin.png" :set-region 47 0 15 15)
;;    :4 (list "cloneskin.png" :set-region 63 0 15 15)
;;    :5 (list "cloneskin.png" :set-region 79 0 15 15)
;;    :6 (list "cloneskin.png" :set-region 95 0 15 15)
;;    :7 (list "cloneskin.png" :set-region 111 0 15 15)
;;    :8 (list "cloneskin.png" :set-region 127 0 15 15)}
  {}
  )

(defn make-test-entities
  []
;;   (->> (map (comp keyword str) (range 9))
;;     (map #(% text-to-texture-map))
;;     (map #(apply texture %)))
  [(assoc (texture "cloneskin.png" :set-region 0 0 15 15)
     :x 10 :y 200)
   (assoc (texture "cloneskin.png" :set-region 15 0 15 15)
     :x 25 :y 200)
   (assoc (texture "cloneskin.png" :set-region 31 0 15 15)
     :x 41 :y 200)
   (assoc (texture "cloneskin.png" :set-region 47 0 15 15)
     :x 57 :y 200)
   (assoc (texture "cloneskin.png" :set-region 63 0 15 15)
     :x 73 :y 200)
   (assoc (texture "cloneskin.png" :set-region 79 0 15 15)
     :x 89 :y 200)
   (assoc (texture "cloneskin.png" :set-region 95 0 15 15)
     :x 105 :y 200)
   (assoc (texture "cloneskin.png" :set-region 111 0 15 15)
     :x 121 :y 200)
   (assoc (texture "cloneskin.png" :set-region 127 0 15 15)
     :x 137 :y 200)])

; The whole texture is 144x122 px
; Each cell is 15x15 px

(defn on-show
  [screen entities]
  (update! screen :renderer (stage))
  (-> entities
      (conj {:type :board
             :board (make-board 9 9 10)})
      (conj (assoc (texture "cloneskin.png"
                            :set-region 15 0 15 15)
              :x 50
              :y 50
              :width 15
              :height 15
              ))
      (conj (label "Hello world!" (color :white)))))

(defn on-render
  [screen entities]
  (clear!)
  (let [board-entity (first (filter #(= :board (:type %)) entities))
        entities (into entities (make-test-entities))
        ]

    (render! screen entities
;;              (conj entities
;;                           (texture "cloneskin.png" :set-region 0 0 15 15)
;;                           (texture "cloneskin.png" :set-region 15 0 15 15)
;;                           (texture "cloneskin.png" :set-region 31 0 15 15)
;;                           )
             )))

(defscreen main-screen
  :on-show on-show
  :on-render on-render)

(defgame minesweeper-j
  :on-create
  (fn [this]
    (set-screen! this main-screen)))
