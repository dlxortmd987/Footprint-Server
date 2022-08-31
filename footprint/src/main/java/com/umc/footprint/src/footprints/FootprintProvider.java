package com.umc.footprint.src.footprints;

import com.umc.footprint.config.BaseException;

import com.umc.footprint.config.EncryptProperties;
import com.umc.footprint.utils.AES128;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umc.footprint.src.footprints.model.*;
import static com.umc.footprint.config.BaseResponseStatus.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FootprintProvider {
    private final FootprintDao footprintDao;
    private final EncryptProperties encryptProperties;

    @Autowired
    public FootprintProvider(FootprintDao footprintDao, EncryptProperties encryptProperties) {
        this.footprintDao = footprintDao;
        this.encryptProperties = encryptProperties;
    }

    public int getFootprintWholeIdx(int walkIdx, int footprintIdx) throws BaseException {
        try {
            log.debug("walkIdx: {} ", walkIdx);
            log.debug("footprintIdx: {} ", footprintIdx);
            int wholeFootprintIdx = footprintDao.getFootprintWholeIdx(walkIdx, footprintIdx);
            log.debug("wholeFootprintIdx: {}", wholeFootprintIdx);
            return wholeFootprintIdx;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
