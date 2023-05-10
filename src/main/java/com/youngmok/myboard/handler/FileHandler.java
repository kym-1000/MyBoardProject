package com.youngmok.myboard.handler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.youngmok.myboard.domain.ProjectFileVO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@AllArgsConstructor
@Component
public class FileHandler {
    private final String UP_DIR ="C:\\_javaweb\\_java_board\\fileUpload";

    public List<ProjectFileVO> uploadFiles(MultipartFile[] files){
        LocalDate date = LocalDate.now();
        log.info(">>> date : "+date);
        String today = date.toString(); //2022-12-27
        //- -> 파일 구분자로 변경 2022-01-01 => 2022\01\01(win) 2022/01/01(linux)
        today = today.replace("-", File.separator);

        File folders = new File(UP_DIR, today);

        //폴더가 있으면 생성X 없으면 생성O
        if(!folders.exists()) {
            folders.mkdirs();  //생성
        }

        //파일 경로설정 완료
        List<ProjectFileVO> fList = new ArrayList<ProjectFileVO>();
        for(MultipartFile file : files) {
            ProjectFileVO fvo = new ProjectFileVO();
            fvo.setSave_dir(today); //파일경로설정
            fvo.setFile_size(file.getSize()); //사이즈설정

            String originalFileName = file.getOriginalFilename(); //경로를 포함할수도 있는 파일명
            log.info(">>> ori fileName : "+originalFileName);

            String onlyFileName = originalFileName
                    .substring(originalFileName.lastIndexOf("\\")+1); //실제파일명만 추출
            log.info(">>> only fileName : "+onlyFileName);
            fvo.setFile_name(onlyFileName); //파일이름설정

            UUID uuid = UUID.randomUUID();
            fvo.setUuid(uuid.toString()); //uuid설정
            //여기까지 fvo에 저장할 파일을 정보 생성 -> set

            //디스크에 저장할 파일객체 생성
            String fullFileName = uuid.toString()+"_"+onlyFileName;
            File storeFile = new File(folders, fullFileName);

            try {
                file.transferTo(storeFile); //원본객체를 저장을 위한 형태의 객체로 복사
                if(isImageFile(storeFile)) {
                    fvo.setFile_type(1);
                    File thumbNail = new File(folders, uuid.toString()+"_th_"+onlyFileName);
                    Thumbnails.of(storeFile).size(75, 75).toFile(thumbNail);
                }
            } catch (Exception e) {
                log.info(">>> File 생성 오류~!!");
                e.printStackTrace();
            }
            fList.add(fvo);
        }
        return fList;
    }

    private boolean isImageFile(File storeFile) throws IOException {
        String mimeType = new Tika().detect(storeFile); // image/png, image/jpg
        return mimeType.startsWith("image")? true : false;
    }
}
