package com.youngmok.myboard.dao;

import com.youngmok.myboard.domain.ProjectFileVO;

import java.util.List;

public interface FileDAO {

    int insertFile(ProjectFileVO file);

//    int insertProfileFile(ProjectFileVO file);

    ProjectFileVO selectFileImage(String writer);

    List<ProjectFileVO> selectFileList(Integer bno);

    int profileFileModify(ProjectFileVO file);

    int boardFileModify(ProjectFileVO file);

//
//    int deleteimg(String uuid);



}
