package software.ulpgc.kata6;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class SpringRequestAdapter {
    private final HttpServletRequest request;


    public SpringRequestAdapter(HttpServletRequest request) {
        this.request = request;
    }


    public Optional<String> getQueryParam(String param){
        return Optional.ofNullable(request.getParameter(param));
    }

    public boolean getBooleanQueryParam(String param, boolean defaultValue){
        String value = request.getParameter(param);
        return value == null ? defaultValue : "true".equalsIgnoreCase(value) || "1".equals(value);
    }
}
