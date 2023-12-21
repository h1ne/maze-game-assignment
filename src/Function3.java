import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;

public class Function3 extends MapGameController{
    public Function3(){
        super();
    }

    @Override
    public void func3ButtonAction(ActionEvent event) {
        //System.out.println("Cat isn't in default position");
        // int cx = chara.getPosX();
        // int cy = chara.getPosY();
        //  mapData = new MapData(21, 15,cx,cy);
        //     chara = new MoveChara(chara.getPosX(), chara.getPosY(), mapData);
        //     mapImageViews = new ImageView[mapData.getHeight() * mapData.getWidth()];
        //     for (int y = 0; y < mapData.getHeight(); y ++) {
        //         for (int x = 0; x < mapData.getWidth(); x ++) {
        //             int index = y * mapData.getWidth() + x;
        //             mapImageViews[index] = mapData.getImageView(x, y);
        //     }
        //     }
        // drawMap(chara, mapData);
        if(chara.getPosX() ==1&& chara.getPosY()==1){
            mapData = new MapData(21, 15);
            chara = new MoveChara(chara.getPosX(), chara.getPosY(), mapData);
            mapImageViews = new ImageView[mapData.getHeight() * mapData.getWidth()];
            for (int y = 0; y < mapData.getHeight(); y ++) {
                for (int x = 0; x < mapData.getWidth(); x ++) {
                    int index = y * mapData.getWidth() + x;
                    mapImageViews[index] = mapData.getImageView(x, y);
            }
            }
        drawMap(chara, mapData);
        }
        
    }
}
