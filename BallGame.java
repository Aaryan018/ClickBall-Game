import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
 *This javaFX application has a central graphics pane and buttons below it. It is click game in which
 *a ball appears randomly within 5 seconds. If the user clicks the ball, the hit count increases by one and the ball 
 *disappears and appears randomly again within 5 seconds.Each time the ball is clicked or missed, its speed 
 *increases the next time it comes. If the user is unable to click the ball, miss count
 *increases by one. When the miss count reaches 5, the game stops and the Game Over message pops up.
 *Also, there are 2 buttons which pauses the game and resets the game. A ball cannot be clicked when the game is paused.
 *The reset button resets the game and everything goes back to their initial value.
 *@author: Aaryan Gupta
 */


public class BallGame extends Application {
	private Pane graphicsPane;
	private Rectangle background;
	private Text resultLine;
	
	private Text GameOver;
	
	private Ellipse Ball;
	
	private int missCount;
	private int hitCount;
	
	private double xVelocity;
	
	private PassingBallAnimation animation;
	
	private Button reset;
	private Button pause;
	
	private double x;
	private double y;
	
	private boolean pauseCheck;
	
	
	@Override
	public void start(Stage primaryStage) {
		
		graphicsPane = new Pane();
		
		pauseCheck = false;
		
		background = new Rectangle(0, 0, 500, 500);
		background.setFill(Color.BLACK);
				
		resultLine = new Text(10, 20, "hits: " + 0 + " " + "misses: " + 0);
		resultLine.setFill(Color.WHITE);
		
		GameOver = new Text(60, 250, "GAME OVER!");
		GameOver.setFill(Color.WHITE);
		GameOver.setVisible(false);
		GameOver.setFont(Font.font("Arial", FontWeight.BOLD, 60));
		
		graphicsPane.getChildren().addAll(background);
		
		
		
		reset = new Button("reset");
		ResetGame rg = new ResetGame();
		reset.setOnAction(rg);
		
		pause = new Button("pause");
		PauseGame pg = new PauseGame();
		pause.setOnAction(pg);
		
		
		HBox gameButtons = new HBox(pause, reset);
		gameButtons.setAlignment(Pos.CENTER_RIGHT);
		
		x = (Math.random() * 300) - 300;
		y = (Math.random() * 390) + 60;
		Ball = new Ellipse(x, y, 50, 50);
		Ball.setFill(Color.WHITE);
			
		animation = new PassingBallAnimation();
		getBallStarted();
		
		Ball.setOnMousePressed(new BallClicked());
		
		graphicsPane.getChildren().addAll(resultLine, Ball, GameOver);
		
		BorderPane root = new BorderPane();
		
		root.setPrefWidth(500);
		root.setCenter(graphicsPane);
		root.setBottom(gameButtons);
		
		
		Scene scene = new Scene(root);
		primaryStage.setTitle("FX Clicking Game");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	private class PassingBallAnimation extends AnimationTimer{
		
		@Override
		public void handle(long arg0) {
			x = Ball.getCenterX();
			y = Ball.getCenterY();
			
			if(x + xVelocity > 550) {
				if(missCount == 4) {
					animation.stop();
					missCount++;
					resultLine.setText("hits: " + hitCount + " " + "misses: " + missCount);
					GameOver.setVisible(true);
					pauseCheck = true;
				}
				else {
					missCount++;
					xVelocity = xVelocity + 0.5;
				
					resultLine.setText("hits: " + hitCount + " " + "misses: " + missCount);
				
					x = (Math.random() * 300 * xVelocity) - (300 * xVelocity);
					y = (Math.random() * 390) + 60;
					Ball.setCenterX(x);
					Ball.setCenterY(y);
				}
			}
			else {
				x = x + xVelocity;
				Ball.setCenterX(x);
			}
			
		}
		
	}
	
	private class ResetGame implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e) {
			xVelocity = 0;
			animation.stop();
			getBallStarted();
			
		}
	}
	
	private class PauseGame implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e) {
			if(pauseCheck != true) {
				animation.stop();
				pauseCheck = true;
			}
			else {
				pauseCheck = false;
				animation.start();
			}
		}
	}
	
	public void getBallStarted() {		
		
		hitCount = 0;
		missCount = 0;
		xVelocity = 1;
		GameOver.setVisible(false);
		
		pauseCheck = false;
		
		resultLine.setText("hits: " + hitCount + " " + "misses: " + missCount);
		
		x = (Math.random() * 300) - 300;
		y = (Math.random() * 390) + 60;
		
		Ball.setCenterX(x);
		Ball.setCenterY(y);
		
		
		animation.start();		
	}
	
	private class BallClicked implements EventHandler<MouseEvent>{
		@Override
		public void handle(MouseEvent e) {
						
			if(pauseCheck == false) {
				hitCount++;
				x = (Math.random() * 300) - 300;
				y = (Math.random() * 390) + 60;
				
				Ball.setCenterX(x);
				Ball.setCenterY(y);
				
				xVelocity = xVelocity + 0.5;
				resultLine.setText("hits: " + hitCount + " " + "misses: " + missCount);
			}
			
			
		}
	}
	
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	
	
}


