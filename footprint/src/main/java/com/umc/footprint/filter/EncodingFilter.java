package com.umc.footprint.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.umc.footprint.config.EncryptProperties;
import com.umc.footprint.utils.AES128;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EncodingFilter implements Filter{

    private static final Logger logger = LoggerFactory.getLogger(DecodingFilter.class);
    private final EncryptProperties encryptProperties;

    public EncodingFilter(EncryptProperties encryptProperties){
        this.encryptProperties = encryptProperties;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        try{
            ResponseBodyEncryptWrapper responseWrapper = new ResponseBodyEncryptWrapper(res);

            chain.doFilter(request, responseWrapper);

            // encode response body
            String url = req.getServletPath();
            if(!(url.equals("/walks/check/encrypt")) && !(url.equals("/walks/check/decrypt"))){
                String responseMessage = new String(responseWrapper.getDataStream(), StandardCharsets.UTF_8);
                String encodedResponse = new AES128(encryptProperties.getKey()).encrypt(responseMessage);
                response.getOutputStream().write(encodedResponse.getBytes());
            }else{
                response.getOutputStream().write(responseWrapper.getDataStream());
            }

        } catch (Exception exception){
            logger.error("디코딩이 불가합니다.");
        }

    }

    @Override
    public void destroy() {
        logger.info("End Decoding");
        Filter.super.destroy();
    }

}
