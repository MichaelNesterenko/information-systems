package mishanesterenko.informationsystems.logicsolve;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mishanesterenko.informationsystems.logicsolve.domain.Production;

/**
 * @author Michael Nesterenko
 *
 */
public class Engine {
    /**
     * 
     */
    private static final String STOP_THEN = "1";

    private List<Production> productions;

    private List<List<Integer>> conflicts = new ArrayList<List<Integer>>();

    private List<Set<String>> workingMemory = new ArrayList<Set<String>>();

    private boolean isSolved;

    public Engine(final List<Production> prods, final Set<String> memory) {
        if (prods == null || memory == null) {
            throw new NullPointerException(prods == null ? "prods " : "" + memory == null ? "memory" : "");
        }
        productions = prods;
        workingMemory.add(memory);
    }

    public void run() {
        if (conflicts.size() > 0) {
            return;
        }

        Set<Production> usedProductions = new HashSet<Production>();
        conflicts.add(new ArrayList<Integer>());
        boolean stopEngine = false;
        int stepNum = 1;
        do {
            List<Integer> currentConflicts = new ArrayList<Integer>(conflicts.get(stepNum - 1));
            Set<String> currentMemory = new HashSet<String>(workingMemory.get(stepNum - 1));
            workingMemory.add(currentMemory);
            conflicts.add(currentConflicts);

            for (int productionIndex = 0; productionIndex < productions.size(); ++productionIndex) {
                Production prod = productions.get(productionIndex);
                if (currentMemory.contains(prod.getIf()) && !usedProductions.contains(prod)) {
                    currentConflicts.add(productionIndex);
                    usedProductions.add(prod);
                }
            }
            if (currentConflicts.size() == 0) {
                stopEngine = true;
            } else {
                String addedThen = productions.get(currentConflicts.get(currentConflicts.size() - 1)).getThen();
                currentConflicts.remove(currentConflicts.size() - 1);
                currentMemory.add(addedThen);
                stopEngine = isSolved = STOP_THEN.equals(addedThen);
            }
            stepNum++;
        } while (!stopEngine);
    }

    public int getStepCount() {
        return conflicts.size();
    }

    public List<Integer> getConflicts(final int step) {
        return Collections.unmodifiableList(conflicts.get(step));
    }

    public Set<String> getWorkingMemory(final int step) {
        return Collections.unmodifiableSet(workingMemory.get(step));
    }

    public boolean isSolved() {
        return isSolved;
    }
}
