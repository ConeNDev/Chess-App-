package com.chess.engine.player.ai;

import com.chess.engine.board.Board;

public interface BoardEvaluator {
    // sto veci pozitivan broj vrati to znaci da beli dobija sto veci negativan znaci crni dobija
    int evaluate(Board board,int depth);

}
