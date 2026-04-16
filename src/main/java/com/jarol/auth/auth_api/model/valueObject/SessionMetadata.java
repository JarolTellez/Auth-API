package com.jarol.auth.auth_api.model.valueObject;

import com.jarol.auth.auth_api.model.enums.Browser;
import com.jarol.auth.auth_api.model.enums.DeviceType;
import com.jarol.auth.auth_api.model.enums.OS;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionMetadata {
    private final String ipAddress;
    private final String userAgent;
    private final DeviceType deviceType;
    private final OS os;
    private final Browser browser;
}

