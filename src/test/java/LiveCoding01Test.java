import org.example.live_coding.LiveCoding01;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LiveCoding01Test {
    private static LiveCoding01 liveCoding01;

    @BeforeAll
    static void setup() {
        liveCoding01 = new LiveCoding01();
    }

    @Test
    public void success() {
        String solution = liveCoding01.solution("1 1 1 1 1,5 5 5 5 5");
        Assertions.assertEquals("draw", solution);
    }
}