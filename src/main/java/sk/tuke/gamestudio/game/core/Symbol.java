package sk.tuke.gamestudio.game.core;

public enum Symbol {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    EMPTY;



    public static Symbol nextRank(Symbol symbol) {
        switch(symbol) {
            case SIX: {
                return Symbol.EMPTY;
            }
            case FIVE: {
                return Symbol.SIX;
            }
            case FOUR: {
                return Symbol.FIVE;
            }
            case THREE: {
                return Symbol.FOUR;
            }
            case TWO: {
                return Symbol.THREE;
            }
            case ONE: {
                return Symbol.TWO;
            }
            default: {
                return Symbol.EMPTY;
            }
        }
    }

    public static int getSymbolScore(Symbol symbol) {
        switch(symbol) {
            case SIX: {
                return 6;
            }
            case FIVE: {
                return 5;
            }
            case FOUR: {
                return 4;
            }
            case THREE: {
                return 3;
            }
            case TWO: {
                return 2;
            }
            case ONE: {
                return 1;
            }
            default: {
                return 0;
            }
        }
    }


    public static int fromEnumToInt(Symbol symbol) {
        switch(symbol) {
            case SIX: {
                return 6;
            }
            case FIVE: {
                return 5;
            }
            case FOUR: {
                return 4;
            }
            case THREE: {
                return 3;
            }
            case TWO: {
                return 2;
            }
            case ONE: {
                return 1;
            }
            default: {
                return 0;
            }
        }
    }
    public static Symbol getSymbolFromInt(int i) {
        switch(i) {
            case 1: {
                return Symbol.ONE;
            }
            case 2: {
                return  Symbol.TWO;
            }
            case 3: {
                return Symbol.THREE;
            }
            case 4: {
                return Symbol.FOUR;
            }
            case 5: {
                return Symbol.FIVE;
            }
            case 6: {
                return Symbol.SIX;
            }
            default: {
                return Symbol.EMPTY;
            }
        }
    }
}

