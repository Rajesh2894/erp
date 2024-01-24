package com.abm.mainet.dms.utility;

import java.io.File;
import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public interface IDocumentDetector extends Serializable {

    boolean isPdfSafe(MultipartFile f);

    boolean isWordDocumentSafe(File f, MultipartFile multiFile);

    boolean isImageSafe(File f, MultipartFile multiFile);

    boolean isPowerpointSafe(File f, MultipartFile multiFile);

}
