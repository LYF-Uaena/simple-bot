package com.mirai.lyf.bot.persistence.service.master;

import catcode.Neko;
import com.mirai.lyf.bot.common.kit.HttpCode;
import com.mirai.lyf.bot.persistence.domain.master.ImageLog;
import com.mirai.lyf.bot.persistence.model.ImageResult;
import com.mirai.lyf.bot.persistence.model.master.ImageLogDto;
import com.mirai.lyf.bot.persistence.repository.master.ImageLogRepository;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.api.message.events.GroupMsg;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * The type Image log service.
 *
 * @author LYF on 2021-02-28
 */
@Slf4j
@Service
public class ImageLogService {
    private final double VIOLATION_STANDARD = 0.85;
    private final ImageLogRepository repo;

    public ImageLogService(ImageLogRepository repo) {
        this.repo = repo;
    }

    public ImageLog findByImageId(String imageId) {
        return repo.findByImageId(imageId);
    }

    public ImageLog save(ImageLog imageLog) {
        return repo.save(imageLog);
    }


    /**
     * 构建数据
     *
     * @param imageResult the image result
     * @return 图片检查结果 image log
     */
    public ImageLogDto buildImageLog(ImageResult imageResult, GroupMsg groupMsg, Neko neko) {
        ImageLogDto imageLogDto = new ImageLogDto();
        imageLogDto.setGroupCode(groupMsg.getGroupInfo().getGroupCodeNumber());
        imageLogDto.setQqCode(groupMsg.getAccountInfo().getAccountCodeNumber());
        imageLogDto.setImageId(neko.get("id"));
        imageLogDto.setUrl(neko.get("url"));
        imageLogDto.setCode(imageResult.getCode());
        imageLogDto.setMsg(imageResult.getMsg());
        imageLogDto.setResult("");
        if (HttpCode.SUCCESS == imageResult.getCode() && imageResult.getData() != null) {
            ImageResult.DataDto dataDto = imageResult.getData();
            // 色情图片检测结果
            if (dataDto.getAntiporn() != null) {
                ImageResult.DataDto.AntipornDto antipornDto = dataDto.getAntiporn();
                log.error(String.valueOf(antipornDto));
                imageLogDto.setPornConclusion(antipornDto.getConclusion());
                imageLogDto.setPornConfidence(antipornDto.getConfidenceCoefficient());
                if ("色情".equals(antipornDto.getConclusion())) {
                    imageLogDto.setResult(imageLogDto.getResult() + antipornDto.getConclusion() + "：" + antipornDto.getConfidenceCoefficient() + "; ");
                }
            }
            // 暴恐图片检测结果
            if (dataDto.getTerror() != null) {
                ImageResult.DataDto.TerrorDto terrorDto = dataDto.getTerror();
                log.error(String.valueOf(terrorDto));
                imageLogDto.setTerrorConfidence(terrorDto.getResult());
                if (terrorDto.getResult() > VIOLATION_STANDARD) {
                    BigDecimal terrorResult = BigDecimal.valueOf(terrorDto.getResult());
                    double result = terrorResult.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                    imageLogDto.setResult(imageLogDto.getResult() + "暴恐：" + result * 100 + "%; ");
                }
            }
            // 恶心图片检测结果
            if (dataDto.getDisgust() != null) {
                ImageResult.DataDto.DisgustDto disgustDto = dataDto.getDisgust();
                log.error(String.valueOf(disgustDto));
                if (disgustDto.getResult() != null) {
                    imageLogDto.setDisgustConfidence(disgustDto.getResult());
                    if (disgustDto.getResult() > VIOLATION_STANDARD) {
                        BigDecimal terrorResult = BigDecimal.valueOf(disgustDto.getResult());
                        double result = terrorResult.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                        imageLogDto.setResult(imageLogDto.getResult() + "恶心：" + result * 100 + "%; ");
                    }
                }
            }
            // 政治人物图片检测结果
            if (dataDto.getPolitician() != null) {
                ImageResult.DataDto.PoliticianDto politicianDto = dataDto.getPolitician();
                log.error(String.valueOf(politicianDto));
                imageLogDto.setPoliticianConfidence(politicianDto.getResultConfidence());
                imageLogDto.setPoliticianConclusion(politicianDto.getIncludePolitician());
                if ("确定".equals(politicianDto.getResultConfidence()) && "是".equals(politicianDto.getIncludePolitician())) {
                    imageLogDto.setResult(imageLogDto.getResult() + "政治人物：是; ");
                }
            }
            // 二维码水印图片检测结果
            if (dataDto.getWatermark() != null) {
                ImageResult.DataDto.WatermarkDto watermarkDto = dataDto.getWatermark();
                log.error(String.valueOf(watermarkDto));
                List<ImageResult.DataDto.WatermarkDto.ResultDto> resultList = watermarkDto.getResult();
                resultList.forEach(resultDto -> {
                    if (imageLogDto.getQrCodeConfidence() < resultDto.getProbability() && "QR code".equals(resultDto.getType())) {
                        imageLogDto.setQrCodeConclusion("QR code");
                        imageLogDto.setQrCodeConfidence(resultDto.getProbability());
                    }
                });
                if (imageLogDto.getQrCodeConfidence() > VIOLATION_STANDARD && "QR code".equals(imageLogDto.getQrCodeConclusion())) {
                    BigDecimal qrResult = BigDecimal.valueOf(imageLogDto.getQrCodeConfidence());
                    BigDecimal result = qrResult.setScale(4, BigDecimal.ROUND_HALF_UP);
                    imageLogDto.setResult(imageLogDto.getResult() + "QR code：" + result.multiply(BigDecimal.valueOf(100)) + "%; ");
                }
            }
        }

        return imageLogDto;
    }
}
