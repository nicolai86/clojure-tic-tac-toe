(ns tic-tac-toc)

(def player-cycle
  (cycle (shuffle [0 1])))

; (0 1 2
;  3 4 5
;  6 7 8)
(defn gen-board [] (repeat 9 -1))

; extract rows, cols and diags
(defn row [board row-nr]
  "row-nr has to be less or equal to 2"
  (take 3 (drop (* 3 row-nr) board)))

(defn col [board col-nr]
  "col-nr has to be less or equal to 2"
  (let [[x y z] (range col-nr 9 3)]
    [(nth board x) (nth board y) (nth board z)]))

(defn ldiag [board]
  (let [[x y z] (range 0 9 4)]
    [(nth board x) (nth board y) (nth board z)]))

(defn rdiag [board]
  (let [[x y z] (range 2 8 2)]
    [(nth board x) (nth board y) (nth board z)]))

; game logic
(defn has-row? [board player]
  "checks if player has 3 hits in any row"
  (or (every? #(= player %) (row board 0)) (every? #(= player %) (row board 1)) (every? #(= player %) (row board 2))))

(defn has-col? [board player]
  "checks if player has 3 hits in any col"
  (or (every? #(= player %) (col board 0)) (every? #(= player %) (col board 1)) (every? #(= player %) (col board 2))))

(defn has-diag? [board player]
  "checks if player has 3 hits in any diagonal"
  (or (every? #(= player %) (ldiag board)) (every? #(= player %) (rdiag board))))

(defn has-won? [board player]
  (or (has-col? board player) (has-row? board player) (has-diag? board player)))

(defn board-full? [board]
  (= 0 (count (filter #(= -1 %) board))))

; TODO rewrite as comprehension
(defn gameover? [board]
  (or (has-won? board 0) (has-won? board 1) (board-full? board)))

; sideeffects
(defn read-move []
  (let [line (read-line)
        [x y] (clojure.string/split line #"\s|,")]
    [(Integer/parseInt x) (Integer/parseInt y)]))

(defn write-move [board player x y]
  (assoc (into [] board) (+ x (* y 3)) player))

(defn print-board [board]
  (do
    (println (row board 0))
    (println (row board 1))
    (println (row board 2))))

; play!
(defn play [board]
  (let [players (take 100 player-cycle)]
    (loop [player (first players)
         upcoming (rest players)
         current-board board]
    ; print current player
    (println "Player: " player)
    ; print the board
    (print-board current-board)

    ; ask for move
    (let [[x y] (read-move)]
      (println "Move (x/y): " x y)

      (let [next-board (write-move current-board player x y)]
        (if (gameover? next-board)
          (do
            (println "done:")
            (print-board next-board)
            (if (has-won? next-board 0)
              (println "player 0 won")
              (if (has-won? next-board 1)
                (println "player 1 won")
                (println "draw!"))))
          (recur (first upcoming) (rest upcoming) next-board))
        )))))
