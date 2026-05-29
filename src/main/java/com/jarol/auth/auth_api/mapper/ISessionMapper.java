package com.jarol.auth.auth_api.mapper;

import com.jarol.auth.auth_api.dto.response.SessionResponse;
import com.jarol.auth.auth_api.model.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ISessionMapper {

    @Mapping(
            target = "current",
            expression = "java(session.getId().equals(currentSessionId))"
    )
    SessionResponse sessionToSessionResponse(Session session,  UUID currentSessionId);


    default List<SessionResponse> sessionsToSessionsResponse(
            List<Session> sessions,
            UUID currentSessionId
    ) {

        return sessions.stream()
                .map(session -> sessionToSessionResponse(
                        session,
                        currentSessionId
                ))
                .toList();
    }
}
