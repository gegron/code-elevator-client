package fr.binome.elevator.model.context;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CallContext {
    private Map<Integer, Boolean> calls;
    private Integer minLevel;
    private Integer maxLevel;
    private Collection<? extends Integer> callLevels;

    public CallContext(Integer minLevel, Integer maxLevel) {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;

        calls = new HashMap<>();

        for (int i = minLevel; i <= maxLevel; i++) {
            calls.put(i, false);
        }
    }

    public void addCall(Integer floorLevel) {
        calls.put(floorLevel, true);
    }

    public void resetCall(int floorLevel) {
        calls.put(floorLevel, false);
    }

    public Boolean hasCallAtThisLevel(Integer floorLevel) {
        return calls.get(floorLevel);
    }

    public List<Integer> getCallLevels() {
        return Lists.newArrayList(Iterables.filter(calls.keySet(), new Predicate<Integer>() {
            @Override
            public boolean apply(@Nullable Integer level) {
                return calls.get(level);
            }
        }));
    }

}
