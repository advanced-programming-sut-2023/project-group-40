package view.enums;

import view.*;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    REGISTER("(?=.* -u (?<username>(\"[^\"]*\")|(\\S*)))(?=.* -e (?<email>(\"[^\"]*\")|(\\S*)))(?=.* -n (?<nickname>(\"[^\"]*\")|(\\S*)))(?=.* -s (?<slogan>(\"[^\"]*\")|(\\S*)))?(?=.* -p (?<password>(\"[^\"]*\")|(\\S*)))(?=.* -c (?<passwordConfirmation>(\"[^\"]*\")|(\\S*)))?^user create( -[upcens] ((\"[^\"]*\")|(\\S*))){5,6}$", RegisterMenu.class, "register"),
    PICK_SECURITY_QUESTION("(?=.* -q (?<securityQuestionNo>\\d+))(?=.* -a (?<answer>(\"[^\"]+\")|(\\S+)))(?=.* -c (?<answerConfirm>((\"[^\"]+\")|(\\S+))))^question pick( -[acq] ((\"[^\"]+\")|(\\S+))){3}$"),
    LOGIN("(?=.* -u (?<username>(\"[^\"]+\")|(\\S+)))(?=.* -p (?<password>(\"[^\"]+\")|(\\S+)))^user login( -[up] ((\"[^\"]+\")|(\\S+))){2}(?<stayLoggedIn> --stay-logged-in)?$", LoginMenu.class, "login"),
    FORGET_PASSWORD("forget my password -u (?<username>\\S+)", LoginMenu.class, "forgetPassword"),
    LOGOUT("user logout", MainMenu.class, "logout"),
    ENTER_REGISTER("enter register menu", LoginMenu.class, "register"),
    ENTER_LOGIN("enter login menu", RegisterMenu.class, "login"),
    ENTER_PROFILE_MENU("enter profile menu", MainMenu.class, "enterProfileMenu"),
    ENTER_GAME_MENU("enter game menu", MainMenu.class, "enterGameMenu"),
    EXIT("exit", LoginMenu.class, "exit"),
    PROFILE_CHANGE_USERNAME("profile change -u (?<username>(\"[^\"]+\")|([\\s]+))", ProfileMenu.class, "changeUsername"),
    PROFILE_CHANGE_NICKNAME("profile change -n (?<nickname>(\"[^\"]+\")|([\\s]+))", ProfileMenu.class, "changeNickname"),
    PROFILE_CHANGE_EMAIL("profile change -e (?<email>(\"[^\"]+\")|([\\s]+))", ProfileMenu.class, "changeEmail"),
    PROFILE_CHANGE_SLOGAN("profile change -s (?<slogan>(\"[^\"]+\")|([\\s]+))", ProfileMenu.class, "changeSlogan"),
    PROFILE_CHANGE_PASSWORD("profile change -o (?<oldPassword>(\"[^\"]+\")|([\\s]+)) -n (?<newPassword>(\"[^\"]+\")|([\\s]+))", ProfileMenu.class, "changePassword"),
    PROFILE_REMOVE_SLOGAN("profile remove slogan", ProfileMenu.class, "removeSlogan"),
    PROFILE_DISPLAY_HIGH_SCORE("profile display highscore", ProfileMenu.class, "displayHighscore"),
    PROFILE_DISPLAY_RANK("profile display rank", ProfileMenu.class, "displayRank"),
    PROFILE_DISPLAY_SLOGAN("profile display slogan", ProfileMenu.class, "displaySlogan"),
    PROFILE_DISPLAY("profile display", ProfileMenu.class, "displayProfile"),
    ENTER_MAP_MENU("enter map menu", GameMenu.class, "enterMapMenu"),
    SHOW_MAP("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))^show map( -[xy] \\S+){2}$", MapMenu.class, "showMap"),
    CHANGE_SIGHT_AREA("map (?<left>left(\\s(?<leftNumber>\\d+))?)|(?<top>top(\\s(?<topNumber>\\d+))?)|(?<right>right(\\s(?<rightNumber>\\d+))?)|(?<down>down(\\s(?<downNumber>\\d+))?)", MapMenu.class, "changeSightArea"),
    SHOW_DETAILS("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))^show details( -[xy] \\S+){2}$", MapMenu.class, "showDetails"),
    DROP_BUILDING("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))(?=.* -t (?<type>((\"[^\"]+\")|(\\S+))))^drop building( -[xyt] ((\"[^\"]+\")|(\\S+))){3}$", GameMenu.class, "dropBuilding"),
    SELECT_BUILDING("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))^select building( -[xy] \\S+){2}$", GameMenu.class, "selectBuilding"),
    CREATE_UNIT("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))(?=.* -t (?<type>((\"[^\"]+\")|(\\S+))))(?=.* -c (?<count>\\d+))^create unit( -[xytc] ((\"[^\"]+\")|(\\S+))){4}$", GameMenu.class, "createUnit"),
    SET_UNIT_STATE("set -s (?<state>\\w+)$", GameMenu.class, "setUnitState"),
    DISBAND_UNIT("disband unit$", GameMenu.class, "disbandUnit"),
    ATTACK_ENEMY("attack -e (?<x>\\d+) (?<y>\\d+)", GameMenu.class, "attackEnemy"),
    AIR_ATTACK("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))^attack( -[xy] \\S+){2}$", GameMenu.class, "airAttack"),
    POUR_OIL("pour oil -d (?<direction>\\w+)$", GameMenu.class, "pourOil"),
    MOVE_TOOL("(?=.* -toolX (?<toolX>\\d+))(?=.* -toolY (?<toolY>\\d+))(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))^move tool( -((toolX)|(toolY)|(x)|(y)) ((\"[^\"]+\")|(\\S+))){4}$", GameMenu.class, "moveTool"),
    PATROL_UNIT("(?=.* -x1 (?<x1>\\d+))(?=.* -y1 (?<y1>\\d+))(?=.* -x2 (?<x2>\\d+))(?=.* -y2 (?<y2>\\d+))^patrol unit( -((y1)|(y2)|(x1)|(x2)) (\\d+)){4}$", GameMenu.class, "patrolUnit"),
    STOP_PATROL("stop patrolling", GameMenu.class, "stopPatrolUnit"),
    SHOW_POPULARITY_FACTORS("show popularity factors", GameMenu.class, "showPopularityFactors"),
    SHOW_POPULARITY("show popularity", GameMenu.class, "showPopularity"),
    SHOW_FOOD_LIST("show food list", GameMenu.class, "showFoodList"),
    SET_FOOD_RATE("food rate -r (?<rateNumber>\\d+)", GameMenu.class, "setFoodRate"),
    SET_TAX_RATE("tax rate -r (?<rateNumber>\\d+)", GameMenu.class, "setTaxRate"),
    SET_FEAR_RATE("fear rate -r (?<rateNumber>\\d+)", GameMenu.class, "fearTaxRate"),
    SHOW_FOOD_RATE("food rate show", GameMenu.class, "showFoodRate"),
    SHOW_TAX_RATE("tax rate show", GameMenu.class, "showTaxRate"),
    SHOW_FEAR_RATE("fear rate show", GameMenu.class, "showFoodRate"),
    SET_TEXTURE("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))(?=.* -t (?<type>((\"[^\"]+\")|(\\S+))))^set texture( -[xyt] ((\"[^\"]+\")|(\\S+))){3}$", EnvironmentMenu.class, "setTexture"),
    SET_TEXTURE_2("(?=.* -x1 (?<x1>\\d+))(?=.* -y1 (?<y1>\\d+))(?=.* -x2 (?<x2>\\d+))(?=.* -y2 (?<y2>\\d+))(?=.* -t (?<type>((\"[^\"]+\")|(\\S+))))^set texture( -((y1)|(y2)|(x1)|(x2)|(t)) ((\"[^\"]+\")|(\\S+))){5}$", EnvironmentMenu.class, "setTexture"),
    DROP_ROCK("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))(?=.* -d (?<direction>\\S+))^drop rock( -[xyd] \\S+){3}$", EnvironmentMenu.class, "dropRock"),
    NEXT_TURN("next turn", GameMenu.class, "nextTurn"),
    DROP_TREE("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))(?=.* -t (?<type>((\"[^\"]+\")|(\\S+))))^drop tree( -[xytc] ((\"[^\"]+\")|(\\S+))){3}$", EnvironmentMenu.class, "dropTree"),
    CLEAR_BLOCK("clear block -x (?<x>\\d+) -y (?<y>\\d+)", EnvironmentMenu.class, "clearBlock"),
    BUILD_EQUIPMENT("build -q (?<equipmentName>((\"[^\"]+\")|(\\S+)))", GameMenu.class, "buildEquipments"),
    SELECT_UNIT("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))^select unit( -[xy] \\S+){2}$", GameMenu.class, "selectUnit"),
    MOVE_UNIT("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))^move unit to( -[xy] \\S+){2}$", GameMenu.class, "moveUnit"),
    DROP_WALL("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))(?=.* -t (?<thickness>\\d+))(?=.* -h (?<height>\\d+))(?=.* -t (?<direction>\\S+))^drop wall( -[xythd] \\S+){5}$", GameMenu.class, "dropWall"),
    DROP_STAIR("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))^drop stair( -[xy] \\d+){2}$", GameMenu.class, "dropStair"),
    ATTACK_TOOL("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))^attack tool( -[xy] \\d+){2}$", GameMenu.class, "attackTool"),
    ATTACK_CASTLE("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))^attack castle( -[xy] \\d+){2}$", GameMenu.class, "attackCastle"),
    START_DIGGING_DITCH("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))^start digging ditch( -[xy] \\S+){2}$", GameMenu.class, "startDiggingDitch"),
    STOP_DIGGING_DITCH("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))^start digging ditch( -[xy] \\S+){2}$", GameMenu.class, "stopDiggingDitch"),
    DELETE_DITCH("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))^delete ditch( -[xy] \\S+){2}$", GameMenu.class, "deleteDitch"),
    FILL_DITCH("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))^fill ditch( -[xy] \\S+){2}$", GameMenu.class, "fillDitch"),
    DIG_TUNNEL("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))^dig tunnel( -[xy] \\S+){2}$", GameMenu.class, "digTunnel"),
    CAPTURE_GATE("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))^stop digging ditch( -[xy] \\S+){2}$", GameMenu.class, "captureTheGate"),
    ENTER_TRADE_MENU("enter trade menu", GameMenu.class, "trade"),
    SEND_REQUEST("(?=.* -t (?<resourceType>((\"[^\"]+\")|(\\S+))))(?=.* -a (?<resourceAmount>\\d+))(?=.* -p (?<price>\\d+))(?=.* -m (?<message>((\"[^\"]+\")|(\\S+))))^trade( -[tamp] ((\"[^\"]+\")|(\\S+))){4}$", TradeMenu.class, "sendRequest"),
    SHOW_TRADE_LIST("trade list", TradeMenu.class, "showTradeList"),
    ACCEPT_TRADE("(?=.* -i (?<id>\\d+))(?=.* -m (?<message>((\"[^\"]+\")|(\\S+))))^trade accept( -[im] ((\"[^\"]+\")|(\\S+))){2}$", TradeMenu.class, "acceptTrade"),
    SHOW_TRADE_HISTORY("trade history", TradeMenu.class, "showTradeHistory"),
    SHOW_PRICE_LIST("show price list", ShopMenu.class, "showPriceList"),
    BUY("(?=.* -i (?<name>((\"[^\"]+\")|(\\S+))))(?=.* -a (?<amount>\\d+))^buy( -[ia] ((\"[^\"]+\")|(\\S+))){2}$", ShopMenu.class, "buy"),
    SELL("(?=.* -i (?<name>((\"[^\"]+\")|(\\S+))))(?=.* -a (?<amount>\\d+))^sell( -[ia] ((\"[^\"]+\")|(\\S+))){2}$", ShopMenu.class, "sell");

    private final String regex;
    private String methodName;
    private Class<?> menuClass;
    public final static Scanner scanner = new Scanner(System.in);

    Commands(String regex, Class<?> menuClass, String methodName) {
        this.regex = regex;
        this.menuClass = menuClass;
        this.methodName = methodName;
    }

    Commands(String regex) {
        this.regex = regex;
    }

    public static String regexFinder(String inputCommand, Class<?> currentClass) throws ReflectiveOperationException {
        for (Commands command : values()) {
            Matcher matcher = Pattern.compile(command.regex).matcher(inputCommand);
            if (currentClass != command.menuClass) continue;
            if (matcher.find())
                return (String) command.menuClass.getMethod(command.methodName, Matcher.class).invoke(null, matcher);
        }
        return "invalid command!";
    }

    public boolean canMatch(String input) {
        return Pattern.compile(regex).matcher(input).matches();
    }

    public Matcher getMatcher(String input) {
        return Pattern.compile(regex).matcher(input);
    }

    public String getRegex() {
        return regex;
    }

    public static String eraseQuot(String input) {
        if (input == null) return null;
        if (input.charAt(0) == '"' && input.charAt(input.length() - 1) == '"')
            return input.substring(1, input.length() - 1);
        return input;
    }
}
