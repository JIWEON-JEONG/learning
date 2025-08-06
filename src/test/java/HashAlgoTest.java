import org.example.algorithm.HashAlgo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HashAlgoTest {
    private static HashAlgo hashAlgo;

    @BeforeAll
    static void setup() {
        hashAlgo = new HashAlgo();
    }


    @Test
    public void hash_algo_03() {
        boolean result = hashAlgo.solution03(new String[]{"abcdefg", "abcdef"});
        assert !result;
    }
}
