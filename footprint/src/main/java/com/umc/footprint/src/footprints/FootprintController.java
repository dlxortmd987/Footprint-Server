package com.umc.footprint.src.footprints;

import com.umc.footprint.src.footprints.model.dto.GetFootprintReq;
import com.umc.footprint.src.footprints.model.dto.GetFootprintRes;
import com.umc.footprint.src.footprints.model.dto.PatchFootprintReq;
import com.umc.footprint.src.users.UserProvider;
import com.umc.footprint.src.users.UserService;
import com.umc.footprint.src.walks.WalkProvider;
import com.umc.footprint.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.umc.footprint.config.BaseException;
import com.umc.footprint.config.BaseResponse;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/footprints")
public class FootprintController {

    private final FootprintService footprintService;
    private final JwtService jwtService;
    private final UserService userService;

    /**
     * 발자국 조회 API
     * [GET] /footprints/:walkidx
     */
    @ResponseBody
    @GetMapping("/{walkIdx}")
    public BaseResponse<List<GetFootprintRes>> getFootprints(@PathVariable("walkIdx") int walkIdx) {
        try {
            log.debug("walkIdx: {}", walkIdx);

            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("userId: {}", userId);

            List<GetFootprintRes> getFootprintRes = footprintService.getFootprints(walkIdx, userId);
            return new BaseResponse<>(getFootprintRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 발자국 수정 API
     * [PATCH] /footprints/:walkIdx/:footprintIdx
     */
    @ResponseBody
    @PatchMapping("/{walkIdx}/{footprintIdx}")
    public BaseResponse<String> modifyFootprint(@PathVariable("walkIdx") int walkIdx,@PathVariable("footprintIdx") int footprintIdx, GetFootprintReq footprint) {
        try {
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("userId: {}", userId);

            PatchFootprintReq patchFootprintReq = new PatchFootprintReq(footprint.getWrite(), footprint.getPhotos(), footprint.getTagList());
            footprintService.modifyFootprint(patchFootprintReq, walkIdx, footprintIdx, userId);

            String result = "발자국이 수정되었습니다.";
            log.debug("result: {}", result);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            exception.printStackTrace();
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 발자국 삭제 API
     * [PATCH] /footprints/:walkIdx/:footprintIdx/status
     */
    @ResponseBody
    @PatchMapping("/{walkIdx}/{footprintIdx}/status")
    public BaseResponse<String> DeleteFootprint(@PathVariable("walkIdx") int walkIdx, @PathVariable("footprintIdx") int footprintIdx) {
        try {
            // userId(구글이나 카카오에서 보낸 ID) 추출 (복호화)
            String userId = jwtService.getUserId();
            log.debug("userId: {}", userId);
            // userId로 userIdx 추출
            int userIdx = userService.getUserIdxByUserId(userId);

            // Walk 테이블 전체에서 인덱스
            int wholeWalkIdx = footprintService.getWalkWholeIdx(walkIdx, userIdx);
            log.debug("wholeWalkIdx: {}", wholeWalkIdx);

            // Footprint 테이블 전체에서 인덱스
            int wholeFootprintIdx = footprintService.getFootprintWholeIdx(wholeWalkIdx, footprintIdx);
            log.debug("wholeFootprintIdx: {}", wholeFootprintIdx);
            footprintService.deleteFootprintJPA(wholeFootprintIdx);
            String result = "발자국을 삭제하였습니다.";

            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
