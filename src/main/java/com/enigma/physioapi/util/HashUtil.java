package com.enigma.physioapi.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

  public static String encryptThisString(String input) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      byte[] messageDigest = md.digest(input.getBytes());
      BigInteger no = new BigInteger(1, messageDigest);
      StringBuilder hashText = new StringBuilder(no.toString(16));
      while (hashText.length() < 32) {
        hashText.insert(0, "0");
      }
      return hashText.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }

  }
}

