import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ItemHandler {

    private MapData mapData;
    private Item item;
    private GridPane darkGrid;
    private MoveChara moveChara;

    public ItemHandler(MapData mapData, MoveChara input, GridPane darkGrid) {
        this.mapData = mapData;
        this.darkGrid = darkGrid;
        this.moveChara = input;
    }

    public void handleItem(int itemType) {

        switch (itemType) {
            case 2: // item1
                if (darkGrid.isVisible()) {
                    darkGrid.setVisible(false);
                }
                break;
            case 3: // item4
                if (darkGrid.isVisible()) {
                    darkGrid.setVisible(false);
                }
                break;
            case 4: // item5
                if (darkGrid.isVisible()) {
                    darkGrid.setVisible(false);
                }
                break;
            case 5: // item2
                if (darkGrid.isVisible()) {
                    darkGrid.setVisible(false);
                }
                break;
            case 6: // item3
                if (darkGrid.isVisible()) {
                    darkGrid.setVisible(false);
                }
                break;
            // Handle item3 specific actions

            default:
                // Handle other item types if needed
                break;
        }
        darkTimer();
    }

    public void setDarkGrid(GridPane input) {
        this.darkGrid = input;
    }

    public void setMoveChara(MoveChara input) {
        this.moveChara = input;
    }

    private void darkTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // ここに3秒後に実行させたい処理を記述
                darkGrid.setVisible(true);
                System.out.println("3秒後に実行された処理");
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000); // 3000ミリ秒（＝3秒）後に実行
    }

    private void warpToItem3(int input) {
        // Implement the logic to warp to item3

    }
}
