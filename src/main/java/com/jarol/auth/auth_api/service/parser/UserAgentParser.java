package com.jarol.auth.auth_api.service.parser;

import com.jarol.auth.auth_api.model.enums.Browser;
import com.jarol.auth.auth_api.model.enums.DeviceType;
import com.jarol.auth.auth_api.model.enums.OS;
import com.jarol.auth.auth_api.model.valueObject.SessionMetadata;
import org.springframework.stereotype.Component;

@Component
public class UserAgentParser {

    public SessionMetadata parse(String userAgent, String ipAddress){
        if(userAgent==null || userAgent.isBlank()){
            if (ipAddress == null) {
                ipAddress = "UNKNOWN";
            }
            return new SessionMetadata(ipAddress, "UNKNOWN",
                    DeviceType.UNKNOWN,
                    OS.UNKNOWN,
                    Browser.UNKNOWN);
        }

        String userAgentLowerCase = userAgent.toLowerCase();

        DeviceType deviceType = detectDeviceType(userAgentLowerCase);
        OS os = detectOs(userAgentLowerCase);
        Browser browser = detectBrowser(userAgentLowerCase);

        return new SessionMetadata(
                ipAddress,
                userAgent,
                deviceType,
                os,
                browser
        );
    }

    private DeviceType detectDeviceType(String userAgent){
        if(userAgent.contains("bot")||userAgent.contains("crawler")||userAgent.contains("spider")){
            return DeviceType.BOT;
        }

        if(userAgent.contains("ipad")||userAgent.contains("tablet")){
            return DeviceType.TABLET;
        }

        if(userAgent.contains("mobi")||userAgent.contains("iphone")||userAgent.contains("android")){
            return DeviceType.MOBILE;
        }

        if(userAgent.contains("windows")|| userAgent.contains("macintosh")||userAgent.contains("linux")||userAgent.contains("x11")){
            return DeviceType.DESKTOP;
        }
        return DeviceType.UNKNOWN;
    }

    private OS detectOs(String userAgent){
        if(userAgent.contains("windows")) return OS.WINDOWS;
        if(userAgent.contains("mac os") || userAgent.contains("macintosh")) return OS.MACOS;
        if(userAgent.contains("android"))return OS.ANDROID;
        if(userAgent.contains("iphone")|| userAgent.contains("ios"))return OS.IOS;
        if(userAgent.contains("linux")) return OS.LINUX;

        return OS.UNKNOWN;
    }

    private Browser detectBrowser(String userAgent){
        if(userAgent.contains("edg")) return Browser.EDGE;
        if(userAgent.contains("chrome")) return Browser.CHROME;
        if(userAgent.contains("firefox"))return Browser.FIREFOX;
        if(userAgent.contains("safari"))return Browser.SAFARI;

        return Browser.UNKNOWN;
    }
}
