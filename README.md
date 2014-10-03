# clojure-tic-tac-toe

a minimal tic tac toe clone written in clojure.

## why?

I'm currently learning clojure.

## how to play

First, run `lein repl` inside your terminal, then execute

```
user=> (load-file "./game.clj")
#'tic-tac-toc/play
user=> (tic-tac-toc/play (tic-tac-toc/gen-board))
Player:  1
(-1 -1 -1)
(-1 -1 -1)
(-1 -1 -1)
1 0
Move (x/y):  1 0
Player:  0
(-1 1 -1)
(-1 -1 -1)
(-1 -1 -1)
```

Enjoy :)