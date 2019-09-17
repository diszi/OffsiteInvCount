package d2.hu.offsiteinvcount.util;

public class HttpCall {


    public static final int GET = 1;
    public static final int POST = 2;

    public enum RequestMethod{
        GET("GET"), POST("POST"),PUT("PUT");

        private final String requestMethod;

        RequestMethod(String requestMethod){
            this.requestMethod = requestMethod;
        }

        public String getValue(){
            return requestMethod;
        }
    }

    private String url;
    private int methodtype;
    //private HashMap<String,String> params ;
    private RequestMethod method;
    private String params;



    private HttpRequestAsyncTask t = new HttpRequestAsyncTask();


    public HttpCall(){}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMethodtype() {
        return methodtype;
    }

    public void setMethodtype(int methodtype) {
        this.methodtype = methodtype;
    }

    public void setMethod(RequestMethod r){
        method = r;
    }

    public RequestMethod getMethod() {
        return method;
    }

//    public HashMap<String, String> getParams() {
//        return params;
//    }
//
//    public void setParams(HashMap<String, String> params) {
//        this.params = params;
//    }


    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public HttpRequestAsyncTask get() {
        return t;
    }




}
