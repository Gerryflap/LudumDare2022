package nl.lipsum.gameLogic.playermodel;

import com.badlogic.gdx.Gdx;
import nl.lipsum.LudumDare2022;
import nl.lipsum.gameLogic.Army;

import java.util.Random;

public class AIPlayerModel extends PlayerModel {
    private Random random;
    public AIPlayerModel(){
        super();
        random = new Random();
    }

    public void step(){
        for(Army army:armies){
            if(random.nextDouble()* 25 < Gdx.graphics.getDeltaTime()){
                int i = random.nextInt(LudumDare2022.gameController.getBaseGraph().getBases().size());
                army.goTo(LudumDare2022.gameController.getBaseGraph().getBases().get(i));
//                random.getRandomFromList
//                army.goTo(base);
            }
        }
    }
}
