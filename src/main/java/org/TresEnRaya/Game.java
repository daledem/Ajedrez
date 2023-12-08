package org.TresEnRaya;

import java.util.Scanner;

public class Game {

    private Square [][] table;
    private String turn;

    public Game(){
        this.table = new Square[3][3];

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                this.table[i][j] = new Square();
            }
        }

        this.turn = "0";
    }

    public String getTurn(){
        return this.turn;
    }

    public Square[][] getTable(){
        return this.table;
    }

    public void setTable(Square [][] table){
        this.table = table;
    }

    public boolean markSquare(int colum, int row){
        boolean marked = false;

        if (0 <= colum && 0 <= row && colum < 3 && row < 3 && this.table[colum][row].isEmpty()){
            this.table[colum][row].mark(turn);
            marked = true;
        }

        return marked;
    }

    public boolean finish(){
        boolean finish = false;

        Square square = new Square(this.turn);
        int i = 0;

        while (i < 3 && !finish) {
            if (this.table[i][i].equals(square)) {
                finish = this.checkLines(i);
            }
            i++;
        }

        return finish;
    }

    public void changeTurn(){
        if (this.turn.equals("X")){
            this.turn = "0";
        }else {
            this.turn = "X";
        }
    }

    public boolean checkLines(int columnAndRow){
        boolean correctLine = false;

        switch (columnAndRow){
            case 0:
                if (this.table[0][0].equals(this.table[1][1]) && this.table[0][0].equals(this.table[2][2])){
                    correctLine = true;
                } else if (this.table[0][0].equals(this.table[0][1]) && this.table[0][0].equals(this.table[0][2])) {
                    correctLine = true;
                } else if (this.table[0][0].equals(this.table[1][0]) && this.table[0][0].equals(this.table[2][0])) {
                    correctLine = true;
                }
                break;
            case 1:
                if (this.table[1][1].equals(this.table[0][1]) && this.table[1][1].equals(this.table[2][1])){
                    correctLine = true;
                } else if (this.table[1][1].equals(this.table[1][0]) && this.table[1][1].equals(this.table[1][2])) {
                    correctLine = true;
                } else if (this.table[1][1].equals(this.table[0][2]) && this.table[1][1].equals(this.table[2][0])) {
                    correctLine = true;
                }
                break;
            case 2:
                if (this.table[2][2].equals(this.table[2][1]) && this.table[2][2].equals(this.table[2][0])) {
                    correctLine = true;
                } else if (this.table[2][2].equals(this.table[1][2]) && this.table[2][2].equals(this.table[0][2])) {
                    correctLine = true;
                }
                break;
        }

        return correctLine;
    }

    public void show(){
        System.out.println("-------");

        for (int i = 0; i < 3; i++){
            System.out.println("|" + this.table[0][i].getType() + "|" + this.table[1][i].getType() + "|" + this.table[2][i].getType() + "|");
            System.out.println("-------");
        }
    }

    public static void play(){
        Game game = new Game();
        int column;
        int row;
        Scanner scanner = new Scanner(System.in);

        do {
            game.changeTurn();
            game.show();
            System.out.println("turn of " + game.getTurn());
            System.out.println("Introduce the column");
            column = scanner.nextInt() - 1;
            System.out.println("Introduce the row");
            row = scanner.nextInt() - 1;
            if(!game.markSquare(column,row)){
                System.out.println("That square is already taken");
            }
        }while (!game.finish());

        game.show();
        System.out.println("the winner is " + game.turn);
    }
}