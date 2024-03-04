import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Item {

    public MapData mapData;
    private ImageView[] itemImageViews;
    public ItemHandler itemHandle;

    public Item(MapData input, ItemHandler input_IH) {
        this.mapData = input;
        this.itemHandle = input_IH;
        initializeItemImageViews();
        placeItemsOnMap();
    }

    // Initialize item images
    private void initializeItemImageViews() {
        itemImageViews = new ImageView[5]; // Assuming 5 different items
        for (int i = 0; i < 5; i++) {
            String imagePath = "png/item" + (i + 1) + ".jpg"; // Adjust the path accordingly
            Image itemImage = new Image(imagePath);
            itemImageViews[i] = new ImageView(itemImage);
        }
    }

    // Place items on the map
    private void placeItemsOnMap() {
        Random random = new Random();
        int itemsPlaced = 0;

        while (itemsPlaced < 5) {
            int x = random.nextInt(mapData.getWidth());
            int y = random.nextInt(mapData.getHeight());
            if (mapData.getMap(x, y) == MapData.TYPE_SPACE) {
                int itemType = MapData.TYPE_OTHERS + itemsPlaced; // Assuming TYPE_OTHERS is the starting index for //
                                                                  // items
                mapData.setMap(x, y, itemType);
                itemsPlaced++;
            }
        }
    }

    public void removeItemAt(int x, int y) {
        if (mapData.getMap(x, y) >= MapData.TYPE_OTHERS
                && mapData.getMap(x, y) < MapData.TYPE_OTHERS + itemImageViews.length) {
            // アイテムを取得したら、その位置を通路に変更
            itemHandle.handleItem(getItemAt(x, y));
            mapData.setMap(x, y, MapData.TYPE_SPACE);
        }
    }

    // Get the item at a specific location
    public int getItemAt(int x, int y) {
        return mapData.getMap(x, y);
    }

    // Get the ImageView for a specific item type
    public ImageView getItemImageView(int itemType) {
        if (itemType >= MapData.TYPE_OTHERS && itemType < MapData.TYPE_OTHERS + itemImageViews.length) {
            return itemImageViews[itemType - MapData.TYPE_OTHERS];
        }
        return null;
    }
}
/*
 * import javafx.scene.image.Image;
 * import javafx.scene.image.ImageView;
 * 
 * public class Item {
 * 
 * public static final int MAX_ITEMS = 5;
 * private MapData mapData;
 * private int[][] items;
 * private Image[] itemImages;
 * 
 * public Item(MapData mapData) {
 * this.mapData = mapData;
 * this.items = new int[mapData.getHeight()][mapData.getWidth()];
 * this.itemImages = new Image[]{
 * new Image("item1.png"),
 * new Image("item2.png"),
 * new Image("item3.png"),
 * new Image("item4.png")
 * };
 * placeItems();
 * }
 * 
 * public int getItemAt(int x, int y) {
 * return items[y][x];
 * }
 * 
 * public ImageView getItemImageView(int x, int y) {
 * int itemType = items[y][x];
 * if (itemType >= 0 && itemType < itemImages.length) {
 * ImageView imageView = new ImageView(itemImages[itemType]);
 * imageView.setFitWidth(mapData.getImageView(x, y).getFitWidth());
 * imageView.setFitHeight(mapData.getImageView(x, y).getFitHeight());
 * return imageView;
 * }
 * return null;
 * }
 * 
 * private void placeItems() {
 * int itemsPlaced = 0;
 * 
 * while (itemsPlaced < MAX_ITEMS) {
 * int x = getRandomCoordinate(mapData.getWidth());
 * int y = getRandomCoordinate(mapData.getHeight());
 * 
 * if (mapData.getMap(x, y) == MapData.TYPE_SPACE && items[y][x] == 0) {
 * items[y][x] = itemsPlaced % itemImages.length; // アイテムの種類を設定
 * itemsPlaced++;
 * }
 * }
 * }
 * 
 * private int getRandomCoordinate(int limit) {
 * return (int) (Math.random() * limit);
 * }
 * }
 */