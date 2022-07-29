package com.umc.footprint.src.notice;

import com.umc.footprint.config.BaseException;
import com.umc.footprint.src.model.Notice;
import com.umc.footprint.src.notice.model.*;
import com.umc.footprint.src.repository.NoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.umc.footprint.config.BaseResponseStatus.INVALID_NOTICE_IDX;

@Slf4j
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Value("${application.version}")
    private String serverVersion;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public GetNoticeListRes getNoticeList(int page, int size) throws BaseException {
        // User에게 page와 size를 받아 Paging 처리된 notice Get
        Page<Notice> findNotice = noticeRepository.findAll(PageRequest.of(page-1,size, Sort.Direction.DESC,"noticeIdx"));

        // find된 Notice가 없을때 exception
        if(findNotice.isEmpty()){
            throw new BaseException(INVALID_NOTICE_IDX);
        }

        // NoticList 형식으로 mapping 진행
        Page<NoticeList> map = findNotice.map(notice -> new NoticeList(notice.getNoticeIdx(), notice.getTitle(),
                                                                notice.getCreateAt().isAfter(LocalDateTime.now(ZoneId.of("Asia/Seoul")).minusDays(5)),
                                                                notice.getCreateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                                                notice.getUpdateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        // 현재 페이지와 전체 페이지를 포함한 GetNoticeListRes 생성
        GetNoticeListRes getNoticeListRes = GetNoticeListRes.builder()
                .pageOn(page)
                .pageTotal(map.getTotalPages())
                .noticeList(map.getContent())
                .build();

        // Page map 안에있는 content만 return
        return getNoticeListRes;
    }

    public Optional<GetNoticeRes> getNotice(int idx) throws BaseException {
        /* index 계산(>=1)
        * page : 현재 page(>=1)
        * size : page의 size(>=1)
        * offset : page 안에서 원하는 공지의 index(>=1)
        *
        * */

        // index를 사용하여 notice find
        Optional<Notice> noticeByIdx = noticeRepository.findById(idx);

        System.out.println("noticeByIdx = " + noticeByIdx);

        // Check isNewNotice
        boolean isNewNotice;
        if(noticeByIdx.get().getCreateAt().isAfter(LocalDateTime.now(ZoneId.of("Asia/Seoul")).minusDays(5)))
            isNewNotice = true;
        else
            isNewNotice = false;

        // Check preNoticeIdx , postNoticeIdx
        // 1) status 가 active한 Notice 추출후 noticeIdx만 추출하여 리스트에 저장(activeNoticeIdxList)
        List<Notice> activeNoticeList = noticeRepository.findAllByStatus("ACTIVE");
        List<Integer> activeNoticeIdxList = new ArrayList<>();
        
        for(Notice acticeNotice : activeNoticeList){
            activeNoticeIdxList.add(acticeNotice.getNoticeIdx());
        }
        System.out.println("activeNoticeIdxList = " + activeNoticeIdxList);

        int nowNoticeIdx = activeNoticeIdxList.indexOf(noticeByIdx.get().getNoticeIdx());

        System.out.println("nowNoticeIdx = " + nowNoticeIdx);

        int preNoticeIdx = -1;
        int postNoticeIdx = -1;
        if(activeNoticeIdxList.size() != 1){
            if(nowNoticeIdx == 0){  // 맨 마지막 Notice를 접근시
                preNoticeIdx = activeNoticeIdxList.get(nowNoticeIdx+1);
            }else if(nowNoticeIdx == activeNoticeIdxList.size()-1){ // 맨 처음 Notice를 접근시
                postNoticeIdx = activeNoticeIdxList.get(nowNoticeIdx-1);
            }else{
                preNoticeIdx = activeNoticeIdxList.get(nowNoticeIdx+1);
                postNoticeIdx = activeNoticeIdxList.get(nowNoticeIdx-1);
            }
        }

        // 찾은 Notice 정보를 GetNoticeRes DTO로 mapping
        // 왜? 이유 생각해보자..!
        int finalPreNoticeIdx = preNoticeIdx;
        int finalPostNoticeIdx = postNoticeIdx;
        Optional<GetNoticeRes> getNoticeRes = noticeByIdx.map(notice -> new GetNoticeRes(notice.getNoticeIdx(), notice.getTitle(), notice.getNotice(),
                notice.getImage(), isNewNotice, finalPreNoticeIdx, finalPostNoticeIdx,
                notice.getCreateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), notice.getUpdateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        System.out.println("noticeByIdx = " + noticeByIdx);
        if(noticeByIdx.equals(Optional.empty())){
            throw new BaseException(INVALID_NOTICE_IDX);
        }

        return getNoticeRes;
    }

    public GetNoticeNewRes getNoticeNew() throws BaseException {

        // createAt과 비교할 Date
        LocalDateTime compDate = LocalDateTime.now(ZoneId.of("Asia/Seoul")).minusDays(5);

        // index를 사용하여 notice find
        List<Notice> allNotice = noticeRepository.findAllByCreateAtAfter(compDate);

        // Check isNewNotice
        boolean isNewNotice;
        if(allNotice.isEmpty())
            isNewNotice = false;
        else
            isNewNotice = true;

        return GetNoticeNewRes.builder()
                .isNoticeNew(isNewNotice)
                .build();

    }

    public PostKeyNoticeRes postNoticeKey(PostKeyNoticeReq postKeyNoticeReq) throws BaseException {

        // 클라이언트에서 이미 확인한 주요 공지 index List
        List<Integer> checkedKeyNoticeIdxList = postKeyNoticeReq.getCheckedKeyNoticeIdxList();

        // 전체 주요공지 List
        List<Notice> keyNoticeList = noticeRepository.findAllByKeyNoticeAndStatus(true,"ACTIVE");

        // 결과 Notice 들을 담을 ArrayList
        List<GetNoticeRes> result = new ArrayList<>();

        // 클라이언트에서 이미 확인한 주요 공지 index를 제외한 주요 공지들만 result에 넣어준다.
        for(Notice keyNotice : keyNoticeList){
            if(!checkedKeyNoticeIdxList.contains(keyNotice.getNoticeIdx()))

                result.add(GetNoticeRes.builder()
                        .noticeIdx(keyNotice.getNoticeIdx())
                        .title(keyNotice.getTitle())
                        .notice(keyNotice.getNotice())
                        .preIdx(-1)
                        .postIdx(-1)
                        .image(keyNotice.getImage())
                        .createAt(keyNotice.getCreateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .updateAt(keyNotice.getUpdateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .build());
        }

        return PostKeyNoticeRes.builder().keyNoticeList(result).build();
    }

    public GetVersionCheckRes checkVersion(String userVersion) {
//        List<Integer> userVersionToken = Arrays.stream(userVersion.split(".")).map(Integer::parseInt).collect(Collectors.toList());
//        List<Integer> serverVersionToken = Arrays.stream(serverVersion.split(".")).map(Integer::parseInt).collect(Collectors.toList());

        Boolean whetherUpdate = null;
        if (userVersion.charAt(0) == serverVersion.charAt(0)) { // 업데이트 불필요
            whetherUpdate = false;
        } else { // 업데이트 필요
            whetherUpdate = true;
        }

        return GetVersionCheckRes.builder()
                .whetherUpdate(whetherUpdate)
                .version(serverVersion)
                .build();
    }
}
