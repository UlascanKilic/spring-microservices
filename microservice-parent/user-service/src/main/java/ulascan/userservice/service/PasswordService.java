package ulascan.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ulascan.userservice.dto.EmailDTO;
import ulascan.userservice.dto.ResetPasswordDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordService {
    public void forgotPassword(EmailDTO email) {
        //kullanıcıyı bul
        //random kod üret

        //password code'u setle
        //mail yolla
    }

    public void resetPassword(ResetPasswordDTO dto){
        //kullanıcıyı bul
        //reset code'ları doğrula

        //kullancııyı kaydet
    }

}
