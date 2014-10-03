(ns tic-tac-toc)

; extract rows, cols and diags
(defn row [board row-nr]
  (take 3 (drop (* 3 row-nr) board)))

(defn col [board col-nr]
  (let [[x y z] (range col-nr 9 3)]
    [(nth board x) (nth board y) (nth board z)]))

(defn ldiag [board] [(nth board 0) (nth board 4) (nth board 8)])
(defn rdiag [board] [(nth board 2) (nth board 4) (nth board 6)])

; game logic
(def empty-cell -1)

(defn has-won? [board player]
  (not-every? #(= false %) (conj (for [n (range 3) gen [row col]] (every? #(= player %) (gen board n)))
                                 (every? #(= player %) (ldiag board))
                                 (every? #(= player %) (rdiag board)))))

(defn board-full? [board]
  (= 0 (count (filter #(= empty-cell %) board))))

(defn gameover? [board]
  (not-every? #(= false %) [(has-won? board 0) (has-won? board 1) (board-full? board)]))

; io/ board manipulation
(defn read-move []
  (try
    (let [line (read-line)
          [x y] (clojure.string/split line #"\s|,")]
      (try)
      [(Integer/parseInt x) (Integer/parseInt y)])
    (catch NumberFormatException e (read-move))))

(defn write-move [board player x y]
  (assoc (into [] board) (+ x (* y 3)) player))

(defn print-board [board]
  (doall (for [r (range 0 3)] (println (row board r)))))

(defn valid-move? [board x y]
  (try
    (= empty-cell (nth board (+ x (* y 3))))
    (catch Exception e false)))

; play!
; 0/0 1/0 2/0
; 0/1 1/1 2/1
; 0/2 1/2 2/2
(defn play []
  (let [players (take 10 (cycle (shuffle [0 1])))
        board (repeat 9 -1)]

    (loop [player (first players)
         upcoming (rest players)
         current-board board]

      ; print current status
      (println "Player: " player)
      (print-board current-board)

      ; ask for move
      (let [[x y] (read-move)]
        (if (valid-move? current-board x y)

          (do
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

                (recur (first upcoming) (rest upcoming) next-board))))
          (recur player upcoming current-board))))))
