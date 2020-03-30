package xmlconverter;

import org.json.JSONObject;
import org.json.XML;

import java.io.*;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class GetXmlData {
    static int MAX_DATA = 1024;
    static String downloadDirectory = "download";
    String url;
    String filename;

    public GetXmlData(String url) {
        this.url = url;
    }


    public String getJson()throws Exception {
        CreateDirectory();
        DownloadFile();
        return UnzipFileToReadString();
    }

    private void CreateDirectory() {
        File dir = new File(downloadDirectory);
        if(!dir.exists()) dir.mkdirs();
    }

    private String UnzipFileToReadString() throws Exception {
        FileInputStream fis = new FileInputStream(downloadDirectory + File.separator + this.filename);
        GZIPInputStream gZIPInputStream = new GZIPInputStream(fis);
        InputStreamReader reader = new InputStreamReader(gZIPInputStream);
        BufferedReader in = new BufferedReader(reader);
        String reading;
        StringBuilder stringBuilder = new StringBuilder();
        JSONObject xmlJSONObj;

        while ((reading = in.readLine()) != null) {
            stringBuilder.append(reading);
        }

        gZIPInputStream.close();

        xmlJSONObj = XML.toJSONObject(stringBuilder.toString());
        return xmlJSONObj.toString(5);

    }

    private void DownloadFile() throws Exception {
        URL url = new URL(this.url);
        BufferedInputStream in = new BufferedInputStream(url.openStream());
        byte dataBuffer[] = new byte[MAX_DATA];
        int bytesRead;

        this.filename = url.getFile().substring(1);
        FileOutputStream fileOutputStream = new FileOutputStream(
                downloadDirectory + File.separator + this.filename);
        while ((bytesRead = in.read(dataBuffer, 0, MAX_DATA)) != -1) {
            fileOutputStream.write(dataBuffer, 0, bytesRead);
        }
    }

}
