import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MapGameController implements Initializable {
    public MapData mapData;
    public MoveChara chara;
    public DarkMap darkMap;
    public GridPane mapGrid;
    public ImageView[] mapImageViews;
    public GridPane darkGrid;
    public ImageView[] darkImageViews;
    public Item item;
    public ItemHandler itemHandl;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapData = new MapData(21, 15);
        chara = new MoveChara(1, 1, mapData);
        darkMap = new DarkMap(21, 15, chara);
        itemHandl = new ItemHandler(mapData, chara, darkGrid);
        item = new Item(mapData, itemHandl);
        mapImageViews = new ImageView[mapData.getHeight() * mapData.getWidth()];
        darkImageViews = new ImageView[mapData.getHeight() * mapData.getWidth()];

        for (int y = 0; y < mapData.getHeight(); y++) {
            for (int x = 0; x < mapData.getWidth(); x++) {
                int index = y * mapData.getWidth() + x;
                mapImageViews[index] = mapData.getImageView(x, y);
                darkImageViews[index] = darkMap.getImageView(x, y);
            }
        }
        itemHandl.setDarkGrid(darkGrid);
        drawMap(chara, mapData);
        setupGoalImage();
    }

    private void setupGoalImage() {
        // ゴールの画像を取得してImageViewに設定
        Image goalImage = new Image("png/red_circle_image.png");
        ImageView goalImageView = new ImageView(goalImage);

        // ゴール座標にImageViewを配置
        mapGrid.add(goalImageView, mapData.getGoalX(), mapData.getGoalY());
    }

    // Draw the map
    public void drawMap(MoveChara c, MapData m) {
        int cx = c.getPosX();
        int cy = c.getPosY();
        mapGrid.getChildren().clear();
        darkGrid.getChildren().clear();
        for (int y = 0; y < mapData.getHeight(); y++) {
            for (int x = 0; x < mapData.getWidth(); x++) {
                int index = y * mapData.getWidth() + x;
                darkGrid.add(darkImageViews[index], x, y);
                if (x == cx && y == cy) {
                    mapGrid.add(c.getCharaImageView(), x, y);
                } else if (x == mapData.getGoalX() && y == mapData.getGoalY()) {
                    // ゴールの座標に赤い丸を描画
                    mapGrid.add(new ImageView(new Image("png/red_circle_image.png")), x, y);
                } else {
                    int itemType = item.getItemAt(x, y);
                    if (3 <= itemType) {
                        mapGrid.add(item.getItemImageView(itemType), x, y);
                    } else {
                        mapGrid.add(mapImageViews[index], x, y);
                    }
                    darkMapVisibleAction(x, y, cx, cy, index);
                }

            }
        }
        if (chara.isAtGoal()) {
            // プレイヤーがゴールに到達した場合、MapGoal.fxml を表示
            showGoalScreen();
        }
    }

    private void showGoalScreen() {
        try {
            System.out.println("Goal");
            StageDB.getMainStage().hide();
            StageDB.getMainSound().stop();
            StageDB.getGoalStage().show();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void darkMapVisibleAction(int x, int y, int cx, int cy, int index) {
        if (darkGrid.isVisible()) {
            if ((x == cx - 1 && y == cy - 1) || (x == cx && y == cy - 1) || (x == cx + 1 && y == cy - 1)) {
                mapImageViews[index].setVisible(true);
            } else if ((x == cx - 1 && y == cy) || (x == cx && y == cy)
                    || (x == cx + 1 && y == cy)) {
                mapImageViews[index].setVisible(true);
            } else if ((x == cx - 1 && y == cy + 1) || (x == cx && y == cy + 1)
                    || (x == cx + 1 && y == cy + 1)) {
                mapImageViews[index].setVisible(true);
            } else {
                mapImageViews[index].setVisible(false);
            }
        } else if (!mapImageViews[index].isVisible()) {
            mapImageViews[index].setVisible(true);
        }
    }

    public void drawDarkMap(MoveChara c) {
        darkMap.darkMove(c);
        for (int y = 0; y < mapData.getHeight(); y++) {
            for (int x = 0; x < mapData.getWidth(); x++) {
                int index = y * mapData.getWidth() + x;
                darkImageViews[index] = darkMap.getImageView(x, y);
            }
        }
    }

    // Get users' key actions
    public void keyAction(KeyEvent event) {
        KeyCode key = event.getCode();
        System.out.println("keycode:" + key);
        if (key == KeyCode.A) {
            leftButtonAction();
        } else if (key == KeyCode.S) {
            downButtonAction();
        } else if (key == KeyCode.W) {
            upButtonAction();
        } else if (key == KeyCode.D) {
            rightButtonAction();
        }
    }

    // Operations for going the cat up
    public void upButtonAction() {
        printAction("UP");
        chara.setCharaDirection(MoveChara.TYPE_UP);
        chara.move(0, -1);
        checkItem(chara.getPosX(), chara.getPosY());
        drawDarkMap(chara);
        drawMap(chara, mapData);
        drawDarkMap(chara);
    }

    // Operations for going the cat down
    public void downButtonAction() {
        printAction("DOWN");
        chara.setCharaDirection(MoveChara.TYPE_DOWN);
        chara.move(0, 1);
        checkItem(chara.getPosX(), chara.getPosY());
        drawDarkMap(chara);
        drawMap(chara, mapData);
    }

    // Operations for going the cat right
    public void leftButtonAction() {
        printAction("LEFT");
        chara.setCharaDirection(MoveChara.TYPE_LEFT);
        chara.move(-1, 0);
        checkItem(chara.getPosX(), chara.getPosY());
        drawDarkMap(chara);
        drawMap(chara, mapData);
    }

    // Operations for going the cat right
    public void rightButtonAction() {
        printAction("RIGHT");
        chara.setCharaDirection(MoveChara.TYPE_RIGHT);
        chara.move(1, 0);
        checkItem(chara.getPosX(), chara.getPosY());
        drawDarkMap(chara);
        drawMap(chara, mapData);
    }

    private void checkItem(int x, int y) {
        int itemType = item.getItemAt(x, y);
        if (itemType != -1) {
            System.out.println("Item acquired: " + itemType);
            // Perform additional actions for the acquired item if needed
            item.removeItemAt(x, y);
        }
    }

    @FXML
    public void func1ButtonAction(ActionEvent event) {
        try {
            System.out.println("func1");
            StageDB.getMainStage().hide();
            StageDB.getMainSound().stop();
            StageDB.getGameOverStage().show();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void func2ButtonAction(ActionEvent event) {
        if (chara.getPosX() == 1 && chara.getPosY() == 1) {
            mapData = new MapData(21, 15);
            chara = new MoveChara(chara.getPosX(), chara.getPosY(), mapData);
            mapImageViews = new ImageView[mapData.getHeight() * mapData.getWidth()];
            for (int y = 0; y < mapData.getHeight(); y++) {
                for (int x = 0; x < mapData.getWidth(); x++) {
                    int index = y * mapData.getWidth() + x;
                    mapImageViews[index] = mapData.getImageView(x, y);
                }
            }
            drawMap(chara, mapData);
        }
    }

    @FXML
    public void func3ButtonAction(ActionEvent event) {
        System.out.println("func3: Nothing to do");
    }

    @FXML
    public void func4ButtonAction(ActionEvent event) {
        System.out.println("func4: Goal coordinates - X: " + mapData.getGoalX() + ", Y: " + mapData.getGoalY());
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Goal Coordinates");
        alert.setHeaderText(null);
        alert.setContentText("Goal coordinates - X: " + mapData.getGoalX() + ", Y: " + mapData.getGoalY());
        alert.showAndWait();
    }

    @FXML
    public void DarkButtonAction(ActionEvent event) {
        if (!darkGrid.isVisible()) {
            darkGrid.setVisible(true);
        } else {
            darkGrid.setVisible(false);
        }
        drawMap(chara, mapData);
        itemHandl.setDarkGrid(darkGrid);
    }

    // Print actions of user inputs
    public void printAction(String actionString) {
        System.out.println("Action: " + actionString);
    }

}
