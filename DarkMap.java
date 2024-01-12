import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DarkMap {
    public static final int TYPE_DARK = 0;
    private static final String darkImageFiles[] = {
            "png/Dark/dark.png",
            "png/Dark/dark1.png",
            "png/Dark/dark2.png",
            "png/Dark/dark3.png",
            "png/Dark/dark4.png",
            "png/Dark/dark5.png",
            "png/Dark/dark6.png",
            "png/Dark/dark7.png",
            "png/Dark/dark8.png",
            "png/Dark/dark9.png"
    };
    private Image[] darkImages;
    private ImageView[][] darkImageViews;
    private int[][] maps;
    private int width; // width of the map
    private int height; // height of the map
    private MapData mapData;

    DarkMap(int x, int y, MoveChara chara) {
        darkImages = new Image[10];
        darkImageViews = new ImageView[y][x];
        for (int i = 0; i < 10; i++) {
            darkImages[i] = new Image(darkImageFiles[i]);
        }
        width = x;
        height = y;
        maps = new int[y][x];
        fillDarkMap(chara);
        setImageViews();
    }

    private void fillDarkMap(MoveChara chara) {
        int cx = chara.getPosX();
        int cy = chara.getPosY();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                maps[y][x] = darkCharaLocation(x, y, cx, cy);
            }
        }
    }

    public void darkMove(MoveChara chara) {
        int cx = chara.getPosX();
        int cy = chara.getPosY();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                maps[y][x] = darkCharaLocation(x, y, cx, cy);
            }
        }
        setImageViews();
    }

    private int darkCharaLocation(int x, int y, int cx, int cy) {// -4,-3,-2,-1,0,1,2,3,4
        if (x == cx - 1 && y == cy - 1) {
            return 1;
        } else if (x == cx && y == cy - 1) {
            return 2;
        } else if (x == cx + 1 && y == cy - 1) {
            return 3;
        } else if (x == cx - 1 && y == cy) {
            return 4;
        } else if (x == cx && y == cy) {
            return 5;
        } else if (x == cx + 1 && y == cy) {
            return 6;
        } else if (x == cx - 1 && y == cy + 1) {
            return 7;
        } else if (x == cx && y == cy + 1) {
            return 8;
        } else if (x == cx + 1 && y == cy + 1) {
            return 9;
        } else {
            return TYPE_DARK;
        }
    }

    public int getMap(int x, int y) {
        if (x < 0 || width <= x || y < 0 || height <= y) {
            return -1;
        }
        return maps[y][x];
    }

    public void setMap(int x, int y, int type) {
        if (x < 1 || width <= x - 1 || y < 1 || height <= y - 1) {
            return;
        }
        maps[y][x] = type;
    }

    public ImageView getImageView(int x, int y) {
        return darkImageViews[y][x];
    }

    public void setImageViews() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                darkImageViews[y][x] = new ImageView(darkImages[maps[y][x]]);
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
