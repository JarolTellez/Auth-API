package com.jarol.auth.auth_api.service;



public interface IEmailService {

    public void sendEmail(String toEmail, String verificationLink);
}
