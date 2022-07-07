package com.nashtech.FutsalShop.services;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    public String upload(MultipartFile multipartFile);
}