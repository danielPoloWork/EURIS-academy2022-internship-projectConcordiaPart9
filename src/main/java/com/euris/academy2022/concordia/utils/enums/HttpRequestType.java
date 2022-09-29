package com.danielpolo.teslaBattery.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HttpRequestType {
  GET("GET"),
  POST("POST"),
  PUT("PUT"),
  PATCH("PATCH"),
  DELETE("DELETE"),
  COPY("COPY"),
  HEAD("HEAD"),
  OPTIONS("OPTIONS"),
  LINK("LINK"),
  UNLINK("UNLINK"),
  PURGE("PURGE"),
  LOCK("LOCK"),
  UNLOCK("UNLOCK"),
  PROPFIND("PROPFIND"),
  VIEW("VIEW");

  private final String label;
}
