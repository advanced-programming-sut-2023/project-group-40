package model;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public enum Tree {
    DESERT_SHRUB(),
    CHERRY_PALM(),
    OLIVE_TREE(),
    COCONUT_PALM(),
    DATE_PALM();
    public static boolean checkType(String type){
        return Arrays.stream(values()).anyMatch(tree -> type.equals(tree.name().toLowerCase()));
    }
    public static Tree getTree(String type) {
        Stream<Tree> stream =  Arrays.stream(values()).filter(tree -> type.equals(tree.name().toLowerCase()));
        Optional<Tree> optional = stream .findAny();
        return optional.orElse(null);
    }
}
