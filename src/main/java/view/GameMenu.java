package view;

import view.enums.Commands;

public class GameMenu {


    public static int[] getCoordinate() {
        System.out.println("type your coordinate :");
        int[] coordinates = new int[2];
        coordinates[0] = Commands.scanner.nextInt();
        coordinates[1] = Commands.scanner.nextInt();
        return coordinates;
    }

}
