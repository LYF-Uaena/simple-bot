package com.mirai.lyf.bot.robot.listener.group;

import catcode.CatCodeUtil;
import com.mirai.lyf.bot.common.kit.AlApi;
import com.mirai.lyf.bot.common.kit.CustomerFilter;
import com.mirai.lyf.bot.common.kit.HttpCode;
import com.mirai.lyf.bot.common.kit.Template;
import com.mirai.lyf.bot.common.utils.StringUtils;
import com.mirai.lyf.bot.persistence.model.alapi.LickDogData;
import com.mirai.lyf.bot.persistence.model.alapi.Response;
import com.mirai.lyf.bot.persistence.model.alapi.TianQiData;
import com.mirai.lyf.bot.persistence.service.alapi.AlApiService;
import com.mirai.lyf.bot.persistence.service.alapi.LickDogService;
import com.mirai.lyf.bot.persistence.service.alapi.ZaoBaoService;
import com.mirai.lyf.bot.persistence.service.system.ConfigService;
import com.mirai.lyf.bot.robot.listener.base.BaseListener;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Filters;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.Reply;
import love.forte.simbot.api.message.ReplyAble;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import love.forte.simbot.filter.MostMatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 监听群聊消息
 */
@Slf4j
@Component
public class GroupAlApiListener extends BaseListener {
    @Autowired
    private LickDogService lickDogService;
    @Autowired
    private AlApiService alApiService;
    @Autowired
    private ZaoBaoService zaoBaoService;

    @Autowired
    public GroupAlApiListener(MessageContentBuilderFactory builderFactory, ConfigService configService) {
        super(builderFactory, configService);

    }

    @OnGroup
    @Filters(
            customMostMatchType = MostMatchType.ALL,
            customFilter = {CustomerFilter.SPEAKING_ROBOT, CustomerFilter.FUNCTION},
            mostMatchType = MostMatchType.ANY,
            value = {
                    @Filter(value = "#宝", matchType = MatchType.STARTS_WITH)
            }
    )
    public Boolean lickDogListener(GroupMsg msg, MsgSender sender) {
        Response<LickDogData> lickDogResponse = lickDogService.lickDog();
        if (lickDogResponse.getCode() == HttpCode.SUCCESS) {
            MessageContentBuilder builder = builderFactory.getMessageContentBuilder();
            builder.at(msg.getAccountInfo().getAccountCode())
                    .text("\n")
                    .text(lickDogResponse.getData().getContent());

            sender.SENDER.sendGroupMsg(msg, builder.build());
        }
        if (lickDogResponse.getCode() == HttpCode.TIMES_UP) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Filters(
            customMostMatchType = MostMatchType.ALL,
            customFilter = {CustomerFilter.SPEAKING_ROBOT, CustomerFilter.FUNCTION},
            mostMatchType = MostMatchType.ANY,
            value = {
                    @Filter(value = "#天气 ", matchType = MatchType.STARTS_WITH)
            }
    )
    @OnGroup
    public ReplyAble weatherListener(GroupMsg msg, MsgSender sender) {
        String city = msg.getMsg().replace("#天气", "").trim();
        if (StringUtils.isEmpty(city)) {
            return Reply.reply("城市不能为空哦。", true);
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("city", city);
        Response<TianQiData> response = alApiService.doHttpGet(AlApi.TIAN_QI, params, TianQiData.class);

        if (response.getCode() == HttpCode.SUCCESS) {
            TianQiData tianqi = response.getData();
            String format = String.format(Template.WEATHER,
                    tianqi.getCity(),
                    tianqi.getWeather(),
                    tianqi.getTemp(),
                    tianqi.getWind(),
                    tianqi.getWindSpeed(),
                    tianqi.getMaxTemp(),
                    tianqi.getMinTemp(),
                    tianqi.getAqi().getAirLevel(),
                    tianqi.getHumidity(),
                    tianqi.getVisibility(),
                    tianqi.getPressure(),
                    tianqi.getRain(),
                    tianqi.getRain24h()
            );
            MessageContentBuilder builder = builderFactory.getMessageContentBuilder();
            builder.at(msg.getAccountInfo().getAccountCode())
                    .text(format);
            sender.SENDER.sendGroupMsg(msg, builder.build());
            return Reply.reject();
        }

        return Reply.reply("我该说点啥！", true);
    }

    @OnGroup
    @Filters(
            customMostMatchType = MostMatchType.ALL,
            customFilter = {CustomerFilter.SPEAKING_ROBOT, CustomerFilter.FUNCTION},
            mostMatchType = MostMatchType.ANY,
            value = {
                    @Filter(value = "#日报", matchType = MatchType.STARTS_WITH)
            }
    )
    public ReplyAble riBaoListener(GroupMsg msg, MsgSender sender) {

        String zaoBaoDataResponse = zaoBaoService.zaoBao();

        if (StringUtils.isEmpty(zaoBaoDataResponse)) {
            return Reply.reply("日报请求失败了哦，重新试试呢。", Boolean.TRUE);
        }
        // 获取猫猫码工具
        CatCodeUtil util = CatCodeUtil.INSTANCE;

        // 构建image, 第二个参数为true代表参数值需要进行转义
        String image = util.toCat("image", true, "url=" + zaoBaoDataResponse);

        sender.SENDER.sendGroupMsg(msg, image);
        return Reply.reject();
    }

}
