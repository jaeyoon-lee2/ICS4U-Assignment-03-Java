/*
* This program solves the text maze.
*
* @author  Jay Lee
* @version 1.0
* @since   2021-05-22
*/
// package ca.mths.assignment.assignment03.java.solveMaze;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public final class SolveMaze {
    private SolveMaze() {
        // Prevent instantiation
        // Optional: throw an exception e.g. AssertionError
        // if this ever *is* called
        throw new IllegalStateException("Cannot be instantiated");
    }

    /** to check the maze is solved.*/
    private static boolean isSolved = false;

    /**
    * This method find direction to solve the maze.
    * @param point - [row, col]
    * @param direction - [[row,col]...[row, col]]
    * @param maze
    * @return direction - [[row,col]...[row, col]]
    */
    public static List<List<Integer>> findDirection(final ArrayList<Integer>
              point, final List<List<Integer>> direction, final char[][] maze) {
        List<Character> blockedSymbols = Arrays.asList('#', 'S', '+');
        ArrayList<Integer> newPoint = new ArrayList<Integer>();
        int row = point.get(0);
        int col = point.get(1);
        for (int counter = 0; counter < 2 * 2; counter++) {
            int newRow = row;
            int newCol = col;
            if (!isSolved) {
                if (counter == 0 && row - 1 >= 0
                    && !(blockedSymbols.contains(maze[col][row - 1]))) {
                    newRow = row - 1;
                } else if (counter == 1 && row + 1 < maze[0].length
                           && !(blockedSymbols.contains(maze[col][row + 1]))) {
                    newRow = row + 1;
                } else if (counter == 2 && col - 1 >= 0
                           && !(blockedSymbols.contains(maze[col - 1][row]))) {
                    newCol = col - 1;
                } else if (counter == 2 + 1 && col + 1 < maze.length
                           && !(blockedSymbols.contains(maze[col + 1][row]))) {
                    newCol = col + 1;
                }
                if (maze[newCol][newRow] == '.' && !isSolved) {
                    point.set(0, newRow);
                    point.set(1, newCol);
                    newPoint = new ArrayList<Integer>(point);
                    maze[newCol][newRow] = '+';
                    direction.add(newPoint);
                    direction = findDirection(point, direction, maze);
                    if (!isSolved) {
                        direction.remove(direction.size() - 1);
                    }
                    maze[newCol][newRow] = '.';
                    point.set(0, row);
                    point.set(1, col);
                } else if (maze[newCol][newRow] == 'G') {
                    isSolved = true;
                    break;
               }
            }
        }
        return direction;
    }

    /**
    * This method find start point 'S' from the maze.
    * @param twoDMaze
    * @return startPoint - [row, col]
    */
    public static ArrayList<Integer> findStartPoint(final char[][] twoDMaze) {
        ArrayList<Integer> startPoint = new ArrayList<Integer>();
        for (int col = 0; col < twoDMaze.length; col++) {
            for (int row = 0; row < twoDMaze[col].length; row++) {
                if (twoDMaze[col][row] == 'S') {
                    startPoint.add(row);
                    startPoint.add(col);
                    return startPoint;
                }
            }
        }
        return startPoint;
    }

    /**
    * This method append 2d direction to maze.
    * @param direction - [[row,col]...[row, col]]
    * @param maze
    * @return maze
    */
    public static char[][] appendDirection(final List<List<Integer>> direction,
                                           final char[][] maze) {
        for (int pathIndex = 0; pathIndex < direction.size(); pathIndex++) {
            List<Integer> path = direction.get(pathIndex);
            int row = path.get(0);
            int col = path.get(1);
            maze[col][row] = '+';
        }
        return maze;
    }

    /**
    * This method converts 1d string arraylist to 2d character array.
    * @param oneDArrayList
    * @param size - [row, col]
    * @return twoDArray
    */
    public static char[][] convert1Dto2DArray(final ArrayList<String>
                                              oneDArrayList, final int[] size) {
        int row = size[0];
        int col = size[1];
        char[][] twoDArray = new char[col][row];
        for (int colIndex = 0; colIndex < size[1]; colIndex++) {
            for (int rowIndex = 0; rowIndex < size[0]; rowIndex++) {
                twoDArray[colIndex][rowIndex] = oneDArrayList.get(
                                                    colIndex).charAt(rowIndex);
            }
        }
        return twoDArray;
    }

    /**
    * This method prints 2D Maze array.
    * @param twoDMaze
    */
    public static void showMaze(final char[][] twoDMaze) {
        for (int col = 0; col < twoDMaze.length; col++) {
            for (int row = 0; row < twoDMaze[col].length; row++) {
                System.out.print(twoDMaze[col][row]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    /**
    * This method gets original maze, converts 1D to 2D maze,
    * finds start point from the maze and direction to solve the maze and
    * shows the solved maze to user.
    * @param args
    */
    public static void main(final String[] args) {
        Scanner myObj = new Scanner(System.in);
        ArrayList<String> oneDMazeArrayList = new ArrayList<String>();

        int col = 0;
        int row = 0;
        System.out.println("S:Start\n#: blocked\n.: open\n+: path\nG: goal\n");
        System.out.print("MAZE:\n");
        while (myObj.hasNextLine()) {
            String eachRow = myObj.nextLine();
            if (eachRow == null || eachRow.isEmpty()) {
                break;
            }
            row = eachRow.length();
            oneDMazeArrayList.add(eachRow);
            col++;
        }
        int[] size = {row, col};
        char[][] twoDMaze = convert1Dto2DArray(oneDMazeArrayList, size);
        ArrayList<Integer> startPoint = findStartPoint(twoDMaze);
        List<List<Integer>> direction = new ArrayList<List<Integer>>();
        direction = findDirection(startPoint, direction, twoDMaze);
        twoDMaze = appendDirection(direction, twoDMaze);

        if (isSolved) {
            System.out.println("Solved Maze:\n");
            showMaze(twoDMaze);
        } else {
            System.out.println("There is no direction to solve the maze!");
        }

        System.out.println("\nDone.");
    }
}
