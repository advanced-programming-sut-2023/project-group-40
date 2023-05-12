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
    CHANGE_SIGHT_AREA("map (?<left>left(\\s(?<leftNumber>\\d+))?)|(?<top>top(\\s(?<topNumber>\\d+))?)|(?<right>right(\\s(?<rightNumber>\\d+))?)|(?<down>down(\\s(?<downNumber>\\d+))?)", GameMenu.class,"changeSightArea"),
    DROP_BUILDING("drop building -x (?<x>\\d+) -y (?<y>\\d+) -type (?<type>\\w+)", GameMenu.class,"dropBuilding"),
    SELECT_BUILDING("select building -x (?<x>\\d+) -y (?<y>\\d+)", GameMenu.class,"selectBuilding"),
    SHOW_POPULARITY_FACTORS("show popularity factors", GameMenu.class, "showPopularityFactors"),
    SHOW_POPULARITY("show popularity", GameMenu.class, "showPopularity"),
    SHOW_FOOD_LIST("show food list", GameMenu.class, "showFoodList"),
    SET_FOOD_RATE("food rate -r (?<rateNumber>\\d+)", GameMenu.class, "setFoodRate"),
    SET_TAX_RATE("tax rate -r (?<rateNumber>\\d+)", GameMenu.class, "setTaxRate"),
    SET_FEAR_RATE("fear rate -r (?<rateNumber>\\d+)", GameMenu.class, "fearTaxRate"),
    SHOW_FOOD_RATE("food rate show", GameMenu.class, "showFoodRate"),
    SHOW_TAX_RATE("tax rate show", GameMenu.class, "showTaxRate"),
    SHOW_FEAR_RATE("fear rate show", GameMenu.class, "showFoodRate"),
    SET_TEXTURE("set texture -x (?<x>\\d+) -y (?<y>\\d+) -t (?<type>\\w+)|set texture -x1 (?<x1>\\d+) -x2 (?<x2>\\d+) -y1 (?<y1>\\d+) -y2 (?<y2>\\d+) -t (?<type>\\w+)", GameMenu.class,"setTexture"),
    DROP_ROCK("drop rock -x (?<x>\\d+) -y (?<y>\\d+) -d (?<direction>\\w+)", GameMenu.class,"dropRock"),
    ENTER_TRADE_MENU("enter trade menu", GameMenu.class, "trade"),
    SEND_REQUEST("trade -t (?<resourceType>\\S+) -n (?<resourceName>\\S+) -a (?<resourceAmount>\\S+) -p (?<price>\\d+) -m (?<message>\\S+)", TradeMenu.class, "sendRequest"),
    SHOW_TRADE_LIST("trade list", TradeMenu.class, "showTradeList"),
    ACCEPT_TRADE("trade accept -i (?<id>\\d+) -m (?<message>\\S+)", TradeMenu.class, "acceptTrade"),
    SHOW_TRADE_HISTORY("trade history", TradeMenu.class, "showTradeHistory"),
    CREATE_UNIT("create unit -t (?<type>\\w+) -c (?<count>\\d)", GameMenu.class,"createUnit"),
    NEXT_TURN("next turn", MapMenu.class,"nextTurn"),
    SHOW_PRICE_LIST("show price list", ShopMenu.class, "showPriceList"),
    BUY("buy -i (?<name>\\S+) -a (?<amount>\\d+)", ShopMenu.class, "buy"),
    sell("sell -i (?<name>\\S+) -a (?<amount>\\d+)", ShopMenu.class, "sell");
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
