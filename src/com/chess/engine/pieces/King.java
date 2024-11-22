package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATE={-9,-8,-7,-1,1,7,8,9};

    public King(final Alliance pieceAlliance,final int piecePosition) {
        super(PieceType.KING,piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves=new ArrayList<>();

        for(final int currentCandidateOffset:CANDIDATE_MOVE_COORDINATE) {
            final int candidateDestinationCoordinate=this.piecePosition+currentCandidateOffset;
            if(isFisrtColumnExlusion(this.piecePosition,currentCandidateOffset)
                    || isEightColumnExlusion(this.piecePosition,currentCandidateOffset)){
                continue;
            }
            if(BoardUtils.isValidTileCordinate(candidateDestinationCoordinate)){
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
    public King movePiece(final Move move) {
        return new King(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString(){
        return PieceType.KING.toString();
    }
    private static boolean isFisrtColumnExlusion(final int currentPosition,final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset==-1 || candidateOffset==7);
    }

    private static boolean isEightColumnExlusion(final int currentPosition,final int candidateOffset){
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset==9 || candidateOffset==1 || candidateOffset==- 7);
    }
}
