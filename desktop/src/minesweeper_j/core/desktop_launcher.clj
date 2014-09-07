(ns minesweeper-j.core.desktop-launcher
  (:require [minesweeper-j.core :refer :all])
  (:import [com.badlogic.gdx.backends.lwjgl LwjglApplication]
           [org.lwjgl.input Keyboard])
  (:gen-class))

(defn -main
  []
  (LwjglApplication. minesweeper-j "minesweeper-j" 800 600)
  (Keyboard/enableRepeatEvents true))
