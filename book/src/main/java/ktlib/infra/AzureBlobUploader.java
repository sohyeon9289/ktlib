package ktlib.infra;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import com.azure.storage.blob.sas.*;

import java.io.ByteArrayInputStream;
import java.time.OffsetDateTime;

public class AzureBlobUploader {

    private final BlobContainerClient containerClient;

    public AzureBlobUploader(String connectionString, String containerName) {
        System.out.println("🔐 [AzureBlobUploader] 연결 문자열 설정 확인 중...");
        if (connectionString == null || connectionString.isBlank()) {
            System.err.println("❌ [AzureBlobUploader] connectionString이 null 또는 비어 있습니다.");
            throw new IllegalArgumentException("Azure Storage connection string is missing");
        }

        try {
            BlobServiceClient serviceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();

            this.containerClient = serviceClient.getBlobContainerClient(containerName);
            System.out.println("✅ [AzureBlobUploader] 컨테이너 연결 성공: " + containerName);
        } catch (Exception e) {
            System.err.println("❌ [AzureBlobUploader] 클라이언트 생성 실패: " + e.getMessage());
            throw e;
        }
    }

    public String uploadPdf(String fileName, byte[] data) {
        try {
            System.out.println("📤 [AzureBlobUploader] 업로드 시작: " + fileName);
            System.out.println("📄 업로드할 PDF 크기: " + data.length + " bytes");

            BlobClient blobClient = containerClient.getBlobClient(fileName);
            blobClient.upload(new ByteArrayInputStream(data), data.length, true);

            System.out.println("✅ [AzureBlobUploader] 업로드 성공: " + blobClient.getBlobUrl());

            BlobServiceSasSignatureValues sasValues = new BlobServiceSasSignatureValues(
                OffsetDateTime.now().plusMinutes(30),
                new BlobSasPermission().setReadPermission(true)
            );

            String sasToken = blobClient.generateSas(sasValues);
            System.out.println("🔗 [AzureBlobUploader] SAS URL 생성 완료");

            return blobClient.getBlobUrl() + "?" + sasToken;

        } catch (Exception e) {
            System.err.println("❌ [AzureBlobUploader] 업로드 실패: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Azure 업로드 실패: " + e.getMessage());
        }
    }
}
