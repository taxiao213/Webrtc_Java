package com.demo;

import java.util.Locale;

/**
 * 会话描述 实体
 * Created by taxiao on 2019/8/14
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 * 微信公众号:他晓
 */
public class SessionDescription {
    public final SessionDescription.Type type;
    public final String description;

    public SessionDescription(SessionDescription.Type type, String description) {
        this.type = type;
        this.description = description;
    }

    String getDescription() {
        return this.description;
    }

    String getTypeInCanonicalForm() {
        return this.type.canonicalForm();
    }

    public static enum Type {
        OFFER,
        PRANSWER,
        ANSWER;

        private Type() {
        }

        public String canonicalForm() {
            return this.name().toLowerCase(Locale.US);
        }

        public static SessionDescription.Type fromCanonicalForm(String canonical) {
            return (SessionDescription.Type) valueOf(SessionDescription.Type.class, canonical.toUpperCase(Locale.US));
        }
    }
}
