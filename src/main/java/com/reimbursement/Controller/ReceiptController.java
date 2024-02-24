package com.reimbursement.Controller;

import java.io.IOException;
import java.util.Properties;
import io.javalin.http.UploadedFile;
import io.javalin.util.FileUtil;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimbursement.DAO.ReceiptDao;


public class ReceiptController {

    private static Properties prop = new Properties();
    private static String dir = "";

    static {
        try {
            prop.load(ReceiptController.class.getClassLoader().getResourceAsStream("db.properties"));
            dir = prop.getProperty("dir");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int saveReceipt(int ticket_id, UploadedFile img) {
        String fileName = String.format("%09x", ticket_id) + ".jpg";
        String filePath = dir + "receipts/" + fileName;
        FileUtil.streamToFile(img.content(), filePath);
        int updated = ReceiptDao.saveFile(ticket_id, fileName);
        if (updated == 1) {
            return 1;
        }
        return 0;
    }

    public static byte[] getImageData(byte[] jsonData) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        int ticket_id = 0;
        try {
            JsonNode rootNode = objectMapper.readTree(jsonData);
            JsonNode idNode = rootNode.path("ticket_id");
            ticket_id = idNode.asInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = ReceiptDao.getFileName(ticket_id);
        if (fileName.equals("")) {
            return new byte[0];
        }
        BufferedImage  bImage = ImageIO.read(new File(dir + "receipts/" + fileName));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos);
        byte[] imageData = bos.toByteArray();
        System.out.println(imageData);
        return imageData;
    }
}
