package com.example.restaurantvoting;

import com.example.restaurantvoting.model.Menu;

import java.util.HashSet;
import java.util.List;

public class MenuTestData {
    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Menu.class);

    public static final int MENU1_ID = 1;

    public static final Menu menu1 = new Menu(MENU1_ID, null);

    public static List<Menu> menuList = List.of(menu1);

    static {
        menu1.setMenu(new HashSet<>());
    }

}
