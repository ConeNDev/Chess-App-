package com.chess.engine.board;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {
    protected final int tileCordinate;

    private static final Map<Integer,EmptyTile> EMPTY_TILES_CACHE=createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer,EmptyTile> emptyTileMap=new HashMap<>();

        for (int i=0;i<BoardUtils.NUM_TILES;i++){
            emptyTileMap.put(i,new EmptyTile(i));
        }

        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final int tileCordinate,final Piece piece){
        return piece!=null ? new OccupiedTile(tileCordinate,piece):EMPTY_TILES_CACHE.get(tileCordinate);
    }

    private Tile(final int tileCordinate){
        this.tileCordinate=tileCordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public int getTileCoordinate() {
        return this.tileCordinate;
    }

    public static final class EmptyTile extends Tile {

        private EmptyTile(final int cordinate){
            super(cordinate);
        }
        @Override
        public String toString(){
            return "-";
        }

        @Override
        public Piece getPiece() {
            return null;
        }
        @Override
        public boolean isTileOccupied() {
            return false;
        }
    }

    public static final class OccupiedTile extends Tile {
       private final Piece pieceOnTile;

        private OccupiedTile(int tileCordinate, Piece pieceOnTile){
            super(tileCordinate);
            this.pieceOnTile=pieceOnTile;
        }
        @Override
        public String toString(){
            return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :getPiece().toString();
        }
        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return pieceOnTile;
        }
    }
}
