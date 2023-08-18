package carrot.app.Profile;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    // 이미지 처리 메서드
    public byte[] processImage(MultipartFile file) throws IOException {
        // 이미지 처리 로직을 여기에 구현하면 됩니다.
        // MultipartFile에서 바이트 배열로 이미지 데이터를 변환하는 등의 작업을 수행합니다.
        if (file != null) {
            return file.getBytes();
        } else {
            throw new IllegalArgumentException("Image file is null.");
        }
    }

}