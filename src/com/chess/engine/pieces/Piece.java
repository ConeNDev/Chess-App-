package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove;
    private final int cacheHashCode;

    Piece(final PieceType pieceType,final int piecePosition,final Alliance pieceAlliance,final boolean isFirstMove){
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        this.isFirstMove = isFirstMove;
        this.cacheHashCode=computeHashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if(!(other instanceof Piece)){
            return false;
        }
        final Piece otherPiece=(Piece)other;
        return this.piecePosition==otherPiece.getPiecePosition() && this.pieceType==otherPiece.getPieceType() &&
                this.pieceAlliance==otherPiece.getPieceAlliance() && this.isFirstMove == otherPiece.isFirstMove();
    }
    @Override
    public int hashCode() {
        return this.cacheHashCode;
    }

    public int computeHashCode() {
        int result=this.pieceType.hashCode();
        result=31*result+this.pieceAlliance.hashCode();
        result=31*result+this.piecePosition;
        result=31*result+(this.isFirstMove?1:0);
        return result;
    }

    public int getPiecePosition(){
        return this.piecePosition;
    }
    public Alliance getPieceAlliance(){
        return this.pieceAlliance;
    }
    public boolean isFirstMove(){
        return this.isFirstMove;
    }
    public PieceType getPieceType(){
        return this.pieceType;
    }
    public int getPieceValue(){
        return this.pieceType.getPieceValue();
    }
    
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public abstract Piece movePiece(Move move);

    

    public enum PieceType{

        PAWN("P",100) {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("N",300) {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("B",300) {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK("R",500) {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        },
        QUEEN("Q",900) {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KING("K",10000) {
            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        };

        private String pieceName;
        private int pieceValue;

        PieceType(final String pieceName,final int pieceValue){
            this.pieceName=pieceName;
            this.pieceValue=pieceValue;
        }
        @Override
        public String toString(){
            return this.pieceName;
        }
        public int getPieceValue(){
            return this.pieceValue;
        }

        public abstract boolean isKing();

        public abstract boolean isRook();
    }
}
