package by.runets.hedgehog.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final String EMAIL_SENDER = "emailSender";
    public static final String MOBILE_SENDER = "mobileSender";

    public static final String TEMPLATE_SERVICE_URL_KEY = "${template.service.url}";

    public static final String GOTIFY_SERVICE_URL_KEY = "${gotify.service.url}";

    public static final String MAIL_SUBJECT_KEY = "${mailhog.service.subject}";
    public static final String MAIL_FROM_KEY = "${mailhog.service.from}";

    public static final String KAFKA_ADDRESS_KEY = "${kafka.address}";
    public static final String KAFKA_GROUP_KEY = "${kafka.group}";
    public static final String KAFKA_TOPIC_KEY = "${kafka.topic}";

    public static final String MOBILE_CONFIRMATION = "mobile_confirmation";
    public static final String EMAIL_CONFIRMATION = "email_confirmation";

    public static final String MOBILE_VERIFICATION = "mobile-verification";
    public static final String EMAIL_VERIFICATION = "email-verification";

    public static final String CODE = "code";

    public static final Map<String, String> CONFIRMATION_MAP = new HashMap<>();
    public static final Map<String, String> VERIFICATION_MAP = new HashMap<>();

    static {
        CONFIRMATION_MAP.put(MOBILE_CONFIRMATION, MOBILE_VERIFICATION);
        CONFIRMATION_MAP.put(EMAIL_CONFIRMATION, EMAIL_VERIFICATION);

        VERIFICATION_MAP.put(MOBILE_VERIFICATION, MOBILE_CONFIRMATION);
        VERIFICATION_MAP.put(EMAIL_VERIFICATION, EMAIL_CONFIRMATION);
    }
}
