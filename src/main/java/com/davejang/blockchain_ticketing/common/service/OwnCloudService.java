package com.davejang.blockchain_ticketing.common.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.Base64;

@Service
@Slf4j
public class OwnCloudService {

    @Value("${owncloud.url}")
    private String ownCloudUrl;
    @Value("${owncloud.username}")
    private String ownCloudUsername;
    @Value("${owncloud.password}")
    private String ownCloudPassword;

    public String uploadToOwnCloud(Path filePath, String fileName, String directory) throws IOException {
        String uploadUrl = ownCloudUrl + "/remote.php/dav/files/admin/" + directory + "/" + fileName;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPut httpPut = new HttpPut(uploadUrl);

            String auth = ownCloudUsername + ":" + ownCloudPassword;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            httpPut.setHeader("Authorization", "Basic " + encodedAuth);

            FileEntity fileEntity = new FileEntity(filePath.toFile(), ContentType.DEFAULT_BINARY);
            httpPut.setEntity(fileEntity);

            HttpResponse response = httpClient.execute(httpPut);
            int statusCode = response.getStatusLine().getStatusCode();

            Header locationHeader = response.getFirstHeader("Location");
            if (locationHeader != null) {
                log.error("Redirecting to: {}", locationHeader.getValue());
            }
            String responseBody = EntityUtils.toString(response.getEntity());
            log.info("Response Code: {}", statusCode);
            log.info("Response Body: {}", responseBody);

            if (statusCode == 201 || statusCode == 200) {
                return uploadUrl;
            } else {
                throw new AccessDeniedException("파일 업로드 실패");
            }
        }
    }
}
