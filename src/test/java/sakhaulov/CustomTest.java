package sakhaulov;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CustomTest extends AbstractTest{

    @BeforeAll
    static void writeLine() {
        System.out.println("2222");
    }

    @Test
    void doThing() {
        System.out.println("1111");
    }
}
