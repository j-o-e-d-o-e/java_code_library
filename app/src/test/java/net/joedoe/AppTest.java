/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package net.joedoe;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AppTest {
    @Test
    public void appHasAGreeting() {
        assertNotNull("library should have entries", App.setupLib());
    }
}
