package mishanesterenko.informationsystems.logicsolve;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mishanesterenko.informationsystems.logicsolve.domain.Production;

import org.junit.Test;

/**
 * @author Michael Nesterenko
 *
 */
public class EngineTest {
    @Test
    public void testEngineSimpleRun() {
        List<Production> productions = Arrays.asList(new Production("A", "B"), new Production("B", "C"), new Production("C", "D"),
                new Production("D", "1"));
        Set<String> memory = new HashSet<String>(Arrays.asList("A"));

        Engine e = new Engine(productions, memory);
        e.run();

        assertTrue(e.isSolved());
    }

    @Test
    public void testEngineMedidumRun() {
        List<Production> productions = Arrays.asList(
                new Production("A", "C"),
                new Production("B", "C"),
                new Production("D", "F"),
                new Production("E", "F"),
                new Production("G", "I"),
                new Production("H", "I"),
                new Production("C", "J"),
                new Production("F", "J"),
                new Production("I", "K"),
                new Production("J", "1"),
                new Production("K", "1"));
        Set<String> memory = new HashSet<String>(Arrays.asList("A", "B", "G", "H"));

        Engine e = new Engine(productions, memory);
        e.run();

        assertTrue(e.isSolved());
    }
}
