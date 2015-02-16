package org.putput.common;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UuidService {
  public String uuid() {
    return UUID.randomUUID().toString();
  }
}
