package com.example.shop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URI;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class S3ServiceTest {

    @Mock
    private S3Presigner s3Presigner;

    @InjectMocks
    private S3Service s3Service;

    @Test
    @DisplayName("Presigned URL 생성 테스트")
    void testCreatePresignedUrl() throws Exception {
        // given
        String path = "test-path";
        URL mockUrl = new URL("https://s3.amazonaws.com/test-bucket/test-path");

        // Mock the behavior of presignPutObject() to return a PresignedPutObjectRequest
        PresignedPutObjectRequest mockPresignedPutObjectRequest = mock(PresignedPutObjectRequest.class);
        when(s3Presigner.presignPutObject(any(PutObjectPresignRequest.class))).thenReturn(mockPresignedPutObjectRequest);

        // Mock the url() method to return a mock URL
        when(mockPresignedPutObjectRequest.url()).thenReturn(mockUrl);

        // when
        String presignedUrl = s3Service.createPresignedUrl(path);

        // then
        assertNotNull(presignedUrl); // Ensure the URL is not null
        assertEquals(mockUrl.toString(), presignedUrl); // Check if the returned URL matches the mock URL
        verify(s3Presigner, times(1)).presignPutObject(any(PutObjectPresignRequest.class)); // Verify the mock method is called
    }
}