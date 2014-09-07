(ns minesweeper-j.core
  (:require [play-clj.core :refer :all]
            [play-clj.ui :refer :all]
            [play-clj.g2d :refer :all]
            [minesweeper-j.mine :refer [make-board]]))



(defn cell->texture
  [cell]
  (condp = cell
    0 (texture "cloneskin.png" :set-region 0 0 15 15)
    1 (texture "cloneskin.png" :set-region 15 0 15 15)
    2 (texture "cloneskin.png" :set-region 31 0 15 15)
    3 (texture "cloneskin.png" :set-region 47 0 15 15)
    4 (texture "cloneskin.png" :set-region 63 0 15 15)
    5 (texture "cloneskin.png" :set-region 79 0 15 15)
    6 (texture "cloneskin.png" :set-region 95 0 15 15)
    7 (texture "cloneskin.png" :set-region 111 0 15 15)
    8 (texture "cloneskin.png" :set-region 127 0 15 15)
    "M" (texture "cloneskin.png" :set-region 31 15 15 15)
    "X" (texture "cloneskin.png" :set-region 63 15 15 15)
    "B" (texture "cloneskin.png" :set-region 79 15 15 15)
    "H" (texture "cloneskin.png" :set-region 0 15 15 15)
    (label "Unexpected input to cell->texture" (color :white))))

(defn make-test-entities
  []
;;   (->> (map (comp keyword str) (range 9))
;;     (map #(% text-to-texture-map))
;;     (map #(apply texture %)))
  [(assoc (cell->texture 0) :x 10 :y 300 :cell true)
;;    (assoc (cell->texture 1) :x 25 :y 315 :cell true)
;;    (assoc (cell->texture 2) :x 41 :y 331 :cell true)
;;    (assoc (cell->texture 3) :x 57 :y 347 :cell true)
;;    (assoc (cell->texture 4) :x 73 :y 363 :cell true)
;;    (assoc (cell->texture 5) :x 89 :y 379 :cell true)
;;    (assoc (cell->texture 6) :x 105 :y 395 :cell true)
;;    (assoc (cell->texture 7) :x 121 :y 411 :cell true)
;;    (assoc (cell->texture 8) :x 137 :y 427 :cell true)
;;    (assoc (cell->texture "M") :x 153 :y 443 :cell true)
;;    (assoc (cell->texture "X") :x 169 :y 459 :cell true)
;;    (assoc (cell->texture "B") :x 185 :y 475 :cell true)
;;    (assoc (cell->texture "H") :x 201 :y 491 :cell true)
   ])

; The whole texture is 144x122 px
; Each cell is 15x15 px

; TODO: Add entities for the num-rows*num-cols cells
; TODO: Add a mask->entities function (same as board->entities?)

(defn on-show
  [screen entities]
  (update! screen :renderer (stage) :camera (orthographic))
  (-> entities
      (conj {:type :board
             :board (make-board 9 9 10)})
      (conj {:type :mask
             :mask (make-mask 9 9)})
      (conj (assoc (texture "cloneskin.png"
                            :set-region 15 0 15 15)
              :x 50
              :y 50
              :width 15
              :height 15
              ))
      (conj (assoc (cell->texture 0) :x 10 :y 300 :cell true)) ; 0 cell
      ;(conj (label "Hello world!" (color :white)))
      ))

(defn on-render
  [screen entities]
  (clear!)
  (let [board-entity (first (filter #(= :board (:type %)) entities))
        cell (first (filter #(true? (:cell %)) entities))
        ;_ (println "cell: " cell)
        ]
    (texture! cell :set-region-x (* 15 (rand-int 8)))
    (render! screen entities)))

(defscreen main-screen
  :on-show on-show
  :on-render on-render
  :on-resize
  (fn [screen entities]
    (height! screen 600)
    (width! screen 800)
    nil))

(defscreen text-screen
  :on-show
  (fn [screen entities]
    (update! screen :camera (orthographic) :renderer (stage))
    (assoc (label "0" (color :white))
      :id :fps
      :x 5))
  :on-render
  (fn [screen entities]
    (->> (for [entity entities]
           (case (:id entity)
             :fps (doto entity (label! :set-text (str "FPS: " (game :fps))))
             entity))
         (render! screen)))
  :on-resize
  (fn [screen entities]
    (height! screen 300)))

(defgame minesweeper-j
  :on-create
  (fn [this]
    (set-screen! this main-screen text-screen)))
