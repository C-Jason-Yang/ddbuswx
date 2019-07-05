package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.common.SystemParameter;
import com.evcas.ddbuswx.common.utils.FileOperation;
import com.evcas.ddbuswx.common.utils.UuidUtil;
import org.springframework.stereotype.Service;

/**
 * Created by noxn on 2018/9/24.
 */
@Service
public class FileUpLoadService {

    public void fileByteArrayUpLoad(byte[] fileByteArray, String fileName) {
        String[] fileNameArray = fileName.split("\\.");
            String fileSuffix = "";
            if (fileNameArray.length > 1) {
                fileSuffix =fileNameArray[1];
            }
            FileOperation.saveBtyeArrayToFile(fileByteArray, SystemParameter.UPLOAD_FILE_PATH + UuidUtil.getUuid() +"." +fileSuffix);
    }

    public String fileBase64UpLoad(String fileBase64code, String fileName) {
        String[] fileNameArray = fileName.split("\\.");
        String fileSuffix = "";
        if (fileNameArray.length > 1) {
            fileSuffix =fileNameArray[1];
        }
        String filePath = SystemParameter.UPLOAD_FILE_PATH + UuidUtil.getUuid() +"." +fileSuffix;
        FileOperation.saveBase64ToFile(fileBase64code, filePath);
        return filePath;
    }
}
