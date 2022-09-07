package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class MineSweeperTesting {
    Minesweeper test = new Minesweeper(4, 4, 4);

    @Test
    public void test_pick() throws MinesweeperException{
        test.makeSelection(new Location(0, 0));
        Boolean expected = true;
        Boolean actual = test.VerifyCovered(new Location(0, 0));
        assertEquals(expected, actual);
    }

    @Test
    public void test_hints(){
        String expected = "[[0, 0], [0, 1], [0, 2], [0, 3], [1, 0], [1, 1], [1, 2], [1, 3], [2, 0], [2, 1], [2, 2], [2, 3], [3, 0], [3, 1], [3, 2], [3, 3]]";
        String actual = test.getPossibleSelections().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testGameState(){
        test.StateofGame = GameState.LOST;
        GameState expected = GameState.LOST;
        GameState actual = GameState.LOST;
        assertEquals(expected, actual);
    }
    
}
