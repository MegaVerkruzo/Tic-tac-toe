package game.players;

import java.util.Arrays;

public class PlayerList {
    private Player[] array;
    private int lengthOfArray;

    public PlayerList() {
        array = new Player[0];
        int lengthOfArray = 0;
    }

    public void clearEmptyCapacity() {
        array = Arrays.copyOf(array, lengthOfArray);
    }

    public void add(Player player) {
        updateSize();
        array[lengthOfArray++] = player;
    }

    public void updateSize() {
        if (array.length == lengthOfArray) {
            array = Arrays.copyOf(array, Math.max(lengthOfArray * 2, 1));
        }
    }

    public Player[] getArray() {
        clearEmptyCapacity();
        return array;
    }


}
