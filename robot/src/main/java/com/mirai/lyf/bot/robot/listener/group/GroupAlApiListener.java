package com.mirai.lyf.bot.robot.listener.group;

import com.mirai.lyf.bot.common.kit.AlApi;
import com.mirai.lyf.bot.common.kit.HttpCode;
import com.mirai.lyf.bot.common.utils.StringUtils;
import com.mirai.lyf.bot.persistence.model.alapi.CloudQRCodeData;
import com.mirai.lyf.bot.persistence.model.alapi.LickDogData;
import com.mirai.lyf.bot.persistence.model.alapi.Response;
import com.mirai.lyf.bot.persistence.service.alapi.AlApiService;
import com.mirai.lyf.bot.persistence.service.alapi.LickDogService;
import com.mirai.lyf.bot.persistence.service.alapi.ZaoBaoService;
import lombok.extern.slf4j.Slf4j;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simboot.filter.MatchType;
import love.forte.simbot.definition.Group;
import love.forte.simbot.event.GroupMessageEvent;
import love.forte.simbot.message.*;
import love.forte.simbot.resources.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 监听群聊消息
 */
@Slf4j
@Component
public class GroupAlApiListener /*extends BaseListener*/ {
    @Autowired
    private LickDogService lickDogService;
    @Autowired
    private AlApiService alApiService;
    @Autowired
    private ZaoBaoService zaoBaoService;

    @Listener
    @Filter(value = "#宝", matchType = MatchType.TEXT_STARTS_WITH)
    public void lickDogListener(GroupMessageEvent event) {

        Response<LickDogData> lickDogResponse = alApiService.doHttpGet(AlApi.LICK_DOG_API, null, LickDogData.class);

        if (lickDogResponse.getCode() == HttpCode.SUCCESS) {
            String content = lickDogResponse.getData().getContent();
            // 构建消息（链）
            Message message = Messages.toMessages(
                    new At(event.getAuthor().getId()),
                    Text.of("\n"),
                    Text.of(content)
            );

            Group group = event.getGroup();
            group.sendBlocking(message);
        }
    }

//    @Filters(
//            customMostMatchType = MostMatchType.ALL,
//            customFilter = {CustomerFilter.SPEAKING_ROBOT, CustomerFilter.FUNCTION},
//            mostMatchType = MostMatchType.ANY,
//            value = {
//                    @Filter(value = "#天气 ", matchType = MatchType.STARTS_WITH)
//            }
//    )
//    public void weatherListener(GroupMsg msg, MsgSender sender) {
//        String city = msg.getMsg().replace("#天气", "").trim();
//        if (StringUtils.isEmpty(city)) {
//            return Reply.reply("城市不能为空哦。", true);
//        }
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("city", city);
//        Response<TianQiData> response = alApiService.doHttpGet(AlApi.TIAN_QI, params, TianQiData.class);
//
//        if (response.getCode() == HttpCode.SUCCESS) {
//            TianQiData tianqi = response.getData();
//            String format = String.format(Template.WEATHER,
//                    tianqi.getCity(),
//                    tianqi.getWeather(),
//                    tianqi.getTemp(),
//                    tianqi.getWind(),
//                    tianqi.getWindSpeed(),
//                    tianqi.getMaxTemp(),
//                    tianqi.getMinTemp(),
//                    tianqi.getAqi().getAirLevel(),
//                    tianqi.getHumidity(),
//                    tianqi.getVisibility(),
//                    tianqi.getPressure(),
//                    tianqi.getRain(),
//                    tianqi.getRain24h()
//            );
//            MessageContentBuilder builder = builderFactory.getMessageContentBuilder();
//            builder.at(msg.getAccountInfo().getAccountCode())
//                    .text(format);
//            sender.SENDER.sendGroupMsg(msg, builder.build());
//            return Reply.reject();
//        }
//
//        return Reply.reply("我该说点啥！", true);
//    }

    @Listener
    @Filter(value = "#日报", matchType = MatchType.TEXT_STARTS_WITH)
    public void riBaoListener(GroupMessageEvent event) {
        String zaoBaoDataResponse = zaoBaoService.zaoBao();

        if (StringUtils.isEmpty(zaoBaoDataResponse)) {
            event.replyBlocking(Text.of("日报请求失败了哦，重新试试呢。"));
        }
        ResourceImage image;
        InputStream inputStream = null;
        try {
            UrlResource urlResource = new UrlResource(zaoBaoDataResponse);
            inputStream = urlResource.getInputStream();
            image = Image.Key.of(Resource.of(inputStream));
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
        // 构建消息（链）
        Message message = Messages.toMessages(image);
        Group group = event.getGroup();
        group.sendBlocking(message);

    }

    @Listener
    @Filter(value = "#网易云登录", matchType = MatchType.TEXT_STARTS_WITH)
    public void qrCodeListener(GroupMessageEvent event) {
        Response<CloudQRCodeData> response = alApiService.doHttpGet(AlApi.CLOUD_MUSIC_QR_CODE, null, CloudQRCodeData.class);

        if (response.getCode() == HttpCode.SUCCESS) {
            CloudQRCodeData data = response.getData();
            ResourceImage image;
            InputStream inputStream = null;
            try {
                String base64FileData = data.getQrimg();
                base64FileData = base64FileData.substring(base64FileData.indexOf(",", 1) + 1);
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] byteArr = decoder.decodeBuffer(base64FileData);
                inputStream = new ByteArrayInputStream(byteArr);

//                UrlResource urlResource = new UrlResource(data.getUrl());
//                inputStream = urlResource.getInputStream();
                image = Image.Key.of(Resource.of(inputStream));
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException ignored) {
                    }
                }
            }
            // 构建消息（链）
            Message message = Messages.toMessages(image);
            Group group = event.getGroup();
            group.sendBlocking(message);
        }


    }

}
