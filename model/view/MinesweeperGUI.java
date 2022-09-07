package model.view;
import java.util.Random;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.image.ImageView;
import javafx.scene.image.*;
import javafx.scene.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;




public class MinesweeperGUI extends Application{
    private final int row = 4;
    private final int column = 4;
    private final int bombs = 4;
    private int covered = 0;

    public int getBomb(){return this.bombs;}
    public int getRow(){return this.row;}
    public int getCol(){return this.column;}
    public int getCovered(){return this.covered;}

    @Override
    public void start(Stage start) throws Exception {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label bombCount = new Label("Bombs: " + this.getBomb());
        GridPane.setConstraints(bombCount, 6, 1);
        bombCount.setTextFill(Color.GREEN);
        bombCount.setFont(Font.font("Georgia", 16));
        bombCount.setMinSize(60, 60);
        GridPane.setConstraints(bombCount, 6, 2);

        Button restart = new Button("Restart");
        restart.setTextFill(Color.RED);
        restart.setFont(Font.font("Georgia", 16));
        restart.setMinSize(60, 60);
        GridPane.setConstraints(restart, 6, 0);

        Label score = new Label("Score: "+ getCovered());
        GridPane.setConstraints(score, 6, 1);
        score.setTextFill(Color.BLUE);
        score.setFont(Font.font("Georgia", 16));
        score.setMinSize(60, 60);
        GridPane.setConstraints(score, 6, 1);

        Label GameState = new Label("Game State: In Progress");
        score.setTextFill(Color.PURPLE);
        score.setFont(Font.font("Georgia", 16));
        score.setMinSize(60, 60);
        GridPane.setConstraints(GameState, 6, 3);

        restart.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t){
                score.setText("Score: 0");
                GameState.setText("Game State: In Progress");

                
            }});

        for (int x=0;x<this.getRow();x++){
            for (int y=0;y<this.getCol();y++){
                Button b = new Button();
                ImageView aview = new ImageView(new Image("media/images/coveredtile.png"));
                aview.setFitHeight(30);
                aview.setFitWidth(30);
                b.setGraphic(aview);
                GridPane.setConstraints(b, x, y);
                grid.getChildren().add(b);
                b.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t){
                        ImageView image = new ImageView("media/images/flag24.png");
                        b.setGraphic(image);
                        covered++;
                        score.setText("Score: "+getCovered());
                    }
                });
        }}
        Random random = new Random(0);
        int rCol;
        int rRow;
        for (int r = 0;r<bombs;r++){
            rCol = random.nextInt(column);
            rRow = random.nextInt(row);
            Button b = new Button();
                ImageView aview = new ImageView(new Image("media/images/coveredtile.png"));
                aview.setFitHeight(30);
                aview.setFitWidth(30);
                b.setGraphic(aview);
                GridPane.setConstraints(b, rRow, rCol);
                grid.getChildren().add(b);
                b.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t){
                        ImageView image = new ImageView("media/images/mine24.png");
                        b.setGraphic(image);
                        covered = 0;
                        GameState.setText("Game State: Lost");
                        return;
                    }
                });

        }
                

        grid.getChildren().addAll(score, restart, bombCount, GameState);
        Scene scene = new Scene(grid, 1920, 1080); //to make the application open fullscreened.
        scene.setFill(Color.CHOCOLATE);
        start.setScene(scene);
        start.setTitle("Minesweeper game");
        start.setResizable(false);
        start.show();}

    public static void main(String[] args) {
        launch(args);
    }
    
}
