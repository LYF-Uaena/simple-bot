package com.mirai.lyf.bot.persistence.model.alapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 天气
 *
 * @author LYF.UAENA
 * @since 2022年07月21日 21:51
 */
@Data
@NoArgsConstructor
public class WeatherData {

    @JsonProperty("city")
    private String city;
    @JsonProperty("city_en")
    private String cityEn;
    @JsonProperty("province")
    private String province;
    @JsonProperty("province_en")
    private String provinceEn;
    @JsonProperty("city_id")
    private String cityId;
    @JsonProperty("update_time")
    private String updateTime;
    @JsonProperty("weather")
    private String weather;
    @JsonProperty("weather_code")
    private String weatherCode;
    @JsonProperty("temp")
    private String temp;
    @JsonProperty("min_temp")
    private String minTemp;
    @JsonProperty("max_temp")
    private String maxTemp;
    @JsonProperty("wind")
    private String wind;
    @JsonProperty("wind_speed")
    private String windSpeed;
    @JsonProperty("wind_scale")
    private String windScale;
    @JsonProperty("rain")
    private String rain;
    @JsonProperty("rain_24h")
    private String rain24h;
    @JsonProperty("humidity")
    private String humidity;
    @JsonProperty("visibility")
    private String visibility;
    @JsonProperty("pressure")
    private String pressure;
    @JsonProperty("tail_number")
    private String tailNumber;
    @JsonProperty("air")
    private String air;
    @JsonProperty("air_pm25")
    private String airPm25;
    @JsonProperty("sunrise")
    private String sunrise;
    @JsonProperty("sunset")
    private String sunset;
    @JsonProperty("aqi")
    private Aqi aqi;
    @JsonProperty("index")
    private IndexDTO index;
    @JsonProperty("alarm")
    private List<?> alarm;
    @JsonProperty("hour")
    private List<Hour> hour;

    @NoArgsConstructor
    @Data
    public static class Aqi {
        @JsonProperty("air")
        private String air;
        @JsonProperty("air_level")
        private String airLevel;
        @JsonProperty("air_tips")
        private String airTips;
        @JsonProperty("pm25")
        private String pm25;
        @JsonProperty("pm10")
        private String pm10;
        @JsonProperty("co")
        private String co;
        @JsonProperty("no2")
        private String no2;
        @JsonProperty("so2")
        private String so2;
        @JsonProperty("o3")
        private String o3;
    }

    @NoArgsConstructor
    @Data
    public static class IndexDTO {
        @JsonProperty("xiche")
        private XicheDTO xiche;
        @JsonProperty("ganmao")
        private GanmaoDTO ganmao;
        @JsonProperty("yundong")
        private YundongDTO yundong;
        @JsonProperty("huazhuang")
        private HuazhuangDTO huazhuang;
        @JsonProperty("chuangyi")
        private ChuangyiDTO chuangyi;
        @JsonProperty("ziwaixian")
        private ZiwaixianDTO ziwaixian;

        @NoArgsConstructor
        @Data
        public static class XicheDTO {
            @JsonProperty("name")
            private String name;
            @JsonProperty("level")
            private String level;
            @JsonProperty("content")
            private String content;
        }

        @NoArgsConstructor
        @Data
        public static class GanmaoDTO {
            @JsonProperty("name")
            private String name;
            @JsonProperty("level")
            private String level;
            @JsonProperty("content")
            private String content;
        }

        @NoArgsConstructor
        @Data
        public static class YundongDTO {
            @JsonProperty("name")
            private String name;
            @JsonProperty("level")
            private String level;
            @JsonProperty("content")
            private String content;
        }

        @NoArgsConstructor
        @Data
        public static class HuazhuangDTO {
            @JsonProperty("name")
            private String name;
            @JsonProperty("level")
            private String level;
            @JsonProperty("content")
            private String content;
        }

        @NoArgsConstructor
        @Data
        public static class ChuangyiDTO {
            @JsonProperty("name")
            private String name;
            @JsonProperty("level")
            private String level;
            @JsonProperty("content")
            private String content;
        }

        @NoArgsConstructor
        @Data
        public static class ZiwaixianDTO {
            @JsonProperty("name")
            private String name;
            @JsonProperty("level")
            private String level;
            @JsonProperty("content")
            private String content;
        }
    }

    @NoArgsConstructor
    @Data
    public static class Hour {
        @JsonProperty("time")
        private String time;
        @JsonProperty("temp")
        private String temp;
        @JsonProperty("wea")
        private String wea;
        @JsonProperty("wea_code")
        private String weaCode;
        @JsonProperty("wind")
        private String wind;
        @JsonProperty("wind_level")
        private String windLevel;
    }
}

