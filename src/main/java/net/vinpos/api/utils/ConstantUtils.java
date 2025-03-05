package net.vinpos.api.utils;

import net.vinpos.api.enums.QrSourceType;

import java.util.Map;

public class ConstantUtils {
  public static final Map<QrSourceType, Integer> UNIQODE_DYNAMIC_QR_MAP =
      Map.of(
          QrSourceType.WEBSITE, 1,
          QrSourceType.SMS, 16,
          QrSourceType.VCARD, 7,
          QrSourceType.EMAIL, 17,
          QrSourceType.PHONE, 15);
  public static final Map<QrSourceType, Integer> UNIQODE_STATIC_QR_MAP =
      Map.of(
          QrSourceType.WEBSITE, 1,
          QrSourceType.SMS, 2,
          QrSourceType.PHONE, 3,
          QrSourceType.EMAIL, 4,
          QrSourceType.VCARD, 5,
          QrSourceType.TEXT, 6,
          QrSourceType.WIFI, 7);
}
