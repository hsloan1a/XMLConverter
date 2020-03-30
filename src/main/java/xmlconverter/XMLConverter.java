package xmlconverter;


public class XMLConverter {
    public static void main(String[] args) {
        String url = "https://sxm-dataservices-samples.s3.amazonaws.com/incidents.xml.gz";
        if (args.length == 1) {
            url = args[0];
        }
        try {
            GetXmlData getXmlData = new GetXmlData(url);
            System.out.println(getXmlData.getJson());
        } catch(Exception ex){
            ex.printStackTrace();
        }

    }
}

