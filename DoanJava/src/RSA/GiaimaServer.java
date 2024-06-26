/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class GiaimaServer {
    private static final Gson gson = new Gson();

    public static String giaiMaDuLieu(String duLieuDaMaHoa) throws Exception {
        // Parse JSON string thành JsonObject
        JsonObject jsonObject = gson.fromJson(duLieuDaMaHoa, JsonObject.class);

        // Lấy dữ liệu đã mã hóa và khóa bí mật từ JsonObject
        String duLieuDaMaHoaBase64 = jsonObject.get("dulieudamahoa").getAsString();
        String khoaBiMatBase64 = jsonObject.get("khoabimat").getAsString();

        // Chuyển đổi chuỗi Base64 thành byte array
        byte[] duLieuDaMaHoaBytes = Base64.getDecoder().decode(duLieuDaMaHoaBase64);
        byte[] khoaBiMatBytes = Base64.getDecoder().decode(khoaBiMatBase64);

        // Tạo lại SecretKey từ byte array
        SecretKey khoaBiMat = new SecretKeySpec(khoaBiMatBytes, "AES");

        // Giải mã dữ liệu
        Cipher giaiMa = Cipher.getInstance("AES");
        giaiMa.init(Cipher.DECRYPT_MODE, khoaBiMat);
        byte[] duLieuGoc = giaiMa.doFinal(duLieuDaMaHoaBytes);

        // Chuyển đổi byte array thành chuỗi và trả về
        return new String(duLieuGoc);
    }
}
