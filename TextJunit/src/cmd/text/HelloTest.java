package cmd.text;

import cmd.play.Hello;
import org.junit.After;
import org.junit.Test;

public class HelloTest {
    @After
    public void bye() {
        System.out.println("bye");
    }

    @Test
    public void testhello() {
        Hello a = new Hello();
        a.hello();

    }
}
