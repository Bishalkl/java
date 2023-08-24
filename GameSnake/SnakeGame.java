package GameSnake;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class SnakeGame {
    public static final int WIDTH = 20;
    public static final int HEIGHT = 10;

    private ArrayList<Point> snake = new ArrayList<>();
    private Point food;
    private boolean isGameOver = false;
    private char[][] board = new char[HEIGHT][WIDTH];
    private int dx = 1;
    private int dy = 0;

    public SnakeGame() {
        initBoard();
        spawnFood();
        snake.add(new Point(0, 0));
    }

    public void initBoard() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public void spawnFood() {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(WIDTH);
            y = rand.nextInt(HEIGHT);
        } while (board[y][x] != ' ');
        food = new Point(x, y);
    }

    public void update() {
        Point newHead = new Point(snake.get(0).x + dx, snake.get(0).y + dy);

        if (newHead.x < 0 || newHead.x >= WIDTH || newHead.y < 0 || newHead.y >= HEIGHT) {
            isGameOver = true;
            return;
        }

        if (snake.contains(newHead)) {
            isGameOver = true;
            return;
        }

        snake.add(0, newHead);

        if (newHead.x == food.x && newHead.y == food.y) {
            spawnFood();
        } else {
            Point tail = snake.remove(snake.size() - 1);
            board[tail.y][tail.x] = ' ';
        }

        board[newHead.y][newHead.x] = 'O';
    }

    public void render() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SnakeGame game = new SnakeGame();

        while (!game.isGameOver) {
            game.update();
            game.render();

            System.out.print("Enter direction (W/A/S/D): ");
            char input = scanner.next().charAt(0);
            
            switch (input) {
                case 'w':
                case 'W':
                    game.dx = 0;
                    game.dy = -1;
                    break;
                case 'a':
                case 'A':
                    game.dx = -1;
                    game.dy = 0;
                    break;
                case 's':
                case 'S':
                    game.dx = 0;
                    game.dy = 1;
                    break;
                case 'd':
                case 'D':
                    game.dx = 1;
                    game.dy = 0;
                    break;
            }
        }

        System.out.println("Game Over!");
        scanner.close();
    }
}
