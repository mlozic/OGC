package com.example.secret_store.security;

public class Constants {
  public static final String SECRET_KEY = "bQeThWmZq4t7w!z$C&F)J@NcRfUjXn2r5u8x/A?D*G-KaPdSgVkYp3s6v9y$B&E)";
  public static final int TOKEN_EXPIRATION = 7200000; // 2 hours
  public static final String BEARER = "Bearer ";
  public static final String AUTHORIZATION = "Authorization";
  public static final String REGISTER_PATH = "/user/register";
  public static final String MANAGEMENT_PATH = "/password/management/**";
  public static final String DEVOPS_PATH = "/password/devops/**";
  public static final String DEVELOPER_PATH = "/password/developer/**";
  public static final String ROLE_DEVELOPER = "DEVELOPER";
  public static final String ROLE_DEVOPS = "DEV_OPS";
  public static final String ROLE_MANAGEMENT = "MANAGEMENT";
  public static final String SINGLE = "single";
  public static final String ALL = "all";
}
