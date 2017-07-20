package com.konex.netty.service.workingWithFiles;

import jcifs.smb.SmbException;

import java.io.IOException;

/**
 * Created by vitaliy on 18.07.2017.
 */
public interface WorkingWithFiles{
    String getFileContent(String fName) throws IOException;

    String writeFile(String text) throws IOException;
}
