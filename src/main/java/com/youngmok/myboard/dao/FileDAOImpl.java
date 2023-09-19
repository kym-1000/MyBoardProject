package com.youngmok.myboard.dao;

import com.youngmok.myboard.domain.ProjectFileVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FileDAOImpl implements FileDAO {

    private final SqlSession session;

    @Autowired
    public FileDAOImpl(SqlSession session) {
        this.session = session;
    }

    private static final String namespace = "com.youngmok.myboard.dao.FileMapper.";

    @Override
    public int insertFile(ProjectFileVO file) {
        return session.insert(namespace+"insertFile", file);
    }

//    @Override
//    public int insertProfileFile(ProjectFileVO file) {
//        return session.insert(namespace+"insertProfileFile", file);
//    }

    @Override
    public ProjectFileVO selectFileImage(String writer) {
        return session.selectOne(namespace+"selectFileImage",writer);
    }

    @Override
    public ProjectFileVO selectFileList(Integer bno) {
        return session.selectOne(namespace+"selectFileList",bno);
    }

    @Override
    public int profileFileModify(ProjectFileVO file) {
        return session.update(namespace+"profileFileModify", file);
    }

    @Override
    public int boardFileModify(ProjectFileVO file) {
        return session.update(namespace + "boardFileModify", file);
    }

    @Override
    public int deleteFile(Integer bno) {
        return session.delete(namespace + "deleteFile", bno);
    }

    @Override
    public int fileCount(Integer bno) {
        return session.selectOne(namespace+"fileCount",bno);
    }

    @Override
    public int deleteUserFile(String id) {
        return session.delete(namespace + "deleteUserFile", id);
    }

}
