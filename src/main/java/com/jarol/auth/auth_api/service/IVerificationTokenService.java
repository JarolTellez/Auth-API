package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.model.User;
import com.jarol.auth.auth_api.model.VerificationToken;


public interface IVerificationTokenService {

  String createOrUpdateToken(User user);

  VerificationToken validateAndGetToken(String token);

  void deleteToken(VerificationToken token);

}
