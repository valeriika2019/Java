package sk.tuke;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.game.core.Symbol;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WoodDiceTest {
    @Test
    public void testGetSymbolFromInt() {
        assertEquals(Symbol.FOUR,Symbol.getSymbolFromInt(4));

    }

}
