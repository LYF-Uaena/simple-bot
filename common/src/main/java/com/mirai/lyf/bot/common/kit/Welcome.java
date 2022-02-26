package com.mirai.lyf.bot.common.kit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Welcome {
    private static final List<String> welcomes = Arrays.asList(
            "不愧是你，小萌新里最靓的崽。",
            "我们完全理解你激动且期待的心情，因为 我们也激动地期待着你。",
            "来都来了，多在这里呆一会吧！",
            "萌新，快到碗里来！",
            "偷偷地，别让人发现你与我相遇的喜悦。",
            "天青色等烟雨，而我在等你~",
            "清水出芙蓉，你是yyds！",
            "有朋自远方来，我们悦乎！",
            "春风十里，终于等到你！",
            "等你好久了，怎么才来啊。"
    );


    public static String getWelcome() {
        int size = welcomes.size();
        Random random = new Random();
        int i = random.nextInt(size);
        return welcomes.get(i);
    }
}
