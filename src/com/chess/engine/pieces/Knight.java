package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class Knight extends Piece{
    private final static int[] CANDIDATE_MOVE_COORDINATES ={-17,-15,-10,-6,6,10,15,17};

    public Knight(final Alliance pieceAlliance ,final int piecePosition) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves=new ArrayList<>();

        for (final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATES){
           final int candidateDestinationCoordinate=this.piecePosition+currentCandidateOffset;
            if(BoardUtils.isValidTileCordinate(candidateDestinationCoordinate)){
                if(isFisrtColumnExlusion(this.piecePosition,currentCandidateOffset)||
                        isSecondColumnExlusion(this.piecePosition,currentCandidateOffset)||
                        isSeventhColumnExlusion(this.piecePosition,currentCandidateOffset)||
                        isEightColumnExlusion(this.piecePosition,currentCandidateOffset)){
                    continue;
                }
                final Tile candidateDestinationTile=board.getTile(candidateDestinationCoordinate);
                if(!candidateDestinationTile.isTileOccupied()){
                    legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));
                }
                else{
                    final Piece pieceAtDestination=candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance=pieceAtDestination.getPieceAlliance();
                    if(this.pieceAlliance!=pieceAlliance){
                        legalMoves.add(new AttackMove(board,this,candidateDestinationCoordinate,pieceAtDestination));
                    }
                }
            }

        }
        return ImmutableList.copyOf(legalMoves);
    }
    @Override
    public String toString(){
        return PieceType.KNIGHT.toString();
    }

    private static boolean isFisrtColumnExlusion(final int currentPosition,final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset==-10 || candidateOffset== 6 ||
                candidateOffset==15);
    }

    private static boolean isSecondColumnExlusion(final int currentPosition,final int candidateOffset){
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset==-10 || candidateOffset==6);
    }

    private static boolean isSeventhColumnExlusion(final int currentPosition,final int candidateOffset){
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset==-6 || candidateOffset==10);
    }
    private static boolean isEightColumnExlusion(final int currentPosition,final int candidateOffset){
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset==-15 || candidateOffset==-6||
                candidateOffset==10||candidateOffset==17);
    }
}
