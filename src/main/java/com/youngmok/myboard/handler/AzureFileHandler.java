//package com.youngmok.myboard.handler;
//
//import com.azure.storage.blob.BlobContainerClient;
//import com.azure.storage.blob.BlobServiceClient;
//import com.azure.storage.blob.BlobServiceClientBuilder;
//import com.azure.storage.blob.specialized.BlockBlobClient;
//import com.youngmok.myboard.domain.ProjectFileVO;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import net.coobird.thumbnailator.Thumbnails;
//import org.apache.tika.Tika;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Slf4j
//@AllArgsConstructor
//@Component
//public class AzureFileHandler {
//    private final String
//            AZURE_STORAGE_CONNECTION_STRING
//            = "DefaultEndpointsProtocol=https;AccountName=youngmokfile;AccountKey=QDcZnBsJ0Wc045LWaGzRieDRmY3hUoziANYFVABMcti35zP75IHQKiFHuO47BIWzXpGBzmF+Jbio+AStI8FXbA==;EndpointSuffix=core.windows.net";
//
//    private static final String CONTAINER_NAME = "youngmokboard";
//
//    public List<ProjectFileVO> uploadFiles(MultipartFile[] files) {
//        LocalDate date = LocalDate.now();
//        log.info(">>> date : {}", date);
//        String today = date.toString(); // 2022-12-27
//        // Use File.separator instead of hardcoding separator character to make it platform-independent
//        today = today.replace("-", File.separator);
//
//        File folders = new File(today);
//        if (!folders.exists()) {
//            folders.mkdirs();
//        }
//
//        // Azure Blob Storage에 연결합니다.
//        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
//                .connectionString(AZURE_STORAGE_CONNECTION_STRING)
//                .buildClient();
//
//        // Blob Container 생성
//        BlobContainerClient containerClient;
//        try {
//            // Blob Container 생성
//            containerClient = blobServiceClient.createBlobContainer(CONTAINER_NAME);
//        } catch (Exception e) {
//            // Blob Container가 이미 존재하는 경우
//            containerClient = blobServiceClient.getBlobContainerClient(CONTAINER_NAME);
//        }
//
//        // 파일 경로설정 완료
//        List<ProjectFileVO> fList = new ArrayList<>();
//        for (MultipartFile file : files) {
//            ProjectFileVO fvo = new ProjectFileVO();
//            fvo.setSave_dir(today); // 파일경로설정
//            fvo.setFile_size(file.getSize()); // 사이즈설정
//
//            String originalFileName = file.getOriginalFilename(); // 경로를 포함할수도 있는 파일명
//            log.info(">>> ori fileName : {}", originalFileName);
//
//            String onlyFileName = Paths.get(originalFileName).getFileName().toString(); // 실제파일명만 추출
//            log.info(">>> only fileName : {}", onlyFileName);
//            fvo.setFile_name(onlyFileName); // 파일이름설정
//
//            UUID uuid = UUID.randomUUID();
//            fvo.setUuid(uuid.toString()); // uuid설정
//            // 여기까지 fvo에 저장할 파일을 정보 생성 -> set
//
//            // 디스크에 저장할 파일객체 생성
//            String fullFileName = uuid + "_" + onlyFileName;
//            File storeFile = new File(folders, fullFileName);
//
//            try {
//                // Azure Blob Storage에 파일을 업로드합니다.
//                BlockBlobClient blobClient = containerClient.getBlobClient(fullFileName).getBlockBlobClient();
//                blobClient.upload(file.getInputStream(), file.getSize());
//
//
//
////                System.out.println("썸네일 추가 직전!");
////
////                file.transferTo(storeFile);
////
////                if (isImageFile(storeFile)) {
////                    fvo.setFile_type(1);
////
////                    // 썸네일 이미지를 생성합니다.
////                    File thumbNail = new File(folders, uuid + "_th_" + onlyFileName);
////                    Thumbnails.of(file.getInputStream()).size(75, 75).toFile(thumbNail);
////
////                    // 썸네일 이미지도 Azure Blob Storage에 업로드합니다.
////                    BlockBlobClient thumbNailBlobClient = containerClient.getBlobClient(uuid.toString() + "_th_" + onlyFileName).getBlockBlobClient();
////                    thumbNailBlobClient.upload(new FileInputStream(thumbNail), thumbNail.length());
////                }
//
//
//
//
//
//            } catch (Exception e) {
//                log.error(">>> File 생성 오류~!!", e);
//            }
//            fList.add(fvo);
//        }
//        return fList;
//    }
//
//    private boolean isImageFile(File storeFile) throws IOException {
//        String mimeType = new Tika().detect(storeFile); // image/png, image/jpg
//        return mimeType.startsWith("image");
//    }
//}
