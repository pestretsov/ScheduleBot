package org.anstreth.hello;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class HelloControllerTest {

    private HelloController helloController = new HelloController();

    @Test
    public void indexMethodReturnsHello() throws Exception {
        assertThat(helloController.index(), is("Hello!"));

    }
}