package com.umc.footprint.src.check;

import com.umc.footprint.config.BaseException;
import com.umc.footprint.config.EncryptProperties;
import com.umc.footprint.src.model.Hashtag;
import com.umc.footprint.src.repository.HashtagRepository;
import com.umc.footprint.utils.AES128;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.umc.footprint.config.BaseResponseStatus.DATABASE_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckService {

    private final EncryptProperties encryptProperties;
    private final HashtagRepository hashtagRepository;


    public String checkEncryptWalk(String encryptString) throws BaseException {
        try{
            String encryptResult = new AES128(encryptProperties.getKey()).encrypt(encryptString);

            log.info("encryptResult = {}",encryptResult );

            return encryptResult;
        } catch(Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public String checkDecryptWalk(String decryptString) throws BaseException{
        try{
            System.out.println("decryptString = " + decryptString);
            String decryptResult = new AES128(encryptProperties.getKey()).decrypt(decryptString);

            System.out.println("decryptResult = " + decryptResult);

            return decryptResult;
        } catch(Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void decryptHashtag() throws Exception {
        List<Hashtag> all = hashtagRepository.findAll();
        try {
            for (Hashtag encryptedHashtag : all) {
                encryptedHashtag.decryptHashtag(new AES128(encryptProperties.getKey()).decrypt(encryptedHashtag.getHashtag()));
            }
            hashtagRepository.saveAll(all);
        } catch (Exception exception) {
            throw new Exception("해시태그 복호화 실패");
        }
    }
}
