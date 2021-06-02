package com.mirai.lyf.bot.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 图片识别返回结果
 *
 * @author LYF on 2020-09-29
 */
@NoArgsConstructor
@Data
public class ImageResult {

    /**
     * code : 200
     * msg : success
     * data : {"antiporn":{"result":[{"probability":0,"class_name":"性感"},{"probability":8.0E-6,"class_name":"色情"},{"probability":0.999992,"class_name":"正常"}],"conclusion":"正常","log_id":1639734983558928093,"result_fine":[{"probability":0,"class_name":"一般色情"},{"probability":8.0E-6,"class_name":"卡通色情"},{"probability":0,"class_name":"SM"},{"probability":0.999913,"class_name":"艺术品色情"},{"probability":0,"class_name":"儿童裸露"},{"probability":0,"class_name":"低俗"},{"probability":0,"class_name":"性玩具"},{"probability":0,"class_name":"女性性感"},{"probability":0,"class_name":"卡通女性性感"},{"probability":0,"class_name":"男性性感"},{"probability":0,"class_name":"自然男性裸露"},{"probability":0,"class_name":"亲密行为"},{"probability":0,"class_name":"卡通亲密行为"},{"probability":0,"class_name":"卡通正常"},{"probability":7.8E-5,"class_name":"一般正常"},{"probability":0,"class_name":"特殊类"}],"result_num":16,"confidence_coefficient":"确定"},"webimage":{"log_id":8508279834662315229,"words_result":[],"words_result_num":0,"direction":0},"terror":{"result":1.7915694E-6,"log_id":4293008258588343549,"result_fine":[{"score":1.7421866E-7,"name":"血腥"},{"score":6.413083E-7,"name":"尸体"},{"score":4.5897212E-7,"name":"爆炸火灾"},{"score":1.6351741E-7,"name":"杀人"},{"score":4.5508834E-8,"name":"暴乱"},{"score":2.3202129E-7,"name":"暴恐人物"},{"score":7.455042E-8,"name":"暴恐旗帜"},{"score":3.662822E-8,"name":"血腥动物或动物尸体"},{"score":1.472272E-9,"name":"车祸"},{"score":1.473051E-7,"name":"军事武器"},{"score":7.851687E-7,"name":"警察部队"},{"score":0.99999726,"name":"正常"}],"result_coarse":[{"score":0.9999982,"name":"正常"},{"score":1.7915694E-6,"name":"暴恐"}]},"watermark":{"result":[{"probability":0.034173626,"type":"watermark"},{"probability":0.021352155,"type":"watermark"},{"probability":0.018282782,"type":"watermark"}],"log_id":1047162363879623325,"result_num":3},"public":{"result":[{"location":{"top":258,"left":431,"width":85,"height":78},"stars":[{"star_id":"3843552","probability":0.76365921020508,"name":"尼尔·罗伯逊"}]}],"log_id":8594848723073505597,"result_num":1},"politician":{"result":[],"log_id":4258669616543637789,"include_politician":"否","result_num":0,"result_confidence":"确定"},"disgust":{"result":2.1002645510193E-5,"log_id":1739317334882091389,"result_fine":[{"score":0.99997899735449,"name":"正常"},{"score":1.2968254834664E-5,"name":"性器官特写"},{"score":1.3252191183505E-7,"name":"脏器"},{"score":2.6080549224035E-6,"name":"疾病表症"},{"score":9.4803180038162E-7,"name":"密集恐惧症"},{"score":1.2554786347307E-7,"name":"腐烂食物"},{"score":5.0080100742589E-8,"name":"排泄物"},{"score":1.2462689876313E-7,"name":"恶心动物"},{"score":4.0028318591217E-6,"name":"人体血腥和尸体"},{"score":4.2695318792703E-8,"name":"动物血腥及尸体"}]},"ocr":{"words_result":[],"log_id":1310920362715774976,"words_result_num":0,"direction":0}}
     * author : {"name":"Alone88","desc":"由Alone88提供的免费API 服务，官方文档：www.alapi.cn"}
     */

    @JsonProperty("code")
    private Integer code;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("data")
    private DataDto data;
    @JsonProperty("author")
    private AuthorDto author;

    @NoArgsConstructor
    @Data
    public static class DataDto {
        /**
         * antiporn : {"result":[{"probability":0,"class_name":"性感"},{"probability":8.0E-6,"class_name":"色情"},{"probability":0.999992,"class_name":"正常"}],"conclusion":"正常","log_id":1639734983558928093,"result_fine":[{"probability":0,"class_name":"一般色情"},{"probability":8.0E-6,"class_name":"卡通色情"},{"probability":0,"class_name":"SM"},{"probability":0.999913,"class_name":"艺术品色情"},{"probability":0,"class_name":"儿童裸露"},{"probability":0,"class_name":"低俗"},{"probability":0,"class_name":"性玩具"},{"probability":0,"class_name":"女性性感"},{"probability":0,"class_name":"卡通女性性感"},{"probability":0,"class_name":"男性性感"},{"probability":0,"class_name":"自然男性裸露"},{"probability":0,"class_name":"亲密行为"},{"probability":0,"class_name":"卡通亲密行为"},{"probability":0,"class_name":"卡通正常"},{"probability":7.8E-5,"class_name":"一般正常"},{"probability":0,"class_name":"特殊类"}],"result_num":16,"confidence_coefficient":"确定"}
         * webimage : {"log_id":8508279834662315229,"words_result":[],"words_result_num":0,"direction":0}
         * terror : {"result":1.7915694E-6,"log_id":4293008258588343549,"result_fine":[{"score":1.7421866E-7,"name":"血腥"},{"score":6.413083E-7,"name":"尸体"},{"score":4.5897212E-7,"name":"爆炸火灾"},{"score":1.6351741E-7,"name":"杀人"},{"score":4.5508834E-8,"name":"暴乱"},{"score":2.3202129E-7,"name":"暴恐人物"},{"score":7.455042E-8,"name":"暴恐旗帜"},{"score":3.662822E-8,"name":"血腥动物或动物尸体"},{"score":1.472272E-9,"name":"车祸"},{"score":1.473051E-7,"name":"军事武器"},{"score":7.851687E-7,"name":"警察部队"},{"score":0.99999726,"name":"正常"}],"result_coarse":[{"score":0.9999982,"name":"正常"},{"score":1.7915694E-6,"name":"暴恐"}]}
         * watermark : {"result":[{"probability":0.034173626,"type":"watermark"},{"probability":0.021352155,"type":"watermark"},{"probability":0.018282782,"type":"watermark"}],"log_id":1047162363879623325,"result_num":3}
         * public : {"result":[{"location":{"top":258,"left":431,"width":85,"height":78},"stars":[{"star_id":"3843552","probability":0.76365921020508,"name":"尼尔·罗伯逊"}]}],"log_id":8594848723073505597,"result_num":1}
         * politician : {"result":[],"log_id":4258669616543637789,"include_politician":"否","result_num":0,"result_confidence":"确定"}
         * disgust : {"result":2.1002645510193E-5,"log_id":1739317334882091389,"result_fine":[{"score":0.99997899735449,"name":"正常"},{"score":1.2968254834664E-5,"name":"性器官特写"},{"score":1.3252191183505E-7,"name":"脏器"},{"score":2.6080549224035E-6,"name":"疾病表症"},{"score":9.4803180038162E-7,"name":"密集恐惧症"},{"score":1.2554786347307E-7,"name":"腐烂食物"},{"score":5.0080100742589E-8,"name":"排泄物"},{"score":1.2462689876313E-7,"name":"恶心动物"},{"score":4.0028318591217E-6,"name":"人体血腥和尸体"},{"score":4.2695318792703E-8,"name":"动物血腥及尸体"}]}
         * ocr : {"words_result":[],"log_id":1310920362715774976,"words_result_num":0,"direction":0}
         */

        @JsonProperty("antiporn")
        private AntipornDto antiporn;
        @JsonProperty("webimage")
        private WebimageDto webimage;
        @JsonProperty("terror")
        private TerrorDto terror;
        @JsonProperty("watermark")
        private WatermarkDto watermark;
        @JsonProperty("public")
        private PublicDto publicX;
        @JsonProperty("politician")
        private PoliticianDto politician;
        @JsonProperty("disgust")
        private DisgustDto disgust;
        @JsonProperty("ocr")
        private OcrDto ocr;

        @NoArgsConstructor
        @Data
        public static class AntipornDto {
            /**
             * result : [{"probability":0,"class_name":"性感"},{"probability":8.0E-6,"class_name":"色情"},{"probability":0.999992,"class_name":"正常"}]
             * conclusion : 正常
             * log_id : 1639734983558928093
             * result_fine : [{"probability":0,"class_name":"一般色情"},{"probability":8.0E-6,"class_name":"卡通色情"},{"probability":0,"class_name":"SM"},{"probability":0.999913,"class_name":"艺术品色情"},{"probability":0,"class_name":"儿童裸露"},{"probability":0,"class_name":"低俗"},{"probability":0,"class_name":"性玩具"},{"probability":0,"class_name":"女性性感"},{"probability":0,"class_name":"卡通女性性感"},{"probability":0,"class_name":"男性性感"},{"probability":0,"class_name":"自然男性裸露"},{"probability":0,"class_name":"亲密行为"},{"probability":0,"class_name":"卡通亲密行为"},{"probability":0,"class_name":"卡通正常"},{"probability":7.8E-5,"class_name":"一般正常"},{"probability":0,"class_name":"特殊类"}]
             * result_num : 16
             * confidence_coefficient : 确定
             */

            @JsonProperty("conclusion")
            private String conclusion;
            @JsonProperty("log_id")
            private Long logId;
            @JsonProperty("result_num")
            private Integer resultNum;
            @JsonProperty("confidence_coefficient")
            private String confidenceCoefficient;
            @JsonProperty("result")
            private List<ResultDto> result;
            @JsonProperty("result_fine")
            private List<ResultFineDto> resultFine;

            @NoArgsConstructor
            @Data
            public static class ResultDto {
                /**
                 * probability : 0
                 * class_name : 性感
                 */

                @JsonProperty("probability")
                private Integer probability;
                @JsonProperty("class_name")
                private String className;
            }

            @NoArgsConstructor
            @Data
            public static class ResultFineDto {
                /**
                 * probability : 0
                 * class_name : 一般色情
                 */

                @JsonProperty("probability")
                private Integer probability;
                @JsonProperty("class_name")
                private String className;
            }
        }

        @NoArgsConstructor
        @Data
        public static class WebimageDto {
            /**
             * log_id : 8508279834662315229
             * words_result : []
             * words_result_num : 0
             * direction : 0
             */

            @JsonProperty("log_id")
            private Long logId;
            @JsonProperty("words_result_num")
            private Integer wordsResultNum;
            @JsonProperty("direction")
            private Integer direction;
            @JsonProperty("words_result")
            private List<?> wordsResult;
        }

        @NoArgsConstructor
        @Data
        public static class TerrorDto {
            /**
             * result : 1.7915694E-6
             * log_id : 4293008258588343549
             * result_fine : [{"score":1.7421866E-7,"name":"血腥"},{"score":6.413083E-7,"name":"尸体"},{"score":4.5897212E-7,"name":"爆炸火灾"},{"score":1.6351741E-7,"name":"杀人"},{"score":4.5508834E-8,"name":"暴乱"},{"score":2.3202129E-7,"name":"暴恐人物"},{"score":7.455042E-8,"name":"暴恐旗帜"},{"score":3.662822E-8,"name":"血腥动物或动物尸体"},{"score":1.472272E-9,"name":"车祸"},{"score":1.473051E-7,"name":"军事武器"},{"score":7.851687E-7,"name":"警察部队"},{"score":0.99999726,"name":"正常"}]
             * result_coarse : [{"score":0.9999982,"name":"正常"},{"score":1.7915694E-6,"name":"暴恐"}]
             */

            @JsonProperty("result")
            private Double result;
            @JsonProperty("log_id")
            private Long logId;
            @JsonProperty("result_fine")
            private List<ResultFineDto> resultFine;
            @JsonProperty("result_coarse")
            private List<ResultCoarseDto> resultCoarse;

            @NoArgsConstructor
            @Data
            public static class ResultFineDto {
                /**
                 * score : 1.7421866E-7
                 * name : 血腥
                 */

                @JsonProperty("score")
                private Double score;
                @JsonProperty("name")
                private String name;
            }

            @NoArgsConstructor
            @Data
            public static class ResultCoarseDto {
                /**
                 * score : 0.9999982
                 * name : 正常
                 */

                @JsonProperty("score")
                private Double score;
                @JsonProperty("name")
                private String name;
            }
        }

        @NoArgsConstructor
        @Data
        public static class WatermarkDto {
            /**
             * result : [{"probability":0.034173626,"type":"watermark"},{"probability":0.021352155,"type":"watermark"},{"probability":0.018282782,"type":"watermark"}]
             * log_id : 1047162363879623325
             * result_num : 3
             */

            @JsonProperty("log_id")
            private Long logId;
            @JsonProperty("result_num")
            private Integer resultNum;
            @JsonProperty("result")
            private List<ResultDto> result;

            @NoArgsConstructor
            @Data
            public static class ResultDto {
                /**
                 * probability : 0.034173626
                 * type : watermark
                 */

                @JsonProperty("probability")
                private Double probability;
                @JsonProperty("type")
                private String type;
            }
        }

        @NoArgsConstructor
        @Data
        public static class PublicDto {
            /**
             * result : [{"location":{"top":258,"left":431,"width":85,"height":78},"stars":[{"star_id":"3843552","probability":0.76365921020508,"name":"尼尔·罗伯逊"}]}]
             * log_id : 8594848723073505597
             * result_num : 1
             */

            @JsonProperty("log_id")
            private Long logId;
            @JsonProperty("result_num")
            private Integer resultNum;
            @JsonProperty("result")
            private List<ResultDto> result;

            @NoArgsConstructor
            @Data
            public static class ResultDto {
                /**
                 * location : {"top":258,"left":431,"width":85,"height":78}
                 * stars : [{"star_id":"3843552","probability":0.76365921020508,"name":"尼尔·罗伯逊"}]
                 */

                @JsonProperty("location")
                private LocationDto location;
                @JsonProperty("stars")
                private List<StarsDto> stars;

                @NoArgsConstructor
                @Data
                public static class LocationDto {
                    /**
                     * top : 258
                     * left : 431
                     * width : 85
                     * height : 78
                     */

                    @JsonProperty("top")
                    private Integer top;
                    @JsonProperty("left")
                    private Integer left;
                    @JsonProperty("width")
                    private Integer width;
                    @JsonProperty("height")
                    private Integer height;
                }

                @NoArgsConstructor
                @Data
                public static class StarsDto {
                    /**
                     * star_id : 3843552
                     * probability : 0.76365921020508
                     * name : 尼尔·罗伯逊
                     */

                    @JsonProperty("star_id")
                    private String starId;
                    @JsonProperty("probability")
                    private Double probability;
                    @JsonProperty("name")
                    private String name;
                }
            }
        }

        @NoArgsConstructor
        @Data
        public static class PoliticianDto {
            /**
             * result : []
             * log_id : 4258669616543637789
             * include_politician : 否
             * result_num : 0
             * result_confidence : 确定
             */

            @JsonProperty("log_id")
            private Long logId;
            @JsonProperty("include_politician")
            private String includePolitician;
            @JsonProperty("result_num")
            private Integer resultNum;
            @JsonProperty("result_confidence")
            private String resultConfidence;
            @JsonProperty("result")
            private List<?> result;
        }

        @NoArgsConstructor
        @Data
        public static class DisgustDto {
            /**
             * result : 2.1002645510193E-5
             * log_id : 1739317334882091389
             * result_fine : [{"score":0.99997899735449,"name":"正常"},{"score":1.2968254834664E-5,"name":"性器官特写"},{"score":1.3252191183505E-7,"name":"脏器"},{"score":2.6080549224035E-6,"name":"疾病表症"},{"score":9.4803180038162E-7,"name":"密集恐惧症"},{"score":1.2554786347307E-7,"name":"腐烂食物"},{"score":5.0080100742589E-8,"name":"排泄物"},{"score":1.2462689876313E-7,"name":"恶心动物"},{"score":4.0028318591217E-6,"name":"人体血腥和尸体"},{"score":4.2695318792703E-8,"name":"动物血腥及尸体"}]
             */

            @JsonProperty("result")
            private Double result;
            @JsonProperty("log_id")
            private Long logId;
            @JsonProperty("result_fine")
            private List<ResultFineDto> resultFine;

            @NoArgsConstructor
            @Data
            public static class ResultFineDto {
                /**
                 * score : 0.99997899735449
                 * name : 正常
                 */

                @JsonProperty("score")
                private Double score;
                @JsonProperty("name")
                private String name;
            }
        }

        @NoArgsConstructor
        @Data
        public static class OcrDto {
            /**
             * words_result : []
             * log_id : 1310920362715774976
             * words_result_num : 0
             * direction : 0
             */

            @JsonProperty("log_id")
            private Long logId;
            @JsonProperty("words_result_num")
            private Integer wordsResultNum;
            @JsonProperty("direction")
            private Integer direction;
            @JsonProperty("words_result")
            private List<?> wordsResult;
        }
    }

    @NoArgsConstructor
    @Data
    public static class AuthorDto {
        /**
         * name : Alone88
         * desc : 由Alone88提供的免费API 服务，官方文档：www.alapi.cn
         */

        @JsonProperty("name")
        private String name;
        @JsonProperty("desc")
        private String desc;
    }
}
