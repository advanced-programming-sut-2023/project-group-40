package view.enums;

import view.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands{
    REGISTER("^user create (?=.*-u( (?<username>\\S*))?)(?=.*-p( (?<password>\\S*))? (?<passwordConfirmation>\\S*))(?=.*--email( (?<email>\\S*))?)(?=.*-n( (?<nickname>(\"[^\"]*\")|([^\"\\s]*)))?)(?=.*(?<sloganExist>-s) (?<slogan>(\"[^\"]*\")|([^\"\\s]*)))?.*$", RegisterMenu.class,"register"),
    LOGIN("user login (?=.*-u( (?<username>\\S*))?)(?=.*-p( (?<password>\\S*))?)(?=.*(?<stayLoggedIn>--stay-logged-in))?.*|user login",LoginMenu.class,"login"),
    FORGET_PASSWORD("forget my password -u (?<username>\\S+)",LoginMenu.class,"forgetPassword"),
    LOGOUT("user logout", MainMenu.class,"logout"),
    ENTER_REGISTER("enter register menu",LoginMenu.class,"register"),
    ENTER_LOGIN("enter login menu",RegisterMenu.class,"login"),
    ENTER_PROFILE_MENU("enter profile menu",MainMenu.class,"enterProfileMenu"),
    ENTER_GAME_MENU("enter game menu", MainMenu.class,"enterGameMenu"),
    EXIT("exit",LoginMenu.class,"exit"),
    PROFILE_CHANGE_USERNAME("profile change -u (?<username>(\"[^\"]*\")|([^\"\\s]*))", ProfileMenu.class, "changeUsername"),
    PROFILE_CHANGE_NICKNAME("profile change -n (?<nickname>(\"[^\"]*\")|([^\"\\s]*))", ProfileMenu.class, "changeNickname"),
    PROFILE_CHANGE_EMAIL("profile change -e (?<email>(\"[^\"]*\")|([^\"\\s]*))", ProfileMenu.class, "changeEmail"),
    PROFILE_CHANGE_SLOGAN("profile change -s (?<slogan>(\"[^\"]*\")|([^\"\\s]*))", ProfileMenu.class, "changeSlogan"),
    PROFILE_CHANGE_PASSWORD("profile change -o (?<oldPassword>(\"[^\"]*\")|([^\"\\s]*)) -n (?<newPassword>(\"[^\"]*\")|([^\"\\s]*))", ProfileMenu.class, "changePassword"),
    PROFILE_REMOVE_SLOGAN("profile remove slogan", ProfileMenu.class, "removeSlogan"),
    PROFILE_DISPLAY_HIGH_SCORE("profile display highscore", ProfileMenu.class, "displayHighscore"),
    PROFILE_DISPLAY_RANK("profile display rank", ProfileMenu.class, "displayRank"),
    PROFILE_DISPLAY_SLOGAN("profile display slogan", ProfileMenu.class, "displaySlogan"),
    PROFILE_DISPLAY("profile display", ProfileMenu.class, "profileDisplay"),
    SHOW_MAP("show map -x (?<x>\\d+) -y (?<y>\\d+)", GameMenu.class,"showMap"),
    CHANGE_SIGHT_AREA("map (?<left>left(\\s(?<leftNumber>\\d+))?)|(?<top>top(\\s(?<topNumber>\\d+))?)|(?<right>right(\\s(?<rightNumber>\\d+))?)|(?<down>down(\\s(?<downNumber>\\d+))?)", MapMenu.class,"changeSightArea"),
    SHOW_DETAILS("show details -x (?<x>\\d+) -y (?<y>\\d+)",MapMenu.class,"showDetails"),
    DROP_BUILDING("drop building -x (?<x>\\d+) -y (?<y>\\d+) -type (?<type>\\w+)", GameMenu.class,"dropBuilding"),
    SELECT_BUILDING("select building -x (?<x>\\d+) -y (?<y>\\d+)", GameMenu.class,"selectBuilding"),
    CREATE_UNIT("create unit -t (?<type>\\w+) -c (?<count>\\d)", GameMenu.class,"createUnit"),
    SHOW_POPULARITY_FACTORS("show popularity factors", GameMenu.class, "showPopularityFactors"),
    SHOW_POPULARITY("show popularity", GameMenu.class, "showPopularity"),
    SHOW_FOOD_LIST("show food list", GameMenu.class, "showFoodList"),
    SET_FOOD_RATE("food rate -r (?<rateNumber>\\d+)", GameMenu.class, "setFoodRate"),
    SET_TAX_RATE("tax rate -r (?<rateNumber>\\d+)", GameMenu.class, "setTaxRate"),
    SET_FEAR_RATE("fear rate -r (?<rateNumber>\\d+)", GameMenu.class, "fearTaxRate"),
    SHOW_FOOD_RATE("food rate show", GameMenu.class, "showFoodRate"),
    SHOW_TAX_RATE("tax rate show", GameMenu.class, "showTaxRate"),
    SHOW_FEAR_RATE("fear rate show", GameMenu.class, "showFoodRate"),
//    SET_TEXTURE("set texture -x (?<x>\\d+) -y (?<y>\\d+) -t (?<type>\\w+)|set texture -x1 (?<x1>\\d+) -x2 (?<x2>\\d+) -y1 (?<y1>\\d+) -y2 (?<y2>\\d+) -t (?<type>\\w+)", EnvironmentMenu.class,"setTexture"),
    DROP_ROCK("drop rock -x (?<x>\\d+) -y (?<y>\\d+) -d (?<direction>\\w+)", EnvironmentMenu.class,"dropRock"),
    NEXT_TURN("next turn", EnvironmentMenu.class,"nextTurn"),
    DROP_TREE("drop tree -x (?<x>\\d+) -y (?<y>\\d+) -type (?<type>\\w+)", EnvironmentMenu.class,"dropTree"),
    CLEAR_BLOCK("clear block -x (?<x>\\d+) -y (?<y>\\d+)",EnvironmentMenu.class,"clearBlock"),
    BUILD_EQUIPMENT("build -q (?<equipmentName>\\w+)",GameMenu.class,"buildEquipments"),
    SELECT_UNIT("select unit -x (?<x>\\d+) -y (?<y>\\d+)",GameMenu.class,"selectUnit"),
    MOVE_UNIT("move unit to -x (?<x>\\d+) -y (?<y>\\d+)",GameMenu.class,"moveUnit"),
    DROP_WALL("drop wall -x (?<x>\\d+) -y (?<y>\\d+) -t (?<thickness>\\d+) -h (?<height>\\d+) -d (?<direction>\\w+)",GameMenu.class,"dropWall"),
    DROP_TOWER("drop tower -x (?<x>\\d+) -y (?<y>\\d+)",GameMenu.class,"dropTower"),
    DROP_TURRET("drop turret -x (?<x>\\d+) -y (?<y>\\d+)",GameMenu.class,"dropTurret"),
    START_DIGGING_DITCH("start digging ditch -x (?<x>\\d+) -y (?<y>\\d+)",GameMenu.class,"startDiggingDitch"),
    STOP_DIGGING_DITCH("stop digging ditch -x (?<x>\\d+) -y (?<y>\\d+)",GameMenu.class,"stopDiggingDitch"),
    DELETE_DITCH("delete ditch -x (?<x>\\d+) -y (?<y>\\d+)",GameMenu.class,"deleteDitch"),
    CAPTURE_GATE("capture the gate -x (?<x>\\d+) -y (?<y>\\d+)",GameMenu.class,"captureTheGate"),
    FILL_DITCH("fill ditch -x (?<x>\\d+) -y (?<y>\\d+)",GameMenu.class,"fillDitch"),
    DIG_TUNNEL("dig tunnel -x (?<x>\\d+) -y (?<y>\\d+)",GameMenu.class,"digTunnel"),
    ;

    private final String regex;
    private final String methodName;
    private final Class<?> menuClass;

    Commands(String regex, Class<?> menuClass, String methodName) {
        this.regex = regex;
        this.menuClass = menuClass;
        this.methodName = methodName;
    }

    public static String regexFinder(String inputCommand, Class<?> currentClass) throws ReflectiveOperationException {
        for (Commands command : values()) {
            Matcher matcher = Pattern.compile(command.regex).matcher(inputCommand);
            if (currentClass != command.menuClass) continue;
            if (matcher.matches())
                return (String) command.menuClass.getMethod(command.methodName, Matcher.class).invoke(null, matcher);
        }
        return "invalid command!";
    }

    public String getRegex() {
        return regex;
    }
}
