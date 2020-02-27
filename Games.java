import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;

import javax.swing.SwingConstants;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Games extends Application {
	int difficulty = 1;
	static int widthOfMinesweeper;
	static int heightOfMinesweeper;
	static Sweepy mineSweeperTiles[][];
	static boolean[][] isBomb;
	int clearSspacesMS;
	int livesForAll;
	boolean canStillPlay;
	static int howMany;
	static int flagCount;
	int totalTilesMS;
	int scoreForHM;
	char[] lettersOrUnders;
	int timesRan;
	static String nameOfUser;
	int labelSize = 30;
	int timeNumber;
	int ticTacToeOddEven = 1;
	char[] wrong;
	static int letterCount;
	GridPane sweep;
	String word;
	face smile;
	HBox mid;
	Label zero, bombsLeft, leftLeft;
	BorderPane miner;
	Label hidden;
	HBox side, counter;
	Timeline timeline;
	Label left, middle, right;
	int clicks;
	String game = "s";
	char lastGuess;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage theStage) {
		// LAUNCHER
		if (game.contentEquals("s")) {
			Region region1 = new Region();
			HBox.setHgrow(region1, Priority.ALWAYS);
			Region region2 = new Region();
			HBox.setHgrow(region2, Priority.ALWAYS);
			Region region3 = new Region();
			HBox.setHgrow(region3, Priority.ALWAYS);
			Region region4 = new Region();
			HBox.setHgrow(region4, Priority.ALWAYS);
			Region r5 = new Region();
			HBox.setHgrow(r5, Priority.ALWAYS);
			Region r6 = new Region();
			HBox.setHgrow(r6, Priority.ALWAYS);
			Region r7 = new Region();
			HBox.setHgrow(r7, Priority.ALWAYS);

			String ss = highscores(0);
			String sm = highscores(1);
			String sl = highscores(3);
			String hm = highscores(2);
			Label title = new Label("Pick a game to play.");
			title.setFont(new Font("Serif", 30));
			title.setText("Pick a game to play.");
			HBox tbox = new HBox(region1, title, region2);
			BorderPane options = new BorderPane();

			Button left = new Button("Minesweeper");
			left.setMinWidth(150);
			Label ldsc = new Label(
					"The objective of the game is to clear a rectangular board containing hidden \"mines\" or bombs without detonating any of them.");
			ldsc.setMaxWidth(150);
			ldsc.setWrapText(true);
			Label highs = new Label("\nThis is a single player game, but tracks highscores.\n\nHighscores are:\n E: "
					+ ss + "\nM: " + sm + "\nH: " + sl);
			highs.setStyle("-fx-font-weight: bold");
			highs.setMaxWidth(150);
			highs.setWrapText(true);

			Button middle = new Button("Tic Tac Toe");
			Label desc = new Label(
					"\nThis is a multiplayer game, it requires one other person to play a game against.");
			Label mdsc = new Label(
					"The player who succeeds in placing three of their marks in a horizontal, vertical, or diagonal row wins the game.");
			mdsc.setMaxWidth(150);
			mdsc.setWrapText(true);
			desc.setMaxWidth(150);
			desc.setWrapText(true);
			desc.setStyle("-fx-font-weight: bold");
			VBox ticTacToe = new VBox(middle, mdsc, desc);
			middle.setMinWidth(150);

			Button right = new Button("Hangman");
			right.setMinWidth(150);
			Label hang = new Label("The goal of the game is to guess letters that might be in the hidden word.");
			Label hanb = new Label("\nHangman is a guessing game for two or more players.");
			Label manhighs = new Label("\nThe highscore for this game is: \n" + hm);
			manhighs.setMaxWidth(150);
			manhighs.setWrapText(true);
			manhighs.setStyle("-fx-font-weight: bold");
			hanb.setMaxWidth(150);
			hanb.setWrapText(true);
			hanb.setStyle("-fx-font-weight:bold");
			hang.setMaxWidth(150);
			hang.setWrapText(true);
			VBox hangMan = new VBox(right, hang, hanb, manhighs);
			VBox mineSweeper = new VBox(left, ldsc, highs);
			HBox items = new HBox(mineSweeper, r5, ticTacToe, r6, hangMan);

			options.setTop(tbox);
			options.setCenter(items);
			options.setStyle("-fx-border-color: black; -fx-border-width: 5");
			options.setAlignment(items, Pos.CENTER);
			Scene scene = new Scene(options, Color.RED);
			theStage.setScene(scene);
			theStage.setTitle("Game Selector");
			theStage.setResizable(false);
			theStage.setWidth(600);
			theStage.setHeight(500);
			theStage.show();

			left.setOnAction(e -> {
				theStage.close();
				game = "m";
				restart(theStage);
			});

			middle.setOnAction(e -> {
				theStage.close();
				game = "t";
				restart(theStage);
			});
			right.setOnAction(e -> {
				game = "h";
				theStage.close();
				restart(theStage);
			});
			// MINESWEEPER
		} else if (game.equals("m")) {

			// EASY MINE
			if (difficulty == 1) {
				theStage.setWidth(346);
				theStage.setHeight(477);
				howMany = 10;
				flagCount = 10;
				widthOfMinesweeper = 8;
				heightOfMinesweeper = 8;

				// MEDIUM MINE
			} else if (difficulty == 2) {
				theStage.setWidth(666);
				theStage.setHeight(797);
				howMany = 40;
				flagCount = 40;
				widthOfMinesweeper = 16;
				heightOfMinesweeper = 16;

				// HARD MINE
			} else if (difficulty == 3) {
				theStage.setWidth(1306);
				theStage.setHeight(797);
				howMany = 99;
				flagCount = 99;
				widthOfMinesweeper = 32;
				heightOfMinesweeper = 16;
			}
			Scene scene;
			nameOfUser = "";
			mineSweeperTiles = new Sweepy[widthOfMinesweeper][heightOfMinesweeper];
			isBomb = new boolean[mineSweeperTiles.length][mineSweeperTiles[0].length];
			totalTilesMS = widthOfMinesweeper * heightOfMinesweeper;
			clicks = 0;
			clearSspacesMS = 0;
			timeNumber = 0;
			sweep = new GridPane();
			smile = new face();
			canStillPlay = true;
			timesRan = 0;
			mid = new HBox();
			zero = new Label();
			bombsLeft = new Label();
			leftLeft = new Label();
			counter = new HBox();
			left = new Label();
			middle = new Label();
			right = new Label();
			timeline = new Timeline();

			timeCounter(timeNumber, labelSize);
			counter = new HBox(left, middle, right);

			miner = new BorderPane();
			bombCounter(flagCount);
			for (int row = 0; row < isBomb.length; row++) {
				for (int col = 0; col < isBomb[row].length; col++) {
					isBomb[row][col] = false;

				}

			}
			boolean bad = false;
			int m = 0;
			while (m < howMany) {
				int frow = (int) (Math.random() * isBomb.length);
				int fcol = (int) (Math.random() * isBomb[0].length);
				if (isBomb[frow][fcol] == false) {
					m++;
					isBomb[frow][fcol] = true;
				} else {
					while (bad == false) {
						int trow = (int) (Math.random() * isBomb.length);
						int tcol = (int) (Math.random() * isBomb[0].length);
						if (trow != frow && fcol != tcol && isBomb[trow][tcol] != true) {
							isBomb[trow][tcol] = true;
							bad = true;

							m++;
						} else {
							bad = false;
						}
					}
				}
			}
			for (int row = 0; row < mineSweeperTiles.length; row++) {
				for (int col = 0; col < mineSweeperTiles[row].length; col++) {
					mineSweeperTiles[row][col] = new Sweepy(row, col);
					Sweepy b = mineSweeperTiles[row][col];

					b.setStyle("-fx-border-color: #787878;" + "-fx-border-width: 10");
					b.setOnMousePressed(x -> {
						if (timesRan == 0) {
							begin();
							timesRan++;
						}

						MouseButton click = x.getButton();
						if (canStillPlay) {
							smile.setGraphic(smile.clicked);
							if (b.state == 0 && click != MouseButton.SECONDARY) {
								b.setGraphic(b.imageBlank);
							}
						}

					});
					b.setOnMouseReleased(e -> {

						clicks++;
						if (clicks == 1 && !allClear(isBomb, b.row, b.col)) {
							isBomb = shift(howMany, isBomb, b);
						}
						if (canStillPlay) {
							smile.setGraphic(smile.start);
						}
						int y;
						MouseButton click = e.getButton();
						if (click == MouseButton.SECONDARY) {
							y = 2;
						} else {
							y = 1;
						}

						if (y == 1 && canStillPlay) {
							if (isBomb[b.getRow()][b.getCol()] == true && b.getState() != 4) {
								smile.setGraphic(smile.loss);
								clearSspacesMS--;
								canStillPlay = false;
								b.state = 1;
								timeline.pause();
								for (int drow = 0; drow < mineSweeperTiles.length; drow++) {
									for (int dcol = 0; dcol < mineSweeperTiles[drow].length; dcol++) {
										Sweepy x = mineSweeperTiles[drow][dcol];
										if (x.state == 4 && isBomb[drow][dcol] == false) {
											x.setGraphic(x.misFlagged);
										} else {
											if (isBomb[drow][dcol] == true) {
												x.setGraphic(x.imageReveal);
											}
										}
									}
								}
								b.setGraphic(b.imageBomb);

							} else if (allClear(isBomb, b.getRow(), b.getCol()) && b.getState() == 0 && b.getState() != 4
									&& canStillPlay) {
								clearSspacesMS++;
								b.state = 1;
								clearSpace(isBomb, mineSweeperTiles, b);
								b.setGraphic(b.imageBlank);

							} else if (b.getState() == 0 && b.getState() != 4 && canStillPlay) {
								clearSspacesMS++;
								b.state = 3;
								choose(mineSweeperTiles, b, isBomb);

							} else if (b.getState() == 3) {
								clickOnNumber(b, isBomb, mineSweeperTiles);
							}
						} else if (flagCount > 0 && b.state == 0 && canStillPlay) {
							b.setGraphic(b.imageFlag);
							flagCount--;
							b.state = 4;
							bombCounter(flagCount);
						} else if (b.state == 4 && canStillPlay) {
							b.setGraphic(b.imageCover);
							flagCount++;
							b.state = 0;
							bombCounter(flagCount);

						}
						if (clearSspacesMS >= totalTilesMS - howMany && canStillPlay) {
							smile.setGraphic(smile.win);
							timeline.pause();
							highScore(difficulty, timeNumber);
						}

					});

					// RESTART LEVEL OF MINESWEEPER
					smile.setOnAction(e -> {
						timeline.pause();
						restart(theStage);
					});

					sweep.add(mineSweeperTiles[row][col], row, col);

				}

			}
			// MENU FOR MINESWEEPER
			VBox options = new VBox();
			MenuBar menus = new MenuBar();
			Menu diffi = new Menu("Difficulties");
			Menu highs = new Menu("Highscores");
			Menu games = new Menu("Games");
			Menu backto = new Menu("Back");
			MenuItem beck = new MenuItem("To Launcher");
			backto.getItems().addAll(beck);
			MenuItem easy = new MenuItem("Easy");
			MenuItem medium = new MenuItem("Medium");
			MenuItem hard = new MenuItem("Hard");
			menus.getMenus().addAll(diffi, highs, games, backto);
			diffi.getItems().addAll(easy, medium, hard);
			String ss = highscores(0);
			String sm = highscores(1);
			String sl = highscores(3);

			MenuItem mine = new MenuItem("Play Minesweeper");
			MenuItem xoro = new MenuItem("Play TicTacToe");
			MenuItem han = new MenuItem("Play Hangman");

			MenuItem eh = new MenuItem("Easy Highscore is " + ss);
			MenuItem mh = new MenuItem("Medium Highscore is " + sm);
			MenuItem lh = new MenuItem("Hard Highscore is " + sl);

			games.getItems().addAll(mine, xoro, han);
			highs.getItems().addAll(eh, mh, lh);
			options.getChildren().add(menus);

			Region region1 = new Region();
			HBox.setHgrow(region1, Priority.ALWAYS);
			Region region2 = new Region();
			HBox.setHgrow(region2, Priority.ALWAYS);

			// CHANGE TO LAUNCHER
			backto.setOnAction(e -> {
				theStage.close();
				game = "s";
				restart(theStage);
			});
			// CHANGE TO HANGMAN
			han.setOnAction(e -> {
				theStage.close();
				game = "h";
				restart(theStage);
			});
			// MINESWEEPER CHANGE TO MINESWEEPER
			mine.setOnAction(e -> {
				game = "m";
				timeline.pause();
				restart(theStage);
			});
			// MINESWEEPER SET TO TIC TAC TOE
			xoro.setOnAction(e -> {
				theStage.close();
				timeline.pause();
				game = "t";
				restart(theStage);

			});
			// MINESWEEPER SET TO EASY
			easy.setOnAction(f -> {
				timeline.pause();
				difficulty = 1;
				restart(theStage);
			});
			// MINESWEEPER SET TO MEDIUM
			medium.setOnAction(f -> {
				timeline.pause();
				difficulty = 2;
				restart(theStage);
			});
			// MINESWEEPER SET TO HARD
			hard.setOnAction(f -> {
				timeline.pause();
				difficulty = 3;
				restart(theStage);
			});

			miner.setTop(options);
			miner.setBottom(sweep);
			sweep.setStyle(
					"-fx-background-color: #B0C6DA; -fx-border-color: #787878 #fafafa #fafafa #787878; -fx-border-width:6; -fx-border-radius: 0.01;");
			side = new HBox(1, zero, leftLeft, bombsLeft);
			mid = new HBox(side, region1, smile, region2, counter);
			mid.setAlignment(Pos.CENTER);
			mid.setStyle(
					"-fx-background-color: #bfbfbf; -fx-border-color: #787878 #fafafa #fafafa #787878; -fx-border-width:6; -fx-border-radius: 0.05;");
			miner.setCenter(mid);
			miner.setStyle(
					"-fx-background-color: #bfbfbf; -fx-border-color:  #fafafa #787878 #787878 #fafafa; -fx-border-width:6; -fx-border-radius: 0.01;");

			scene = new Scene(miner);
			theStage.setTitle("MineSweeper");
			theStage.setScene(scene);
			theStage.setResizable(false);
			theStage.show();

			// SET GAME TO TIC TAC TOE
		} else if (game.contentEquals("t")) {
			canStillPlay = true;
			face start = new face();
			MenuBar menu = new MenuBar();

			three buttons[][] = new three[3][3];
			GridPane board = new GridPane();
			for (int row = 0; row < buttons.length; row++) {
				for (int col = 0; col < buttons[row].length; col++) {
					buttons[row][col] = new three(row, col);
					three x = buttons[row][col];

					x.setOnMousePressed(e -> {
						if (x.state == 0 && canStillPlay) {
							start.setGraphic(start.clicked);
						}
					});
					x.setOnMouseClicked(e -> {

						if (x.state == 0 && canStillPlay) {

							start.setGraphic(start.start);
							if (ticTacToeOddEven % 2 == 0) {
								x.setGraphic(x.o);
								x.state = 1;
							} else {
								x.setGraphic(x.x);
								x.state = 2;
							}

							ticTacToeOddEven++;
						}
						if (checkWin(buttons)) {
							canStillPlay = false;
							start.setGraphic(start.win);
						}
					});

					board.add(buttons[row][col], row, col);
				}

			}

			BorderPane gamefield = new BorderPane();
			BorderPane clicks = new BorderPane();
			clicks.setBottom(board);
			gamefield.setBottom(clicks);
			Region region1 = new Region();
			HBox.setHgrow(region1, Priority.ALWAYS);
			Region region2 = new Region();
			HBox.setHgrow(region2, Priority.ALWAYS);

			Menu games = new Menu("Games");
			MenuItem mine = new MenuItem("Play Minesweeper");
			MenuItem tic = new MenuItem("Play TicTacToe");
			MenuItem han = new MenuItem("Play Hangman");
			Menu backto = new Menu("Back");
			MenuItem beck = new MenuItem("To Launcher");

			backto.getItems().addAll(beck);
			HBox sets = new HBox(region1, start, region2);
			gamefield.setCenter(sets);
			Scene scene = new Scene(gamefield);
			gamefield.setTop(menu);
			games.getItems().addAll(mine, tic, han);
			menu.getMenus().addAll(games, backto);

			theStage.setHeight(690);
			theStage.setWidth(600);
			theStage.setTitle("Tic Tac Toe");
			theStage.setScene(scene);
			theStage.setResizable(false);
			theStage.show();

			// BACK TO LAUNCHER
			beck.setOnAction(e -> {
				theStage.close();
				game = "s";
				restart(theStage);
			});
			// CHANGE TO MINESWEEPER
			mine.setOnAction(e -> {
				theStage.close();
				game = "m";
				restart(theStage);

			});
			// CHANGE TO TIC TAC TOE
			tic.setOnAction(e -> {
				restart(theStage);
			});
			// RESTART THE LEVEL
			start.setOnAction(e -> {
				restart(theStage);
			});
			// CHANGE TO HANGMAN
			han.setOnAction(e -> {
				theStage.close();
				game = "h";
				restart(theStage);
			});
			// HANGMAN
		} else if (game.contentEquals("h")) {
			Menu games = new Menu("Games");
			MenuBar menu = new MenuBar();

			Button[] buttons = new Button[26];
			BorderPane outside = new BorderPane();
			BorderPane bp = new BorderPane();
			letterCount = 0;
			scoreForHM = 0;
			word = "";
			canStillPlay = true;
			livesForAll = 7;
			try {
				word = getNewWord();
			} catch (Exception e) {
				System.out.println(e);
			}
			lettersOrUnders = new char[word.length()];
			wrong = new char[26];

			for (int y = 0; y < word.length(); y++) {
				lettersOrUnders[y] = '_';
			}

			hidden = new Label("Hidden word: " + format(lettersOrUnders));
			Label guesses = new Label("Guesses left: " + livesForAll);
			Label incorrect = new Label("Incorrect guesses: ");
			Label notCorrect = new Label();
			Label score = new Label("Score: ");
			VBox hang = new VBox(hidden, incorrect, notCorrect, guesses, score);
			ArrayList<String> badLetters = new ArrayList<String>();
			for (int row = 0; row < buttons.length; row++) {
				buttons[row] = new Button((Character.toString((char) ('a' + row))));

				Button b = buttons[row];
				b.setFont(new Font(25));
				b.setMaxWidth(80);
				b.setMinWidth(70);
				score.setText("Score: " + scoreForHM);
				b.setOnAction(e -> {
					if (canStillPlay) {
						b.setDisable(true);
						lastGuess = b.getText().charAt(0);
						if (!badLetters.contains(Character.toString(lastGuess))) {
							boolean isRight = false;
							for (int i = 0; i < word.length(); i++) {
								if (Character.toLowerCase(lastGuess) == word.charAt(i)) {
									lettersOrUnders[i] = word.charAt(i);
									letterCount++;
									scoreForHM += 10;
									score.setText("Score : " + scoreForHM);
									isRight = true;
								}
							}
							if (isRight == false) {
								livesForAll--;
								badLetters.add(Character.toString(lastGuess));
								Collections.sort(badLetters);
								guesses.setText("Guesses left: " + livesForAll);
								notCorrect.setText(proper(badLetters));
								Pane pane = new Pane();

								//if (livesForAll < 1) {
									//Line leg3 = new Line(100, 300, 100, 350);
									//pane.getChildren().add(leg3);
								//}
								if (livesForAll <= 1) {

									Circle circle = new Circle();
									circle.setCenterX(100);
									circle.setCenterY(100);
									circle.setRadius(50);
									pane.getChildren().add(circle);
								}
								if (livesForAll <= 2) {
									Line arm2 = new Line(100, 200, 50, 150);
									pane.getChildren().add(arm2);
								}
								if (livesForAll <= 3) {
									Line arm1 = new Line(100, 200, 150, 150);
									pane.getChildren().add(arm1);
								}
								if (livesForAll <= 4) {
									Line body = new Line(100, 150, 100, 300);
									pane.getChildren().add(body);
								}
								if (livesForAll <= 5) {
									Line leg2 = new Line(100, 300, 50, 450);
									pane.getChildren().add(leg2);
								}
								if (livesForAll <= 6) {
									Line leg1 = new Line(100, 300, 150, 450);
									pane.getChildren().add(leg1);
								}

								Region reg3 = new Region();
								HBox.setHgrow(reg3, Priority.ALWAYS);
								Region reg4 = new Region();
								HBox.setHgrow(reg4, Priority.ALWAYS);
								HBox man = new HBox(reg3, pane, reg4);
								bp.setCenter(man);
							}
							hidden.setText("Hidden word: " + format(lettersOrUnders));
							if (livesForAll < 1) {
								canStillPlay = false;
								for (int r = 0; r < buttons.length; r++) {
									buttons[r].setDisable(true);

								}
							}
							if (letterCount >= word.length()) {
								for (int r = 0; r < buttons.length; r++) {
									buttons[r].setDisable(false);

								}
								letterCount = 0;
								livesForAll = 7;
								System.out.println(scoreForHM);
								scoreForHM += (livesForAll * 30);
								scoreForHM += 100;
								System.out.println(scoreForHM);
								score.setText("Score: " + scoreForHM);
								incorrect.setText("Incorrect guesses: ");
								notCorrect.setText(proper(badLetters));
								badLetters.clear();
								try {
									word = getNewWord();
									lettersOrUnders = new char[word.length()];
									unders(word, lettersOrUnders);
									hidden.setText("Hidden word: " + format(lettersOrUnders));
								} catch (Exception xz) {
									System.out.println(xz);
								}
							}
						}
					} else {

					}
				});

			}
			// Restarts Hangman Game
			face set = new face();
			set.setOnAction(e -> {
				restart(theStage);
			});
			Region r = new Region();
			HBox.setHgrow(r, Priority.ALWAYS);
			Region r2 = new Region();
			HBox.setHgrow(r2, Priority.ALWAYS);
			Region r3 = new Region();
			HBox.setHgrow(r3, Priority.ALWAYS);
			Region r4 = new Region();
			HBox.setHgrow(r4, Priority.ALWAYS);
			Region r5 = new Region();
			HBox.setHgrow(r5, Priority.ALWAYS);
			Region r6 = new Region();
			HBox.setHgrow(r6, Priority.ALWAYS);
			Region r7 = new Region();
			HBox.setHgrow(r7, Priority.ALWAYS);
			Region r8 = new Region();
			HBox.setHgrow(r8, Priority.ALWAYS);
			HBox a = new HBox();
			HBox b = new HBox();
			HBox c = new HBox();
			HBox d = new HBox();
			VBox all = new VBox(a, b, c, d);
			for (int row = 0; row < 5; row++) {
				if (row == 0) {
					a.getChildren().addAll(r, buttons[row]);
				} else if (row == 4) {
					a.getChildren().addAll(buttons[row], r3);
				} else {
					a.getChildren().add(buttons[row]);
				}
			}
			for (int row = 5; row < 12; row++) {
				if (row == 5) {
					b.getChildren().addAll(r7, buttons[row]);
				} else if (row == 11) {
					b.getChildren().addAll(buttons[row], r8);
				} else {
					b.getChildren().add(buttons[row]);
				}
			}
			for (int row = 12; row < 19; row++) {
				if (row == 12) {
					c.getChildren().addAll(r5, buttons[row]);
				} else if (row == 18) {
					c.getChildren().addAll(buttons[row], r6);
				} else {
					c.getChildren().add(buttons[row]);
				}
			}
			for (int row = 19; row < 26; row++) {
				if (row == 19) {
					d.getChildren().addAll(r2, buttons[row]);
				} else if (row == 25) {
					d.getChildren().addAll(buttons[row], r4);
				} else {
					d.getChildren().add(buttons[row]);
				}
			}
			Menu backto = new Menu("Back");
			MenuItem beck = new MenuItem("To Launcher");
			backto.getItems().addAll(beck);
			MenuItem mine = new MenuItem("Play Minesweeper");
			MenuItem tic = new MenuItem("Play TicTacToe");
			MenuItem han = new MenuItem("Play Hangman");
			games.getItems().addAll(mine, tic, han);
			menu.getMenus().addAll(games, backto);
			Region reg = new Region();
			HBox.setHgrow(reg, Priority.ALWAYS);
			Region reg2 = new Region();
			HBox.setHgrow(reg2, Priority.ALWAYS);

			VBox toop = new VBox(hang);
			HBox too = new HBox(reg, toop, reg2);
			bp.setTop(too);
			bp.setBottom(all);
			bp.setLeft(hang);
			outside.setCenter(bp);
			Region re = new Region();
			HBox.setHgrow(re, Priority.ALWAYS);
			Region re2 = new Region();
			HBox.setHgrow(re2, Priority.ALWAYS);
			HBox smiley = new HBox(re, set, re2);
			VBox controllers = new VBox(menu, smiley);
			outside.setTop(controllers);

			// BACK TO LAUNCHER
			beck.setOnAction(e -> {
				theStage.close();
				game = "s";
				restart(theStage);
			});
			// CHANGE TO MINESWEEPER
			mine.setOnAction(e -> {
				theStage.close();
				game = "m";
				restart(theStage);

			});
			// CHANGE TO TIC TAC TOE
			tic.setOnAction(e -> {
				theStage.close();
				game = "t";
				restart(theStage);
			});
			// CHANGE TO HANGMAN
			han.setOnAction(e -> {
				theStage.close();
				game = "h";
				restart(theStage);
			});
			Scene scene = new Scene(outside);
			theStage.setWidth(600);
			theStage.setHeight(900);
			theStage.setScene(scene);
			theStage.show();

		}
	}

	public static String proper(ArrayList<String> badLetters) {
		String s = "";
		for (int i = 0; i < badLetters.size(); i++) {
			if (i == badLetters.size() - 1) {
				s = s + badLetters.get(i) + " ";
			} else {
				s = s + badLetters.get(i) + ", ";
			}
		}
		return s;
	}

	public static String format(char[] c) {
		String s = "";
		for (int y = 0; y < c.length; y++) {
			s = s + c[y] + " ";
		}
		return s;
	}

	public static void unders(String word, char[] letters) {
		for (int y = 0; y < word.length(); y++) {
			letters[y] = '_';
		}
	}

	public static boolean checkWin(three[][] b) {
		for (int row = 0; row < b.length; row++) {
			int x = b[row][0].state;
			if (b[row][1].state == x && b[row][2].state == x && x != 0) {
				return true;
			}
			int y = b[0][row].state;
			if (b[1][row].state == y && b[2][row].state == y && y != 0) {
				return true;
			}

		}

		int x = b[0][0].state;
		if (b[1][1].state == x && b[2][2].state == x && x != 0) {
			return true;
		}
		int y = b[2][0].state;
		if (b[0][2].state == y && b[1][1].state == y && y != 0) {
			return true;
		}
		return false;
	}

	public static String getHighScores(int number, String s) {
		String out = "";
		File f = new File("res/highscores/highscores.txt");
		int score = 0;
		try {
			String y = Integer.toString(number);
			Scanner scan = new Scanner(f);
			score = scan.nextInt();
		} catch (Exception e) {
			out = "No Highscore";
		}
		if (score < number) {
			out = Integer.toString(number);
			TextInputDialog name = new TextInputDialog();
			name.setTitle("New HighScore");
			name.setHeaderText("You beat the previous Highscore");
			name.setContentText("Please enter your name:");
			name.showAndWait().ifPresent(high -> {
				nameOfUser = high;
			});
			out = out + nameOfUser;
		}

		return out;
	}

	public static String getNewWord() throws FileNotFoundException {
		File f = new File("res/highscores/dictionary");
		Scanner scan = new Scanner(f);
		ArrayList<String> words = new ArrayList<String>();
		while (scan.hasNext()) {
			words.add(scan.next());
		}
		String word = words.get((int) (Math.random() * words.size() + 1));
		return word;
	}

	public String highscores(int x) {
		String outs = "";
		try {
			File fs = new File("res/highscores/small");
			fs.createNewFile();
			Scanner ss = new Scanner(fs);
			String pss = ss.next();
			int ps = Integer.parseInt(pss);
			String ns = "";
			if (ps != 0) {
				ns = ss.next();
			}
			outs = ns + ": " + ps + " seconds";
		} catch (Exception e) {
			outs = "No Highscore";
		}

		String outm = "";
		try {
			File fm = new File("res/highscores/medium");
			fm.createNewFile();
			Scanner sm = new Scanner(fm);
			String pms = sm.next();
			int pm = Integer.parseInt(pms);
			String nm = "";

			if (pm != 0) {

				nm = sm.next();
			}
			outm = nm + ": " + pm + " seconds";
		} catch (Exception e) {
			outm = "No Highscore";
		}
		String outl = "";
		try {
			File fl = new File("res/highscores/large");
			fl.createNewFile();
			Scanner sl = new Scanner(fl);
			String pls = sl.next();
			int pl = Integer.parseInt(pls);
			String nl = "";

			if (pl != 0) {
				nl = sl.next();
			}
			outl = nl + ": " + pl + " seconds";
		} catch (Exception e) {
			outl = "No Highscore";
		}
		String hanger = "";
		try {
			File hl = new File("res/highscores/hangerman");
			hl.createNewFile();
			Scanner hms = new Scanner(hl);
			String hs = hms.next();
			int ahs = Integer.parseInt(hs);
			String nhs = "";

			if (ahs != 0) {
				nhs = hms.next();
			}
			hanger = nhs + ": " + ahs + " seconds";
		} catch (Exception e) {
			hanger = "No Highscore";
		}
		if (x == 0) {
			return outs;
		} else if (x == 1) {
			return outm;
		} else if (x == 2) {
			return hanger;
		} else {
			return outl;
		}
	}

	public boolean flags(Sweepy b, Sweepy[][] buttons) {
		int count = 0;
		int row = b.getRow();
		int col = b.getCol();

		if (row < buttons.length - 1 && buttons[row + 1][col].state == 4) {
			count++;
		}
		if (row > 0 && buttons[row - 1][col].state == 4) {
			count++;
		}

		if (col < buttons[0].length - 1 && buttons[row][col + 1].state == 4) {
			count++;
		}
		if (col > 0 && buttons[row][col - 1].state == 4) {
			count++;
		}
		if (row < buttons.length - 1 && col < buttons[0].length - 1 && buttons[row + 1][col + 1].state == 4) {
			count++;
		}
		if (row > 0 && col > 0 && buttons[row - 1][col - 1].state == 4) {
			count++;
		}
		if (row < buttons.length - 1 && col > 0 && buttons[row + 1][col - 1].state == 4) {
			count++;
		}
		if (row > 0 && col < buttons[0].length - 1 && buttons[row - 1][col + 1].state == 4) {
			count++;
		}
		if (b.count == count) {
			return true;
		} else {
			return false;
		}
	}

	public void clickOnNumber(Sweepy b, boolean[][] bombs, Sweepy[][] buttons) {
		int row = b.getRow();
		int col = b.getCol();
		if (flags(b, buttons)) {
			if (row < bombs.length - 1 && bombs[row + 1][col] == true && buttons[row + 1][col].state == 0) {
				buttons[row + 1][col].setGraphic(b.imageBomb);
				canStillPlay = false;
				timeline.pause();
				smile.setGraphic(smile.loss);
			} else if (row < bombs.length - 1 && bombs[row + 1][col] == false && buttons[row + 1][col].state == 0) {
				choose(buttons, buttons[row + 1][col], bombs);
				buttons[row + 1][col].state = 3;
				clearSspacesMS++;
			}

			if (row > 0 && bombs[row - 1][col] == true && buttons[row - 1][col].state == 0) {
				buttons[row - 1][col].setGraphic(b.imageBomb);
				canStillPlay = false;
				timeline.pause();
				smile.setGraphic(smile.loss);
			} else if (row > 0 && bombs[row - 1][col] == false && buttons[row - 1][col].state == 0) {
				choose(buttons, buttons[row - 1][col], bombs);
				buttons[row - 1][col].state = 3;
				clearSspacesMS++;
			}

			if (col < bombs[0].length - 1 && bombs[row][col + 1] == true && buttons[row][col + 1].state == 0) {
				buttons[row][col + 1].setGraphic(b.imageBomb);
				canStillPlay = false;
				timeline.pause();
				smile.setGraphic(smile.loss);
			} else if (col < bombs[0].length - 1 && bombs[row][col + 1] == false && buttons[row][col + 1].state == 0) {
				choose(buttons, buttons[row][col + 1], bombs);
				buttons[row][col + 1].state = 3;
				clearSspacesMS++;
			}

			if (col > 0 && bombs[row][col - 1] == true && buttons[row][col - 1].state == 0) {
				buttons[row][col - 1].setGraphic(b.imageBomb);
				canStillPlay = false;
				timeline.pause();
				smile.setGraphic(smile.loss);
			} else if (col > 0 && bombs[row][col - 1] == false && buttons[row][col - 1].state == 0) {
				choose(buttons, buttons[row][col - 1], bombs);
				buttons[row][col - 1].state = 3;
				clearSspacesMS++;
			}

			if (row < bombs.length - 1 && col < bombs[0].length - 1 && bombs[row + 1][col + 1] == true
					&& buttons[row + 1][col + 1].state == 0) {
				buttons[row + 1][col + 1].setGraphic(b.imageBomb);
				canStillPlay = false;
				timeline.pause();
				smile.setGraphic(smile.loss);
			} else if (row < bombs.length - 1 && col < bombs[0].length - 1 && bombs[row + 1][col + 1] == false
					&& buttons[row + 1][col + 1].state == 0) {
				choose(buttons, buttons[row + 1][col + 1], bombs);
				buttons[row + 1][col + 1].state = 3;
				clearSspacesMS++;
			}

			if (row > 0 && col > 0 && bombs[row - 1][col - 1] == true && buttons[row - 1][col - 1].state == 0) {
				buttons[row - 1][col - 1].setGraphic(b.imageBomb);
				canStillPlay = false;
				timeline.pause();
				smile.setGraphic(smile.loss);
			} else if (row > 0 && col > 0 && bombs[row - 1][col - 1] == false && buttons[row - 1][col - 1].state == 0) {
				choose(buttons, buttons[row - 1][col - 1], bombs);
				buttons[row - 1][col - 1].state = 3;
				clearSspacesMS++;
			}

			if (row < bombs.length - 1 && col > 0 && bombs[row + 1][col - 1] == true
					&& buttons[row + 1][col - 1].state == 0) {
				buttons[row + 1][col - 1].setGraphic(b.imageBomb);
				canStillPlay = false;
				timeline.pause();
				smile.setGraphic(smile.loss);
			} else if (row < bombs.length - 1 && col > 0 && bombs[row + 1][col - 1] == false
					&& buttons[row + 1][col - 1].state == 0) {
				choose(buttons, buttons[row + 1][col - 1], bombs);
				buttons[row + 1][col - 1].state = 3;
				clearSspacesMS++;
			}

			if (row > 0 && col < bombs[0].length - 1 && bombs[row - 1][col + 1] == true
					&& buttons[row - 1][col + 1].state == 0) {
				buttons[row - 1][col + 1].setGraphic(b.imageBomb);
				canStillPlay = false;
				timeline.pause();
				smile.setGraphic(smile.loss);
			} else if (row > 0 && col < bombs[0].length - 1 && bombs[row - 1][col + 1] == false
					&& buttons[row - 1][col + 1].state == 0) {
				choose(buttons, buttons[row - 1][col + 1], bombs);
				buttons[row - 1][col + 1].state = 3;
				clearSspacesMS++;
			}
		}
	}

	public void highScore(int difficulty, int number) {
		if (difficulty == 1) {
			try {
				File file = new File("res/highscores/small");
				if (highscores(0).equals("No Highscore")) {
					TextInputDialog name = new TextInputDialog();
					name.setTitle("New Highscore");
					name.setHeaderText("You beat the previous Highscore for Easy");
					name.setContentText("Please enter your name:");
					name.showAndWait().ifPresent(high -> {
						nameOfUser = high;
					});
					PrintWriter pw = new PrintWriter(file);
					pw.print(number + " " + nameOfUser);
					pw.close();

				} else {
					Scanner scan = new Scanner(file);
					String points = scan.next();
					int score = Integer.parseInt(points);
					if (number < score || score == 0) {
						TextInputDialog name = new TextInputDialog();
						name.setTitle("New Highscore");
						name.setHeaderText("You beat the previous Highscore for Easy");
						name.setContentText("Please enter your name:");
						name.showAndWait().ifPresent(high -> {
							nameOfUser = high;
						});
						PrintWriter pw = new PrintWriter(file);
						pw.print(number + " " + nameOfUser);
						pw.close();
						scan.close();
					}
				}
			} catch (Exception q) {
				System.out.println(q);
			}

		} else if (difficulty == 2) {
			try {
				File file = new File("res/highscores/medium");
				if (highscores(1).equals("No Highscore")) {
					TextInputDialog name = new TextInputDialog();
					name.setTitle("New Highscore");
					name.setHeaderText("You beat the previous Highscore for Medium");
					name.setContentText("Please enter your name:");
					name.showAndWait().ifPresent(high -> {
						nameOfUser = high;
					});
					PrintWriter pw = new PrintWriter(file);
					pw.print(number + " " + nameOfUser);
					pw.close();
				} else {
					Scanner scan = new Scanner(file);
					String points = scan.next();
					int score = Integer.parseInt(points);
					if (number < score || score == 0) {
						TextInputDialog name = new TextInputDialog();
						name.setTitle("New HighScore");
						name.setHeaderText("You beat the previous HighScore for Medium");
						name.setContentText("Please enter your name:");
						name.showAndWait().ifPresent(high -> {
							nameOfUser = high;
						});
						PrintWriter pw = new PrintWriter(file);
						pw.print(number + " " + nameOfUser);
						pw.close();
						scan.close();
					}
				}
			} catch (Exception q) {
				System.out.println(q);
			}

		} else if (difficulty == 3) {
			try {
				File file = new File("res/highscores/large");
				if (highscores(3).equals("No Highscore")) {
					TextInputDialog name = new TextInputDialog();
					name.setTitle("New HighScore");
					name.setHeaderText("You beat the prevois HighScore for Hard");
					name.setContentText("Please enter your name:");
					name.showAndWait().ifPresent(high -> {
						nameOfUser = high;
					});
					PrintWriter pw = new PrintWriter(file);
					pw.print(number + " " + nameOfUser);
					pw.close();
				} else {
					Scanner scan = new Scanner(file);
					String points = scan.next();
					int score = Integer.parseInt(points);
					if (number < score || score == 0) {
						TextInputDialog name = new TextInputDialog();
						name.setTitle("New HighScore");
						name.setHeaderText("You beat the prevois HighScore for Hard");
						name.setContentText("Please enter your name:");
						name.showAndWait().ifPresent(high -> {
							nameOfUser = high;
						});
						PrintWriter pw = new PrintWriter(file);
						pw.print(number + " " + nameOfUser);
						pw.close();
						scan.close();
					}
				}
			} catch (Exception q) {
				System.out.println(q);
			}

		} else if (difficulty == 4) {
			try {
				File file = new File("res/highscores/hangerman");
				if (highscores(2).equals("No Highscore")) {
					TextInputDialog name = new TextInputDialog();
					name.setTitle("New HighScore");
					name.setHeaderText("You beat the prevois HighScore for Hangman");
					name.setContentText("Please enter your name:");
					name.showAndWait().ifPresent(high -> {
						nameOfUser = high;
					});
					PrintWriter pw = new PrintWriter(file);
					pw.print(number + " " + nameOfUser);
					pw.close();
				} else {
					Scanner scan = new Scanner(file);
					String points = scan.next();
					int score = Integer.parseInt(points);
					if (number < score || score == 0) {
						TextInputDialog name = new TextInputDialog();
						name.setTitle("New HighScore");
						name.setHeaderText("You beat the prevois HighScore for Hard");
						name.setContentText("Please enter your name:");
						name.showAndWait().ifPresent(high -> {
							nameOfUser = high;
						});
						PrintWriter pw = new PrintWriter(file);
						pw.print(number + " " + nameOfUser);
						pw.close();
						scan.close();
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public boolean[][] shift(int howMany, boolean[][] bombs, Sweepy b) {
		do {
			for (int row = 0; row < bombs.length; row++) {
				for (int col = 0; col < bombs[row].length; col++) {
					bombs[row][col] = false;
					;
				}

			}

			boolean bad = false;
			int x = 0;
			while (x < howMany) {
				int frow = (int) (Math.random() * bombs.length);
				int fcol = (int) (Math.random() * bombs[0].length);
				if (bombs[frow][fcol] == false) {
					x++;
					bombs[frow][fcol] = true;

				} else {
					while (bad == false) {
						int trow = (int) (Math.random() * bombs.length);
						int tcol = (int) (Math.random() * bombs[0].length);
						if (trow != frow && fcol != tcol && bombs[trow][tcol] != true) {
							bombs[trow][tcol] = true;

							bad = true;
							x++;
						} else {
							bad = false;
						}
					}
				}
			}
		} while (!allClear(bombs, b.row, b.col));
		return bombs;
	}

	public boolean trueCount(boolean[][] bombs) {
		int count = 0;
		for (int row = 0; row < bombs.length; row++) {
			for (int col = 0; col < bombs[row].length; col++) {
				if (bombs[row][col] == true) {
					count++;
				}
			}
		}
		if (count == howMany) {
			return true;
		}
		return false;
	}

	public void clearSpace(boolean[][] bombs, Sweepy[][] buttons, Sweepy b) {
		int row = b.getRow();
		int col = b.getCol();
		b.state = 1;
		if (row < bombs.length - 1 && bombs[row + 1][col] == false) {
			Sweepy check = buttons[row + 1][col];
			if (check.state == 0) {
				if (allClear(bombs, row + 1, col)) {
					check.setGraphic(check.imageBlank);
					clearSpace(bombs, buttons, check);
					check.state = 1;
				} else {
					check.state = 3;
					choose(buttons, check, bombs);
				}

				clearSspacesMS++;
			}
		}
		if (row > 0 && bombs[row - 1][col] == false) {
			Sweepy check = buttons[row - 1][col];
			if (check.state == 0) {
				if (allClear(bombs, row - 1, col)) {
					check.setGraphic(check.imageBlank);
					clearSpace(bombs, buttons, check);
					check.state = 1;
				} else {
					check.state = 3;
					choose(buttons, check, bombs);
				}
				clearSspacesMS++;
			}
		}

		if (col < bombs[0].length - 1 && bombs[row][col + 1] == false) {
			Sweepy check = buttons[row][col + 1];
			if (check.state == 0) {
				if (allClear(bombs, row, col + 1)) {
					check.setGraphic(check.imageBlank);
					clearSpace(bombs, buttons, check);
					check.state = 1;
				} else {
					check.state = 3;
					choose(buttons, check, bombs);
				}
				clearSspacesMS++;
			}
		}
		if (col > 0 && bombs[row][col - 1] == false) {
			Sweepy check = buttons[row][col - 1];
			if (check.state == 0) {
				if (allClear(bombs, row, col - 1)) {
					check.setGraphic(check.imageBlank);
					clearSpace(bombs, buttons, check);
					check.state = 1;
				} else {
					check.state = 3;
					choose(buttons, check, bombs);
				}
				clearSspacesMS++;
			}
		}
		if (row < bombs.length - 1 && col < bombs[0].length - 1 && bombs[row + 1][col + 1] == false) {
			Sweepy check = buttons[row + 1][col + 1];
			if (check.state == 0) {
				if (allClear(bombs, row + 1, col + 1)) {
					check.setGraphic(check.imageBlank);
					clearSpace(bombs, buttons, check);
					check.state = 1;
				} else {
					check.state = 3;
					choose(buttons, check, bombs);
				}
				clearSspacesMS++;
			}
		}
		if (row > 0 && col > 0 && bombs[row - 1][col - 1] == false) {
			Sweepy check = buttons[row - 1][col - 1];
			if (check.state == 0) {
				if (allClear(bombs, row - 1, col - 1)) {
					check.setGraphic(check.imageBlank);
					clearSpace(bombs, buttons, check);
					check.state = 1;
				} else {
					check.state = 3;
					choose(buttons, check, bombs);
				}
				clearSspacesMS++;
			}
		}
		if (row < bombs.length - 1 && col > 0 && bombs[row + 1][col - 1] == false) {
			Sweepy check = buttons[row + 1][col - 1];
			if (check.state == 0) {
				if (allClear(bombs, row + 1, col - 1)) {
					check.setGraphic(check.imageBlank);
					clearSpace(bombs, buttons, check);
					check.state = 1;
				} else {
					check.state = 3;
					choose(buttons, check, bombs);
				}
				clearSspacesMS++;
			}
		}
		if (row > 0 && col < bombs[0].length - 1 && bombs[row - 1][col + 1] == false) {
			Sweepy check = buttons[row - 1][col + 1];
			if (check.state == 0) {
				if (allClear(bombs, row - 1, col + 1)) {
					check.setGraphic(check.imageBlank);
					clearSpace(bombs, buttons, check);
					check.state = 1;
				} else {
					check.state = 3;
					choose(buttons, check, bombs);
				}
				clearSspacesMS++;
			}
		}
	}

	public void bombCounter(int flags) {

		int size = labelSize;
		if (flags >= 10) {
			ImageView zeroPNG = new ImageView("file:res/digits/0.png");
			int r = flags / 10;
			int lastDigit = flags % 10;
			ImageView leftPNG = new ImageView("file:res/digits/" + String.valueOf(r) + ".png");
			ImageView bombsPNG = new ImageView("file:res/digits/" + String.valueOf(lastDigit) + ".png");

			zeroPNG.setFitWidth(size);
			zeroPNG.setFitHeight(size + 10);
			leftPNG.setFitWidth(size);
			leftPNG.setFitHeight(size + 10);
			bombsPNG.setFitWidth(size);
			bombsPNG.setFitHeight(size + 10);
			if (r == 8) {
				leftPNG.setFitHeight(size + 11.6);
			}
			if (lastDigit == 8) {
				bombsPNG.setFitHeight(size + 11.6);
			}
			zero.setGraphic(zeroPNG);
			leftLeft.setGraphic(leftPNG);
			bombsLeft.setGraphic(bombsPNG);
		} else {
			ImageView bombsPNG = new ImageView("file:res/digits/" + String.valueOf(flags) + ".png");
			ImageView zeroPNG = new ImageView("file:res/digits/0.png");
			ImageView leftPNG = new ImageView("file:res/digits/0.png");
			leftPNG.setFitHeight(size + 10);
			leftPNG.setFitWidth(size);
			zeroPNG.setFitHeight(size + 10);
			zeroPNG.setFitWidth(size);
			bombsPNG.setFitWidth(size);
			bombsPNG.setFitHeight(size + 10);
			if (flags == 8) {
				bombsPNG.setFitHeight(size + 11.6);
			}
			zero.setGraphic(zeroPNG);
			leftLeft.setGraphic(leftPNG);
			bombsLeft.setGraphic(bombsPNG);
		}
	}

	public void choose(Sweepy[][] buttons, Sweepy b, boolean[][] bombs) {
		int count = 0;
		int row = b.getRow();
		int col = b.getCol();

		if (row < bombs.length - 1 && bombs[row + 1][col] == true) {
			count++;
		}
		if (row > 0 && bombs[row - 1][col] == true) {
			count++;
		}

		if (col < bombs[0].length - 1 && bombs[row][col + 1] == true) {
			count++;
		}
		if (col > 0 && bombs[row][col - 1] == true) {
			count++;
		}
		if (row < bombs.length - 1 && col < bombs[0].length - 1 && bombs[row + 1][col + 1] == true) {
			count++;
		}
		if (row > 0 && col > 0 && bombs[row - 1][col - 1] == true) {
			count++;
		}
		if (row < bombs.length - 1 && col > 0 && bombs[row + 1][col - 1] == true) {
			count++;
		}
		if (row > 0 && col < bombs[0].length - 1 && bombs[row - 1][col + 1] == true) {
			count++;
		}
		b.count = count;
		switch (count) {
		case 0:
			b.setGraphic(b.imageBlank);
			clearSpace(bombs, buttons, b);
			break;
		case 1:
			b.setGraphic(b.b1);
			break;
		case 2:
			b.setGraphic(b.b2);
			break;
		case 3:
			b.setGraphic(b.b3);
			break;
		case 4:
			b.setGraphic(b.b4);
			break;
		case 5:
			b.setGraphic(b.b5);
			break;
		case 6:
			b.setGraphic(b.b6);
			break;
		case 7:
			b.setGraphic(b.b7);
			break;
		case 8:
			b.setGraphic(b.b8);
			break;

		}

	}

	public boolean allClear(boolean[][] bombs, int row, int col) {

		if ((row < bombs.length - 1 && bombs[row + 1][col] == true) || (bombs[row][col] == true)
				|| (col < bombs[0].length - 1 && bombs[row][col + 1] == true)
				|| (row > 0 && bombs[row - 1][col] == true) || (col > 0 && bombs[row][col - 1] == true)
				|| (row < bombs.length - 1 && col < bombs[0].length - 1 && bombs[row + 1][col + 1] == true)
				|| (row > 0 && col > 0 && bombs[row - 1][col - 1] == true)
				|| (row < bombs.length - 1 && col > 0 && bombs[row + 1][col - 1] == true)
				|| (row > 0 && col < bombs[0].length - 1 && bombs[row - 1][col + 1] == true)) {
			return false;
		}

		return true;
	}

	public void restart(Stage theStage) {
		start(theStage);
	}

	public void begin() {
		timeline = new Timeline(new KeyFrame(Duration.millis(1000), ae -> addSecond()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	public void addSecond() {

		timeNumber += 1;
		update();
	}

	public void update() {
		this.timeCounter(timeNumber, labelSize);

	}

	public void timeCounter(int x, int labelSize) {
		if (x < 10) {
			ImageView zeroPNG = new ImageView("file:res/digits/0.png");
			ImageView midPNG = new ImageView("file:res/digits/0.png");
			midPNG.setFitWidth(labelSize);
			midPNG.setFitHeight(labelSize + 10);
			zeroPNG.setFitWidth(labelSize);
			zeroPNG.setFitHeight(labelSize + 10);
			ImageView countPNG = new ImageView("file:res/digits/" + String.valueOf(x) + ".png");
			countPNG.setFitWidth(labelSize);
			countPNG.setFitHeight(labelSize + 10);

			if (x == 8) {
				countPNG.setFitHeight(labelSize + 11.6);
			}
			left.setGraphic(zeroPNG);
			middle.setGraphic(midPNG);
			right.setGraphic(countPNG);
		} else if (x < 100) {
			ImageView zeroPNG = new ImageView("file:res/digits/0.png");
			int first = x / 10;
			int lastDigit = x % 10;
			ImageView midPNG = new ImageView("file:res/digits/" + String.valueOf(first) + ".png");
			ImageView countPNG = new ImageView("file:res/digits/" + String.valueOf(lastDigit) + ".png");

			zeroPNG.setFitWidth(labelSize);
			zeroPNG.setFitHeight(labelSize + 10);
			midPNG.setFitWidth(labelSize);
			midPNG.setFitHeight(labelSize + 10);
			countPNG.setFitWidth(labelSize);
			countPNG.setFitHeight(labelSize + 10);
			if (first == 8) {
				midPNG.setFitHeight(labelSize + 11.6);
			}
			if (lastDigit == 8) {
				countPNG.setFitHeight(labelSize + 11.6);
			}
			left.setGraphic(zeroPNG);
			middle.setGraphic(midPNG);
			right.setGraphic(countPNG);
		} else {
			int l = x / 100;
			int m = (x % 100) / 10;
			int r = x % 10;
			ImageView leftPNG = new ImageView("file:res/digits/" + String.valueOf(l) + ".png");
			ImageView midPNG = new ImageView("file:res/digits/" + String.valueOf(m) + ".png");
			ImageView rightPNG = new ImageView("file:res/digits/" + String.valueOf(r) + ".png");

			leftPNG.setFitWidth(labelSize);
			leftPNG.setFitHeight(labelSize + 10);
			midPNG.setFitWidth(labelSize);
			midPNG.setFitHeight(labelSize + 10);
			rightPNG.setFitWidth(labelSize);
			rightPNG.setFitHeight(labelSize + 10);
			if (r == 8) {
				rightPNG.setFitHeight(labelSize + 11.6);
			}
			if (m == 8) {
				midPNG.setFitHeight(labelSize + 11.6);
			}
			if (l == 8) {
				leftPNG.setFitHeight(labelSize + 11.6);
			}
			left.setGraphic(leftPNG);
			middle.setGraphic(midPNG);
			right.setGraphic(rightPNG);
		}

	}
}

class Timer {

	int size = 30;

	int number;
	Label left, mid, right;
	HBox box = new HBox(left, mid, right);

	public Timer(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

}

class face extends Button {
	ImageView start, win, loss, clicked;

	public face() {
		double size = 60;
		setMinWidth(size);
		setMaxWidth(size);
		setMinHeight(size);
		setMaxHeight(size);

		start = new ImageView(new Image("file:res/face-smile.png"));
		win = new ImageView(new Image("file:res/face-win.png"));
		loss = new ImageView(new Image("file:res/face-dead.png"));
		clicked = new ImageView(new Image("file:res/face-O.png"));
		start.setFitWidth(size);
		start.setFitHeight(size);
		win.setFitWidth(size);
		win.setFitHeight(size);
		loss.setFitWidth(size);
		loss.setFitHeight(size);
		clicked.setFitWidth(size);
		clicked.setFitHeight(size);
		setGraphic(start);
	}
}

class three extends Button {
	ImageView image, o, x;
	int row, col;
	int state = 0;

	public three(int row, int col) {
		double size = 180;
		this.row = row;
		this.col = col;
		image = new ImageView(new Image("file:res/tictac.png"));
		image.setFitWidth(size);
		image.setFitHeight(size);

		o = new ImageView(new Image("file:res/o.png"));
		o.setFitWidth(size);
		o.setFitHeight(size);

		x = new ImageView(new Image("file:res/x.jpeg"));
		x.setFitWidth(size);
		x.setFitHeight(size);
		setGraphic(image);
	}

}

class Sweepy extends Button {
	ImageView imageCover, imageFlag, misFlagged, imageBlank, imageBomb, imageReveal, b1, b2, b3, b4, b5, b6, b7, b8;
	int row, col;
	int count = 0;
	int state = 0;
	boolean isBomb = false;

	public Sweepy(int row, int col) {
		this.row = row;
		this.col = col;

		double size = 40;
		double border = 2;
		setMinWidth(size);
		setMaxWidth(size);
		setMinHeight(size);
		setMaxHeight(size);

		b1 = new ImageView(new Image("file:res/1.png"));
		b1.setFitWidth(size - border);
		b1.setFitHeight(size - border);

		b2 = new ImageView(new Image("file:res/2.png"));
		b2.setFitWidth(size - border);
		b2.setFitHeight(size - border);

		b3 = new ImageView(new Image("file:res/3.png"));
		b3.setFitWidth(size - border);
		b3.setFitHeight(size - border);

		b4 = new ImageView(new Image("file:res/4.png"));
		b4.setFitWidth(size - border);
		b4.setFitHeight(size - border);

		b5 = new ImageView(new Image("file:res/5.png"));
		b5.setFitWidth(size - border);
		b5.setFitHeight(size - border);

		b6 = new ImageView(new Image("file:res/6.png"));
		b6.setFitWidth(size - border);
		b6.setFitHeight(size - border);

		b7 = new ImageView(new Image("file:res/7.png"));
		b7.setFitWidth(size - border);
		b7.setFitHeight(size - border);

		b8 = new ImageView(new Image("file:res/8.png"));
		b8.setFitWidth(size - border);
		b8.setFitHeight(size - border);

		imageFlag = new ImageView(new Image("file:res/flag.png"));
		imageFlag.setFitHeight(size);
		imageFlag.setFitWidth(size);

		imageCover = new ImageView(new Image("file:res/cover.png"));
		imageCover.setFitWidth(size);
		imageCover.setFitHeight(size);

		imageBlank = new ImageView(new Image("file:res/0.png"));
		imageBlank.setFitWidth(size - border);
		imageBlank.setFitHeight(size - border);

		imageBomb = new ImageView(new Image("file:res/mine-red.png"));
		imageBomb.setFitWidth(size - border);
		imageBomb.setFitHeight(size - border);

		imageReveal = new ImageView(new Image("file:res/mine-grey.png"));
		imageReveal.setFitWidth(size - border);
		imageReveal.setFitHeight(size - border);

		misFlagged = new ImageView(new Image("file:res/mine-misflagged.png"));
		misFlagged.setFitWidth(size - border);
		misFlagged.setFitHeight(size - border);

		setGraphic(imageCover);
	}

	public int getState() {
		return state;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
}
